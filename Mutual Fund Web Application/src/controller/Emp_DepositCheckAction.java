package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.mybeans.form.FormBeanFactory;

import utility.AmountCheck;
import databeans.Customer;
import databeans.Employee;
import databeans.Transaction;
import formbeans.Emp_DepositCheckForm;
import model.Model;
import model.CustomerDAO;
import model.TransDAO;
import java.text.DecimalFormat;


public class Emp_DepositCheckAction extends Action {   
    private FormBeanFactory<Emp_DepositCheckForm> formBeanFactory = FormBeanFactory.getInstance(Emp_DepositCheckForm.class);
    private CustomerDAO customerDAO;
    private TransDAO transDAO;
    
    public Emp_DepositCheckAction(Model model) {
        customerDAO = model.getCustomerDAO();
        transDAO = model.getTransDAO();
    }
    
    public String getName() { return "emp_depositCheck.do"; }
    
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        Long amount = 0l;
        DecimalFormat dfAmount = new DecimalFormat("###,###,###,###,###,###,##0.00");
        
        try {
        	Customer customer = null;
			Emp_DepositCheckForm form = formBeanFactory.create(request);
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
            	return "emp-deposit-check.jsp";
            }
            
            customer = customerDAO.read(Integer.parseInt(id));
            if (customer == null) {
            	errors.add("The customer does not exist.");
            	return "emp-deposit-check.jsp";
            }
            
            if (!form.isPresent()) {
				return "emp-deposit-check.jsp";
			}
            
            // Any validation errors?
         	errors.addAll(form.getValidationErrors());
         	if (errors.size() != 0) {
         		return "emp-deposit-check.jsp";
         	}      	
         	
         	org.genericdao.Transaction.begin();
            customer = customerDAO.read(Integer.parseInt(id));
            amount = AmountCheck.checkValueString(form.getDeposit());
            Transaction transaction = new Transaction();
            transaction.setAmount(amount);
            transaction.setCustomer_id(customer.getCustomer_id());
            transaction.setExecute_date(null);
            transaction.setTransaction_type("DEPOSIT");
            transaction.setStatus("PENDING");
            transDAO.createAutoIncrement(transaction);
            if (org.genericdao.Transaction.isActive())
            	org.genericdao.Transaction.commit();
            
            System.out.println("The employee =>"+employee.getUsername()+" just deposited a check of =>$"+amount/100+" for customer =>"+customer.getUsername()+"\n");

            request.setAttribute("message","$" + dfAmount.format(transaction.getAmount() / 100.0) +" deposit requested for " + customer.getFirstname() + ".");
    		return "emp-success.jsp";  
        } catch (Exception e) {
        	if (org.genericdao.Transaction.isActive())
        		org.genericdao.Transaction.rollback();
            errors.add(e.getMessage());
            e.printStackTrace();
            return "emp-deposit-check.jsp";
        }
    }
}
