package controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
	
import model.Model;

public class Emp_LogoutAction extends Action {

	
    public Emp_LogoutAction(Model model) {}
	@Override
	public String getName() {
		return "emp-logout.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		ArrayList<String> errors = new ArrayList<String>();
		request.setAttribute("errors",errors);
		
        HttpSession session = request.getSession(true);
        session.setAttribute("employee", null);
        session.setAttribute("identity", null);
        return "homepage.do";
    }
}
