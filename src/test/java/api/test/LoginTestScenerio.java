package api.test;

import api.endpoints.UserEndPoints2;
import api.payloads.LoginDataBuilder;
import api.pojos.LoginScenerioPojo;
import api.utilities.AssertionUtils;
import api.utilities.ExcelUtils;
import api.utilities.reporting.Setup;
import com.aventstack.extentreports.ExtentTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.*;

public class LoginTestScenerio {
    @Test(dataProvider = "getLoginScenerio")
    public void testLoginExcel(LoginScenerioPojo login) {
        ExtentTest test = Setup.extentReports.createTest("Test Name - " + login.getScenerioId(), login.getScenerioDesc());
        Setup.extentTest.set(test);

        Response response = UserEndPoints2.login(login);

        if(login.getExpectedStatusCode() != 200){
            if(login.getScenerioId().equalsIgnoreCase("Login_1"))
                response = UserEndPoints2.login(login);
            Assert.assertEquals(response.getStatusCode(), login.getExpectedStatusCode());
            Assert.assertEquals(response.jsonPath().getString("message"), login.getExpectedMessage());
        }
        else {
            Map<String,Object> expectedValueMap = new HashMap<>();
            expectedValueMap.put("username", login.getUsername());
            expectedValueMap.put("password", login.getPassword());

            if(login.getScenerioId().equalsIgnoreCase("Login_4")) {
                expectedValueMap.remove("username");
                expectedValueMap.remove("password");
            }
            AssertionUtils.assertExpectedValuesWithJsonPath(response, expectedValueMap);
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
            LoginScenerioPojo login = LoginDataBuilder.getCustomizedLoginData(data);
            loginData.add(login);
        }
        return loginData.iterator();
    }


}
