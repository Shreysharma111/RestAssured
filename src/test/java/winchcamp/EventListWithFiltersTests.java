package winchcamp;

import Dataproviders.Dataproviders;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import payloads.EventListWithFiltersDataBuilder;
import pojos.EventListWithFiltersPojo;
import utilities.OAuth2Authorization;
import utilities.reporting.Setup;

import java.util.Iterator;

import static endpoints.winchcamp.EventListWithFilters.*;
import static payloads.EventListWithFiltersDataBuilder.eventListWithFiltersSingleData;
import static utilities.RestAssuredUtils.printResponseLogInReport;
import static utilities.RestAssuredUtils.validateJsonSchema;
import static utilities.restAssuredFunctions.API.*;

public class EventListWithFiltersTests {
    private static Response response;

    @BeforeClass
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Test(dataProviderClass = EventListWithFiltersDataBuilder.class, dataProvider = "eventListWithFiltersSingleData")
    public void jsonSchemaValidationCase(EventListWithFiltersPojo payload) {
        ExtentTest test = Setup.extentReports.createTest("EventsByFilters_SchemaValidation", "schema validation case");
        Setup.extentTest.set(test);
        response = eventListWithFiltersPositiveCase(payload);

        validateJsonSchema(response, "schema/Winchcamp/eventListWithFilters.json");

    }

    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider")
    public void emptyAndWrongAuthTokenCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("EventListWithFilters_EmptyAndWrongAuth", "authorization token case : empty token | wrong token");
        Setup.extentTest.set(test);
        // Logic to use payload data from getEventsByFiltersSingleData data provider
        Iterator<EventListWithFiltersPojo> eventsIterator = eventListWithFiltersSingleData();
        while (eventsIterator.hasNext()) {
            EventListWithFiltersPojo eventData = eventsIterator.next();

            response = eventListWithFiltersHeaderCase(eventData, key + ":" + "Bearer " + value);

            assertStatusCode(response, 401);
            assertKeyValue(response, "error", "Unauthorized");
        }
    }

    @Test(dataProviderClass = EventListWithFiltersDataBuilder.class, dataProvider = "eventListWithFiltersSingleData")
    public void wrongEndPointCase(EventListWithFiltersPojo payload) {
        ExtentTest test = Setup.extentReports.createTest("EventListWithFilters_WrongEndpoint", "wrong endpoint case");
        Setup.extentTest.set(test);
        response = eventListWithFiltersWrongEndpointCase(payload);

        assertStatusCode(response, 404);
        assertKeyValue(response, "error", "Not Found");
    }

    @Test(dataProviderClass = EventListWithFiltersDataBuilder.class, dataProvider = "eventListWithFiltersSingleData")
    public void wrongMethodCase(EventListWithFiltersPojo payload) {
        ExtentTest test = Setup.extentReports.createTest("EventListWithFilters_WrongMethod", "wrong request method case");
        Setup.extentTest.set(test);
        response = eventListWithFiltersMethodCase(payload);

        assertStatusCode(response, 405);
        assertKeyValue(response, "error", "Method Not Allowed");
    }

    @Test(dataProviderClass = EventListWithFiltersDataBuilder.class, dataProvider = "eventListWithFiltersData")
    public void BodyCase(EventListWithFiltersPojo payload) {
        ExtentTest test = Setup.extentReports.createTest(payload.getScenerioId(), payload.getScenerioDesc());
        Setup.extentTest.set(test);

        response = eventListWithFiltersPositiveCase(payload);

        if(payload.getExpectedStatusCode()!=200) {
            if(payload.getScenerioId().equalsIgnoreCase("EventListWithFilters_BodyScenerio3")) {
                assertStatusCode(response, 204);
            }
            else {
                assertStatusCode(response, 400);
                assertKeyValue(response, "message", payload.getExpectedMessage());
            }
        }
        else {
            assertKeyValue(response, "status", String.valueOf(payload.getExpectedStatusCode()));
        }
        assertResponseTime(response);

    }

    @AfterMethod
    public static void afterMethodRespLog() {
        printResponseLogInReport(response);
    }
}
