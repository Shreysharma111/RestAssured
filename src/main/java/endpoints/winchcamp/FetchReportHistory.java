package endpoints.winchcamp;

import io.restassured.response.Response;
import utilities.restAssuredFunctions.HttpMethod;

import static utilities.RestAssuredUtils.*;

public class FetchReportHistory {
    public static String fetchReportHistory_url = getUrl().getString("fetchReportHistory_url");
    public static Response fetchReportHistoryPositiveCase(int... pathParams) {
        return positiveCase(HttpMethod.GET, fetchReportHistory_url, pathParams);
    }
    public static Response fetchReportHistoryWrongEndpointCase(int... pathParams) {
        return positiveCase(HttpMethod.GET, fetchReportHistory_url+"/shr", pathParams);
    }
    public static Response fetchReportHistoryMethodCase(int... pathParams) {
        return positiveCase(HttpMethod.POST, fetchReportHistory_url, pathParams);
    }
    public static Response fetchReportHistoryHeaderCase(int pathParams, String... headers) {
        return positiveCase(HttpMethod.GET, fetchReportHistory_url, pathParams, headers);
    }
}
