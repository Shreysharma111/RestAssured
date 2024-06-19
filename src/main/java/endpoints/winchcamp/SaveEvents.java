package endpoints.winchcamp;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojos.SaveEventsPojo;
import utilities.reporting.ExtentReportManager;

import static utilities.RestAssuredUtils.*;

public class SaveEvents {
    private static Response response;
    private static String accessToken = getToken();
    public static String saveEvents_url = getUrl().getString("saveEvents_url");

    public static Response saveEventsPositiveCase(SaveEventsPojo payload) {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecPost(accessToken, payload))// Set access token as Bearer token
                .when()
                .post(saveEvents_url);

        //log details and verify status code in extent report
        printRequestLogInReport(saveEvents_url, "POST", commonRequestSpecPost(accessToken, payload));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response saveEventsWrongEndpointCase(SaveEventsPojo payload) {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecPost(accessToken, payload))// Set access token as Bearer token
                .when()
                .post(saveEvents_url+"shr");

        //log details and verify status code in extent report
        printRequestLogInReport(saveEvents_url, "POST", commonRequestSpecPost(accessToken, payload));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }

    public static Response saveEventsMethodCase(SaveEventsPojo payload) {
        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecPost(accessToken, payload))// Set access token as Bearer token
                .when()
                .get(saveEvents_url);

        //log details and verify status code in extent report
        printRequestLogInReport(saveEvents_url, "GET", commonRequestSpecPost(accessToken, payload));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response saveEventsHeaderCase(SaveEventsPojo payload, String... headers) {
        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecPost(payload, headers))// Don't set access token as Bearer token
                .when()
                .post(saveEvents_url);

        //log details and verify status code in extent report
        printRequestLogInReport(saveEvents_url, "POST", commonRequestSpecPost(payload, headers));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
}
