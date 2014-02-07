package controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import databeans.Employee;
import databeans.Fund;
import formbeans.Emp_CreateFundForm;
import model.FundDAO;
import model.Model;


public class Emp_CreateFundAction extends Action {   
    private FormBeanFactory<Emp_CreateFundForm> formBeanFactory = FormBeanFactory.getInstance(Emp_CreateFundForm.class);
    private FundDAO fundDAO;
    
    public Emp_CreateFundAction(Model model) {
        fundDAO = model.getFundDAO();
    }
    
    public String getName() { return "create-fund-emp.do"; }
    
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors",errors);
        
        try {         
            Employee employee = (Employee) request.getSession(false).getAttribute("employee");
            if(employee == null) {
                return "emp-login.do";
            }
            
            Emp_CreateFundForm form = formBeanFactory.create(request);
            request.setAttribute("form",form);
            
            if (!form.isPresent()) {
                return "emp-create-fund.jsp";
            }

            // Any validation errors?
            errors.addAll(form.getValidationErrors());
            if (errors.size() != 0) {
                return "emp-create-fund.jsp";
            }
            
            
            org.genericdao.Transaction.begin();
            // Create new fund
            Fund fund = fundDAO.readByName(form.getFundName());
            if (fund != null) {
                errors.add("Fund Name " + "["+fund.getName()+"] already exists!");
                if (org.genericdao.Transaction.isActive())
                	org.genericdao.Transaction.rollback();
                return "emp-create-fund.jsp";
            }
            
            fund = fundDAO.readBySymbol(form.getFundSymbol());
            if (fund != null) {
                errors.add("Fund Symbol " + "["+fund.getSymbol()+"] already exists!");
                if (org.genericdao.Transaction.isActive())
                	org.genericdao.Transaction.rollback();
                return "emp-create-fund.jsp";
            }
            
			// Attach (this copy of) the user bean to the session
            fund = new Fund();
            fund.setName(form.getFundName());
            fund.setSymbol(form.getFundSymbol());
            fundDAO.createAutoIncrement(fund);
            if (org.genericdao.Transaction.isActive())
            	org.genericdao.Transaction.commit();
            System.out.println("The employee =>"+employee.getUsername()+" created the fund =>"+form.getFundName()+"\n");
			
            request.setAttribute("fund", fund);
            request.setAttribute("message","Fund " + form.getFundName() + "[" + form.getFundSymbol() + "] has been created.");
			return "emp-success.jsp";
        } catch (FormBeanException e) {
            errors.add(e.getMessage());
            return "emp-create-fund.jsp";
        } catch (RollbackException e) {
        	if (org.genericdao.Transaction.isActive())
				org.genericdao.Transaction.rollback();
        	errors.add(e.getMessage());
            return "emp-create-fund.jsp";
		}
    }
}
