package Dataproviders;

import org.testng.annotations.DataProvider;

import static utilities.RestAssuredUtils.getWrongToken;

public class Dataproviders {

    @DataProvider(name = "headerDataProvider")
    public static Object[][] headerDataProvider() {

        Object[][] obj= {{"Authoriation", getWrongToken()},{"Authorization",""}};
        return obj;
    }

    @DataProvider(name ="queryDataProvider")
    public static Object[][] queryParamDataProvider() {
        Object[][] obj= {{"agentId", "0"},{"agentId",""}};
        return obj;
    }
}
