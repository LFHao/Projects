package model;

import java.util.ArrayList;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databeans.Fund;

public class FundDAO extends GenericDAO<Fund> {
	public FundDAO(ConnectionPool pool, String tableName) throws DAOException {
		super(Fund.class, tableName, pool);
	}
	
	public Fund readBySymbol (String symbol) {		
		Fund[] tmp = null;
		try {
			tmp = match(MatchArg.equals("symbol",symbol));
		} catch (RollbackException e) {
			e.printStackTrace();
			if (Transaction.isActive())
				Transaction.rollback();
		}
		if (tmp == null || tmp.length == 0)
			return null;
		else
			return tmp[0];
	}
			
			
	public Fund readByName(String name) {
		Fund[] tmp = null;
		try {
			tmp = match(MatchArg.equals("name", name));
		} catch (RollbackException e) {
			e.printStackTrace();
			if (Transaction.isActive())
				Transaction.rollback();
		}
		if (tmp == null || tmp.length == 0)
			return null;
		else
			return tmp[0];
	}

	
	public ArrayList<Fund> getAll() {
		Fund[] tmp = null;
		ArrayList<Fund> arrFund = new ArrayList<Fund>();
		
		try {
			tmp = match();
		} catch (RollbackException e) {
			e.printStackTrace();
			if (Transaction.isActive())
				Transaction.rollback();
		}
		
		if (tmp == null || tmp.length == 0)
			return arrFund;
		for(Fund x : tmp){
			arrFund.add(x);
		}
		return arrFund;
	}

}