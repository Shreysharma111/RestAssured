package endpoints.winchcamp;

import io.restassured.response.Response;
import pojos.EventsByFiltersPojo;
import utilities.restAssuredFunctions.HttpMethod;

import static utilities.RestAssuredUtils.*;

public class EventsByFilters {
    private static Response response;
    private static String accessToken = getToken();
    public static String eventsByFilters_url = getUrl().getString("eventsByFilters_url");

    public static Response eventsByFiltersPositiveCase(EventsByFiltersPojo payload) {
        return positiveCaseWithPayload(HttpMethod.POST, eventsByFilters_url, payload);

    }
    public static Response eventsByFiltersWrongEndpointCase(EventsByFiltersPojo payload) {
        return positiveCaseWithPayload(HttpMethod.POST, eventsByFilters_url+"/shr", payload);

    }
    public static Response eventsByFiltersMethodCase(EventsByFiltersPojo payload) {
        return positiveCaseWithPayload(HttpMethod.GET, eventsByFilters_url, payload);

    }
    public static Response eventsByFiltersHeaderCase(EventsByFiltersPojo payload, String... headers) {
        return positiveCaseWithPayload(HttpMethod.POST, eventsByFilters_url, payload, headers);
    }

}
