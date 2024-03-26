package endpoints.ozonetel;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static utilities.RestAssuredUtils.*;

public class GetOzonetelAccounts {
    private static Response response;
    private static String accessToken = getToken();
    public static String getOzonetelAccounts_url = getUrl().getString("getOzonetelAccounts_url");


    public static Response getOzontelAccountsPositiveCase() {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .get(getOzonetelAccounts_url);

        //log details and verify status code in extent report
        printRequestLogInReport(getOzonetelAccounts_url, "GET", commonRequestSpecWithToken(accessToken));
        printResponseLogInReport(response);
        return response;
    }

    public static Response getOzontelAccountWrongEndpointCase() {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .get(getOzonetelAccounts_url+"wrong");

        //log details and verify status code in extent report
        printRequestLogInReport(getOzonetelAccounts_url+"wrong", "GET", commonRequestSpecWithToken(accessToken));
        printResponseLogInReport(response);
        return response;
    }

    public static Response getOzontelAccountMethodCase() {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .post(getOzonetelAccounts_url);

        //log details and verify status code in extent report
        printRequestLogInReport(getOzonetelAccounts_url, "POST", commonRequestSpecWithToken(accessToken));
        printResponseLogInReport(response);
        return response;
    }

    public static Response getOzontelAccountHeaderCase(String... headers) {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecGet(headers))// Don't set access token as Bearer token
                .when()
                .get(getOzonetelAccounts_url);

        //log details and verify status code in extent report
        printRequestLogInReport(getOzonetelAccounts_url, "GET", commonRequestSpecGet(headers));
        printResponseLogInReport(response);
        return response;
    }

}
