package utility;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateCheck {
	public static int checkDate(int year, int month, int day) {
		if (year < 1970 || year > 2100)
			return -1;
		if (month < 1 || month > 12)
			return -2;
		if (!checkDay(year, month, day))
			return -3;
		return 1;
	}
	
	public static String getErrorByCode(int year, int month, int day, int ec) {
		if (ec == -1)
			return "Year " + year + " out of range: 1970-2100.";
		if (ec == -2)
			return "Month " + month + " out of range: 1-12.";
		if (ec == -3)
			return "Day " + day + " out of range";
		return "The date " + year + "-" + month + "-" + day + " is not a valid date.";
	}
	
	public static Date getDate(int year, int month, int day) {
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    	try {
			return df.parse(month + "/" + day + "/" + year);
		} catch (Exception e) {
			return new Date();
		}
	}
	
	private static boolean checkDay(int year, int month, int day) {
		if (month < 1 || month > 12)
			return false;
		
		int[] days = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		if (month != 2) {
			if (day < 1 || day > days[month - 1])
				return false;
			return true;
		} else if (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0))
			return day > 0 && day < 30;
		else
			return day > 0 && day < 29;
	}
}
