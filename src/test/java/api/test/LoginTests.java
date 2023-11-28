package api.test;

import api.endpoints.UserEndPoints;
import api.payload.Login;
import api.utilities.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.equalTo;
public class LoginTests extends ExtentManager {

    Login loginPayloadIT;
    Login loginPayloadDr;

    String usernameIT;
    String nameIT;
    String usernameDr;
    String nameDr;
    String password;

    private Logger logger = LogManager.getLogger(this.getClass());
    @BeforeClass
    public void setupData() {
        loginPayloadIT=new Login();
        loginPayloadDr=new Login();

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
    public void testLoginIT() {
        Response response = UserEndPoints.login(loginPayloadIT);
// Verify keys in the response body
        response.then()
                .assertThat()
                .body("username", equalTo(usernameIT))
                .body("name", equalTo(nameIT))
                .body("id", equalTo(15))
                .statusCode(200);
        int statusCode = response.getStatusCode();
        String responseTime = response.getTimeIn(TimeUnit.MILLISECONDS)+"ms";
        String respo = response.getBody().asString();
        ExtentManager.logStatusCodeAndResponseBody(test, statusCode, responseTime, respo);

        logger.info("Status code : "+response.getStatusCode());
        logger.info("Response time : "+response.getTimeIn(TimeUnit.MILLISECONDS)+"ms");
    }
    @Test(priority = 2)
    public void testLoginDr() {
        Response response = UserEndPoints.login(loginPayloadDr);
// Verify keys in the response body
        response.then()
                .assertThat()
                .body("username", equalTo(usernameDr))
                .body("name", equalTo(nameDr))
                .body("id", equalTo(13))
                .statusCode(200);
        int statusCode = response.getStatusCode();
        String responseTime = response.getTimeIn(TimeUnit.MILLISECONDS)+"ms";
        String respo = response.getBody().asString();
        ExtentManager.logStatusCodeAndResponseBody(test, statusCode, responseTime, respo);

        logger.info("Status code : "+response.getStatusCode());
        logger.info("Response time : "+response.getTimeIn(TimeUnit.MILLISECONDS)+"ms");
    }
}
