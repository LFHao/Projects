package controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import databeans.Customer;
import databeans.Fund;
import databeans.Position;
import model.CustomerDAO;
import model.FundDAO;
import model.FundHistDAO;
import model.Model;
import model.PosDAO;
import model.TransDAO;

public class Cus_ViewAccountAction extends Action {
	private CustomerDAO customerDAO;
	private FundDAO	fundDAO;
	private FundHistDAO fundHistDAO;
	private PosDAO positionDAO;
	private TransDAO transactionDAO;
	
	public Cus_ViewAccountAction(Model model) {
		customerDAO = model.getCustomerDAO();
		fundDAO = model.getFundDAO();
		fundHistDAO = model.getFundHistDAO();
		positionDAO = model.getPosDAO();
		transactionDAO = model.getTransDAO();
	}
	
	public String getName() {
		return "cus_viewAccount.do";
	}
	
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		try {
		    Customer customer = (Customer) request.getSession(true).getAttribute("customer");   
		    
            if(customer == null) {
                return "cus-login.jsp";
            }

            customer = customerDAO.readByName(customer.getUsername());
            Date lastDate = transactionDAO.lastTradingDay(customer.getCustomer_id());
            request.setAttribute("lastDate",lastDate);
            request.getSession(true).setAttribute("customer", customer);
          
            
            Position[] pos =  positionDAO.readByCustomerID(customer.getCustomer_id());         
            
            if(pos != null && pos.length!=0){
	            ArrayList<Fund> funds = new ArrayList<Fund>();
	            ArrayList<Long> prices = new ArrayList<Long>();
	            
	            for(Position x:pos){
	            	funds.add(fundDAO.read(x.getFund_id()));
	            	if(fundHistDAO.getPrice(x.getFund_id())==null)
	            		prices.add(-1L);
	            	else
	            		prices.add(fundHistDAO.getPrice(x.getFund_id()).getPrice());
	            }
	
	            
				request.setAttribute("funds",funds);
				request.setAttribute("prices",prices);
				request.setAttribute("pos",pos);
            }            

	        return "cus-view-account.jsp";
		} catch (Exception e) {
			if (org.genericdao.Transaction.isActive())
				org.genericdao.Transaction.rollback();
			errors.add(e.toString());
			return "cus-view-account.jsp";
		} 
	}
}
