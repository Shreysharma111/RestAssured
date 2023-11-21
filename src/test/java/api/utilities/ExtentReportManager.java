package api.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager implements ITestListener {

    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest test;

    String repName;
    public void onTestStart(ITestResult result) {
        ITestListener.super.onTestStart(result);
    }

    public void onTestSuccess(ITestResult result) {
        test = extent.createTest(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.PASS, "Test passed : "+result.getMethod());
    }

    public void onTestFailure(ITestResult result) {
        test = extent.createTest(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.FAIL, "Test failed : "+result.getTestName());
        test.log(Status.FAIL, result.getThrowable().getMessage());
    }

    public void onTestSkipped(ITestResult result) {
        test = extent.createTest(result.getName());
        test.createNode(result.getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP, "Test skipped");
        test.log(Status.SKIP, result.getThrowable().getMessage());
    }

    public void onStart(ITestContext testContext) {
        String timeSStamp = new SimpleDateFormat("dd.MM.yyyy.HH.mm.ss").format(new Date());
        repName = "Test-Report-"+timeSStamp+".html";

        sparkReporter = new ExtentSparkReporter(".\\reports\\"+repName);//specify location of the report

        sparkReporter.config().setDocumentTitle("RestAssuredFramework");
        sparkReporter.config().setReportName("Incidence API");
        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Application", "Incidence");
        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
        extent.setSystemInfo("Environment", "QA");

    }

    public void onFinish(ITestContext testContext) {
        extent.flush();
    }
}
