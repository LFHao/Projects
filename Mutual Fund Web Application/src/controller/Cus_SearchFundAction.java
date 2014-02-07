package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import databeans.Customer;
import databeans.Fund;
import model.FundDAO;
import model.FundHistDAO;
import model.Model;

public class Cus_SearchFundAction extends Action{
//	private FormBeanFactory<Cus_SearchFundForm> formBeanFactory = FormBeanFactory
//			.getInstance(Cus_SearchFundForm.class);
	private FundDAO fundDAO;
	private FundHistDAO fundHistDAO;
	
	public Cus_SearchFundAction(Model model) {
		fundDAO = model.getFundDAO();
		fundHistDAO = model.getFundHistDAO();
	}
	
	public String getName() {
		return "cus_searchFund.do";
	}
	
	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
		try {
		    Customer customer = (Customer) request.getSession(false).getAttribute("customer");          
            if(customer == null)
                return "cus-login.jsp";
            
            if (errors.size() != 0)
				return "cus-search-fund.jsp";
            
			ArrayList<Fund> allFunds = fundDAO.getAll();
			request.getSession().setAttribute("allFunds",allFunds);
			
			ArrayList<Long> allFundPrices = new ArrayList<Long>();
			for(Fund x : allFunds){ 
				if(fundHistDAO.getPrice(x.getFund_id())!=null)
					allFundPrices.add(fundHistDAO.getPrice(x.getFund_id()).getPrice());
				else
					allFundPrices.add(-1L);
			}
			request.getSession().setAttribute("allFundPrices",allFundPrices);
			
			return "cus-search-fund.jsp";
		} catch (Exception e) {
			if (org.genericdao.Transaction.isActive())
				org.genericdao.Transaction.rollback();
	      	errors.add(e.toString());
	      	return "cus-search-fund.jsp";
	      }
	}
}
