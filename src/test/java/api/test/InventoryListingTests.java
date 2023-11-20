package api.test;

import api.endpoints.UserEndPoints;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

public class InventoryListingTests {
    @Test(priority = 1)
    public void testInventoryListing() {
        Response response = UserEndPoints.inventoryListing();
// Verify keys in the response body
        response.then()
                .assertThat()
                .statusCode(200);
        System.out.println("Response time : "+response.getTime()+"ms");

    }
}
