package model;


import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databeans.Customer;

public class CustomerDAO extends GenericDAO<Customer> {
	public CustomerDAO(ConnectionPool pool, String tableName) throws DAOException {
		super(Customer.class, tableName, pool);
	}
	
	public Customer readByName(String name) {
		Customer[] tmp = null;
		try {
			tmp = match(MatchArg.equals("username", name));
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
	
	public void setPassword(int userid, String password) {
        try {
			Customer dbCustomer = read(userid);
			
			if (dbCustomer == null) {
				throw new RollbackException("UserID "+userid+" no longer exists");
			}
			
			dbCustomer.setPassword(password);			
			update(dbCustomer);
		} catch (RollbackException e) {
			e.printStackTrace();
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}
	
	public void setPassword(String username, String password) {
        try {
			Customer dbCustomer = read(username);
			
			if (dbCustomer == null) {
				throw new RollbackException("Username "+username+" no longer exists");
			}
			
			dbCustomer.setPassword(password);
			
			update(dbCustomer);
		} catch (RollbackException e) {
			e.printStackTrace();
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}
	
	public void setCash(int userid, Long cash){
        try {
			Customer dbCustomer = read(userid);
			
			if (dbCustomer == null) {
				throw new RollbackException("UserID "+userid+" no longer exists");
			}
			
			dbCustomer.setCash(cash);
			
			update(dbCustomer);
		} catch (RollbackException e) {
			e.printStackTrace();
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}
}
