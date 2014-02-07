package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;

import databeans.*;
import model.*;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
        Model model = new Model(getServletConfig());
        Action.add(new Cus_LoginAction(model));
        Action.add(new Cus_ChangePwdAction(model));
        Action.add(new Cus_LogoutAction(model));
        Action.add(new Cus_RequestCheckAction(model));
        Action.add(new Cus_BuyFundAction(model));
        Action.add(new Cus_SellFundAction(model));
        Action.add(new Cus_ViewAccountAction(model));
        Action.add(new Cus_RegistrationAction(model));
        Action.add(new Cus_SearchFundAction(model));
        Action.add(new Cus_TransHistoryAction(model));
        Action.add(new Cus_GetFundDetailsAction(model));
        Action.add(new Emp_LoginAction(model));
        Action.add(new Emp_LogoutAction(model));
        Action.add(new Emp_ChangePwdAction(model));
        Action.add(new Emp_RegistrationAction(model));
        Action.add(new Emp_CreateFundAction(model));
        Action.add(new Emp_TransitionDayAction(model));
        Action.add(new Emp_ResetPwdAction(model));
        Action.add(new Emp_CustomerListAction(model));
        Action.add(new Emp_DepositCheckAction(model));
        Action.add(new Emp_TransHistoryAction(model));

        
        Action.add(new HomePageAction());
        try {
			if (model.getCustomerDAO().readByName("jason") == null) {
				Customer user = new Customer();
				user.setAddr_line1("Pitts");
				user.setAddr_line2("Pitts");
				user.setCash(100000000);
				user.setCity("Pittsburgh");
				user.setFirstname("John");
				user.setLastname("Smith");
				user.setPassword("aaa");
				user.setUsername("jason");
				user.setState("PA");
				user.setZip(15213);
				model.getCustomerDAO().createAutoIncrement(user);
				System.out.println("User jason created.");
			}
			
			if (model.getEmployeeDAO().read("jeff") == null) {
				Employee emp = new Employee();
				emp.setFirstname("Jeff");
				emp.setLastname("Eppinger");
				emp.setUsername("jeff");
				emp.setPassword("aaa");
				model.getEmployeeDAO().create(emp);
				System.out.println("Employee jeff created.");
			}
		} catch (RollbackException e) {
			// TODO Auto-generated catch block
			System.out.println("Insertion failed");
			e.printStackTrace();
		}
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nextPage = performTheAction(request);
        sendToNextPage(nextPage,request,response);
    }
    
    /*
     * Extracts the requested action and (depending on whether the user is logged in)
     * perform it (or make the user login).
     * @param request
     * @return the next page (the view)
     */
    private String performTheAction(HttpServletRequest request) {
        String      servletPath = request.getServletPath();     
        String      action = getActionName(servletPath);
        HttpSession session = request.getSession(true);
        //String 		identity = (String) request.getSession().getAttribute("identity");
        
        // User is not logged in or at the root of the app.
        if (action.equals("welcome")){
        	return "index.jsp";
        }
      	// Let the logged in user run his chosen action
		return Action.perform(action,request);
    }
    
    /*
     * If nextPage is null, send back 404
     * If nextPage ends with ".do", redirect to this page.
     * If nextPage ends with ".jsp", dispatch (forward) to the page (the view)
     *    This is the common case
     */
    private void sendToNextPage(String nextPage, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    	if (nextPage == null) {
    		response.sendError(HttpServletResponse.SC_NOT_FOUND,request.getServletPath());
    		return;
    	}
    	
    	if (nextPage.endsWith(".do")) {
			response.sendRedirect(nextPage);
			return;
    	}
    	
    	if (nextPage.endsWith(".jsp")) {
	   		RequestDispatcher d = request.getRequestDispatcher(nextPage);
	   		d.forward(request,response);
	   		return;
    	}
    	
    	throw new ServletException(Controller.class.getName()+".sendToNextPage(\"" + nextPage + "\"): invalid extension.");
    }

	/*
	 * Returns the path component after the last slash removing any "extension"
	 * if present.
	 */
    private String getActionName(String path) {
    	// We're guaranteed that the path will start with a slash
        int slash = path.lastIndexOf('/');
        return path.substring(slash+1);
    }
}
