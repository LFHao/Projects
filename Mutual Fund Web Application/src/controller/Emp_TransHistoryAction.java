package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.MatchArg;

import databeans.Customer;
import databeans.Employee;
import databeans.Fund;
import databeans.Transaction;
import formbeans.Emp_ResetPwdForm;
import model.CustomerDAO;
import model.FundDAO;
import model.Model;
import model.TransDAO;

public class Emp_TransHistoryAction extends Action{
	private TransDAO transactionDAO;
	private CustomerDAO customerDAO;
	private FundDAO fundDAO;
	
    public Emp_TransHistoryAction(Model model) {
        transactionDAO = model.getTransDAO();
        customerDAO = model.getCustomerDAO();
        fundDAO = model.getFundDAO();
    }
    
	public String getName() {
		return "emp_transHistory.do";
	}
	
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		try {
			Customer customer = null;
		    Employee employee = (Employee) request.getSession(false).getAttribute("employee");
		    
            if(employee == null) {
                return "emp-login.do";
            }
            
            String id = request.getParameter("cusid");
            if (id != null) {
            	customer = customerDAO.read(Integer.parseInt(id));
            	request.setAttribute("customer", customer);
            } else {
            	errors.add("The customer does not exist.");
            	return "emp-cus-trans-history.jsp";
            }
            
            customer = customerDAO.read(Integer.parseInt(id));
            if (customer == null) {
            	errors.add("The customer does not exist.");
            	return "emp-cus-trans-history.jsp";
            }
            
            Transaction[] trans = transactionDAO.match(MatchArg.equals("customer_id", customer.getCustomer_id()));

            Fund[] funds = new Fund[trans.length];
            
            for(int i=0; i<funds.length; i++){
            	funds[i] = fundDAO.read(trans[i].getFund_id());
            }

            request.setAttribute("customer", customer);
            request.setAttribute("trans", trans);
            request.setAttribute("funds", funds);
            
	        return "emp-cus-trans-history.jsp";
		} catch (Exception e) {
			if (org.genericdao.Transaction.isActive())
        		org.genericdao.Transaction.rollback();
			errors.add(e.toString());
			return "emp-cus-trans-history.jsp";
		} 
	}
}
