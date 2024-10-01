package endpoints.winchcamp;

import io.restassured.response.Response;
import pojos.EventListWithFiltersPojo;
import utilities.restAssuredFunctions.HttpMethod;

import static utilities.RestAssuredUtils.*;

public class EventListWithFilters {
    private static Response response;
    private static String accessToken = getToken();
    public static String eventListWithFilters_url = getUrl().getString("eventListWithFilters_url");

    public static Response eventListWithFiltersPositiveCase(EventListWithFiltersPojo payload) {
        return positiveCaseWithPayload(HttpMethod.POST, eventListWithFilters_url, payload);

    }

    public static Response eventListWithFiltersWrongEndpointCase(EventListWithFiltersPojo payload) {
        return positiveCaseWithPayload(HttpMethod.POST, eventListWithFilters_url+"/shr", payload);

    }

    public static Response eventListWithFiltersMethodCase(EventListWithFiltersPojo payload) {
        return positiveCaseWithPayload(HttpMethod.GET, eventListWithFilters_url, payload);

    }
    public static Response eventListWithFiltersHeaderCase(EventListWithFiltersPojo payload, String... headers) {
        return positiveCaseWithPayload(HttpMethod.POST, eventListWithFilters_url, payload, headers);

    }

}
