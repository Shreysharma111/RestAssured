package api.test;

import api.endpoints.UserEndPoints;
import api.endpoints.UserEndPoints2;
import api.payload.Login;
import api.utilities.reporting.Setup;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class LoginTests extends Setup {

    Login loginPayloadIT;
    Login loginPayloadDr;

    String usernameIT;
    String nameIT;
    String usernameDr;
    String nameDr;
    String password;

    private final Logger logger = LogManager.getLogger(this.getClass());
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
        Response response = UserEndPoints2.login(loginPayloadIT);
// Verify keys and log in the report
        logApiDetails(response);
        logResultAndDetails(response);
        logger.info("Status code : "+response.getStatusCode());
        logger.info("Response time : "+response.getTimeIn(TimeUnit.MILLISECONDS)+"ms");
    }
    @Test(priority = 2)
    public void testLoginDr() {
        Response response = UserEndPoints2.login(loginPayloadDr);
// Verify keys and log in the report
        logApiDetails(response);
        logResultAndDetails(response);
        logger.info("Status code : "+response.getStatusCode());
        logger.info("Response time : "+response.getTimeIn(TimeUnit.MILLISECONDS)+"ms");
    }
}
