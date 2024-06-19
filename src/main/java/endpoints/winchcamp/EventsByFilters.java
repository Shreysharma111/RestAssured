package endpoints.winchcamp;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojos.EventsByFiltersPojo;
import utilities.reporting.ExtentReportManager;

import static utilities.RestAssuredUtils.*;

public class EventsByFilters {
    private static Response response;
    private static String accessToken = getToken();
    public static String eventsByFilters_url = getUrl().getString("eventsByFilters_url");

    public static Response eventsByFiltersPositiveCase(EventsByFiltersPojo payload) {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecPost(accessToken, payload))// Set access token as Bearer token
                .when()
                .post(eventsByFilters_url);

        //log details and verify status code in extent report
        printRequestLogInReport(eventsByFilters_url, "POST", commonRequestSpecPost(accessToken, payload));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response eventsByFiltersWrongEndpointCase(EventsByFiltersPojo payload) {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecPost(accessToken, payload))// Set access token as Bearer token
                .when()
                .post(eventsByFilters_url+"shr");

        //log details and verify status code in extent report
        printRequestLogInReport(eventsByFilters_url, "POST", commonRequestSpecPost(accessToken, payload));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response eventsByFiltersMethodCase(EventsByFiltersPojo payload) {
        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecPost(accessToken, payload))// Set access token as Bearer token
                .when()
                .get(eventsByFilters_url);

        //log details and verify status code in extent report
        printRequestLogInReport(eventsByFilters_url, "GET", commonRequestSpecPost(accessToken, payload));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response eventsByFiltersHeaderCase(EventsByFiltersPojo payload, String... headers) {
        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecPost(payload, headers))// Don't set access token as Bearer token
                .when()
                .post(eventsByFilters_url);

        //log details and verify status code in extent report
        printRequestLogInReport(eventsByFilters_url, "POST", commonRequestSpecPost(payload, headers));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }

}
