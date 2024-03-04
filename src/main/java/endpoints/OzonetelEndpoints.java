package endpoints;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static utilities.RestAssuredUtils.*;

public class OzonetelEndpoints {
    private static Response response;
    static String accessToken = getToken();
    static String getAllOzontelAccount_url = getUrl().getString("getAllOzontelAccount_url");
    static String getOzonetelAccounts_url = getUrl().getString("getOzonetelAccounts_url");
    static String getOzonetelCampaigns_url = getUrl().getString("getOzonetelCampaigns_url");
    static String createNewSkill_url = getUrl().getString("base_url") + getUrl().getString("createNewSkill_url");
    static String getSkills_url = getUrl().getString("base_url") + getUrl().getString("getSkills_url");
    static String createAgent_url = getUrl().getString("base_url") + getUrl().getString("createAgent_url");
    static String editAgent_url = getUrl().getString("base_url") + getUrl().getString("editAgent_url");
    static String getCampaign_url = getUrl().getString("base_url") + getUrl().getString("getCampaign_url");
    static String addCampaign_url = getUrl().getString("base_url") + getUrl().getString("addCampaign_url");
    static String manualDial_url = getUrl().getString("base_url") + getUrl().getString("manualDial_url");
    static String setDisposition_url = getUrl().getString("base_url") + getUrl().getString("setDisposition_url");
    static String logout_url = getUrl().getString("base_url") + getUrl().getString("logout_url");
    static String updateSkill_url = getUrl().getString("base_url") + getUrl().getString("updateSkill_url");
    static String updateCampaign_url = getUrl().getString("base_url") + getUrl().getString("updateCampaign_url");
    static String getSkillByOzonetel_url = getUrl().getString("base_url") + getUrl().getString("getSkillByOzonetel_url");
    static String changeAgentCompany_url = getUrl().getString("base_url") + getUrl().getString("changeAgentCompany_url");
    static String getAllPrmCampaigns_url = getUrl().getString("base_url") + getUrl().getString("getAllPrmCampaigns_url");
    static String getPrmCampaignByCampaignId_url = getUrl().getString("base_url") + getUrl().getString("getPrmCampaignByCampaignId_url");
    static String getAllSource_url = getUrl().getString("base_url") + getUrl().getString("getAllSource_url");
    static String getSourceBySourceId_url = getUrl().getString("base_url") + getUrl().getString("getSourceBySourceId_url");
    static String getAllZones_url = getUrl().getString("base_url") + getUrl().getString("getAllZones_url");
    static String getZoneByZoneId_url = getUrl().getString("base_url") + getUrl().getString("getZoneByZoneId_url");


    public static Response getAllOzontelAccount() {


        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpec(accessToken))// Set access token as Bearer token
                .when()
                .get(getAllOzontelAccount_url);

        //log details and verify status code in extent report
        printRequestLogInReport(getAllOzontelAccount_url, "GET", commonRequestSpec(accessToken));
        printResponseLogInReport(response);
        validateJsonSchema(response, "Ozonetel/getAllOzonetelAccSchema.json");
        return response;
    }

    public static Response getOzonetelAccounts() {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpec(accessToken))// Set access token as Bearer token
                .when()
                .get(getOzonetelAccounts_url);

        //log details and verify status code in extent report
        printRequestLogInReport(getOzonetelAccounts_url, "GET", commonRequestSpec(accessToken));
        printResponseLogInReport(response);
        validateJsonSchema(response, "Ozonetel/getOzonetelAccSchema.json");
        return response;
    }

    public static Response getOzonetelCampaigns(int ozonetelAccId) {
        // Construct the URI with path parameter
        String uri = String.format("%s%s/%s/%s", baseUrl, "/ozonetel", ozonetelAccId, "cloud-campaigns");


        // Send a request using the obtained access token
        response = RestAssured.given().log().all()
                .spec(commonRequestSpec(accessToken))// Set access token as Bearer token
                .when()
                .get("/ozonetel"+"/{ozonetelId}/cloud-campaigns", ozonetelAccId);

        //log details and verify status code in extent report

        printRequestLogInReport(uri, "GET", commonRequestSpec(accessToken));
        printResponseLogInReport(response);
        validateJsonSchema(response, "Ozonetel/getOzonetelCampaignsSchema.json");
        return response;
    }

}
