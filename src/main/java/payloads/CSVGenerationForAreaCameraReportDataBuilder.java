package payloads;

import org.testng.annotations.DataProvider;
import pojos.CSVGenerationForAreaCameraReportPojo;
import utilities.GoogleSheetsUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class CSVGenerationForAreaCameraReportDataBuilder {
    private static final String SPREADSHEET_ID = "1nWOicILt_LleQtEKM2ffgIaEda_8cVnE9C35OpKCGR8";
    private static final String SHEET_NAME = "CSVGenerationForAreaCameraReport";
    private static final String SHEET_RANGE = "A1:K8";

    //data builder for multiple scenerios from google sheets
    public static CSVGenerationForAreaCameraReportPojo getCustomizedCSVGenerationForAreaCameraReportData(LinkedHashMap<String, String> data, Integer monitoringStatus) {
        CSVGenerationForAreaCameraReportPojo csvGenerationForAreaCameraReportPojo = new CSVGenerationForAreaCameraReportPojo();

        csvGenerationForAreaCameraReportPojo.setScenerioId(data.get("scenerioId"));
        csvGenerationForAreaCameraReportPojo.setScenerioDesc(data.get("scenerioDesc"));
        csvGenerationForAreaCameraReportPojo.setExpectedStatusCode(Integer.parseInt(data.get("expectedStatusCode")));
        if (!data.get("expectedMessage").equalsIgnoreCase("NO_DATA"))
            csvGenerationForAreaCameraReportPojo.setExpectedMessage(data.get("expectedMessage"));
        if (!data.get("regionId").equalsIgnoreCase("NO_DATA"))
            csvGenerationForAreaCameraReportPojo.setRegionId(Integer.parseInt(data.get("regionId")));
        if (!data.get("zoneId").equalsIgnoreCase("NO_DATA"))
            csvGenerationForAreaCameraReportPojo.setZoneId(Integer.parseInt(data.get("zoneId")));
        if (!data.get("facilityId").equalsIgnoreCase("NO_DATA"))
            csvGenerationForAreaCameraReportPojo.setFacilityId(Integer.parseInt(data.get("facilityId")));
        if (!data.get("fromDate").equalsIgnoreCase("NO_DATA"))
            csvGenerationForAreaCameraReportPojo.setFromDate(data.get("fromDate"));
        if (!data.get("toDate").equalsIgnoreCase("NO_DATA"))
            csvGenerationForAreaCameraReportPojo.setToDate(data.get("toDate"));
        csvGenerationForAreaCameraReportPojo.setMonitoringStatus(monitoringStatus != null ? monitoringStatus :
                (!data.get("monitoringStatus").equalsIgnoreCase("NO_DATA") ? Integer.valueOf(data.get("monitoringStatus")) : null));

        if (!data.get("allEntries").equalsIgnoreCase("NO_DATA"))
            csvGenerationForAreaCameraReportPojo.setAllEntries(data.get("allEntries"));
        return csvGenerationForAreaCameraReportPojo;

    }

    //data provider to get data from google sheets
    @DataProvider(name = "csvGenerationForAreaCameraReportData")
    public Iterator<CSVGenerationForAreaCameraReportPojo> csvGenerationForAreaCameraReportData() {
        List<LinkedHashMap<String, String>> sheetDataAsListOfMap = null;
        try {
            sheetDataAsListOfMap = GoogleSheetsUtils.getGoogleSheetsDataAsListOfMap(SPREADSHEET_ID,SHEET_NAME, SHEET_RANGE);
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        List<CSVGenerationForAreaCameraReportPojo> csvGenerationForAreaCameraReportData = new ArrayList<>();
        for(LinkedHashMap<String,String> data : sheetDataAsListOfMap) {
            CSVGenerationForAreaCameraReportPojo csvGenerationForAreaCameraReport = CSVGenerationForAreaCameraReportDataBuilder.getCustomizedCSVGenerationForAreaCameraReportData(data, null);
            csvGenerationForAreaCameraReportData.add(csvGenerationForAreaCameraReport);
        }
        return csvGenerationForAreaCameraReportData.iterator();
    }
    @DataProvider(name = "csvGenerationForAreaCameraReportSingleData")
    public static Iterator<CSVGenerationForAreaCameraReportPojo> csvGenerationForAreaCameraReportSingleData() {
        List<LinkedHashMap<String, String>> sheetDataAsListOfMap = null;
        try {
            sheetDataAsListOfMap = GoogleSheetsUtils.getGoogleSheetsDataAsListOfMap(SPREADSHEET_ID,SHEET_NAME, "A1:K2");
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        List<CSVGenerationForAreaCameraReportPojo> csvGenerationForAreaCameraReportData = new ArrayList<>();
        for(LinkedHashMap<String,String> data : sheetDataAsListOfMap) {
            CSVGenerationForAreaCameraReportPojo csvGenerationForAreaCameraReport = CSVGenerationForAreaCameraReportDataBuilder.getCustomizedCSVGenerationForAreaCameraReportData(data, null);
            csvGenerationForAreaCameraReportData.add(csvGenerationForAreaCameraReport);
        }
        return csvGenerationForAreaCameraReportData.iterator();
    }

    @DataProvider(name = "csvGenerationForAreaCameraReportDataModified")
    public static Iterator<CSVGenerationForAreaCameraReportPojo> csvGenerationForAreaCameraReportDataModified() {
        List<LinkedHashMap<String, String>> sheetDataAsListOfMap = null;
        try {
            sheetDataAsListOfMap = GoogleSheetsUtils.getGoogleSheetsDataAsListOfMap(SPREADSHEET_ID,SHEET_NAME, SHEET_RANGE);
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        List<CSVGenerationForAreaCameraReportPojo> data = new ArrayList<>();

        for (LinkedHashMap<String, String> row : sheetDataAsListOfMap) {
            CSVGenerationForAreaCameraReportPojo pojo = GenericDataBuilder.buildDataFromMap(row, CSVGenerationForAreaCameraReportPojo.class);
            data.add(pojo);
        }

        return data.iterator();
    }
    public static Iterator<CSVGenerationForAreaCameraReportPojo> csvGenerationForAreaCameraReportIngnData(Integer monitoringStatus) {
        List<LinkedHashMap<String, String>> sheetDataAsListOfMap = null;
        try {
            sheetDataAsListOfMap = GoogleSheetsUtils.getGoogleSheetsDataAsListOfMap(SPREADSHEET_ID,SHEET_NAME, "A1:K2");
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        List<CSVGenerationForAreaCameraReportPojo> csvGenerationForAreaCameraReportData = new ArrayList<>();
        for(LinkedHashMap<String,String> data : sheetDataAsListOfMap) {
            CSVGenerationForAreaCameraReportPojo csvGenerationForAreaCameraReport = CSVGenerationForAreaCameraReportDataBuilder.getCustomizedCSVGenerationForAreaCameraReportData(data, monitoringStatus);
            csvGenerationForAreaCameraReportData.add(csvGenerationForAreaCameraReport);
        }
        return csvGenerationForAreaCameraReportData.iterator();
    }

}
