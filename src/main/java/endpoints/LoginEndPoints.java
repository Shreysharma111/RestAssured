package endpoints;

import api.pojos.Login;
import io.restassured.response.Response;

import static utilities.RestAssuredUtils.*;
import static endpoints.IncidenceEndPoints.getUrl;
import static io.restassured.RestAssured.given;

public class LoginEndPoints {
    private static Response response;
    private static String oAuthToken;

    public static Response login(Login payload, String oAuthToken, String... headers)
    {
        String login_url = getUrl().getString("base_url")+getUrl().getString("login_url");

        response = RestAssured.given()
                .spec(commonRequestSpecWithoutToken(payload, headers))
                .when()
                .post(login_url);

//        validateJsonSchema(response, "loginSchema.json");
        //log details and verify status code in extent report
        printRequestLogInReport(login_url, "POST", commonRequestSpecWithoutToken(payload, headers));
        printResponseLogInReport(response);


        return response;
    }

}
