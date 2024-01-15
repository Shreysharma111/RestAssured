package api.payloads;

import api.pojos.Login;
import api.utilities.ExcelUtils;
import api.utilities.GoogleSheetsUtils;
import org.testng.annotations.DataProvider;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

public class LoginDataBuilder {
    private static final String SPREADSHEET_ID = "1nWOicILt_LleQtEKM2ffgIaEda_8cVnE9C35OpKCGR8";
    private static final String SHEET_NAME = "Sheet1";
    private static final String SHEET_RANGE = "A1:F7";
    private static final ResourceBundle usersBundle = ResourceBundle.getBundle("users");

    //naive approach to pass data
    public static Login setupDataIT() {

        return Login
                .builder()
                .username(usersBundle.getString("IT"))
                .password(usersBundle.getString("password"))
                .build();

    }
    public static Login setupDataDr() {

        return Login
                .builder()
                .username(usersBundle.getString("doctor"))
                .password(usersBundle.getString("password"))
                .build();

    }

    //general data driven testing from excel
    @DataProvider(name = "getLoginData")
    public Iterator<Login> getLoginDataFromExcel() {
        List<LinkedHashMap<String, String>> excelDataAsListOfMap = null;
        try {
            excelDataAsListOfMap = ExcelUtils.getExcelDataAsListOfMap("Login", "Sheet1");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<Login> loginData = new ArrayList<>();
        for(LinkedHashMap<String,String> data : excelDataAsListOfMap) {
            Login login = Login.builder()
                    .username(data.get("username"))
                    .password(data.get("password"))
                    .build();
            loginData.add(login);
        }
        return loginData.iterator();
    }

    //data builder for multiple scenerios from google sheets
    public static Login getCustomizedLoginData(LinkedHashMap<String, String> data) {
        Login loginScePojo=new Login();

        loginScePojo.setScenerioId(data.get("scenerioId"));
        loginScePojo.setScenerioDesc(data.get("scenerioDesc"));
        loginScePojo.setExpectedStatusCode(Integer.parseInt(data.get("expectedStatusCode")));
        if(!data.get("expectedMessage").equals("NO_DATA"))
            loginScePojo.setExpectedMessage(data.get("expectedMessage"));
        if(!data.get("username").equalsIgnoreCase("NO_DATA"))
            loginScePojo.setUsername(data.get("username"));
        if(!data.get("password").equalsIgnoreCase("NO_DATA"))
            loginScePojo.setPassword(data.get("password"));

        return loginScePojo;
    }

    //data provider to get data from google sheets
    @DataProvider(name = "getLoginScenerio")
    public Iterator<Login> getLoginData() {
        List<LinkedHashMap<String, String>> sheetDataAsListOfMap = null;
        try {
            sheetDataAsListOfMap = GoogleSheetsUtils.getGoogleSheetsDataAsListOfMap(SPREADSHEET_ID,SHEET_NAME, SHEET_RANGE);
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        List<Login> loginData = new ArrayList<>();
        for(LinkedHashMap<String,String> data : sheetDataAsListOfMap) {
            Login login = LoginDataBuilder.getCustomizedLoginData(data);
            loginData.add(login);
        }
        return loginData.iterator();
    }

}
