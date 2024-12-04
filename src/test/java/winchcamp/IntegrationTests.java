package winchcamp;

import com.aventstack.extentreports.ExtentTest;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojos.SaveCameraConfigsPojo;
import utilities.OAuth2Authorization;
import utilities.reporting.Setup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static endpoints.winchcamp.ActivateCamera.activateCameraQueryParamCase;
import static endpoints.winchcamp.AllCameraConfigs.allCameraConfigsPositiveCase;
import static endpoints.winchcamp.CreateCamera.createCameraPositiveCase;
import static endpoints.winchcamp.DeactivateCamera.deactivateCameraPositiveCase;
import static endpoints.winchcamp.DeleteCameraConfigsByConfigId.deleteCameraConfigsByConfigIdPositiveCase;
import static endpoints.winchcamp.EditMasterCamera.editMasterCameraPositiveCase;
import static endpoints.winchcamp.GetAllCamera.getAllCameraPositiveCase;
import static endpoints.winchcamp.GetCameraConfigsByConfigId.getCameraConfigsByConfigIdPositiveCase;
import static payloads.IntegrationDataBuilder.*;
import static utilities.RestAssuredUtils.*;
import static utilities.restAssuredFunctions.API.*;

public class IntegrationTests {
    private String cameraNameValue;
    private String cameraNameNewValue;
    private int idValue;
    private int cameraIdInGetAllCameraResponse;
    private String guidInGetAllCameraResponse;
    private String keywordValue;
    private SaveCameraConfigsPojo saveCameraConfigsPayload;
    private int statusValue;
    private int monitoringStatusPassed;
    private int monitoringStatusUpdated;
    private int dynamicMonitoringStatusValue;
    private static int cameraConfigId = Integer.parseInt(getValue("winchcamp", "dynamicCameraConfigId"));

    private String rtspPassed;
    private String rtspUpdated;
    private String imageCreatedAtPassed;
    private String eventStatusPassed;
    private static Response response;
    private static List<String> eventIdPassed;
    private static List<String> eventIdUpdated;
    private static final String DELETE_SUCCESS_MSG = "Facility configuration with id : "+cameraConfigId+"  deleted successfully";

    @BeforeClass
    public void before(){
        OAuth2Authorization.getAccessTokenAndUpdateToken();
        cameraNameValue = getValue("winchcamp", "cameraNameIngn");
        cameraNameNewValue = getValue("winchcamp", "editedCameraName");
        idValue = Integer.parseInt(getValue("winchcamp", "idForActivateDeactivateCamera"));
        keywordValue = getValue("winchcamp", "keywordIngn");
//        rtspPassed = getSaveCameraConfigData().getRtspUrl();
//        rtspUpdated = getUpdateCameraConfigsData().getRtspUrl();
        statusValue = Integer.parseInt(getValue("winchcamp", "statusIngn"));
        dynamicMonitoringStatusValue = Integer.parseInt(getValue("winchcamp", "dynamicMonitoringStatus"));
//        eventIdPassed =getSaveCameraConfigData().getEventId();
//        eventIdUpdated =getUpdateCameraConfigsData().getEventId();
//        monitoringStatusPassed = getSaveCameraConfigData().getMonitoringStatus();
//        monitoringStatusUpdated = getUpdateCameraConfigsData().getMonitoringStatus();
//        imageCreatedAtPassed = getSaveEventsData().getImage_created_at();
//        eventStatusPassed = getSaveEventsData().getCategory2();
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test(priority = 1)
    public void masterCameraIntegartionCase() {
        ExtentTest test = Setup.extentReports.createTest("MasterCameraIntegrationCase", "Create -> Deactivate -> Activate -> Edit flow").assignCategory("IntegrationTests");
        Setup.extentTest.set(test);

        response=createCameraPositiveCase("cameraName"+":"+cameraNameValue);
        assertStatusCode(response, 200);
        response = getAllCameraPositiveCase("keyword"+":"+cameraNameValue, "status"+":"+statusValue);
        assertKeyValue(response, "data.content[0].name", cameraNameValue);
        cameraIdInGetAllCameraResponse = response.jsonPath().getInt("data.content[0].id");
        guidInGetAllCameraResponse = response.jsonPath().getString("data.content[0].guid");

        deactivateCameraPositiveCase("id"+":"+cameraIdInGetAllCameraResponse);
        response = getAllCameraPositiveCase("keyword"+":"+cameraNameValue, "status"+":"+statusValue);
        assertArrayIsPresentAndEmpty(response, "data.content");
        activateCameraQueryParamCase("id"+":"+cameraIdInGetAllCameraResponse);
        response = getAllCameraPositiveCase("keyword"+":"+cameraNameValue, "status"+":"+statusValue);
        assertKeyValue(response, "data.content[0].name", cameraNameValue);

        editMasterCameraPositiveCase("cameraName"+":"+cameraNameNewValue, "id"+":"+cameraIdInGetAllCameraResponse);
        response = getAllCameraPositiveCase("keyword"+":"+cameraNameNewValue, "status"+":"+statusValue);
        assertKeyValue(response, "data.content[0].name", cameraNameNewValue);

        response = getAllCameraPositiveCase("keyword"+":"+cameraNameValue, "status"+":"+statusValue);
        assertArrayIsPresentAndEmpty(response, "data.content");

    }
    @Test(priority = 2, dependsOnMethods = "masterCameraIntegartionCase")
    public void cameraConfigIntegartionCase() {
        ExtentTest test = Setup.extentReports.createTest("CameraConfigIntegrationCase", "Save CC -> Update CC -> Save Event -> Manual override -> Delete CC flow").assignCategory("IntegrationTests");
        Setup.extentTest.set(test);

        //passing data from google sheet Scenario1 only overriding cameraId because the mentioned one in google sheet already configured at time of SaveCameraConfigs
        response = saveCameraConfigIntegrationCase(cameraIdInGetAllCameraResponse, null, null);
        assertStatusCode(response, 200);
        response = allCameraConfigsPositiveCase();
        cameraConfigId = (Integer) findFieldByGuid(response, "data", "guid", guidInGetAllCameraResponse, "id");
        response = csvGenerationForAreaCameraReportIntegrationCase();
        rtspPassed = getSaveCameraConfigData().getRtspUrl();
        eventIdPassed =getSaveCameraConfigData().getEventId();
        monitoringStatusPassed = getSaveCameraConfigData().getMonitoringStatus();

        // Conditions to validate
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("monitoringStatus", 0);
        conditions.put("rtspUrl", rtspPassed);
        conditions.put("status", "INACTIVE");
        conditions.put("events", eventIdPassed);
        conditions.put("cameraName", cameraNameNewValue);

        assertConditionsByGuid(response, "data","guid", guidInGetAllCameraResponse, conditions);

        // updating only id here and rest updates from UpdateCameraConfig google sheet Scenario1(rtspUrl, eventId, monitoringStatus)
        response = updateCameraConfigsIntegrationCase(cameraConfigId, cameraIdInGetAllCameraResponse, guidInGetAllCameraResponse, null);
        assertStatusCode(response, 200);
        response = getCameraConfigsByConfigIdPositiveCase(cameraConfigId);
        rtspUpdated = getUpdateCameraConfigsData().getRtspUrl();
        eventIdUpdated =getUpdateCameraConfigsData().getEventId();

        // Updated conditions to validate
        conditions.put("rtspUrl", rtspUpdated);
        conditions.put("events", eventIdUpdated);

        assertConditionsByGuid(response, "data","cameraGuid", guidInGetAllCameraResponse, conditions);

        // save event
        response = saveEventsIntegrationCase(guidInGetAllCameraResponse, cameraNameNewValue);
        assertStatusCode(response, 200);

        // use eventsByFilters Scenario1 from google sheets to validate saved event
        response = eventsByFiltersIntegrationCase(guidInGetAllCameraResponse);
        imageCreatedAtPassed = getSaveEventsData().getImage_created_at();

        // Conditions to validate
        Map<String, Object> conditions2 = new HashMap<>();
        conditions2.put("imageCreatedAt", imageCreatedAtPassed);

        assertConditionsByGuid(response, "data","entityId1", guidInGetAllCameraResponse, conditions2);

        // delete camera config
        response = deleteCameraConfigsByConfigIdPositiveCase(cameraConfigId);
        assertKeyValue(response, "message", DELETE_SUCCESS_MSG);
        monitoringStatusUpdated = getUpdateCameraConfigsData().getMonitoringStatus();

        response = csvGenerationForAreaCameraReportIntegrationCase(monitoringStatusUpdated);

        // Conditions to validate
        Map<String, Object> conditions3 = new HashMap<>();
        conditions3.put("status", "DELETED");

        validateFields(response, "guid", guidInGetAllCameraResponse, conditions3);

    }
}

