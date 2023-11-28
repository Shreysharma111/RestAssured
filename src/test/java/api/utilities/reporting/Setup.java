package api.utilities.reporting;

import api.utilities.reporting.ExtentReportManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;


public class Setup implements ITestListener {

    private static ExtentReports extentReports;
    public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    public void onStart(ITestContext context) {
        String fileName = ExtentReportManager.getReportNameWithTimestamp();
        String fullReportPath = System.getProperty("user.dir")+ "\\reports\\"+fileName;
        extentReports = ExtentReportManager.createInstance(fullReportPath, "Test API Automation Report", "Test Execution Report");
    }

    public void onFinish(ITestContext context) {
        if(extentReports != null)
            extentReports.flush();
    }

    public void onTestStart(ITestResult result) {
        ExtentTest test = extentReports.createTest(result.getTestContext().getCurrentXmlTest().getClasses().get(0).getName() + " - " + result.getMethod().getMethodName());
        extentTest.set(test);
    }
    public void onTestSuccess(ITestResult result) {
        ExtentReportManager.logPassDetails("Test case passed : "+result.getMethod().getMethodName());

        extentReports.flush();
    }

    public void onTestFailure(ITestResult result) {
        ExtentReportManager.logFailureDetails(result.getThrowable().getMessage());
        String stackTrace= Arrays.toString(result.getThrowable().getStackTrace());

        ExtentReportManager.logFailureDetails(stackTrace);

    }
    private static ExtentTest getTest() {
        if (extentTest.get() == null) {
            extentTest.set(extentReports.createTest("API Test"));
        }
        return extentTest.get();
    }
    public static void logApiDetails(Response response) {
        ExtentTest test = getTest();

        // Log status code, response time, and response body
        test.log(Status.INFO, "Status Code: " + response.getStatusCode());
        test.log(Status.INFO, "Response Time: " + response.getTimeIn(TimeUnit.MILLISECONDS)+"ms");
        ExtentReportManager.logJson("Response Body: " + response.getBody().prettyPrint());
        extentReports.flush();
    }
}
