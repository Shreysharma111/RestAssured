package endpoints.ozonetel;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static utilities.RestAssuredUtils.*;
import static utilities.RestAssuredUtils.printResponseLogInReport;

public class GetOzonetelCampaigns {
    private static Response response;
    private static String accessToken = getToken();

    static String getOzonetelCampaigns_url = getUrl().getString("getOzonetelCampaigns_url");

    public static Response getOzonetelCampaignsPositiveCase(int ozonetelAccId) {
        String uri = String.format("%s%s/%s/%s", baseUrl, "/ozonetel", ozonetelAccId, "cloud-campaigns");

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .get("/ozonetel"+"/{ozonetelId}/cloud-campaigns", ozonetelAccId);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "GET", commonRequestSpecWithToken(accessToken));
        printResponseLogInReport(response);
        return response;
    }

    public static Response getOzonetelCampaignsWrongEndpointCase(int ozonetelAccId) {
        String uri = String.format("%s%s/%s/%s", baseUrl, "/ozonetel", ozonetelAccId, "cloud-campaigns");

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .get("/ozonetel"+"/{ozonetelId}/cloud-campaigns wrong",ozonetelAccId);

        //log details and verify status code in extent report
        printRequestLogInReport(uri+"wrong", "GET", commonRequestSpecWithToken(accessToken));
        printResponseLogInReport(response);
        return response;
    }

    public static Response getOzonetelCampaignsMethodCase(int ozonetelAccId) {
        String uri = String.format("%s%s/%s/%s", baseUrl, "/ozonetel", ozonetelAccId, "cloud-campaigns");

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .post("/ozonetel"+"/{ozonetelId}/cloud-campaigns", ozonetelAccId);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "POST", commonRequestSpecWithToken(accessToken));
        printResponseLogInReport(response);
        return response;
    }

    public static Response getOzonetelCampaignsHeaderCase(int ozonetelAccId, String... headers) {
        String uri = String.format("%s%s/%s/%s", baseUrl, "/ozonetel", ozonetelAccId, "cloud-campaigns");

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecGet(headers))// Don't set access token as Bearer token
                .when()
                .get("/ozonetel"+"/{ozonetelId}/cloud-campaigns", ozonetelAccId);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "GET", commonRequestSpecGet(headers));
        printResponseLogInReport(response);
        return response;
    }

}
