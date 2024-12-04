package payloads;

import org.testng.annotations.DataProvider;
import pojos.SaveFCMTokenPojo;
import utilities.GoogleSheetsUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class SaveFCMTokenDataBuilder {
    private static final String SPREADSHEET_ID = "1nWOicILt_LleQtEKM2ffgIaEda_8cVnE9C35OpKCGR8";
    private static final String SHEET_NAME = "SaveFCMToken";
    private static final String SHEET_RANGE = "A1:F6";

    //data builder for multiple scenerios from google sheets
    public static SaveFCMTokenPojo getCustomizedSaveFCMTokenData(LinkedHashMap<String, String> data) {
        SaveFCMTokenPojo saveFCMTokenPojo = new SaveFCMTokenPojo();

        saveFCMTokenPojo.setScenerioId(data.get("scenerioId"));
        saveFCMTokenPojo.setScenerioDesc(data.get("scenerioDesc"));
        saveFCMTokenPojo.setExpectedStatusCode(Integer.parseInt(data.get("expectedStatusCode")));
        saveFCMTokenPojo.setExpectedMessage(data.get("expectedMessage"));
        if (!data.get("oldToken").equalsIgnoreCase("NO_DATA"))
            saveFCMTokenPojo.setOldToken(data.get("oldToken"));
        if (!data.get("newToken").equalsIgnoreCase("NO_DATA"))
            saveFCMTokenPojo.setNewToken(data.get("newToken"));

        return saveFCMTokenPojo;
    }
    //data provider to get data from google sheets
    @DataProvider(name = "saveFCMTokenData")
    public Iterator<SaveFCMTokenPojo> saveFCMTokenData() {
        List<LinkedHashMap<String, String>> sheetDataAsListOfMap = null;
        try {
            sheetDataAsListOfMap = GoogleSheetsUtils.getGoogleSheetsDataAsListOfMap(SPREADSHEET_ID,SHEET_NAME, SHEET_RANGE);
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        List<SaveFCMTokenPojo> saveFCMTokenData = new ArrayList<>();
        for(LinkedHashMap<String,String> data : sheetDataAsListOfMap) {
            SaveFCMTokenPojo saveFCMToken = SaveFCMTokenDataBuilder.getCustomizedSaveFCMTokenData(data);
            saveFCMTokenData.add(saveFCMToken);
        }
        return saveFCMTokenData.iterator();
    }
    @DataProvider(name = "saveFCMTokenSingleData")
    public static Iterator<SaveFCMTokenPojo> saveFCMTokenSingleData() {
        List<LinkedHashMap<String, String>> sheetDataAsListOfMap = null;
        try {
            sheetDataAsListOfMap = GoogleSheetsUtils.getGoogleSheetsDataAsListOfMap(SPREADSHEET_ID,SHEET_NAME, "A1:F2");
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        List<SaveFCMTokenPojo> saveFCMTokenData = new ArrayList<>();
        for(LinkedHashMap<String,String> data : sheetDataAsListOfMap) {
            SaveFCMTokenPojo saveFCMToken = SaveFCMTokenDataBuilder.getCustomizedSaveFCMTokenData(data);
            saveFCMTokenData.add(saveFCMToken);
        }
        return saveFCMTokenData.iterator();
    }

}
