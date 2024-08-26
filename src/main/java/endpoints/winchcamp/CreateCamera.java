package endpoints.winchcamp;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utilities.reporting.ExtentReportManager;

import static utilities.RestAssuredUtils.*;

public class CreateCamera {
    private static Response response;
    private static String accessToken = getToken();
    public static String createCamera_url = getUrl().getString("createCamera_url");

    public static Response createCameraPositiveCase(String... queryParams) {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecParam(accessToken, queryParams))// Set access token as Bearer token
                .when()
                .post(createCamera_url);

        //log details and verify status code in extent report
        printRequestLogInReport(createCamera_url, "POST", commonRequestSpecParam(accessToken, queryParams));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response createCameraWrongEndpointCase(String... queryParams) {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecParam(accessToken, queryParams))// Set access token as Bearer token
                .when()
                .post(createCamera_url+"shr");

        //log details and verify status code in extent report
        printRequestLogInReport(createCamera_url+"shr", "POST", commonRequestSpecParam(accessToken, queryParams));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }

    public static Response createCameraMethodCase(String... queryParams) {
        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecParam(accessToken, queryParams))// Set access token as Bearer token
                .when()
                .get(createCamera_url);

        //log details and verify status code in extent report
        printRequestLogInReport(createCamera_url, "GET", commonRequestSpecParam(accessToken, queryParams));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }

    public static Response createCameraHeaderCase(String cameraNameValue, String... headers) {
        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecGet(headers))// Don't set access token as Bearer token
                .queryParam("cameraName",cameraNameValue)
                .when()
                .post(createCamera_url);

        //log details and verify status code in extent report
        printRequestLogInReport(createCamera_url, "POST", commonRequestSpecGet(headers));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }

    public static Response createCameraQueryParamCase(String... queryParams) {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecParam(accessToken, queryParams))// Don't set access token as Bearer token
                .when()
                .post(createCamera_url);

        //log details and verify status code in extent report
        printRequestLogInReport(createCamera_url, "GET", commonRequestSpecParam(accessToken, queryParams));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }

}
