package formbeans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import databeans.FundPriceHistory;
import utility.AmountCheck;
import utility.DateCheck;

public class Emp_TransitionDayForm {
	public ArrayList<FundPriceHistory> prices = null;
	private ArrayList<String> sprices = null;
	public String iyear = null;
	public String imonth = null;
	public String iday = null;
	public Date date = null;
	public Date lastday = null;
	
	private String button = null;
	
	public boolean isPresent() { return button != null; }
	
	@SuppressWarnings("unchecked")
	public Emp_TransitionDayForm(HttpServletRequest request) {
		button = request.getParameter("transbutton");
		prices = (ArrayList<FundPriceHistory>) request.getAttribute("prices");
		sprices = new ArrayList<String>();
		for (FundPriceHistory p : prices)
			sprices.add(request.getParameter("price_" + p.getFund_id())); 
		
		iyear = request.getParameter("iyear");
		imonth = request.getParameter("imonth");
		iday = request.getParameter("iday");

		lastday = (Date) request.getAttribute("lastday");
	}
	
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<String>();
		
		int year = 1950, month = 1, day = 1;
		
		try {
			year = Integer.parseInt(iyear);
			month = Integer.parseInt(imonth);
			day = Integer.parseInt(iday);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			errors.add("The string you input in date columns are not valid numbers");
		}
		
		if (errors.size() > 0)
			return errors;
		
		int ec = DateCheck.checkDate(year, month, day);
		if (ec < 0)
			errors.add(DateCheck.getErrorByCode(year, month, day, ec));
		else {
			date = DateCheck.getDate(year, month, day);
			if (lastday != null && date.before(lastday))
				errors.add("New transition date should be after last transition date.");
		}
		
		if (errors.size() > 0)
			return errors;
		
		for (int i = 0; i < sprices.size(); i++) {
			String s = sprices.get(i);
			if (s == null || s.length() == 0)
				errors.add("The price of fund " + prices.get(i).getFund_id() + " is empty.");
			else {
				long np = AmountCheck.checkValueString(s);
				if (np < 0)
					errors.add(AmountCheck.getErrorByCode(sprices.get(i), np));
			}
		}	
		
		if (errors.size() > 0)
			return errors;
		
		for (int i = 0; i < sprices.size(); i++) {
			String s = sprices.get(i);
			long np = AmountCheck.checkValueString(s);
			prices.get(i).setPrice(np);
			prices.get(i).setPrice_date(date);
		}
		return errors;
	}
}
