package model;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.genericdao.ConnectionPool;
import org.genericdao.DAOException;


public class Model {
	private CustomerDAO 		customerDAO;
	private FundDAO 			fundDAO;
	private EmployeeDAO			employeeDAO;
	private FundHistDAO			fundHistDAO;
	private TransDAO			transDAO;
	private PosDAO				posDAO;
	private TransDateDAO		dateDAO;
	
	

	public Model(ServletConfig config) throws ServletException {
		String jdbcDriver = config.getInitParameter("jdbcDriverName");
		String jdbcURL    = config.getInitParameter("jdbcURL");
		System.out.println("Debug: " + jdbcDriver + " " + jdbcURL);
		
		ConnectionPool pool = new ConnectionPool(jdbcDriver,jdbcURL);
		
		try {
			customerDAO = new CustomerDAO(pool, "Customer");
			fundDAO = new FundDAO(pool, "Fund");
			employeeDAO = new EmployeeDAO(pool,"Employee");
			fundHistDAO = new FundHistDAO(pool, "FundHistory");
			posDAO = new PosDAO(pool,"Position");
			dateDAO = new TransDateDAO(pool, "TransitionDate");
			transDAO = new TransDAO(pool,"Transaction", customerDAO, fundHistDAO, posDAO, dateDAO);
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public CustomerDAO getCustomerDAO() { return customerDAO; }
	public FundDAO getFundDAO() { return fundDAO; }
	public EmployeeDAO getEmployeeDAO() { return employeeDAO; }
	public FundHistDAO getFundHistDAO() { return fundHistDAO; }
	public TransDAO getTransDAO() { return transDAO; }
	public PosDAO getPosDAO() { return posDAO; }
	public TransDateDAO getDateDAO() { return dateDAO; }

}