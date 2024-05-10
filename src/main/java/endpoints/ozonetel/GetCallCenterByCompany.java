package endpoints.ozonetel;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utilities.reporting.ExtentReportManager;

import static utilities.RestAssuredUtils.*;

public class GetCallCenterByCompany {
    private static Response response;
    private static String accessToken = getToken();
    public static String getCallCenterByCompany_url = getUrl().getString("getCallCenterByCompany_url");

    public static Response getCallCenterByCompanyPositiveCase(int companyId) {
        String uri = String.format("%s/%s/%s", "/ozonetel/companies", companyId, "call-centers");

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .get("/ozonetel/companies"+"/{companyId}/call-centers", companyId);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "GET", commonRequestSpecWithToken(accessToken));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }

    public static Response getCallCenterByCompanyWrongEndpointCase(int companyId) {
        String uri = String.format("%s/%s/%s", "/ozonetel/companies", companyId, "call-centers wrong");

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .get("/ozonetel/companies"+"/{companyId}/call-centers wrong", companyId);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "GET", commonRequestSpecWithToken(accessToken));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }

    public static Response getCallCenterByCompanyMethodCase(int companyId) {
        String uri = String.format("%s/%s/%s", "/ozonetel/companies", companyId, "call-centers");

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .post("/ozonetel/companies"+"/{companyId}/call-centers", companyId);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "POST", commonRequestSpecWithToken(accessToken));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }

    public static Response getCallCenterByCompanyHeaderCase(int companyId, String... headers) {
        String uri = String.format("%s/%s/%s", "/ozonetel/companies", companyId, "call-centers");

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecGet(headers))// Don't set access token as Bearer token
                .when()
                .get("/ozonetel/companies"+"/{companyId}/call-centers", companyId);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "GET", commonRequestSpecGet(headers));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
}
