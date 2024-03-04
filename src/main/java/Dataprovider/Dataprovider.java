package Dataprovider;

import org.testng.annotations.DataProvider;

import static utilities.RestAssuredUtils.getToken;

public class Dataprovider {

    @DataProvider(name = "headerDataProvider")
    public static Object[][] headerDataProvider() {

        Object[][] obj= {{"Authorization",""},{"Authorization", getToken()}};
        return obj;
    }
}
