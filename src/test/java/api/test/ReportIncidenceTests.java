package api.test;

import api.endpoints.UserEndPoints;
import api.payload.Accessories;
import api.payload.Images;
import api.payload.Login;
import api.payload.ReportIncidence;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.ResourceBundle;

import static org.hamcrest.Matchers.equalTo;

public class ReportIncidenceTests {
    ReportIncidence reportPayload;

    int assetId;
    String incidenceType;
    String assetName;
    String reporterRemark;
    List<Accessories> accessories;
    String incidenceSource;
    String incidenceBelongs;
    List<Images> images;

    @BeforeClass
    public void setupData() {
        reportPayload=new ReportIncidence();

        ResourceBundle usersBundle = ResourceBundle.getBundle("users");
        usernameIT = usersBundle.getString("IT");
        usernameDr = usersBundle.getString("doctor");
        password = usersBundle.getString("password");
        nameIT = usersBundle.getString("nameIT");
        nameDr = usersBundle.getString("nameDr");

        loginPayloadIT.setUsername(usernameIT);
        loginPayloadIT.setPassword(password);
        loginPayloadDr.setUsername(usernameDr);
        loginPayloadDr.setPassword(password);
    }

    @Test(priority = 1)
    public void testIncidenceReport() {
        Response response = UserEndPoints.reportIncidence(reportPayload);
// Verify keys in the response body
        response.then()
                .assertThat()
                .body("username", equalTo(usernameIT))
                .body("name", equalTo(nameIT))
                .body("id", equalTo(15))
                .statusCode(200);
        System.out.println("Response time : "+response.getTime()+"ms");
    }
}
