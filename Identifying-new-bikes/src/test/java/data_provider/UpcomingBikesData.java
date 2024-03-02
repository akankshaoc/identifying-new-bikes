package data_provider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

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
		//System.out.println(lastDay);
		res = LocalDate.of(res.getYear(), res.getMonth(), lastDay);
		return res;
	}

	@DataProvider(name = "bikes")
	public Object[][] getBikaData() throws IOException {
		BikeDataReaderWriter excelIO = new BikeDataReaderWriter();
		String[][] data = excelIO.readFromNewBikes();
		if (data.length < 1)
			return new Object[][] { {} };
		Object[][] res = new Object[data.length][data[0].length];

		for(int i = 0; i < res.length; i++) {
			res[i][0] = data[i][0];
			System.out.println(data[i][0] + data[i][1]);
			res[i][1] = Double.parseDouble(data[i][1]);
			res[i][2] = parseWithDefaultDay(data[i][2]);
		}

		return res;
	}
}
