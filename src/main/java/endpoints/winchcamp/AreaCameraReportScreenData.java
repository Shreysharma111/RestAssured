package endpoints.winchcamp;

import io.restassured.response.Response;

import static utilities.RestAssuredUtils.*;

public class AreaCameraReportScreenData {
    public static String areaCameraReportScreenData_url = getUrl("areaCameraReportScreenData_url");
    public static Response areaCameraReportScreenDataPositiveCase() {
        return positiveCaseGet(areaCameraReportScreenData_url);
    }
    public static Response areaCameraReportScreenDataWrongEndpointCase() {
        return wrongEndpointCaseGet(areaCameraReportScreenData_url);
    }
    public static Response areaCameraReportScreenDataMethodCase() {
        return methodCaseGet(areaCameraReportScreenData_url);
    }
    public static Response areaCameraReportScreenDataHeaderCase(String... headers) {
        return headerCaseGet(areaCameraReportScreenData_url, headers);
    }
}
