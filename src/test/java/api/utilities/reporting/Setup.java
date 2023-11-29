package api.utilities.reporting;

import api.endpoints.UserEndPoints;
import api.test.LoginTests;
import api.utilities.reporting.ExtentReportManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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
        extentReports = ExtentReportManager.createInstance(fullReportPath, "Test API Automation Report", "Extent Report");
    }

    public void onFinish(ITestContext context) {
        if(extentReports != null)
            extentReports.flush();
    }

    public void onTestStart(ITestResult result) {
        ExtentTest test = extentReports.createTest(result.getTestClass().getName() + " - " + result.getMethod().getMethodName());
        extentTest.set(test);
    }
    public void onTestSuccess(ITestResult result) {
//        ExtentReportManager.logPassDetails("Test case passed : "+result.getMethod().getMethodName());

    }

    public void onTestFailure(ITestResult result) {
//        ExtentReportManager.logFailureDetails("Test case failed : "+result.getMethod().getMethodName());

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

    }

    public static void logResultAndDetails(Response response) {
        ExtentTest test = getTest();

        int statusCode = response.getStatusCode();
        if (statusCode == 403) {
            test.fail("403 — Forbidden error occurred");
            // Perform test cases of LoginTest.java test class
//            LoginTests loginInstance = new LoginTests();
//            loginInstance.testLoginIT();

        } else if (statusCode == 500) {
            test.log(Status.FAIL, "500 - Server error occurred");
            test.log(Status.INFO, "Response Message - " + response.jsonPath().get("message"));

        } else if (statusCode == 404) {
            test.log(Status.FAIL, "404 - Not found error occurred");
            test.log(Status.INFO, "Response Message - " + response.jsonPath().get("message"));

        } else if (statusCode == 200) {
            test.log(Status.PASS, "200 OK - Verified successfully");

        } else if (statusCode == 401) {
            test.fail("401 - Unauthorized error occurred");
            test.log(Status.INFO, "Response Message - " + response.jsonPath().get("message"));

        } else {
            test.log(Status.FAIL, "Error occurred : "+response.getStatusCode());

        }
        extentReports.flush();
    }

}


