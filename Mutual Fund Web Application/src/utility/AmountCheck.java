package utility;

public class AmountCheck {
	private static final long MAX = 1000000000;
	private static final long MIN = 1;
	
	public static long checkValueString(String s) {
		float fn = 0;
		try {
			fn = Float.valueOf(s) * 100;
		} catch (NumberFormatException e) {
			return -1;
		}
		int in = Math.round(fn);
		if (in < MIN || in > MAX)
			return -2;
		return in;
	}
	
	public static long checkShareString(String s) {
		float fn = 0;
		try {
			fn = Float.valueOf(s) * 1000;
		} catch (NumberFormatException e) {
			return -1;
		}
		int in = Math.round(fn);
		if (in < MIN || in > MAX)
			return -3;
		return in;
	}
	
	public static int checkZip(String s) {
		int zip;
		try {
			zip = Integer.valueOf(s);
		} catch (NumberFormatException e) {
			return -1;
		}
		return zip;
	}
	
	
	public static String getErrorByCode(String s, long ec) {
		if (ec == -1)
			return new String("\"" + s + "\" is not a number.");
		if (ec == -2)
			return new String(s + " is out of the range of cash value: 0.01 - 10000000.00");
		if (ec == -3)
			return new String(s + " is out of the range of shares: 0.001 - 1000000.000");
		return "Error code out of range.";
	}
}
