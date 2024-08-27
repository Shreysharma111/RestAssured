package endpoints.winchcamp;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utilities.reporting.ExtentReportManager;
import utilities.restAssuredFunctions.HttpMethod;

import static utilities.RestAssuredUtils.*;

public class ActivateCamera {
    private static Response response;
    private static String accessToken = getToken();
    public static String activateCamera_url = getUrl().getString("activateCamera_url");

    public static Response activateCameraPositiveCase(String... queryParams) {
        return queryParamsPositiveCase(HttpMethod.POST, activateCamera_url, accessToken, queryParams);
    }
    public static Response activateCameraWrongEndpointCase(String... queryParams) {
        return queryParamsPositiveCase(HttpMethod.POST, activateCamera_url+"shr", accessToken, queryParams);
    }
    public static Response activateCameraMethodCase(String... queryParams) {
        return queryParamsPositiveCase(HttpMethod.GET, activateCamera_url, accessToken, queryParams);
    }
    public static Response activateCameraHeaderCase(int idValue, String... headers) {
        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecGet(headers))// Don't set access token as Bearer token
                .queryParam("id",idValue)
                .when()
                .post(activateCamera_url);

        //log details and verify status code in extent report
        printRequestLogInReport(activateCamera_url, "POST", commonRequestSpecGet(headers));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response activateCameraQueryParamCase(String... queryParams) {
        return queryParamsQueryParamCase(activateCamera_url,accessToken, queryParams);
    }

}
