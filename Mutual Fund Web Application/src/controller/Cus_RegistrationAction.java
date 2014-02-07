package controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.genericdao.RollbackException;
import org.genericdao.Transaction;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import utility.AmountCheck;
import databeans.Customer;
import databeans.Employee;
import formbeans.Cus_RegistrationForm;
import model.CustomerDAO;
import model.Model;

public class Cus_RegistrationAction extends Action {
	private FormBeanFactory<Cus_RegistrationForm> formBeanFactory = FormBeanFactory.getInstance(Cus_RegistrationForm.class);
	private CustomerDAO customerDAO;
	public Cus_RegistrationAction(Model model) {
		customerDAO = model.getCustomerDAO();
	}
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "cus-registration.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		// TODO Auto-generated method stub
		ArrayList<String> errors = new ArrayList<String>();
		request.setAttribute("errors",errors);
		
        try {
	    	Cus_RegistrationForm form = formBeanFactory.create(request);
			Employee employee = (Employee) request.getSession(false).getAttribute("employee");
			if(employee==null){
	        	return "emp-login.jsp";
	        }
	        // If no params were passed, return with no errors so that the form will be
	        // presented (we assume for the first time).
	        if (!form.isPresent()) {
	            return "cus-registration.jsp";
	        }

	        // Any validation errors?
	        errors.addAll(form.getValidationErrors());
	        if (errors.size() != 0) {
	            return "cus-registration.jsp";
	        }
        
	        Customer customer = new Customer();
	        customer.setFirstname(form.getFirstname());
	        customer.setLastname(form.getLastname());
	        customer.setUsername(form.getUsername());
	        customer.setPassword(form.getPassword());
	        customer.setAddr_line1(form.getAddrline1());
	        customer.setAddr_line2(form.getAddrline2());
	        customer.setCity(form.getCity());
	        customer.setState(form.getState());
	        customer.setCash(AmountCheck.checkValueString(form.getCash()));
	        customer.setZip(AmountCheck.checkZip(form.getZip()));
	        
	        Transaction.begin();
	        if(customerDAO.readByName(form.getUsername()) !=null){
	        	errors.add("Username already exists!");
	        	if (Transaction.isActive())
	        		Transaction.rollback();
	        	return "cus-registration.jsp";
	        }
	        customerDAO.createAutoIncrement(customer);
	        if (Transaction.isActive())
	        	Transaction.commit();
	        
	        request.setAttribute("message","Customer "+ customer.getUsername() + " is created.");
			return "emp-success.jsp";
        } catch (FormBeanException e) {
        	errors.add(e.getMessage());
        	return "error.jsp";
        } catch (RollbackException e) {
        	errors.add(e.getMessage());
        	if (Transaction.isActive())
        		Transaction.rollback();
        	return "error.jsp";
        }
	}

}
