package endpoints.ozonetel;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utilities.restAssuredFunctions.API;

import static endpoints.OzonetelEndpoints.getAllOzontelAccount_url;
import static utilities.RestAssuredUtils.*;

public class GetAllOzonetelAccount extends API {
    private static Response response;
    private static String accessToken = getToken();
    private static String wrongAccessToken = getWrongToken();

    public static Response getAllOzontelAccountPositiveCase() {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .get(getAllOzontelAccount_url);

        //log details and verify status code in extent report
        printRequestLogInReport(getAllOzontelAccount_url, "GET", commonRequestSpecWithToken(accessToken));
        printResponseLogInReport(response);
        return response;
    }

    public static Response getAllOzontelAccountWrongEndpointCase() {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .get(getAllOzontelAccount_url+"wrong");

        //log details and verify status code in extent report
        printRequestLogInReport(getAllOzontelAccount_url+"wrong", "GET", commonRequestSpecWithToken(accessToken));
        printResponseLogInReport(response);
        return response;
    }

    public static Response getAllOzontelAccountMethodCase() {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .post(getAllOzontelAccount_url);

        //log details and verify status code in extent report
        printRequestLogInReport(getAllOzontelAccount_url, "POST", commonRequestSpecWithToken(accessToken));
        printResponseLogInReport(response);
        return response;
    }

    public static Response getAllOzontelAccountHeaderCase(String... headers) {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecGet(headers))// Don't set access token as Bearer token
                .when()
                .get(getAllOzontelAccount_url);

        //log details and verify status code in extent report
        printRequestLogInReport(getAllOzontelAccount_url, "GET", commonRequestSpecGet(headers));
        printResponseLogInReport(response);
        return response;
    }


}
