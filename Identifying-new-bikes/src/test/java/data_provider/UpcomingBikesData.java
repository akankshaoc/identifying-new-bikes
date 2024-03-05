package data_provider;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.annotations.DataProvider;

import utils.BikeDataReaderWriter;

public class UpcomingBikesData {

	private static LocalDate parseWithDefaultDay(String str) {
		DateTimeFormatter parseFormatter = new DateTimeFormatterBuilder()
				.appendPattern("MMM yyyy")
				.parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
				.toFormatter(Locale.ENGLISH);
		LocalDate res = LocalDate.parse(str, parseFormatter);
		int lastDay = res.getMonth().length(res.isLeapYear());
		res = LocalDate.of(res.getYear(), res.getMonth(), lastDay);
		return res;
	}
	
	/**
	 * utility method to recognize of the upcoming date is of any known type
	 * @param dateString
	 * @return String ['dd MM yyyy' | 'MM yyyy']
	 */
	private static String dateOfPattern(String dateString) {
		String regex[] = new String[] {
				"[0-9]{2} [A-za-z]{3} [0-9]{4}", 
				"[A-za-z]{3} [0-9]{4}"
		};
		
		String dateType[] = new String[] {
				"dd MM yyyy",
				"MM yyyy",
		};
		
		for(int i = 0; i < regex.length; i++) {
			Pattern pattern = Pattern.compile(regex[i]);
			Matcher matcher = pattern.matcher(dateString);
			if(matcher.matches()) {
				return dateType[i];
			}
		}
		
		return null;
	}

	@DataProvider(name = "bikes")
	public Object[][] getBikaData() throws Exception {
		BikeDataReaderWriter excelIO = new BikeDataReaderWriter();
		String[][] data = excelIO.readFromNewBikes();
		if (data.length < 1)
			return new Object[][] { {} };
		Object[][] res = new Object[data.length][data[0].length];

		for(int i = 0; i < res.length; i++) {
			res[i][0] = data[i][0];
			res[i][1] = Double.parseDouble(data[i][1]);
			String datestr = data[i][2];
			switch(dateOfPattern(datestr)) {
			case "dd MM yyyy" :
				res[i][2] = LocalDate.parse(data[i][2], DateTimeFormatter.ofPattern("dd MM yyyy"));
				break;
			case "MM yyyy" :
				res[i][2] = parseWithDefaultDay(data[i][2]);
				break;
			default : throw new Exception (data[i][2] + " date not recognised");
			}
		}

		return res;
	}
}
