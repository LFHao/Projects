package controller;

import javax.servlet.http.HttpServletRequest;

public class HomePageAction extends Action {
	public String getName() {
		return "homepage.do";
	}

	public String perform(HttpServletRequest request) {
		return "index.jsp";
	}
}
