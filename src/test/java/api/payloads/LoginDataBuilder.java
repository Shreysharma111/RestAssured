package api.payloads;

import api.pojos.Login;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.ResourceBundle;

public class LoginDataBuilder {
    private static final ResourceBundle usersBundle = ResourceBundle.getBundle("users");

    public static Login setupDataIT() {

        return Login
                .builder()
                .username(usersBundle.getString("IT"))
                .password(usersBundle.getString("password"))
                .build();

    }
    public static Login setupDataDr() {

        return Login
                .builder()
                .username(usersBundle.getString("doctor"))
                .password(usersBundle.getString("password"))
                .build();

    }
//    public static void assertIT(Response response) {
//        Assert.assertEquals(response.jsonPath().get("username"), usersBundle.getString("usernameIT"));
//    }
//    public static void assertDr(Response response) {
//        Assert.assertEquals(response.jsonPath().get("username"), usersBundle.getString("usernameDr"));
//    }

}
