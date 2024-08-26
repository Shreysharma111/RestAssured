package Dataproviders;

import org.testng.annotations.DataProvider;

import static utilities.RestAssuredUtils.getWrongToken;

public class Dataproviders {

    @DataProvider(name = "headerDataProvider")
    public static Object[][] headerDataProvider() {

        Object[][] obj= {{"Authorization", getWrongToken()},{"Authorization",""}};
        return obj;
    }

    @DataProvider(name ="queryDataProvider")
    public static Object[][] queryParamDataProvider() {
        Object[][] obj= {{"agentId", "0"},{"agentId","!@#"}};
        return obj;
    }

    @DataProvider(name ="queryDataProviderForCreateCamera")
    public static Object[][] queryDataProviderForCreateCamera() {
        Object[][] obj= {{"cameraName", ""},{"cameraNam","Cameraret"}};
        return obj;
    }

    @DataProvider(name ="queryDataProviderForGetAllCamera")
    public static Object[][] queryDataProviderForGetAllCamera() {
        Object[][][] obj= {{{"keyword", "cam2"}, {"status", "1"}},{{"keyword", "cam1"}, {"status", "0"}}};
        return obj;
    }
    @DataProvider(name ="queryDataProviderForActivateCamera")
    public static Object[][] queryDataProviderForActivateCamera() {
        Object[][] obj= {{"id", "0"},{"id","9988"}};
        return obj;
    }
    @DataProvider(name ="queryDataProviderForEditMasterCamera")
    public static Object[][] queryDataProviderForEditMasterCamera() {
        Object[][][] obj= {{{"cameraName", ""}, {"id", "60"}},{{"cameraName", "Camera-60"}, {"id", ""}}};
        return obj;
    }
}
