package api.test;

import api.endpoints.UserEndPoints;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ResourceBundle;

public class IncidenceHistoryTests {

    private int assetId;
    @BeforeClass
    public void setupData() {
        ResourceBundle incDet = ResourceBundle.getBundle("incidenceHistory");
        assetId = Integer.parseInt(incDet.getString("assetId"));

    }
    @Test(priority = 1)
    public void testIncidenceHistory() {
        Response response = UserEndPoints.incidenceHistory(assetId);
// Verify keys in the response body
        response.then()
                .assertThat()
                .statusCode(200);
        System.out.println("Response time : "+response.getTime()+"ms");
    }
}
