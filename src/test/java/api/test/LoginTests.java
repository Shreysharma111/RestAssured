package api.test;

import api.endpoints.UserEndPoints;
import api.payload.Login;
import api.utilities.ExtentManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.equalTo;

public class LoginTests {

    Login loginPayloadIT;
    Login loginPayloadDr;

    String usernameIT;
    String nameIT;
    String usernameDr;
    String nameDr;
    String password;

    private ExtentReports extent;
    private ExtentTest test;
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
        extent = ExtentManager.getInstance();
        test = extent.createTest("login with IT user", "verify username, name and id");
        Response response = UserEndPoints.login(loginPayloadIT);
// Verify keys in the response body
        response.then()
                .assertThat()
                .body("username", equalTo(usernameIT))
                .body("name", equalTo(nameIT))
                .body("id", equalTo(15))
                .statusCode(200);
        test.info("Status code : "+response.getStatusCode());
        test.info("Response time : "+response.getTimeIn(TimeUnit.MILLISECONDS)+"ms");
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
        test.info("Status code : "+response.getStatusCode());
        test.info("Response time : "+response.getTimeIn(TimeUnit.MILLISECONDS)+"ms");
        logger.info("Status code : "+response.getStatusCode());
        logger.info("Response time : "+response.getTimeIn(TimeUnit.MILLISECONDS)+"ms");
        extent.flush();
    }
}
