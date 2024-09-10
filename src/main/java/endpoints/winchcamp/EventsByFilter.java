package endpoints.winchcamp;

import io.restassured.response.Response;
import pojos.EventsByFilterPojo;
import utilities.restAssuredFunctions.HttpMethod;

import static utilities.RestAssuredUtils.*;

public class EventsByFilter {
    private static Response response;
    private static String accessToken = getToken();
    public static String eventsByFilter_url = getUrl().getString("eventsByFilter_url");

    public static Response eventsByFilterPositiveCase(EventsByFilterPojo payload) {
        return positiveCaseWithPayload(HttpMethod.POST, eventsByFilter_url, payload);
    }
    public static Response eventsByFilterWrongEndpointCase(EventsByFilterPojo payload) {
        return positiveCaseWithPayload(HttpMethod.POST, eventsByFilter_url+"shr", payload);
    }
    public static Response eventsByFilterMethodCase(EventsByFilterPojo payload) {
        return positiveCaseWithPayload(HttpMethod.GET, eventsByFilter_url, payload);
    }
    public static Response eventsByFilterHeaderCase(EventsByFilterPojo payload, String... headers) {
        return positiveCaseWithPayload(HttpMethod.POST, eventsByFilter_url, payload, headers);
    }
}
