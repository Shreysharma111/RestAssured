package api.utilities;


import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.restassured.response.ResponseBody;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.annotations.Listeners;

public class ExtentManager implements ITestListener {

    ExtentSparkReporter htmlReporter;
    protected static ExtentReports reports;
    protected static ExtentTest test;

    public void configureReport() {


        String timestamp = new SimpleDateFormat("dd.MM.yyyy.hh.mm.ss").format(new Date());
        String reportName = "TestReport-"+timestamp+".html";

        htmlReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "//reports//" + reportName);
        reports = new ExtentReports();
        reports.attachReporter(htmlReporter);

        //add system/environment info
        reports.setSystemInfo("Machine", "testpc1");
        reports.setSystemInfo("OS", "windows 11");
        reports.setSystemInfo("Username", "shrey");

        //change look and feel of report
        htmlReporter.config().setDocumentTitle("Extent Reports");
        htmlReporter.config().setReportName("Rest Assured Automation");
        htmlReporter.config().setTheme(Theme.DARK);

    }

    public void onStart(ITestContext Result) {

        configureReport();
        System.out.println("On start method invoked");
    }

    public void onTestStart(ITestResult Result) {
        System.out.println("Name of test method started : "+ Result.getName());
    }

    public void onTestFailure(ITestResult Result) {
        System.out.println("Name of test method failed : "+ Result.getName());
        test = reports.createTest(Result.getName()); //create entry in html reports
        test.log(Status.FAIL, MarkupHelper.createLabel("Name of failed TC IS : "+Result.getName(), ExtentColor.RED));

    }

    public void onTestSkipped(ITestResult Result) {
        System.out.println("Name of test method skipped : "+ Result.getName());

        test = reports.createTest(Result.getName());
        test.log(Status.SKIP, MarkupHelper.createLabel("Name of skipped TC IS : "+Result.getName(), ExtentColor.ORANGE));

    }


    public void onTestSuccess(ITestResult Result) {
        System.out.println("Name of test method success : "+ Result.getName());

        test = reports.createTest(Result.getName());
        test.log(Status.PASS, MarkupHelper.createLabel("Name of passed TC IS : "+Result.getName(), ExtentColor.GREEN));
    }

    // Custom method to log status code and response body
    public static void logStatusCodeAndResponseBody(ExtentTest extentTest, int statusCode, String responseTime, String responseBody) {
        extentTest.createNode(De);
        extentTest.log(Status.INFO, "Status Code: " + statusCode);
        extentTest.log(Status.INFO, "Response Time: " + responseTime);
        extentTest.log(Status.INFO, "Response Body: " + responseBody);
    }


    public void onFinish(ITestContext Result) {
        System.out.println("On finish method invoked");
        reports.flush(); //it is mandatory to ensure info is written to the started report
    }

}
