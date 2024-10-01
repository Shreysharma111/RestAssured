package endpoints.winchcamp;

import io.restassured.response.Response;
import utilities.restAssuredFunctions.HttpMethod;

import static utilities.RestAssuredUtils.getUrl;
import static utilities.RestAssuredUtils.positiveCaseWithPayload;

public class AIEventReportScreenData {
    public static String aiEventReportScreenData_url = getUrl().getString("aiEventReportScreenData_url");

    public static Response aiEventReportScreenDataPositiveCase() {
        return positiveCaseWithPayload(HttpMethod.POST, aiEventReportScreenData_url, "{}");
    }
    public static Response aiEventReportScreenDataWrongEndpointCase() {
        return positiveCaseWithPayload(HttpMethod.POST, aiEventReportScreenData_url+"shr", "{}");
    }
    public static Response aiEventReportScreenDataMethodCase() {
        return positiveCaseWithPayload(HttpMethod.GET, aiEventReportScreenData_url, "{}");
    }
    public static Response aiEventReportScreenDataHeaderCase(String... headers) {
        return positiveCaseWithPayload(HttpMethod.POST, aiEventReportScreenData_url, "{}", headers);
    }
}
