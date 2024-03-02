package utils;
public class CustomPriceFormatter {
	public static double format(String price) {
		if(price.contains("Lakh")) {
			String parts[] = price.split(" ");
			return Double.parseDouble(parts[1]) * 100000;
		}
		return 0;
	}
}
