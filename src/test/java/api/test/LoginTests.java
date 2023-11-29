package api.test;

import api.endpoints.UserEndPoints;
import api.payload.Login;
import api.utilities.reporting.ExtentReportManager;
import api.utilities.reporting.Setup;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.equalTo;
public class LoginTests extends Setup {

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
// Verify keys and log in the report
        logApiDetails(response);
        logResultAndDetails(response);
        logger.info("Status code : "+response.getStatusCode());
        logger.info("Response time : "+response.getTimeIn(TimeUnit.MILLISECONDS)+"ms");
    }
    @Test(priority = 2)
    public void testLoginDr() {
        Response response = UserEndPoints.login(loginPayloadDr);
// Verify keys and log in the report
        logApiDetails(response);
        logResultAndDetails(response);
        logger.info("Status code : "+response.getStatusCode());
        logger.info("Response time : "+response.getTimeIn(TimeUnit.MILLISECONDS)+"ms");
    }
}
