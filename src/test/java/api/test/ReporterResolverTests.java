package api.test;

import api.endpoints.UserEndPoints;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ResourceBundle;

public class ReporterResolverTests {
    private int incidenceId;
    @BeforeClass
    public void setupData() {
        ResourceBundle incDet = ResourceBundle.getBundle("resolveIncidence");
        incidenceId = Integer.parseInt(incDet.getString("incidenceId"));

    }
    @Test(priority = 1)
    public void testReporterResolver() {
        Response response = UserEndPoints.reporterResolverDetails(incidenceId);
// Verify keys in the response body
        response.then()
                .assertThat()
                .statusCode(200);
        System.out.println("Response time : "+response.getTime()+"ms");
    }
}
