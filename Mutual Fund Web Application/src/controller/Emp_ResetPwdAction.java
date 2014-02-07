package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import model.CustomerDAO;
import model.Model;

import org.genericdao.Transaction;
import org.mybeans.form.FormBeanFactory;

import databeans.Customer;
import databeans.Employee;
import formbeans.Emp_ResetPwdForm;

public class Emp_ResetPwdAction extends Action{
	private FormBeanFactory<Emp_ResetPwdForm> formBeanFactory = FormBeanFactory
			.getInstance(Emp_ResetPwdForm.class);

	private CustomerDAO customerDAO;

	public Emp_ResetPwdAction(Model model) {
		customerDAO = model.getCustomerDAO();
	}

	public String getName() {
		return "emp_resetPwd.do";
	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			Customer customer = null;
			Emp_ResetPwdForm form = formBeanFactory.create(request);
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
            	return "emp-reset-customer-pwd.jsp";
            }
            
            customer = customerDAO.read(Integer.parseInt(id));
            if (customer == null) {
            	errors.add("The customer does not exist.");
            	return "emp-reset-customer-pwd.jsp";
            }
            
            if (!form.isPresent()) {
				return "emp-reset-customer-pwd.jsp";
			}
            
            // Any validation errors?
         	errors.addAll(form.getValidationErrors());
         	if (errors.size() != 0) {
         		return "emp-reset-customer-pwd.jsp";
         	}
            
            Transaction.begin();
            customer = customerDAO.read(Integer.parseInt(id));
            customer.setPassword(form.getPassword());
            customerDAO.update(customer);
            if (org.genericdao.Transaction.isActive())
            	Transaction.commit();
            
			request.setAttribute("message", "Password reset for user " + customer.getUsername() + ".");
	        return "emp-success.jsp";
		} catch (Exception e) {
			if (org.genericdao.Transaction.isActive())
        		org.genericdao.Transaction.rollback();
			errors.add(e.toString());
			e.printStackTrace();
			return "emp-reset-customer-pwd.jsp";
		}
	}
}
