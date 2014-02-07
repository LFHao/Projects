package controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Model;

public class Cus_LogoutAction extends Action {
	
    public Cus_LogoutAction(Model model) {}
	@Override
	public String getName() {
		return "cus-logout.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		ArrayList<String> errors = new ArrayList<String>();
		request.setAttribute("errors",errors);
		
        HttpSession session = request.getSession(true);
        session.setAttribute("customer", null);
        session.setAttribute("identity", null);

        return "homepage.do";
    }
}
