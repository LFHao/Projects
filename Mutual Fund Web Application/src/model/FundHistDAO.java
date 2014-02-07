package model;

import java.util.ArrayList;
import java.util.Date;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databeans.FundPriceHistory;

public class FundHistDAO extends GenericDAO<FundPriceHistory>{
	public FundHistDAO(ConnectionPool pool, String tableName) throws DAOException {
		super(FundPriceHistory.class, tableName, pool);
	}
	
	public void updateAll(ArrayList<FundPriceHistory> prices, Date date) throws RollbackException {
		for (FundPriceHistory f : prices) {
			f.setPrice_date(date);
			createAutoIncrement(f);
		}
	}
	
	public ArrayList<FundPriceHistory> getAll() {
		ArrayList<FundPriceHistory> ret = new ArrayList<FundPriceHistory>();
		FundPriceHistory[] tmp = null;
		
		try {
			tmp = match(MatchArg.max("price_date"));
		} catch (RollbackException e) {
			e.printStackTrace();
			if (Transaction.isActive())
				Transaction.rollback();
		}
		
		if (tmp == null || tmp.length == 0)
			return ret;
		for (FundPriceHistory f : tmp)
			ret.add(f);
		return ret;
	}
	
	public FundPriceHistory getPrice(int id) {
		FundPriceHistory[] tmp = null;
		try {
			tmp = match(MatchArg.and(MatchArg.max("price_date"), MatchArg.equals("fund_id", id)));
		} catch (RollbackException e) {
			e.printStackTrace();
			if (Transaction.isActive())
				Transaction.rollback();
		}
		if (tmp == null || tmp.length == 0)
			return null;
		return tmp[0];
	}
	
	public ArrayList<FundPriceHistory> getFundHist(int id) {
		ArrayList<FundPriceHistory> ret = new ArrayList<FundPriceHistory>();
		FundPriceHistory[] tmp = null;
		try {
			tmp = match(MatchArg.equals("fund_id", id));
		} catch (RollbackException e) {
			e.printStackTrace();
			if (Transaction.isActive())
				Transaction.rollback();
		}
		for (FundPriceHistory f : tmp)
			ret.add(f);
		return ret;
	}
}
