package endpoints.winchcamp;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import utilities.reporting.ExtentReportManager;
import utilities.restAssuredFunctions.HttpMethod;

import static utilities.RestAssuredUtils.*;

public class EditMasterCamera {

    private static Response response;
    private static String accessToken = getToken();
    public static String editMasterCamera_url = getUrl().getString("editMasterCamera_url");

    public static Response editMasterCameraPositiveCase(String... queryParams) {

        return queryParamsPositiveCase(HttpMethod.PUT, editMasterCamera_url, accessToken, queryParams);
    }

    public static Response editMasterCameraWrongEndpointCase(String... queryParams) {
        return queryParamsPositiveCase(HttpMethod.PUT, editMasterCamera_url+"shr", accessToken, queryParams);
    }

    public static Response editMasterCameraMethodCase(String... queryParams) {
        return queryParamsPositiveCase(HttpMethod.GET, editMasterCamera_url, accessToken, queryParams);
    }

    public static Response editMasterCameraQueryParamCase(String... queryParams) {
        return queryParamsPositiveCase(HttpMethod.PUT, editMasterCamera_url, accessToken, queryParams);
    }

    public static Response editMasterCameraHeaderCase(String paramValue1, int paramValue2, String headers) {
        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecGet(headers))// Don't set access token as Bearer token
                .queryParam("cameraName",paramValue1)
                .queryParam("id", paramValue2)
                .when()
                .put(editMasterCamera_url);

        //log details and verify status code in extent report
        printRequestLogInReport(editMasterCamera_url, "PUT", commonRequestSpecGet(headers));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
}
