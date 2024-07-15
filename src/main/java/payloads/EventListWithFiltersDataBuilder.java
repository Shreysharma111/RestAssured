package payloads;

import org.testng.annotations.DataProvider;
import pojos.EventListWithFiltersPojo;
import utilities.GoogleSheetsUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

public class EventListWithFiltersDataBuilder {
    private static final String SPREADSHEET_ID = "1nWOicILt_LleQtEKM2ffgIaEda_8cVnE9C35OpKCGR8";
    private static final String SHEET_NAME = "EventListWithFilters";
    private static final String SHEET_RANGE = "A1:I7";

    //data builder for multiple scenerios from google sheets
    public static EventListWithFiltersPojo getCustomizedEventListWithFiltersData(LinkedHashMap<String, String> data) {
        EventListWithFiltersPojo eventListWithFiltersPojo=new EventListWithFiltersPojo();

        eventListWithFiltersPojo.setScenerioId(data.get("scenerioId"));
        eventListWithFiltersPojo.setScenerioDesc(data.get("scenerioDesc"));
        eventListWithFiltersPojo.setExpectedStatusCode(Integer.parseInt(data.get("expectedStatusCode")));
        if(!data.get("expectedMessage").equals("NO_DATA"))
            eventListWithFiltersPojo.setExpectedMessage(data.get("expectedMessage"));
        if(!data.get("entityId1").equalsIgnoreCase("NO_DATA"))
            eventListWithFiltersPojo.setEntityId1(data.get("entityId1"));
        if(!data.get("entityId2").equalsIgnoreCase("NO_DATA"))
            eventListWithFiltersPojo.setEntityId2(Collections.singletonList(Integer.valueOf(data.get("entityId2"))));
        if(!data.get("entityId3").equalsIgnoreCase("NO_DATA"))
            eventListWithFiltersPojo.setEntityId3(Integer.parseInt(data.get("entityId3")));
        if(!data.get("category1").equalsIgnoreCase("NO_DATA"))
            eventListWithFiltersPojo.setCategory1(data.get("category1"));
        if(!data.get("category2").equalsIgnoreCase("NO_DATA"))
            eventListWithFiltersPojo.setCategory2(data.get("category2"));

        return eventListWithFiltersPojo;
    }

    //data provider to get data from google sheets
    @DataProvider(name = "eventListWithFiltersData")
    public Iterator<EventListWithFiltersPojo> getEventListWithFiltersData() {
        List<LinkedHashMap<String, String>> sheetDataAsListOfMap = null;
        try {
            sheetDataAsListOfMap = GoogleSheetsUtils.getGoogleSheetsDataAsListOfMap(SPREADSHEET_ID,SHEET_NAME, SHEET_RANGE);
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        List<EventListWithFiltersPojo> eventListWithFiltersData = new ArrayList<>();
        for(LinkedHashMap<String,String> data : sheetDataAsListOfMap) {
            EventListWithFiltersPojo eventListWithFilters = EventListWithFiltersDataBuilder.getCustomizedEventListWithFiltersData(data);
            eventListWithFiltersData.add(eventListWithFilters);
        }
        return eventListWithFiltersData.iterator();
    }

    //data provider to get only positive case data from google sheets
    @DataProvider(name = "eventListWithFiltersSingleData")
    public static Iterator<EventListWithFiltersPojo> eventListWithFiltersSingleData() {
        List<LinkedHashMap<String, String>> sheetDataAsListOfMap = null;
        try {
            sheetDataAsListOfMap = GoogleSheetsUtils.getGoogleSheetsDataAsListOfMap(SPREADSHEET_ID,SHEET_NAME, "A1:I2");
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        List<EventListWithFiltersPojo> eventListWithFiltersData = new ArrayList<>();
        for(LinkedHashMap<String,String> data : sheetDataAsListOfMap) {
            EventListWithFiltersPojo eventListWithFilters = EventListWithFiltersDataBuilder.getCustomizedEventListWithFiltersData(data);
            eventListWithFiltersData.add(eventListWithFilters);
        }
        return eventListWithFiltersData.iterator();
    }
}
