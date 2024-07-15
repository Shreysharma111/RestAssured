package payloads;

import org.testng.annotations.DataProvider;
import pojos.EventsByFiltersPojo;
import pojos.PageableRequestPojo;
import utilities.GoogleSheetsUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

public class EventsByFiltersDataBuilder {
    private static final String SPREADSHEET_ID = "1nWOicILt_LleQtEKM2ffgIaEda_8cVnE9C35OpKCGR8";
    private static final String SHEET_NAME = "EventsByFilters";
    private static final String SHEET_RANGE = "A1:R7";

    //data builder for multiple scenerios from google sheets
    public static EventsByFiltersPojo getCustomizedEventsByFiltersData(LinkedHashMap<String, String> data) {
        EventsByFiltersPojo eventsByFiltersPojo=new EventsByFiltersPojo();

        eventsByFiltersPojo.setScenerioId(data.get("scenerioId"));
        eventsByFiltersPojo.setScenerioDesc(data.get("scenerioDesc"));
        eventsByFiltersPojo.setExpectedStatusCode(Integer.parseInt(data.get("expectedStatusCode")));
        if(!data.get("expectedMessage").equals("NO_DATA"))
            eventsByFiltersPojo.setExpectedMessage(data.get("expectedMessage"));
        if(!data.get("entityId1").equalsIgnoreCase("NO_DATA"))
        eventsByFiltersPojo.setEntityId1(data.get("entityId1"));
        if(!data.get("entityId2").equalsIgnoreCase("NO_DATA"))
            eventsByFiltersPojo.setEntityId2(Collections.singletonList(Integer.valueOf(data.get("entityId2"))));
        if(!data.get("entityId3").equalsIgnoreCase("NO_DATA"))
            eventsByFiltersPojo.setEntityId3(Integer.parseInt(data.get("entityId3")));
        if(!data.get("entityId4").equalsIgnoreCase("NO_DATA"))
            eventsByFiltersPojo.setEntityId4(Integer.parseInt(data.get("entityId4")));
        if(!data.get("category1").equalsIgnoreCase("NO_DATA"))
            eventsByFiltersPojo.setCategory1(data.get("category1"));
        if(!data.get("category2").equalsIgnoreCase("NO_DATA"))
            eventsByFiltersPojo.setCategory2(data.get("category2"));
        if(!data.get("fromDate").equalsIgnoreCase("NO_DATA"))
            eventsByFiltersPojo.setFromDate(data.get("fromDate"));
        if(!data.get("toDate").equalsIgnoreCase("NO_DATA"))
            eventsByFiltersPojo.setToDate(data.get("toDate"));
        if(!data.get("scenerioId").equalsIgnoreCase("EventsByFiltersBodyScenerio_3"))
            eventsByFiltersPojo.setPageableRequest(setupPageableRequest(data));
        if(!data.get("searchText").equalsIgnoreCase("NO_DATA"))
            eventsByFiltersPojo.setSearchText(data.get("searchText"));
//
        return eventsByFiltersPojo;
    }

    //data provider to get data from google sheets
    @DataProvider(name = "getEventsByFiltersData")
    public Iterator<EventsByFiltersPojo> getEventsByFiltersData() {
        List<LinkedHashMap<String, String>> sheetDataAsListOfMap = null;
        try {
            sheetDataAsListOfMap = GoogleSheetsUtils.getGoogleSheetsDataAsListOfMap(SPREADSHEET_ID,SHEET_NAME, SHEET_RANGE);
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        List<EventsByFiltersPojo> eventsByFiltersData = new ArrayList<>();
        for(LinkedHashMap<String,String> data : sheetDataAsListOfMap) {
            EventsByFiltersPojo eventsByFilters = EventsByFiltersDataBuilder.getCustomizedEventsByFiltersData(data);
            eventsByFiltersData.add(eventsByFilters);
        }
        return eventsByFiltersData.iterator();
    }

    //data provider to get only positive case data from google sheets
    @DataProvider(name = "getEventsByFiltersPositiveData")
    public static Iterator<EventsByFiltersPojo> getEventsByFiltersPositiveData() {
        List<LinkedHashMap<String, String>> sheetDataAsListOfMap = null;
        try {
            sheetDataAsListOfMap = GoogleSheetsUtils.getGoogleSheetsDataAsListOfMap(SPREADSHEET_ID,SHEET_NAME, "A1:R2");
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        List<EventsByFiltersPojo> eventsByFiltersData = new ArrayList<>();
        for(LinkedHashMap<String,String> data : sheetDataAsListOfMap) {
            EventsByFiltersPojo eventsByFilters = EventsByFiltersDataBuilder.getCustomizedEventsByFiltersData(data);
            eventsByFiltersData.add(eventsByFilters);
        }
        return eventsByFiltersData.iterator();
    }

    public static PageableRequestPojo setupPageableRequest(LinkedHashMap<String, String> data) {
        return PageableRequestPojo
                .builder()
                .draw(Integer.valueOf(data.get("draw")))
                .page(Integer.parseInt(data.get("page")))
                .size(Integer.parseInt(data.get("size")))
                .sortBy(data.get("sortBy"))
                .sortType(data.get("sortType"))
                .build();
    }

}
