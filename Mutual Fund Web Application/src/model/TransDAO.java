package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;

import databeans.Customer;
import databeans.FundPriceHistory;
import databeans.Position;
import databeans.Transaction;
import databeans.TransitionDate;
import formbeans.Emp_TransitionDayForm;

public class TransDAO extends GenericDAO<Transaction> {
	CustomerDAO customerDAO;
	FundHistDAO histDAO;
	PosDAO posDAO;
	TransDateDAO dateDAO;
	
	public TransDAO(ConnectionPool pool, String tableName, CustomerDAO cdao, FundHistDAO hdao, PosDAO pdao, TransDateDAO tdao) throws DAOException {
		super(Transaction.class, tableName, pool);
		customerDAO = cdao;
		histDAO = hdao;
		posDAO = pdao;
		dateDAO = tdao;
	}
	
	public Date lastTradingDay(int cus_id) {
		Transaction[] trans = null;
		try {
			trans = match(MatchArg.and(MatchArg.equals("customer_id", cus_id), MatchArg.max("execute_date")));
		} catch (RollbackException e) {
			e.printStackTrace();
			if (org.genericdao.Transaction.isActive())
				org.genericdao.Transaction.rollback();
		}
		if (trans == null || trans.length == 0)
			return null;
		return trans[0].getExecute_date();
	}
	
	public void clearPending(Date date) throws RollbackException {			
		// Only handle pending BUYs and SELLs
		Transaction[] pending = match(MatchArg.equals("status", "PENDING"));
		System.out.println("Handling all pending transactions.");
		for (Transaction t : pending) {
			if (t.getTransaction_type().equals("SELL")) {
				Customer user = customerDAO.read(t.getCustomer_id());
				if (user == null)
					continue;
				FundPriceHistory price = histDAO.getPrice(t.getFund_id());
				if (price == null)
					continue;
					
				// Update the cash amount.
				long sellAmount = Math.round(price.getPrice() * (t.getShares() / 1000.0));
				t.setAmount(sellAmount);
				user.setCash(user.getCash() + sellAmount);
				customerDAO.update(user);
			} else if (t.getTransaction_type().equals("BUY")){
				FundPriceHistory price = histDAO.getPrice(t.getFund_id());
				if (price == null)
					continue;
				
				// Update the position table.
				Position pos = posDAO.getShares(t.getCustomer_id(), t.getFund_id());
				long buyShares = Math.round(t.getAmount() * 1000.0 / price.getPrice());
				if (pos == null) {
					pos = new Position();
					pos.setCustomer_id(t.getCustomer_id());
					pos.setFund_id(t.getFund_id());
					pos.setShares(buyShares);
					posDAO.createAutoIncrement(pos);
					}
				else {
					pos.setShares(pos.getShares() + buyShares);
					posDAO.update(pos);
				}
				t.setShares(buyShares);		
			} else if (t.getTransaction_type().equals("DEPOSIT")) {
				Customer user = customerDAO.read(t.getCustomer_id());
				if (user == null)
					continue;
					
				// Update the cash amount.
				long amount = t.getAmount();
				user.setCash(user.getCash() + amount);
				customerDAO.update(user);
			}
			t.setExecute_date(date);
			t.setStatus("APPROVED");
			update(t);
		}
	}
	
	public List<String> transitionDay(Emp_TransitionDayForm form) {
		ArrayList<String> errors = new ArrayList<String>();
		try {
			boolean inTransaction = org.genericdao.Transaction.isActive();
        	org.genericdao.Transaction.begin();
        	
			Date lastDate = dateDAO.getLastTransitionDay();
			if (lastDate != null && (lastDate.after(form.date) || lastDate.equals(form.date))) {
				errors.add("New transition date should be after last transition date.");
				org.genericdao.Transaction.commit();
			}
			
			// Update all prices.
			histDAO.updateAll(form.prices, form.date);
			
			// Handle all pending transactions. 
			clearPending(form.date);
			
			// Add an new transition date.
			TransitionDate newDate = new TransitionDate();
			newDate.setDate(form.date);
			dateDAO.createAutoIncrement(newDate);
			if (!inTransaction)
				org.genericdao.Transaction.commit();
			System.out.println("New transition day: " + form.date);
		} catch (RollbackException e) {
			if (org.genericdao.Transaction.isActive())
				org.genericdao.Transaction.rollback();
			errors.add("Error happened when processing transition day, the database rollbacked.");
		}
		return errors;
	}
}
