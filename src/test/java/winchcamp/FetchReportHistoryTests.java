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

import static endpoints.winchcamp.FetchReportHistory.*;
import static utilities.RestAssuredUtils.*;
import static utilities.restAssuredFunctions.API.*;

public class FetchReportHistoryTests {
    private static int reportIdForAreaCameraReportValue;
    private static int reportIdForAIEventReportValue;
    private static Response response;
    @BeforeSuite
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        reportIdForAreaCameraReportValue = Integer.parseInt(getValue("winchcamp", "reportIdForAreaCameraReport"));
        reportIdForAIEventReportValue = Integer.parseInt(getValue("winchcamp", "reportIdForAIEventReport"));
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "pathDataProviderForFetchReportHistory")
    public void positiveCase(int value) {
        ExtentTest test = Setup.extentReports.createTest("FetchReportHistory_PositiveCase", "test positive flow for area camera report and ai event report history" );
        Setup.extentTest.set(test);

        response = fetchReportHistoryPositiveCase(value);
        assertStatusCode(response, 200);
        assertResponseTime(response);
    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "pathDataProviderForFetchReportHistory")
    public void jsonSchemaValidationCase(int value) {
        ExtentTest test = Setup.extentReports.createTest("FetchReportHistory_SchemaValidation", "schema validation case");
        Setup.extentTest.set(test);
        response = fetchReportHistoryPositiveCase(value);

        validateJsonSchema(response, "schema/Winchcamp/fetchReportHistory.json");
    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider")
    public void emptyAndWrongAuthTokenCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("FetchReportHistory_EmptyAndWrongAuth", "authorization token case : empty token | wrong token");
        Setup.extentTest.set(test);
        response = fetchReportHistoryHeaderCase(reportIdForAIEventReportValue,key+":"+"Bearer "+value);

        assertStatusCode(response, 401);
    }
    @Test
    public void wrongEndPointCase() {
        ExtentTest test = Setup.extentReports.createTest("FetchReportHistory_WrongEndpoint", "wrong endpoint case");
        Setup.extentTest.set(test);
        response = fetchReportHistoryWrongEndpointCase(reportIdForAIEventReportValue);

        assertStatusCode(response, 404);
    }
    @Test
    public void wrongMethodCase() {
        ExtentTest test = Setup.extentReports.createTest("FetchReportHistory_WrongMethod", "wrong request method case");
        Setup.extentTest.set(test);
        response = fetchReportHistoryMethodCase(reportIdForAIEventReportValue);

        assertStatusCode(response, 405);
    }
    //used this dataprovider as we need invalid int values like 0 and 99999. In this case, this dataprovider best offers the usecase
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "pathDataProviderForDeleteCameraConfigByConfigId")
    public void invalidReportIdCase(int reportIdValue) {
        ExtentTest test = Setup.extentReports.createTest("FetchReportHistory_InvalidGuid", "invalid guid as path param case");
        Setup.extentTest.set(test);
        response = fetchReportHistoryPositiveCase(reportIdValue);

        assertStatusCode(response, 200);
        assertArrayIsPresentAndEmpty(response, "data.data");
        assertArrayIsPresentAndEmpty(response, "data.history");
    }
    @AfterMethod
    public static void afterMethodRespLog() {
        printResponseLogInReport(response);
    }
}
