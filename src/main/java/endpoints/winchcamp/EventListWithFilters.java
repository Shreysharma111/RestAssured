package endpoints.winchcamp;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojos.EventListWithFiltersPojo;
import utilities.reporting.ExtentReportManager;

import static utilities.RestAssuredUtils.*;

public class EventListWithFilters {
    private static Response response;
    private static String accessToken = getToken();
    public static String eventListWithFilters_url = getUrl().getString("eventListWithFilters_url");

    public static Response eventListWithFiltersPositiveCase(EventListWithFiltersPojo payload) {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecPost(accessToken, payload))// Set access token as Bearer token
                .when()
                .post(eventListWithFilters_url);

        //log details and verify status code in extent report
        printRequestLogInReport(eventListWithFilters_url, "POST", commonRequestSpecPost(accessToken, payload));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }

    public static Response eventListWithFiltersWrongEndpointCase(EventListWithFiltersPojo payload) {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecPost(accessToken, payload))// Set access token as Bearer token
                .when()
                .post(eventListWithFilters_url+"shr");

        //log details and verify status code in extent report
        printRequestLogInReport(eventListWithFilters_url, "POST", commonRequestSpecPost(accessToken, payload));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }

    public static Response eventListWithFiltersMethodCase(EventListWithFiltersPojo payload) {
        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecPost(accessToken, payload))// Set access token as Bearer token
                .when()
                .get(eventListWithFilters_url);

        //log details and verify status code in extent report
        printRequestLogInReport(eventListWithFilters_url, "GET", commonRequestSpecPost(accessToken, payload));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response eventListWithFiltersHeaderCase(EventListWithFiltersPojo payload, String... headers) {
        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecPost(payload, headers))// Don't set access token as Bearer token
                .when()
                .post(eventListWithFilters_url);

        //log details and verify status code in extent report
        printRequestLogInReport(eventListWithFilters_url, "POST", commonRequestSpecPost(payload, headers));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }

}
