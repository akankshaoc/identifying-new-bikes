package listeners;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import utils.CaptureScreenshot;

public class ExtentReportManager implements ITestListener {
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;

	String repName;

	public void onStart(ITestContext testContext) {
		String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy [HH.mm.ss]"));
		repName = "Test-Report-" + timeStamp + ".html";
		sparkReporter = new ExtentSparkReporter(".\\" + repName);// specify location of the report

		sparkReporter.config().setDocumentTitle("Automation Report"); // Title of report
		sparkReporter.config().setReportName("Zigwheels's Finding New Bikes"); // name of the report
		sparkReporter.config().setTheme(Theme.DARK);
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Computer Name", "HP Elitebook");
		extent.setSystemInfo("Environment", "QEA");
		extent.setSystemInfo("Tester Name", "Anand Tomar");
		extent.setSystemInfo("os", "Windows11");
		extent.setSystemInfo("Browser name", "Chrome,Edge");

		String os = testContext.getCurrentXmlTest().getParameter("os");
		extent.setSystemInfo("Operating System", os);
		String browser = testContext.getCurrentXmlTest().getParameter("browser");
		extent.setSystemInfo("Browser", browser);
		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		if (!includedGroups.isEmpty()) {
			extent.setSystemInfo("Groups", includedGroups.toString());
		}
	}

	public void onTestSuccess(ITestResult result) {
		test = extent.createTest(result.getName());
		test.assignCategory(result.getMethod().getGroups()); // to display groups in report
		test.log(Status.PASS, result.getName() + " got successfully executed");
		try {
			String filePathString = CaptureScreenshot.save(result.getMethod().getMethodName());
			test.addScreenCaptureFromPath(filePathString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onTestFailure(ITestResult result) {
		test = extent.createTest(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.FAIL, result.getName() + " got failed");
		test.log(Status.INFO, result.getThrowable().getMessage());
		try {
			String filePathString = CaptureScreenshot.save(result.getMethod().getMethodName());
			test.addScreenCaptureFromPath(filePathString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onTestSkipped(ITestResult result) {
		test = extent.createTest(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, result.getName() + " got skipped");
		test.log(Status.INFO, result.getThrowable().getMessage());

		try {
			String filePathString = CaptureScreenshot.save(result.getMethod().getMethodName());
			test.addScreenCaptureFromPath(filePathString);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onFinish(ITestContext testContext) {
		extent.flush();
	}

}