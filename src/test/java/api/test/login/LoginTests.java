package api.test.login;

import api.endpoints.IncidenceEndPoints;
import api.endpoints.LoginEndPoints;
import api.payloads.LoginDataBuilder;
import api.pojos.Login;
import api.utilities.AssertionUtils;
import api.utilities.GoogleSheetsUtils;
import api.utilities.reporting.Setup;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

public class LoginTests {

    @BeforeTest
    public void before(){
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Test(dataProvider = "getLoginScenerio", dataProviderClass = LoginDataBuilder.class)
    public void testLogin(Login login) {
        ExtentTest test = Setup.extentReports.createTest("Test Name - " + login.getScenerioId(), login.getScenerioDesc());
        Setup.extentTest.set(test);

        Response response = LoginEndPoints.login(login);

        if(login.getExpectedStatusCode() != 200){
            if(login.getScenerioId().equalsIgnoreCase("Login_4") || login.getScenerioId().equalsIgnoreCase("Login_6")) {
                Assert.assertEquals(response.getStatusCode(), login.getExpectedStatusCode());
                Assert.assertEquals(response.jsonPath().getString("message"), login.getExpectedMessage());
            }
            else
                Assert.assertEquals(response.getStatusCode(), login.getExpectedStatusCode());
        }
        else {
            if(login.getScenerioId().equalsIgnoreCase("Login_1")) {
                response = LoginEndPoints.login(login);
                Assert.assertEquals(response.getStatusCode(), login.getExpectedStatusCode());
                Assert.assertEquals(response.jsonPath().getString("message"), login.getExpectedMessage());
            }
            else {
                Map<String, Object> expectedValueMap = new HashMap<>();
                expectedValueMap.put("username", login.getUsername());
            AssertionUtils.assertExpectedValuesWithJsonPath(response, expectedValueMap);
            }
        }

    }

}
