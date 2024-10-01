package winchcamp;

import Dataproviders.Dataproviders;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.datafaker.Faker;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utilities.OAuth2Authorization;
import utilities.reporting.Setup;

import static endpoints.winchcamp.CreateCamera.*;
import static utilities.RestAssuredUtils.*;
import static utilities.restAssuredFunctions.API.*;

public class CreateCameraTests {
    private String cameraNameValue;
    private static Response response;

    @BeforeClass
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        // Initialize the Faker instance to get random camera names
        Faker faker = new Faker();
        cameraNameValue = faker.name().firstName();
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test
    public void positiveCase() {
        ExtentTest test = Setup.extentReports.createTest("CreateCamera_PositiveCase", "test positive flow" ).assignCategory("CreateCamera");
        Setup.extentTest.set(test);

        response = createCameraPositiveCase("cameraName"+":"+cameraNameValue);

        assertStatusCode(response, 200);
        assertKeyValue(response, "message", "Camera Created successfully.");
        assertKeyValue(response, "status", "200");
        assertResponseTime(response);

    }
    @Test
    public void jsonSchemaValidationCase() {
        ExtentTest test = Setup.extentReports.createTest("CreateCamera_SchemaValidation", "schema validation case").assignCategory("CreateCamera");
        Setup.extentTest.set(test);
        response = createCameraQueryParamCase("cameraName"+":"+cameraNameValue);

        validateJsonSchema(response, "schema/Winchcamp/createCamera.json");
    }

    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider")
    public void emptyAndWrongAuthTokenCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("CreateCamera_EmptyAndWrongAuth", "authorization token case : empty token | wrong token").assignCategory("CreateCamera");
        Setup.extentTest.set(test);
        response = createCameraHeaderCase(cameraNameValue, key+":"+"Bearer "+value);

        assertStatusCode(response, 401);
    }

    @Test(dataProviderClass = Dataproviders.class, dataProvider = "queryDataProviderForCreateCamera")
    public void emptyAndWrongParamCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("CreateCamera_EmptyAndWrongParam", "query param case : empty param | wrong param").assignCategory("CreateCamera");
        Setup.extentTest.set(test);
        response = createCameraQueryParamCase(key+":"+value);

        assertStatusCode(response, 400);
        assertKeyValue(response, "message", "Required request parameter 'cameraName' for method parameter type String is not present");
    }

    @Test
    public void correctAuthTokenCase() {
        ExtentTest test = Setup.extentReports.createTest("CreateCamera_CorrectAuth", "authorization token case : correct token").assignCategory("CreateCamera");
        Setup.extentTest.set(test);
        response = createCameraHeaderCase(cameraNameValue,"Authorization:"+"Bearer "+getToken());

        assertStatusCode(response, 200);
    }

    @Test
    public void wrongEndPointCase() {
        ExtentTest test = Setup.extentReports.createTest("CreateCamera_WrongEndpoint", "wrong endpoint case").assignCategory("CreateCamera");
        Setup.extentTest.set(test);
        response = createCameraWrongEndpointCase("cameraName"+":"+cameraNameValue);

        assertStatusCode(response, 404);
    }
    @Test
    public void wrongMethodCase() {
        ExtentTest test = Setup.extentReports.createTest("CreateCamera_WrongMethod", "wrong request method case").assignCategory("CreateCamera");
        Setup.extentTest.set(test);
        response = createCameraMethodCase("cameraName"+":"+cameraNameValue);

        assertStatusCode(response, 405);

    }

    @Test
    public void duplicateCameraCreationCase() {
        ExtentTest test = Setup.extentReports.createTest("CreateCamera_DuplicateCamera", "duplicate camera creation case").assignCategory("CreateCamera");
        Setup.extentTest.set(test);
        createCameraPositiveCase("cameraName"+":"+cameraNameValue);
        response = createCameraPositiveCase("cameraName"+":"+cameraNameValue);

        assertStatusCode(response, 200); //duplicate camera names are allowed for now

    }
    @AfterMethod
    public static void afterMethodForAssertionHead() {
        printResponseLogInReport(response);
    }
}
