package endpoints.winchcamp;

import io.restassured.response.Response;
import utilities.restAssuredFunctions.HttpMethod;

import static utilities.RestAssuredUtils.*;

public class GetDetailsByFacilityId {
    public static String getDetailsByFacilityId_url = getUrl("getDetailsByFacilityId_url");

    public static Response getDetailsByFacilityIdPositiveCase(int... pathParams) {
        return positiveCase(HttpMethod.GET, getDetailsByFacilityId_url, pathParams);
    }
    public static Response getDetailsByFacilityIdWrongEndpointCase(int... pathParams) {
        return positiveCase(HttpMethod.GET, getDetailsByFacilityId_url+"/shr", pathParams);
    }
    public static Response getDetailsByFacilityIdMethodCase(int... pathParams) {
        return positiveCase(HttpMethod.POST, getDetailsByFacilityId_url, pathParams);
    }
    public static Response getDetailsByFacilityIdHeaderCase(int pathParams, String... headers) {
        return positiveCase(HttpMethod.GET, getDetailsByFacilityId_url, pathParams, headers);
    }
}
