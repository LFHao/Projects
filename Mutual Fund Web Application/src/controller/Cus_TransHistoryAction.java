package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.MatchArg;

import databeans.Customer;
import databeans.Fund;
import databeans.Transaction;
import model.CustomerDAO;
import model.FundDAO;
import model.Model;
import model.TransDAO;

public class Cus_TransHistoryAction extends Action{
	private TransDAO transactionDAO;
	private CustomerDAO customerDAO;
	private FundDAO fundDAO;
	
    public Cus_TransHistoryAction(Model model) {
        transactionDAO = model.getTransDAO();
        customerDAO = model.getCustomerDAO();
        fundDAO = model.getFundDAO();
    }
    
	public String getName() {
		return "cus_transHistory.do";
	}
	
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		try {
		    Customer customer = (Customer) request.getSession(false).getAttribute("customer");   
		   
            if(customer == null) {
                return "cus-login.jsp";
            }
            
            customer = customerDAO.readByName(customer.getUsername());
            
            Transaction[] trans = transactionDAO.match(MatchArg.equals("customer_id", customer.getCustomer_id()));
            Fund[] funds = new Fund[trans.length];
            
            for(int i=0; i<funds.length; i++){
            	funds[i] = fundDAO.read(trans[i].getFund_id());
            }

            request.setAttribute("trans", trans);
            request.setAttribute("funds", funds);

	        return "cus-trans-history.jsp";
		} catch (Exception e) {
			if (org.genericdao.Transaction.isActive())
				org.genericdao.Transaction.rollback();
			errors.add(e.toString());
	      	return "cus-trans-history.jsp";
	    } 
	}
}
