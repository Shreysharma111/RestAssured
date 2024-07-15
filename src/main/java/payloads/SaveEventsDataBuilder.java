package payloads;

import org.testng.annotations.DataProvider;
import pojos.SaveEventsPojo;
import utilities.GoogleSheetsUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class SaveEventsDataBuilder {
    private static final String SPREADSHEET_ID = "1nWOicILt_LleQtEKM2ffgIaEda_8cVnE9C35OpKCGR8";
    private static final String SHEET_NAME = "SaveEvents";
    private static final String SHEET_RANGE = "A1:X12";

    //data builder for multiple scenerios from google sheets
    public static SaveEventsPojo getCustomizedSaveEventsData(LinkedHashMap<String, String> data) {
        SaveEventsPojo saveEventsPojo = new SaveEventsPojo();

        saveEventsPojo.setScenerioId(data.get("scenerioId"));
        saveEventsPojo.setScenerioDesc(data.get("scenerioDesc"));
        saveEventsPojo.setExpectedStatusCode(Integer.parseInt(data.get("expectedStatusCode")));
        if(!data.get("expectedMessage").equals("NO_DATA"))
            saveEventsPojo.setExpectedMessage(data.get("expectedMessage"));
        if(!data.get("application_id").equals("NO_DATA"))
            saveEventsPojo.setApplication_id(Integer.parseInt(data.get("application_id")));
        if(!data.get("entity_id1").equalsIgnoreCase("NO_DATA"))
            saveEventsPojo.setEntity_id1(data.get("entity_id1"));
            saveEventsPojo.setEntity_name1(data.get("entity_name1"));
        saveEventsPojo.setEntity_id2(Integer.valueOf(data.get("entity_id2")));
        saveEventsPojo.setEntity_name2(data.get("entity_name2"));
            saveEventsPojo.setEntity_id3(Integer.parseInt(data.get("entity_id3")));
        saveEventsPojo.setEntity_id4(Integer.parseInt(data.get("entity_id4")));
        saveEventsPojo.setEntity_name4(data.get("entity_name4"));
        if(!data.get("category1").equalsIgnoreCase("NO_DATA"))
            saveEventsPojo.setCategory1(data.get("category1"));
        if(!data.get("category2").equalsIgnoreCase("NO_DATA"))
            saveEventsPojo.setCategory2(data.get("category2"));
        if(!data.get("category3").equalsIgnoreCase("NO_DATA"))
            saveEventsPojo.setCategory3(data.get("category3"));
            saveEventsPojo.setEvent_version(data.get("event_version"));
        if(!data.get("event_type").equalsIgnoreCase("NO_DATA"))
            saveEventsPojo.setEvent_type(data.get("event_type"));
        saveEventsPojo.setImage_name(data.get("image_name"));
        saveEventsPojo.setImage_created_at(data.get("image_created_at"));
        if(!data.get("created_gmt").equalsIgnoreCase("NO_DATA"))
            saveEventsPojo.setCreated_gmt(data.get("created_gmt"));
        saveEventsPojo.setTitle("");
        if(!data.get("message").equalsIgnoreCase("NO_DATA"))
            saveEventsPojo.setMessage(data.get("message"));
        saveEventsPojo.setDescription("");
        if(!data.get("result").equalsIgnoreCase("NO_DATA"))
            saveEventsPojo.setResult(data.get("result"));

        return saveEventsPojo;
    }

    //data provider to get data from google sheets
    @DataProvider(name = "saveEventsData")
    public Iterator<SaveEventsPojo> saveEventsData() {
        List<LinkedHashMap<String, String>> sheetDataAsListOfMap = null;
        try {
            sheetDataAsListOfMap = GoogleSheetsUtils.getGoogleSheetsDataAsListOfMap(SPREADSHEET_ID,SHEET_NAME, SHEET_RANGE);
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        List<SaveEventsPojo> saveEventsData = new ArrayList<>();
        for(LinkedHashMap<String,String> data : sheetDataAsListOfMap) {
            SaveEventsPojo saveEvents = SaveEventsDataBuilder.getCustomizedSaveEventsData(data);
            saveEventsData.add(saveEvents);
        }
        return saveEventsData.iterator();
    }
    @DataProvider(name = "saveEventsSingleData")
    public static Iterator<SaveEventsPojo> saveEventsSingleData() {
        List<LinkedHashMap<String, String>> sheetDataAsListOfMap = null;
        try {
            sheetDataAsListOfMap = GoogleSheetsUtils.getGoogleSheetsDataAsListOfMap(SPREADSHEET_ID,SHEET_NAME, "A1:X2");
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        List<SaveEventsPojo> saveEventsData = new ArrayList<>();
        for(LinkedHashMap<String,String> data : sheetDataAsListOfMap) {
            SaveEventsPojo saveEvents = SaveEventsDataBuilder.getCustomizedSaveEventsData(data);
            saveEventsData.add(saveEvents);
        }
        return saveEventsData.iterator();
    }


}
