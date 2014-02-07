package model;


import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databeans.Position;

public class PosDAO extends GenericDAO<Position>{
	public PosDAO(ConnectionPool pool, String tableName) throws DAOException {
		super(Position.class, tableName, pool);
	}
	
	public Position[] readByCustomerID (int id) {
		Position[] tmp = null;
		try {
			tmp = match(MatchArg.equals("customer_id", id));
		} catch (RollbackException e) {
			e.printStackTrace();
			if (Transaction.isActive())
				Transaction.rollback();
		}
		
		if (tmp == null || tmp.length == 0)
			return null;
		else
			return tmp;
	}
	
	public Position getShares(int customer_id, int fund_id) {
		Position[] tmp = null;
		try {
			tmp = match(MatchArg.and(MatchArg.equals("customer_id", customer_id), MatchArg.equals("fund_id", fund_id)));
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
}
