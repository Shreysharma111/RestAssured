package endpoints.ozonetel;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static utilities.RestAssuredUtils.*;
import static utilities.RestAssuredUtils.printResponseLogInReport;

public class UserCompany {
    private static Response response;
    private static String accessToken = getToken();
    public static String userCompany_url = getUrl().getString("userCompany_url");

    public static Response userCompanyPositiveCase(String agentId) {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .queryParam("agentId",agentId)
                .when()
                .get(userCompany_url);

        //log details and verify status code in extent report
        printRequestLogInReport(userCompany_url, "GET", commonRequestSpecWithToken(accessToken));
        printResponseLogInReport(response);
        return response;
    }
    public static Response userCompanyWrongEndpointCase(String agentId) {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .queryParam("agentId",agentId)
                .when()
                .get(userCompany_url+"wrong");

        //log details and verify status code in extent report
        printRequestLogInReport(userCompany_url+"wrong", "GET", commonRequestSpecWithToken(accessToken));
        printResponseLogInReport(response);
        return response;
    }
    public static Response userCompanyMethodCase(String agentId) {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .queryParam("agentId",agentId)
                .when()
                .post(userCompany_url);

        //log details and verify status code in extent report
        printRequestLogInReport(userCompany_url, "POST", commonRequestSpecWithToken(accessToken));
        printResponseLogInReport(response);
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
        printResponseLogInReport(response);
        return response;
    }

    public static Response userCompanyQueryParamCase(String agentId, String... queryParams) {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecParamGet(queryParams))// Don't set access token as Bearer token
                .when()
                .get(userCompany_url);

        //log details and verify status code in extent report
        printRequestLogInReport(userCompany_url, "GET", commonRequestSpecParamGet(queryParams));
        printResponseLogInReport(response);
        return response;
    }
}
