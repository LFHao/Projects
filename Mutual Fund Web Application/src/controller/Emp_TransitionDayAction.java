package controller;

import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;

import model.*;
import databeans.*;
import formbeans.*;

public class Emp_TransitionDayAction extends Action {
	FundDAO fundDAO;
	FundHistDAO histDAO;
	TransDAO transDAO;
	TransDateDAO dateDAO;
	Emp_TransitionDayForm transForm;
	
	public Emp_TransitionDayAction(Model model) {
		fundDAO = model.getFundDAO();
		histDAO = model.getFundHistDAO();
		transDAO = model.getTransDAO();
		dateDAO = model.getDateDAO();
	}
	
	@Override
	public String getName() {
		return "emp-transitionday.do";
	}

	@Override
	public String perform(HttpServletRequest request) {		
		ArrayList<String> errors = new ArrayList<String>();
		request.setAttribute("errors",errors);
		ArrayList<FundPriceHistory> prices = new ArrayList<FundPriceHistory>();
		request.setAttribute("prices", prices);
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<String> symbols = new ArrayList<String>();
		request.setAttribute("names", names);
		request.setAttribute("symbols", symbols );
		Date lastday = null;
		
		if (request.getSession(false).getAttribute("employee") == null)
			return "emp-login.jsp";
		
		// Get the updated price and update it.
		Fund[] funds = null;
		try {
			funds = fundDAO.match();
			lastday = dateDAO.getLastTransitionDay();
			request.setAttribute("lastday", lastday);
			
			// Initialize the fund information for displaying first.
			// Can also fresh the price if some other employee sets the new price.
			for (Fund f : funds) {
				if (histDAO.getPrice(f.getFund_id()) == null) {
					FundPriceHistory tmp = new FundPriceHistory();
					tmp.setFund_id(f.getFund_id());
					tmp.setPrice_date(new Date());
					tmp.setPrice(-1);
					prices.add(tmp);
				} else
					prices.add(histDAO.getPrice(f.getFund_id()));
				names.add(fundDAO.read(f.getFund_id()).getName());
				symbols.add(fundDAO.read(f.getFund_id()).getSymbol());		
			}			
			
			transForm = new Emp_TransitionDayForm(request);
			
			if (!transForm.isPresent())
				return "emp-transitionday.jsp";
			
			errors.addAll(transForm.getValidationErrors());
			if (errors.size() > 0)
				return "emp-transitionday.jsp";
			
			errors.addAll(transDAO.transitionDay(transForm));
			if (errors.size() > 0)
				return "emp-transitionday.jsp";
			
			lastday = dateDAO.getLastTransitionDay();
			request.setAttribute("lastday", lastday);
		} catch (RollbackException e) {
			if (org.genericdao.Transaction.isActive())
        		org.genericdao.Transaction.rollback();
			errors.add(e.getMessage());
		}		
		return "emp-transitionday.jsp";
	}

}
