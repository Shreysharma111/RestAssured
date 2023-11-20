package api.test;

import api.endpoints.UserEndPoints;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class IncidenceListTests {
    @Test(priority = 1)
    public void testIncidenceList() {
        Response response = UserEndPoints.incidenceList();
// Verify keys in the response body
        response.then()
                .assertThat()
                .statusCode(200);
        System.out.println("Response time : "+response.getTime()+"ms");
    }
}
