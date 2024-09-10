package payloads;

import org.testng.annotations.DataProvider;
import pojos.EventsByFilterPojo;
import utilities.GoogleSheetsUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import static payloads.EventsByFiltersDataBuilder.setupPageableRequest;

public class EventsByFilterDataBuilder {
    private static final String SPREADSHEET_ID = "1nWOicILt_LleQtEKM2ffgIaEda_8cVnE9C35OpKCGR8";
    private static final String SHEET_NAME = "EventsByFilter";
    private static final String SHEET_RANGE = "A1:R9";

    //data builder for multiple scenerios from google sheets
    public static EventsByFilterPojo getCustomizedEventsByFilterData(LinkedHashMap<String, String> data) {
        EventsByFilterPojo eventsByFilterPojo = new EventsByFilterPojo();

        eventsByFilterPojo.setScenerioId(data.get("scenerioId"));
        eventsByFilterPojo.setScenerioDesc(data.get("scenerioDesc"));
        eventsByFilterPojo.setExpectedStatusCode(Integer.parseInt(data.get("expectedStatusCode")));
        if (!data.get("expectedMessage").equals("NO_DATA"))
            eventsByFilterPojo.setExpectedMessage(data.get("expectedMessage"));
        if (!data.get("facilityId").equalsIgnoreCase("NO_DATA"))
            eventsByFilterPojo.setFacilityId(Integer.parseInt(data.get("facilityId")));
        if (!data.get("regionId").equalsIgnoreCase("NO_DATA"))
            eventsByFilterPojo.setRegionId(Integer.parseInt(data.get("regionId")));
        if (!data.get("areaId").equalsIgnoreCase("NO_DATA"))
            eventsByFilterPojo.setAreaId(Integer.parseInt(data.get("areaId")));
        if (!data.get("zoneId").equalsIgnoreCase("NO_DATA"))
            eventsByFilterPojo.setZoneId(Integer.parseInt(data.get("zoneId")));
        if (!data.get("event").equalsIgnoreCase("NO_DATA"))
            eventsByFilterPojo.setEvent(data.get("event"));
        if (!data.get("status").equalsIgnoreCase("NO_DATA"))
            eventsByFilterPojo.setStatus(data.get("status"));
        if (!data.get("toDate").equalsIgnoreCase("NO_DATA"))
            eventsByFilterPojo.setToDate(data.get("toDate"));
        if (!data.get("fromDate").equalsIgnoreCase("NO_DATA"))
            eventsByFilterPojo.setFromDate(data.get("fromDate"));
        eventsByFilterPojo.setPage(setupPageableRequest(data));
        eventsByFilterPojo.setCSV(Boolean.parseBoolean(data.get("isCSV")));
        return eventsByFilterPojo;
    }
    //data provider to get data from google sheets
    @DataProvider(name = "getEventsByFilterData")
    public Iterator<EventsByFilterPojo> getEventsByFilterData() {
        List<LinkedHashMap<String, String>> sheetDataAsListOfMap = null;
        try {
            sheetDataAsListOfMap = GoogleSheetsUtils.getGoogleSheetsDataAsListOfMap(SPREADSHEET_ID,SHEET_NAME, SHEET_RANGE);
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        List<EventsByFilterPojo> eventsByFilterData = new ArrayList<>();
        for(LinkedHashMap<String,String> data : sheetDataAsListOfMap) {
            EventsByFilterPojo eventsByFilter = EventsByFilterDataBuilder.getCustomizedEventsByFilterData(data);
            eventsByFilterData.add(eventsByFilter);
        }
        return eventsByFilterData.iterator();
    }
    @DataProvider(name = "getEventsByFilterSingleData")
    public static Iterator<EventsByFilterPojo> getEventsByFilterSingleData() {
        List<LinkedHashMap<String, String>> sheetDataAsListOfMap = null;
        try {
            sheetDataAsListOfMap = GoogleSheetsUtils.getGoogleSheetsDataAsListOfMap(SPREADSHEET_ID,SHEET_NAME, "A1:Q2");
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        List<EventsByFilterPojo> eventsByFilterData = new ArrayList<>();
        for(LinkedHashMap<String,String> data : sheetDataAsListOfMap) {
            EventsByFilterPojo eventsByFilter = EventsByFilterDataBuilder.getCustomizedEventsByFilterData(data);
            eventsByFilterData.add(eventsByFilter);
        }
        return eventsByFilterData.iterator();
    }
}