package payloads;

import org.testng.annotations.DataProvider;
import pojos.SaveCameraConfigsPojo;
import utilities.GoogleSheetsUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

public class SaveCameraConfigsDataBuilder {
    private static final String SPREADSHEET_ID = "1nWOicILt_LleQtEKM2ffgIaEda_8cVnE9C35OpKCGR8";
    private static final String SHEET_NAME = "SaveCameraConfigs";
    private static final String SHEET_RANGE = "A1:J8";

    //data builder for multiple scenerios from google sheets
    public static SaveCameraConfigsPojo getCustomizedSaveCameraConfigsData(LinkedHashMap<String, String> data, Integer dynamicCameraId, String dynamicRtspUrl, Integer dynamicMonitoringStatus) {
        SaveCameraConfigsPojo saveCameraConfigsPojo = new SaveCameraConfigsPojo();

        saveCameraConfigsPojo.setScenerioId(data.get("scenerioId"));
        saveCameraConfigsPojo.setScenerioDesc(data.get("scenerioDesc"));
        saveCameraConfigsPojo.setExpectedStatusCode(Integer.parseInt(data.get("expectedStatusCode")));
        saveCameraConfigsPojo.setExpectedMessage(data.get("expectedMessage"));
        saveCameraConfigsPojo.setAreaId(Integer.parseInt(data.get("areaId")));
        saveCameraConfigsPojo.setFacilityId(Integer.parseInt(data.get("facilityId")));

        // Set dynamicRtspUrl if provided, otherwise use value from LinkedHashMap
        saveCameraConfigsPojo.setCameraId(dynamicCameraId != null ? dynamicCameraId : Integer.parseInt(data.get("cameraId")));
        saveCameraConfigsPojo.setRtspUrl(dynamicRtspUrl != null ? dynamicRtspUrl :
                (!data.get("rtspUrl").equalsIgnoreCase("NO_DATA") ? data.get("rtspUrl") : null));

        saveCameraConfigsPojo.setMonitoringStatus(dynamicMonitoringStatus != null ? dynamicMonitoringStatus : Integer.parseInt(data.get("monitoringStatus")));
        if(!data.get("eventId").equalsIgnoreCase("NO_DATA"))
            saveCameraConfigsPojo.setEventId(Collections.singletonList(data.get("eventId")));

        return saveCameraConfigsPojo;
        }
    //data provider to get data from google sheets
    @DataProvider(name = "saveCameraConfigsData")
    public Iterator<SaveCameraConfigsPojo> saveCameraConfigsData() {
        List<LinkedHashMap<String, String>> sheetDataAsListOfMap = null;
        try {
            sheetDataAsListOfMap = GoogleSheetsUtils.getGoogleSheetsDataAsListOfMap(SPREADSHEET_ID,SHEET_NAME, SHEET_RANGE);
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        List<SaveCameraConfigsPojo> saveCameraConfigsData = new ArrayList<>();
        for(LinkedHashMap<String,String> data : sheetDataAsListOfMap) {
            SaveCameraConfigsPojo saveCameraConfigs = SaveCameraConfigsDataBuilder.getCustomizedSaveCameraConfigsData(data, null, null, null);
            saveCameraConfigsData.add(saveCameraConfigs);
        }
        return saveCameraConfigsData.iterator();
    }
    @DataProvider(name = "saveCameraConfigsSingleData")
    public static Iterator<SaveCameraConfigsPojo> saveCameraConfigsSingleData() {
        List<LinkedHashMap<String, String>> sheetDataAsListOfMap = null;
        try {
            sheetDataAsListOfMap = GoogleSheetsUtils.getGoogleSheetsDataAsListOfMap(SPREADSHEET_ID,SHEET_NAME, "A1:J2");
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        List<SaveCameraConfigsPojo> saveCameraConfigsData = new ArrayList<>();
        for(LinkedHashMap<String,String> data : sheetDataAsListOfMap) {
            SaveCameraConfigsPojo saveCameraConfigs = SaveCameraConfigsDataBuilder.getCustomizedSaveCameraConfigsData(data, null, null, null);
            saveCameraConfigsData.add(saveCameraConfigs);
        }
        return saveCameraConfigsData.iterator();
    }
    @DataProvider(name = "saveCameraConfigsIngnData")
    public static Iterator<SaveCameraConfigsPojo> saveCameraConfigsIngnData(Integer dynamicCameraId, String dynamicRtsp, Integer dynamicMonitoringStatus) {
        List<LinkedHashMap<String, String>> sheetDataAsListOfMap = null;
        try {
            sheetDataAsListOfMap = GoogleSheetsUtils.getGoogleSheetsDataAsListOfMap(SPREADSHEET_ID,SHEET_NAME, "A1:J2");
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        List<SaveCameraConfigsPojo> saveCameraConfigsData = new ArrayList<>();
        for(LinkedHashMap<String,String> data : sheetDataAsListOfMap) {
            SaveCameraConfigsPojo saveCameraConfigs = SaveCameraConfigsDataBuilder.getCustomizedSaveCameraConfigsData(data, dynamicCameraId, dynamicRtsp, dynamicMonitoringStatus);
            saveCameraConfigsData.add(saveCameraConfigs);
        }
        return saveCameraConfigsData.iterator();
    }
}
