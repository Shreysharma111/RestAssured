package winchcamp;

import Dataproviders.Dataproviders;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import utilities.OAuth2Authorization;
import utilities.reporting.Setup;

import static endpoints.winchcamp.FetchAllArea.*;
import static utilities.RestAssuredUtils.*;
import static utilities.restAssuredFunctions.API.*;

public class FetchAllAreaTests {

    private static Response response;

    @BeforeSuite
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Test
    public void positiveCase() {
        ExtentTest test = Setup.extentReports.createTest("FetchAllArea_PositiveCase", "test positive flow" ).assignCategory("FetchAllArea");
        Setup.extentTest.set(test);

        response = fetchAllAreaPositiveCase();

        assertStatusCode(response, 200);
        assertFieldsExistInEachObject(response, "id", "name");
        assertKeyValue(response, "status", "200");
        assertResponseTime(response);
    }
    @Test
    public void jsonSchemaValidationCase() {
        ExtentTest test = Setup.extentReports.createTest("FetchAllArea_SchemaValidation", "schema validation case").assignCategory("FetchAllArea");
        Setup.extentTest.set(test);
        response = fetchAllAreaPositiveCase();

        validateJsonSchema(response, "schema/Winchcamp/fetchAllArea.json");
    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider")
    public void emptyAndWrongAuthTokenCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("FetchAllArea_EmptyAndWrongAuth", "authorization token case : empty token | wrong token").assignCategory("FetchAllArea");
        Setup.extentTest.set(test);
        response = fetchAllAreaHeaderCase(key+":"+"Bearer "+value);

        assertStatusCode(response, 401);
    }
    @Test
    public void correctAuthTokenCase() {
        ExtentTest test = Setup.extentReports.createTest("FetchAllArea_CorrectAuth", "authorization token case : correct token").assignCategory("FetchAllArea");
        Setup.extentTest.set(test);
        response = fetchAllAreaHeaderCase("Authorization:"+"Bearer "+getToken());

        assertStatusCode(response, 200);
    }
    @Test
    public void wrongEndPointCase() {
        ExtentTest test = Setup.extentReports.createTest("FetchAllArea_WrongEndpoint", "wrong endpoint case").assignCategory("FetchAllArea");
        Setup.extentTest.set(test);
        response = fetchAllAreaWrongEndpointCase();

        assertStatusCode(response, 404);
    }
    @Test
    public void wrongMethodCase() {
        ExtentTest test = Setup.extentReports.createTest("FetchAllArea_WrongMethod", "wrong request method case").assignCategory("FetchAllArea");
        Setup.extentTest.set(test);
        response = fetchAllAreaMethodCase();

        assertStatusCode(response, 405);
    }
    @AfterMethod
    public static void afterMethodRespLog() {
        printResponseLogInReport(response);
    }
}

