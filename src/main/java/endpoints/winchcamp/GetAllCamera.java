package endpoints.winchcamp;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utilities.reporting.ExtentReportManager;

import static utilities.RestAssuredUtils.*;

public class GetAllCamera {
    private static Response response;
    private static String accessToken = getToken();
    public static String getAllCamera_url = getUrl().getString("getAllCamera_url");

    public static Response getAllCameraPositiveCase(String... queryParams) {

       return queryParamsPositiveCase(getAllCamera_url, accessToken, queryParams);
    }

    public static Response getAllCameraWrongEndpointCase(String... queryParams) {
        return queryParamsWrongEndpointCase(getAllCamera_url, accessToken, queryParams);
    }

    public static Response getAllCameraMethodCase(String... queryParams) {
        return queryParamsMethodCase(getAllCamera_url, accessToken, queryParams);
    }

    public static Response getAllCameraQueryParamCase(String... queryParams) {
        return queryParamsQueryParamCase(getAllCamera_url, accessToken, queryParams);
    }

    public static Response getAllCameraHeaderCase(String paramValue1, int paramValue2, String headers) {
        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecGet(headers))// Don't set access token as Bearer token
                .queryParam("keyword",paramValue1)
                .queryParam("status", paramValue2)
                .when()
                .post(getAllCamera_url);

        //log details and verify status code in extent report
        printRequestLogInReport(getAllCamera_url, "POST", commonRequestSpecGet(headers));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
}
