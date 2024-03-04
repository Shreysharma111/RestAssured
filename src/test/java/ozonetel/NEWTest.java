package ozonetel;

import Dataprovider.Dataprovider;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static utilities.RestAssuredUtils.getUrl;

public class NEWTest {
    static String getSkills_url = getUrl().getString("base_url") + getUrl().getString("getSkills_url");



    @Test(dataProviderClass = Dataprovider.class, dataProvider = "headerDataProvider")
    public void testHeader(String headerKey,String headerValue){

        RestAssured.given().log().all().header(headerKey,headerValue).get(getSkills_url).then().statusCode(200);



    }
}
