package winchcamp;

import Dataproviders.Dataproviders;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import payloads.EventsByFiltersDataBuilder;
import pojos.EventsByFiltersPojo;
import utilities.OAuth2Authorization;
import utilities.reporting.Setup;

import java.util.Iterator;

import static endpoints.winchcamp.EventsByFilters.*;
import static payloads.EventsByFiltersDataBuilder.getEventsByFiltersPositiveData;
import static utilities.RestAssuredUtils.printResponseLogInReport;
import static utilities.RestAssuredUtils.validateJsonSchema;
import static utilities.restAssuredFunctions.API.*;

public class EventsByFiltersTests {
    private static Response response;

    @BeforeClass
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        RestAssured.useRelaxedHTTPSValidation();
    }

    @Test(dataProviderClass = EventsByFiltersDataBuilder.class, dataProvider = "getEventsByFiltersPositiveData")
    public void jsonSchemaValidationCase(EventsByFiltersPojo payload) {
        ExtentTest test = Setup.extentReports.createTest("EventsByFilters_SchemaValidation", "schema validation case");
        Setup.extentTest.set(test);
        response = eventsByFiltersPositiveCase(payload);

        validateJsonSchema(response, "schema/Winchcamp/eventsByFilters.json");

    }

    @Test(dataProviderClass = Dataproviders.class, dataProvider = "headerDataProvider")
    public void emptyAndWrongAuthTokenCase(String key, String value) {
        ExtentTest test = Setup.extentReports.createTest("AllCameraConfigs_EmptyAndWrongAuth", "authorization token case : empty token | wrong token");
        Setup.extentTest.set(test);
        // Logic to use payload data from getEventsByFiltersPositiveData data provider
        Iterator<EventsByFiltersPojo> eventsIterator = getEventsByFiltersPositiveData();
        while (eventsIterator.hasNext()) {
            EventsByFiltersPojo eventData = eventsIterator.next();

            response = eventsByFiltersHeaderCase(eventData, key + ":" + "Bearer " + value);

            assertStatusCode(response, 401);
            assertKeyValue(response, "error", "Unauthorized");
        }
    }

    @Test(dataProviderClass = EventsByFiltersDataBuilder.class, dataProvider = "getEventsByFiltersPositiveData")
    public void wrongEndPointCase(EventsByFiltersPojo payload) {
        ExtentTest test = Setup.extentReports.createTest("AllCameraConfigs_WrongEndpoint", "wrong endpoint case");
        Setup.extentTest.set(test);
        response = eventsByFiltersWrongEndpointCase(payload);

        assertStatusCode(response, 404);
        assertKeyValue(response, "error", "Not Found");
    }

    @Test(dataProviderClass = EventsByFiltersDataBuilder.class, dataProvider = "getEventsByFiltersPositiveData")
    public void wrongMethodCase(EventsByFiltersPojo payload) {
        ExtentTest test = Setup.extentReports.createTest("AllCameraConfigs_WrongMethod", "wrong request method case");
        Setup.extentTest.set(test);
        response = eventsByFiltersMethodCase(payload);

        assertStatusCode(response, 405);
        assertKeyValue(response, "error", "Method Not Allowed");
    }


    @Test(dataProviderClass = EventsByFiltersDataBuilder.class, dataProvider = "getEventsByFiltersData")
    public void BodyCase(EventsByFiltersPojo payload) {
        ExtentTest test = Setup.extentReports.createTest(payload.getScenerioId(), payload.getScenerioDesc());
        Setup.extentTest.set(test);

        response = eventsByFiltersPositiveCase(payload);

        if(payload.getExpectedStatusCode()!=200) {
            if(payload.getScenerioId().equalsIgnoreCase("EventsByFiltersBodyScenerio_3")) {
                assertStatusCode(response, 500);
                assertKeyValue(response, "message", payload.getExpectedMessage());
            }
            else {
                assertStatusCode(response, 400);
                assertKeyValue(response, "message", payload.getExpectedMessage());
            }
        }
        else {
            if(payload.getScenerioId().equalsIgnoreCase("EventsByFiltersBodyScenerio_2"))
                assertArrayIsPresentAndEmpty(response, "data.data");
            else {
                assertKeyValue(response, "data.data[0].entityId1", payload.getEntityId1());
            }
        }
        assertResponseTime(response);

    }

    @AfterMethod
    public static void afterMethodRespLog() {
        printResponseLogInReport(response);
    }
}
