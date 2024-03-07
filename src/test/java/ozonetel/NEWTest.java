package ozonetel;

import Dataproviders.Dataproviders;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static utilities.RestAssuredUtils.*;

public class NEWTest {
    static String getSkills_url = getUrl().getString("base_url") + getUrl().getString("getSkills_url");
Response response;


    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider")
    public void testHeader(String headerKey,String headerValue){

        RestAssured.given().log().all().header(headerKey,headerValue).get(getSkills_url).then().statusCode(200);



    }

    @Test
    public void getOzonetelCampaigns(int ozonetelAccId, String... headers) {
        String uri = String.format("%s%s/%s/%s", baseUrl, "/ozonetel", ozonetelAccId, "cloud-campaigns");


        // Send a request using the obtained access token
        response = RestAssured.given().log().all()
                .spec(commonRequestSpecGet(headers))
                .when()
                .get("/ozonetel"+"/{ozonetelId}/cloud-campaigns", ozonetelAccId);

        //log details and verify status code in extent report

        printRequestLogInReport(uri, "GET", commonRequestSpecGet(headers));
        printResponseLogInReport(response);
        validateJsonSchema(response, "schema/Ozonetel/getOzonetelCampaignsSchema.json");

    }
}
