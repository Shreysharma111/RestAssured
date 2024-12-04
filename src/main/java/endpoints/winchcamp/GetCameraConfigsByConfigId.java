package endpoints.winchcamp;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utilities.reporting.ExtentReportManager;
import utilities.restAssuredFunctions.HttpMethod;

import static utilities.RestAssuredUtils.*;
import static utilities.RestAssuredUtils.commonRequestSpecWithToken;

public class GetCameraConfigsByConfigId {
    private static Response response;
    private static String accessToken = getToken();
    public static String getCameraConfigsByConfigId_url = getUrl().getString("getCameraConfigsByConfigId_url");


    public static Response getCameraConfigsByConfigIdPositiveCase(int... configId) {
        return positiveCase(HttpMethod.GET, getCameraConfigsByConfigId_url, configId);

    }
    public static Response getCameraConfigsByConfigIdWrongEndpointCase(int... configId) {
        return positiveCase(HttpMethod.GET, getCameraConfigsByConfigId_url+"/shr", configId);

    }
    public static Response getCameraConfigsByConfigIdMethodCase(int... configId) {
        return positiveCase(HttpMethod.POST, getCameraConfigsByConfigId_url, configId);

    }

    public static Response getCameraConfigsByConfigIdHeaderCase(int configId, String... headers) {
        return positiveCase(HttpMethod.GET, getCameraConfigsByConfigId_url, configId, headers);

    }
    public static Response getCameraConfigsByConfigIdZeroConfigIdCase() {
        String uri = String.format("%s/%s", "/facility/details/camera/config", 0);

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .get("/v1/user/facility/details/camera/config"+"/{configId}", 0);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "GET", commonRequestSpecWithToken(accessToken));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }

}
