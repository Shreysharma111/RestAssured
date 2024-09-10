package winchcamp;

import Dataproviders.Dataproviders;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import payloads.EventsByFilterDataBuilder;
import pojos.EventsByFilterPojo;
import utilities.OAuth2Authorization;
import utilities.reporting.Setup;

import java.util.Iterator;

import static endpoints.winchcamp.EventsByFilter.*;
import static payloads.EventsByFilterDataBuilder.getEventsByFilterSingleData;
import static utilities.RestAssuredUtils.printResponseLogInReport;
import static utilities.RestAssuredUtils.validateJsonSchema;
import static utilities.restAssuredFunctions.API.*;

public class EventsByFilterTests {
    private static Response response;

    @BeforeClass
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test(dataProviderClass = EventsByFilterDataBuilder.class, dataProvider = "getEventsByFilterSingleData")
    public void jsonSchemaValidationCase(EventsByFilterPojo payload) {
        ExtentTest test = Setup.extentReports.createTest("EventsByFilter_SchemaValidation", "schema validation case").assignCategory("EventsByFilter");
        Setup.extentTest.set(test);
        response = eventsByFilterPositiveCase(payload);

        validateJsonSchema(response, "schema/Winchcamp/eventsByFilter.json");
    }
    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider")
    public void emptyAndWrongAuthTokenCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("EventsByFilter_EmptyAndWrongAuth", "authorization token case : empty token | wrong token").assignCategory("EventsByFilter");
        Setup.extentTest.set(test);
        // Logic to use payload data from getEventsByFiltersPositiveData data provider
        Iterator<EventsByFilterPojo> eventsIterator = getEventsByFilterSingleData();
        while (eventsIterator.hasNext()) {
            EventsByFilterPojo eventData = eventsIterator.next();

            response = eventsByFilterHeaderCase(eventData, key + ":" + "Bearer " + value);

            assertStatusCode(response, 401);
            assertKeyValue(response, "error", "Unauthorized");
        }
    }
    @Test(dataProviderClass = EventsByFilterDataBuilder.class, dataProvider = "getEventsByFilterSingleData")
    public void wrongEndPointCase(EventsByFilterPojo payload) {
        ExtentTest test = Setup.extentReports.createTest("EventsByFilter_WrongEndpoint", "wrong endpoint case").assignCategory("EventsByFilter");
        Setup.extentTest.set(test);
        response = eventsByFilterWrongEndpointCase(payload);

        assertStatusCode(response, 404);
        assertKeyValue(response, "error", "Not Found");
    }
    @Test(dataProviderClass = EventsByFilterDataBuilder.class, dataProvider = "getEventsByFilterSingleData")
    public void wrongMethodCase(EventsByFilterPojo payload) {
        ExtentTest test = Setup.extentReports.createTest("EventsByFilter_WrongMethod", "wrong request method case").assignCategory("EventsByFilter");
        Setup.extentTest.set(test);
        response = eventsByFilterMethodCase(payload);

        assertStatusCode(response, 405);
        assertKeyValue(response, "error", "Method Not Allowed");
    }
    @Test(dataProviderClass = EventsByFilterDataBuilder.class, dataProvider = "getEventsByFilterData")
    public void BodyCase(EventsByFilterPojo payload) {
        ExtentTest test = Setup.extentReports.createTest(payload.getScenerioId(), payload.getScenerioDesc()).assignCategory("EventsByFilter");
        Setup.extentTest.set(test);

        response = eventsByFilterPositiveCase(payload);

        if(payload.getScenerioId().equalsIgnoreCase("EventsByFilterBodyScenerio_3")) {
            assertKeyValue(response, "message", payload.getExpectedMessage());
        }
        if(payload.getScenerioId().equalsIgnoreCase("EventsByFilterBodyScenerio_1")) {
            validateDatesWithinRange(response, payload.getFromDate(), payload.getToDate());
        }
        assertStatusCode(response, payload.getExpectedStatusCode());
        assertResponseTime(response);
    }
    @AfterMethod
    public static void afterMethodRespLog() {
        printResponseLogInReport(response);
    }
}
