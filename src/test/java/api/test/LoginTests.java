package api.test;

import api.endpoints.UserEndPoints;
import api.payload.Login;
import io.restassured.response.Response;
import org.testng.annotations.*;
import java.util.ResourceBundle;
import static org.hamcrest.Matchers.equalTo;

public class LoginTests {

    Login loginPayloadIT;
    Login loginPayloadDr;

    String usernameIT;
    String nameIT;
    String usernameDr;
    String nameDr;
    String password;
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
        System.out.println("Response time : "+response.getTime()+"ms");
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
        System.out.println("Response time : "+response.getTime()+"ms");
    }
}
