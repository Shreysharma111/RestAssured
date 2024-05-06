package endpoints.ozonetel;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static utilities.RestAssuredUtils.*;

public class GetCallCenterByCompany {
    private static Response response;
    private static String accessToken = getToken();
    public static String getCallCenterByCompany_url = getUrl().getString("getCallCenterByCompany_url");

    public static Response getCallCenterByCompanyPositiveCase(int companyId) {
        String uri = String.format("%s/%s/%s", "/ozonetel/companies", companyId, "call-centers");

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .get("/ozonetel/companies"+"/{companyId}/call-centers", companyId);

        //log details and verify status code in extent report
        printRequestLogInReport(uri, "GET", commonRequestSpecWithToken(accessToken));
        printResponseLogInReport(response);
        return response;
    }
}
