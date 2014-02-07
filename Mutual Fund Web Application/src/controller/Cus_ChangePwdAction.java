package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import model.CustomerDAO;
import model.Model;

import org.mybeans.form.FormBeanFactory;

import databeans.Customer;
import formbeans.Cus_ChangePwdForm;

public class Cus_ChangePwdAction extends Action {
	private FormBeanFactory<Cus_ChangePwdForm> formBeanFactory = FormBeanFactory
			.getInstance(Cus_ChangePwdForm.class);

	private CustomerDAO customerDAO;

	public Cus_ChangePwdAction(Model model) {
		customerDAO = model.getCustomerDAO();
	}

	public String getName() {return "cus_changePwd.do";}

	public String perform(HttpServletRequest request) {
		
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		try {

			Cus_ChangePwdForm form = formBeanFactory.create(request);

			Customer customer = (Customer) request.getSession().getAttribute("customer");
			
			if (customer==null) {
				return "cus-login.do";
			}
            if (!form.isPresent()) {
				return "cus-change-pwd.jsp";
			}
			request.setAttribute("form", form);
						
			errors.addAll(form.getValidationErrors());
			if (errors.size() != 0) {
				return "cus-change-pwd.jsp";
			}

			customerDAO.setPassword(customer.getCustomer_id(),form.getNewPassword());
			
			request.setAttribute("message","Password changed for "+customer.getFirstname());
			return "cus-success.jsp";
	       // return "viewportfolio.do";
		} catch (Exception e) {
			if (org.genericdao.Transaction.isActive())
				org.genericdao.Transaction.rollback();
			errors.add(e.toString());
	      	return "error-list.jsp";
	    }
	}
}
