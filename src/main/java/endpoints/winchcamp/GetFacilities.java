package endpoints.winchcamp;

import io.restassured.response.Response;

import static utilities.RestAssuredUtils.*;

public class GetFacilities {
    public static String getFacilities_url = getUrl("getFacilities_url");

    public static Response getFacilitiesPositiveCase() {
        return positiveCaseGet(getFacilities_url);
    }
    public static Response getFacilitiesWrongEndpointCase() {
        return wrongEndpointCaseGet(getFacilities_url);
    }
    public static Response getFacilitiesMethodCase() {
        return methodCaseGet(getFacilities_url);
    }
    public static Response getFacilitiesHeaderCase(String... headers) {
        return headerCaseGet(getFacilities_url, headers);
    }
}
