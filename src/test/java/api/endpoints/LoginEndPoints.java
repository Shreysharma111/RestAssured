package api.endpoints;

import api.pojos.Login;
import api.utilities.JwtTokenUtil;
import io.restassured.response.Response;

import static api.endpoints.BaseEndPoints.*;
import static api.endpoints.IncidenceEndPoints.getUrl;
import static api.utilities.JwtTokenUtil.tokenChange;
import static io.restassured.RestAssured.given;

public class LoginEndPoints {
    private static Response response;
    private static String jwtToken;

    public static Response login(Login payload)
    {
        String login_url = getUrl().getString("base_url")+getUrl().getString("login_url");

        response = given()
                .spec(commonRequestSpecWithoutToken(payload))
                .when()
                .post(login_url);

//        validateJsonSchema(response, "loginSchema.json");
        //log details and verify status code in extent report
        printRequestLogInReport(login_url, "POST", commonRequestSpecWithoutToken(payload));
        printResponseLogInReport(response);

        JwtTokenUtil.jwtToken =response.jsonPath().getString("jwtToken");
        jwtToken =response.jsonPath().getString("jwtToken");
        tokenChange();
        return response;
    }

}
