package winchcamp;

import Dataproviders.Dataproviders;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import payloads.CSVGenerationForAreaCameraReportDataBuilder;
import pojos.CSVGenerationForAreaCameraReportPojo;
import utilities.OAuth2Authorization;
import utilities.reporting.Setup;

import java.util.Iterator;

import static endpoints.winchcamp.CSVGenerationForAreaCameraReport.*;
import static payloads.CSVGenerationForAreaCameraReportDataBuilder.csvGenerationForAreaCameraReportSingleData;
import static utilities.RestAssuredUtils.printResponseLogInReport;
import static utilities.RestAssuredUtils.validateJsonSchema;
import static utilities.restAssuredFunctions.API.assertKeyValue;
import static utilities.restAssuredFunctions.API.assertStatusCode;

public class CSVGenerationForAreaCameraReportTests {
    private static Response response;
    @BeforeClass
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test(dataProviderClass = CSVGenerationForAreaCameraReportDataBuilder.class, dataProvider = "csvGenerationForAreaCameraReportSingleData")
    public void jsonSchemaValidationCase(CSVGenerationForAreaCameraReportPojo payload) {
        ExtentTest test = Setup.extentReports.createTest("CSVGenerationForAreaCameraReport_SchemaValidation", "schema validation case").assignCategory("CSVGenerationForAreaCameraReport");
        Setup.extentTest.set(test);
        response = csvGenerationForAreaCameraReportPositiveCase(payload);

        validateJsonSchema(response, "schema/Winchcamp/csvGenerationForAreaCameraReport.json");
    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider")
    public void emptyAndWrongAuthTokenCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("CSVGenerationForAreaCameraReport_EmptyAndWrongAuth", "authorization token case : empty token | wrong token").assignCategory("CSVGenerationForAreaCameraReport");
        Setup.extentTest.set(test);
        // Logic to use payload data from updateCameraConfigsSingleData data provider
        Iterator<CSVGenerationForAreaCameraReportPojo> eventsIterator = csvGenerationForAreaCameraReportSingleData();
        while (eventsIterator.hasNext()) {
            CSVGenerationForAreaCameraReportPojo eventData = eventsIterator.next();

            response = csvGenerationForAreaCameraReportHeaderCase(eventData, key + ":" + "Bearer " + value);

            assertStatusCode(response, 401);
            assertKeyValue(response, "error", "Unauthorized");
        }
    }
        @Test(dataProviderClass = CSVGenerationForAreaCameraReportDataBuilder.class, dataProvider = "csvGenerationForAreaCameraReportSingleData")
        public void wrongEndPointCase(CSVGenerationForAreaCameraReportPojo payload) {
            ExtentTest test = Setup.extentReports.createTest("CSVGenerationForAreaCameraReport_WrongEndpoint", "wrong endpoint case").assignCategory("CSVGenerationForAreaCameraReport");
            Setup.extentTest.set(test);
            response = csvGenerationForAreaCameraReportWrongEndpointCase(payload);

            assertStatusCode(response, 404);
            assertKeyValue(response, "error", "Not Found");
        }
    @Test(dataProviderClass = CSVGenerationForAreaCameraReportDataBuilder.class, dataProvider = "csvGenerationForAreaCameraReportSingleData")
    public void wrongMethodCase(CSVGenerationForAreaCameraReportPojo payload) {
        ExtentTest test = Setup.extentReports.createTest("CSVGenerationForAreaCameraReport_WrongMethod", "wrong request method case").assignCategory("CSVGenerationForAreaCameraReport");
        Setup.extentTest.set(test);
        response = csvGenerationForAreaCameraReportMethodCase(payload);

        assertStatusCode(response, 405);
        assertKeyValue(response, "error", "Method Not Allowed");
    }
    @Test(dataProviderClass = CSVGenerationForAreaCameraReportDataBuilder.class, dataProvider = "csvGenerationForAreaCameraReportData")
    public void bodyCase(CSVGenerationForAreaCameraReportPojo payload) {
        ExtentTest test = Setup.extentReports.createTest(payload.getScenerioId(), payload.getScenerioDesc()).assignCategory("CSVGenerationForAreaCameraReport");
        Setup.extentTest.set(test);

        response = csvGenerationForAreaCameraReportPositiveCase(payload);
        if(payload.getScenerioId().equalsIgnoreCase("CSVGenerationForAreaCameraReport_BodyScenerio3")) {
            assertKeyValue(response, "message", payload.getExpectedMessage());
        }
        assertStatusCode(response, payload.getExpectedStatusCode());
    }
    @AfterMethod
    public static void afterMethodRespLog() {

        printResponseLogInReport(response);
    }
}

