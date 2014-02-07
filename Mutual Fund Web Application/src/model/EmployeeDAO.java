package model;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;
import org.genericdao.GenericDAO;
import org.genericdao.MatchArg;
import org.genericdao.RollbackException;
import org.genericdao.Transaction;

import databeans.Customer;
import databeans.Employee;

public class EmployeeDAO extends GenericDAO<Employee>{
	public EmployeeDAO(ConnectionPool pool, String tableName) throws DAOException {
		super(Employee.class, tableName, pool);
	}
	
	public Employee readByName(String name) {
		Employee[] tmp = null;
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
	
	public void setPassword(String username, String password){
        try {
			Employee dbEmployee = read(username);
			
			if (dbEmployee == null) {
				throw new RollbackException("Username "+username+" no longer exists");
			}
			
			dbEmployee.setPassword(password);			
			update(dbEmployee);
		} catch (RollbackException e) {
			e.printStackTrace();
			if (Transaction.isActive())
				Transaction.rollback();
		}
	}
}
