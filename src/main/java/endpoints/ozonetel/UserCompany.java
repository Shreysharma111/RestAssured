package endpoints.ozonetel;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utilities.reporting.ExtentReportManager;

import static utilities.RestAssuredUtils.*;

public class UserCompany {
    private static Response response;
    private static String accessToken = getToken();
    public static String userCompany_url = getUrl().getString("userCompany_url");

    public static Response userCompanyPositiveCase(String... queryParams) {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecParam(accessToken, queryParams))// Set access token as Bearer token
                .when()
                .get(userCompany_url);

        //log details and verify status code in extent report
        printRequestLogInReport(userCompany_url, "GET", commonRequestSpecParam(accessToken, queryParams));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response userCompanyWrongEndpointCase(String... queryParams) {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecParam(accessToken, queryParams))// Set access token as Bearer token
                .when()
                .get(userCompany_url+"wrong");

        //log details and verify status code in extent report
        printRequestLogInReport(userCompany_url+"wrong", "GET", commonRequestSpecParam(accessToken, queryParams));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response userCompanyMethodCase(String... queryParams) {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecParam(accessToken, queryParams))// Set access token as Bearer token
                .when()
                .post(userCompany_url);

        //log details and verify status code in extent report
        printRequestLogInReport(userCompany_url, "POST", commonRequestSpecParam(accessToken, queryParams));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response userCompanyHeaderCase(String agentId, String... headers) {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecGet(headers))// Don't set access token as Bearer token
                .queryParam("agentId",agentId)
                .when()
                .get(userCompany_url);

        //log details and verify status code in extent report
        printRequestLogInReport(userCompany_url, "GET", commonRequestSpecGet(headers));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }

    public static Response userCompanyQueryParamCase(String... queryParams) {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecParam(accessToken, queryParams))// Don't set access token as Bearer token
                .when()
                .get(userCompany_url);

        //log details and verify status code in extent report
        printRequestLogInReport(userCompany_url, "GET", commonRequestSpecParam(accessToken, queryParams));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }


}
