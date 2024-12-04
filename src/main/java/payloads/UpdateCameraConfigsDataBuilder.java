package payloads;

import org.testng.annotations.DataProvider;
import pojos.UpdateCameraConfigsPojo;
import utilities.GoogleSheetsUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

public class UpdateCameraConfigsDataBuilder {
    private static final String SPREADSHEET_ID = "1nWOicILt_LleQtEKM2ffgIaEda_8cVnE9C35OpKCGR8";
    private static final String SHEET_NAME = "UpdateCameraConfigs";
    private static final String SHEET_RANGE = "A1:L9";

    //data builder for multiple scenerios from google sheets
    public static UpdateCameraConfigsPojo getCustomizedUpdateCameraConfigsData(LinkedHashMap<String, String> data, Integer dynamicId, Integer dynamicCameraId, String guid, Integer dynamicMonitoringStatus) {
        UpdateCameraConfigsPojo updateCameraConfigsPojo = new UpdateCameraConfigsPojo();

        updateCameraConfigsPojo.setScenerioId(data.get("scenerioId"));
        updateCameraConfigsPojo.setScenerioDesc(data.get("scenerioDesc"));
        updateCameraConfigsPojo.setExpectedStatusCode(Integer.parseInt(data.get("expectedStatusCode")));
        updateCameraConfigsPojo.setExpectedMessage(data.get("expectedMessage"));
        updateCameraConfigsPojo.setId(dynamicId != null ? dynamicId :
                (!data.get("id").equalsIgnoreCase("NO_DATA") ? Integer.valueOf(data.get("id")) : null));
        if(!data.get("areaId").equalsIgnoreCase("NO_DATA"))
            updateCameraConfigsPojo.setAreaId(Integer.parseInt(data.get("areaId")));
        if(!data.get("facilityId").equalsIgnoreCase("NO_DATA"))
            updateCameraConfigsPojo.setFacilityId(Integer.parseInt(data.get("facilityId")));
        // Set dynamicRtspUrl if provided, otherwise use value from LinkedHashMap
        updateCameraConfigsPojo.setCameraId(dynamicCameraId != null ? dynamicCameraId : Integer.parseInt(data.get("cameraId")));
        updateCameraConfigsPojo.setGuid(guid != null ? guid :
                (!data.get("guid").equalsIgnoreCase("NO_DATA") ? data.get("guid") : null));

        updateCameraConfigsPojo.setMonitoringStatus(dynamicMonitoringStatus != null ? dynamicMonitoringStatus : Integer.parseInt(data.get("monitoringStatus")));
        if(!data.get("rtspUrl").equalsIgnoreCase("NO_DATA"))
            updateCameraConfigsPojo.setRtspUrl(data.get("rtspUrl"));
        updateCameraConfigsPojo.setEventId(Arrays.asList(data.get("eventId").split(",")));

        return updateCameraConfigsPojo;
    }

    //data provider to get data from google sheets
    @DataProvider(name = "updateCameraConfigsData")
    public Iterator<UpdateCameraConfigsPojo> updateCameraConfigsData() {
        List<LinkedHashMap<String, String>> sheetDataAsListOfMap = null;
        try {
            sheetDataAsListOfMap = GoogleSheetsUtils.getGoogleSheetsDataAsListOfMap(SPREADSHEET_ID,SHEET_NAME, SHEET_RANGE);
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        List<UpdateCameraConfigsPojo> updateCameraConfigsData = new ArrayList<>();
        for(LinkedHashMap<String,String> data : sheetDataAsListOfMap) {
            UpdateCameraConfigsPojo updateCameraConfigs = UpdateCameraConfigsDataBuilder.getCustomizedUpdateCameraConfigsData(data, null,null, null, null);
            updateCameraConfigsData.add(updateCameraConfigs);
        }
        return updateCameraConfigsData.iterator();
    }
    @DataProvider(name = "updateCameraConfigsSingleData")
    public static Iterator<UpdateCameraConfigsPojo> updateCameraConfigsSingleData() {
        List<LinkedHashMap<String, String>> sheetDataAsListOfMap = null;
        try {
            sheetDataAsListOfMap = GoogleSheetsUtils.getGoogleSheetsDataAsListOfMap(SPREADSHEET_ID,SHEET_NAME, "A1:L2");
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        List<UpdateCameraConfigsPojo> updateCameraConfigsData = new ArrayList<>();
        for(LinkedHashMap<String,String> data : sheetDataAsListOfMap) {
            UpdateCameraConfigsPojo updateCameraConfigs = UpdateCameraConfigsDataBuilder.getCustomizedUpdateCameraConfigsData(data, null,null, null,null);
            updateCameraConfigsData.add(updateCameraConfigs);
        }
        return updateCameraConfigsData.iterator();
    }
    @DataProvider(name = "updateCameraConfigsIngnData")
    public static Iterator<UpdateCameraConfigsPojo> updateCameraConfigsIngnData(Integer dynamicId, Integer cameraId, String guid, Integer monitoringStatus) {
        List<LinkedHashMap<String, String>> sheetDataAsListOfMap = null;
        try {
            sheetDataAsListOfMap = GoogleSheetsUtils.getGoogleSheetsDataAsListOfMap(SPREADSHEET_ID,SHEET_NAME, "A1:L2");
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        List<UpdateCameraConfigsPojo> updateCameraConfigsData = new ArrayList<>();
        for(LinkedHashMap<String,String> data : sheetDataAsListOfMap) {
            UpdateCameraConfigsPojo updateCameraConfigs = UpdateCameraConfigsDataBuilder.getCustomizedUpdateCameraConfigsData(data, dynamicId, cameraId, guid,monitoringStatus);
            updateCameraConfigsData.add(updateCameraConfigs);
        }
        return updateCameraConfigsData.iterator();
    }
}
