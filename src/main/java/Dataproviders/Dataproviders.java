package Dataproviders;

import org.testng.annotations.DataProvider;

import static utilities.RestAssuredUtils.getWrongToken;

public class Dataproviders {

    @DataProvider(name = "headerDataProvider")
    public static Object[][] headerDataProvider() {

        Object[][] obj= {{"Authoriation", getWrongToken()},{"Authorization",""}};
        return obj;
    }
}
