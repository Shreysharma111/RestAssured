package api.test;

import api.endpoints.UserEndPoints2;
import api.pojos.LoginScenerioPojo;
import api.utilities.ExcelUtils;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

public class LoginTestScenerio {
    @Test(dataProvider = "getLoginScenerio")
    public void testLoginExcel(LoginScenerioPojo login) {
        Response response = UserEndPoints2.login(login);

        if(login.getExpectedStatusCode()!=200) {
            if(login.getScenerioId().equalsIgnoreCase("Login_1"))
                response = UserEndPoints2.login(login);
            Assert.assertEquals(response.getStatusCode(),login.getExpectedStatusCode());
            Assert.assertEquals(response.jsonPath().getString("message"),login.getExpectedMessage());
        }
        else {
            if(login.getScenerioId().equalsIgnoreCase("Login_4")) {

            }
        }

        }

    @DataProvider(name = "getLoginScenerio")
    public Iterator<LoginScenerioPojo> getLoginData() {
        List<LinkedHashMap<String, String>> excelDataAsListOfMap = null;
        try {
            excelDataAsListOfMap = ExcelUtils.getExcelDataAsListOfMap("Login", "Sheet1");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(excelDataAsListOfMap);
        List<LoginScenerioPojo> loginData = new ArrayList<>();
        for(LinkedHashMap<String,String> data : excelDataAsListOfMap) {
            LoginScenerioPojo login = getCustomizedLoginData(data);
            loginData.add(login);
        }
        return loginData.iterator();
    }

    private LoginScenerioPojo getCustomizedLoginData(LinkedHashMap<String,String> data) {
        LoginScenerioPojo loginScePojo=new LoginScenerioPojo();

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
}
