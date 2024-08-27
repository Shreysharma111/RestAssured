package endpoints.winchcamp;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utilities.reporting.ExtentReportManager;

import static utilities.RestAssuredUtils.*;

public class DeactivateCamera {
    private static Response response;
    private static String accessToken = getToken();
    public static String deactivateCamera_url = getUrl().getString("deactivateCamera_url");

    public static Response deactivateCameraPositiveCase(String... queryParams) {
        return queryParamsPositiveCase(deactivateCamera_url, accessToken, queryParams);
    }
    public static Response deactivateCameraWrongEndpointCase(String... queryParams) {
        return queryParamsWrongEndpointCase(deactivateCamera_url, accessToken, queryParams);
    }
    public static Response deactivateCameraMethodCase(String... queryParams) {
        return queryParamsMethodCase(deactivateCamera_url, accessToken, queryParams);
    }
    public static Response deactivateCameraHeaderCase(int idValue, String... headers) {
        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecGet(headers))// Don't set access token as Bearer token
                .queryParam("id",idValue)
                .when()
                .post(deactivateCamera_url);

        //log details and verify status code in extent report
        printRequestLogInReport(deactivateCamera_url, "POST", commonRequestSpecGet(headers));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response deactivateCameraQueryParamCase(String... queryParams) {
        return queryParamsQueryParamCase(deactivateCamera_url,accessToken, queryParams);
    }
}
