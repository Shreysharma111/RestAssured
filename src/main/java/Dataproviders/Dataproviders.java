package Dataproviders;

import org.testng.annotations.DataProvider;

import static utilities.RestAssuredUtils.getValue;
import static utilities.RestAssuredUtils.getWrongToken;

public class Dataproviders {
    private static int reportIdForAreaCameraReportValue = Integer.parseInt(getValue("winchcamp", "reportIdForAreaCameraReport"));
    private static int reportIdForAIEventReportValue = Integer.parseInt(getValue("winchcamp", "reportIdForAIEventReport"));


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
    @DataProvider(name ="pathDataProviderForGetFacilitiesByZoneAndRegion")
    public static Object[][] pathDataProviderForGetFacilitiesByZoneAndRegion() {
        return new Object[][]{
                {0, 0},
                {999, 999},
                {1, 2}
        };
    }

    @DataProvider(name ="pathDataProviderForDeleteCameraConfigByConfigId")
    public static Object[][] pathDataProviderForGetFacilityById() {
        return new Object[][]{
                {0},
                {99999}
        };
    }
    @DataProvider(name ="pathDataProviderForFCMTokenList")
    public static Object[][] pathDataProviderForFCMTokenList() {
        return new Object[][]{
                {"24a79839-4310-413a-9129-dce712e23ba"},
                {"99999"}
        };
    }
    @DataProvider(name ="pathDataProviderForFetchReportHistory")
    public static Object[][] pathDataProviderForFetchReportHistory() {
        return new Object[][]{
                {reportIdForAreaCameraReportValue},
                {reportIdForAIEventReportValue}
        };
    }
}
