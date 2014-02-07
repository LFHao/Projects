package controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mybeans.form.FormBeanFactory;

import utility.AmountCheck;
import databeans.*;
import model.*;
import formbeans.Cus_SellFundForm;

public class Cus_SellFundAction extends Action{
	private FormBeanFactory<Cus_SellFundForm> formBeanFactory = FormBeanFactory
			.getInstance(Cus_SellFundForm.class);
	
	private TransDAO transactionDAO;
	private PosDAO positionDAO;
	private CustomerDAO customerDAO;
	private FundDAO fundDAO;
	private FundHistDAO histDAO;
	public Cus_SellFundAction(Model model) {
		transactionDAO = model.getTransDAO();
		positionDAO = model.getPosDAO();
		customerDAO = model.getCustomerDAO();
		fundDAO = model.getFundDAO();
		histDAO = model.getFundHistDAO();
	}
	public String getName() {
		return "cus_sellFund.do";
	}
	
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		try {
			Customer customer = (Customer) request.getSession(false).getAttribute("customer"); 
	        if(customer == null)
	            return "cus-login.jsp";
	        
	        //Bulid the funds list
			Position[] lpos =  positionDAO.readByCustomerID(customer.getCustomer_id());

            if(lpos != null && lpos.length!=0){
	            ArrayList<Fund> funds = new ArrayList<Fund>();
	            ArrayList<Long> lprices = new ArrayList<Long>();
	            
	            for(Position x : lpos){
	            	funds.add(fundDAO.read(x.getFund_id()));
	            	if(histDAO.getPrice(x.getFund_id())==null)
	            		lprices.add(-1L);
	            	else
	            		lprices.add(histDAO.getPrice(x.getFund_id()).getPrice());
	            }           
				request.setAttribute("funds", funds);
				request.setAttribute("prices", lprices);
				request.setAttribute("pos", lpos);
            }        
            
            //Get value from input
	        Cus_SellFundForm form = formBeanFactory.create(request);
	        request.setAttribute("form", form);
	        
            if (!form.isPresent())
            	return "cus-sell-fund.jsp";
            
            errors.addAll(form.getValidationErrors());
            
            if (errors.size() > 0){
            	 
            	return "cus-sell-fund.jsp";
            }
            
            String symbol = form.getFundSymbol();  
            Fund fund = fundDAO.readBySymbol(symbol);
            if (fund == null) {
            	errors.add("Fund symbol [" + symbol + "] does not exist.");
            	return "cus-sell-fund.jsp";
            }
            long shares = AmountCheck.checkShareString(form.getShares());
            
            
            Customer updatedCus = customerDAO.readByName(customer.getUsername());
            if (updatedCus == null)
            	return "cus-login.jsp";
            
            org.genericdao.Transaction.begin();
            Position pos = positionDAO.getShares(customer.getCustomer_id(), fund.getFund_id());            
            if (pos == null || shares > pos.getShares()) {
            	errors.add("You do not have enough share of fund " + fund.getName() + ".");       	
            	if (org.genericdao.Transaction.isActive())
            		org.genericdao.Transaction.rollback();
            	return "cus-sell-fund.jsp";
            }
            
            // Position table is updated.
            if (pos.getShares() - shares > 0) {
            	pos.setShares(pos.getShares() - shares);
            	positionDAO.update(pos);    
            } else 
            	positionDAO.delete(pos.getPosition_id());
            
            // Transaction table is updated.
            Transaction trans = new Transaction();
            trans.setCustomer_id(updatedCus.getCustomer_id());
            trans.setExecute_date(null);
            trans.setFund_id(fund.getFund_id());
            trans.setShares(shares);
            trans.setStatus("PENDING");
            trans.setTransaction_type("SELL");
            transactionDAO.createAutoIncrement(trans);
            if (org.genericdao.Transaction.isActive())
            	org.genericdao.Transaction.commit();

            DecimalFormat nf = new DecimalFormat("###,###,###,###,##0.000");
            nf.setMaximumFractionDigits(3);
           	nf.setMinimumFractionDigits(3);

            System.out.println("The customer =>"+customer.getUsername()+" just sold the fund=> "+fund.getName());

            request.setAttribute("message", "You have successfully sold " + nf.format(shares / 1000.0) + " shares of fund " + fund.getName() + ".");
            
	        return "cus-success.jsp";

	    } catch (Exception e) {
	    	if (org.genericdao.Transaction.isActive())
	    		org.genericdao.Transaction.rollback();
	    	errors.add(e.toString());
	    	return "cus-sell-fund.jsp";
	    }
	}
}
