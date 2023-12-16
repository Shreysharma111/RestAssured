package api.test.incidence;

import api.endpoints.UserEndPoints2;
import api.payloads.LoginDataBuilder;
import api.pojos.Login;
import api.utilities.ExcelUtils;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class LoginTests {


    private final Logger logger = LogManager.getLogger(this.getClass());

    @Test(priority = 1)
    public void testLoginIT() {
        Login loginPayloadIT = LoginDataBuilder.setupDataIT();
         Response response = UserEndPoints2.login(loginPayloadIT);

        Assert.assertEquals(response.jsonPath().get("username"), loginPayloadIT.getUsername());

        logger.info("Status code : "+response.getStatusCode());
        logger.info("Response time : "+response.getTimeIn(TimeUnit.MILLISECONDS)+"ms");
    }
    @Test
    public void testLoginDr() {
        Login loginPayloadDr = LoginDataBuilder.setupDataDr();
        Response response = UserEndPoints2.login(loginPayloadDr);

        Assert.assertEquals(response.jsonPath().get("username"), loginPayloadDr.getUsername());

        logger.info("Status code : "+response.getStatusCode());
        logger.info("Response time : "+response.getTimeIn(TimeUnit.MILLISECONDS)+"ms");
    }

    @Test(dataProvider = "getLoginData")
    public void testLoginExcel(Login login) {
        Response response = UserEndPoints2.login(login);

        Assert.assertEquals(response.jsonPath().get("username"), login.getUsername());

        logger.info("Status code : "+response.getStatusCode());
        logger.info("Response time : "+response.getTimeIn(TimeUnit.MILLISECONDS)+"ms");
    }

    @DataProvider(name = "getLoginData")
    public Iterator<Login> getLoginData() {
        List<LinkedHashMap<String, String>> excelDataAsListOfMap = null;
        try {
            excelDataAsListOfMap = ExcelUtils.getExcelDataAsListOfMap("Login", "Sheet1");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(excelDataAsListOfMap);
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

}
