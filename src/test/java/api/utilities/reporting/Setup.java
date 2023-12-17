package api.utilities.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.util.concurrent.TimeUnit;


public class Setup implements ITestListener {

    public static ExtentReports extentReports;
    public static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    public void onStart(ITestContext context) {
//        String fileName = ExtentReportManager.getReportNameWithTimestamp();
        String fullReportPath = System.getProperty("user.dir")+ "\\reports\\"+"TestReport.html";
        extentReports = ExtentReportManager.createInstance(fullReportPath, "Test API Extent Report", "Extent Report");
    }

    public void onFinish(ITestContext context) {
        if(extentReports != null)
            extentReports.flush();
        ExtentReportManager.openReportInBrowser();
    }

    public void onTestStart(ITestResult result) {
//        ExtentTest test = extentReports.createTest(result.getTestClass().getRealClass().getSimpleName() + " - " + result.getMethod().getMethodName());
//        extentTest.set(test);
    }
    public void onTestSuccess(ITestResult result) {
//        ExtentReportManager.logPassDetails("Test case passed : "+result.getMethod().getMethodName());

    }

    public void onTestFailure(ITestResult result) {
//        ExtentReportManager.logFailureDetails("Test case failed : "+result.getMethod().getMethodName());

    }
    public static ExtentTest getTest() {
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
        ExtentReportManager.logJson(response.getBody().prettyPrint());

    }

    public static void logResultAndDetails(Response response) {
        ExtentTest test = getTest();

        int statusCode = response.getStatusCode();
        if (statusCode == 403) {
            ExtentReportManager.logFailureDetails("403 — Forbidden error occurred");

        } else if (statusCode == 500) {
            test.log(Status.INFO, "API Message - " + response.jsonPath().get("message"));
            ExtentReportManager.logFailureDetails("500 - Server error occurred");

        } else if (statusCode == 404) {
            test.log(Status.INFO, "API Message - " + response.jsonPath().get("message"));
            ExtentReportManager.logFailureDetails("404 - Not found error occurred");

        } else if (statusCode == 200) {
            ExtentReportManager.logPassDetails("200 OK - Verified successfully");

        } else if (statusCode == 401) {
            test.log(Status.INFO, "API Message - " + response.jsonPath().get("message"));
            ExtentReportManager.logFailureDetails("401 - Unauthorized error occurred");

        } else {
            ExtentReportManager.logFailureDetails("Error occurred : "+response.getStatusCode());

        }
//        extentReports.flush();
    }

}


