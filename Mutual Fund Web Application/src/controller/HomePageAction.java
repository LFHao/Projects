package controller;

import javax.servlet.http.HttpServletRequest;

public class HomePageAction extends Action {
	@Override
	public String getName() {
		return "homepage.do";
	}

	@Override
	public String perform(HttpServletRequest request) {
		return "index.jsp";
	}

}
