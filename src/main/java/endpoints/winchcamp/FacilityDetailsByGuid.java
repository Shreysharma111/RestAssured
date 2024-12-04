package endpoints.winchcamp;

import io.restassured.response.Response;
import utilities.restAssuredFunctions.HttpMethod;

import static utilities.RestAssuredUtils.*;

public class FacilityDetailsByGuid {
    public static String facilityDetailsByGuid_url = getUrl().getString("facilityDetailsByGuid_url");


    public static Response facilityDetailsByGuidPositiveCase(String guid) {
        return positiveCaseWithStringPathParams(HttpMethod.GET, facilityDetailsByGuid_url, guid);

    }
    public static Response facilityDetailsByGuidWrongEndpointCase(String guid) {
        return positiveCaseWithStringPathParams(HttpMethod.GET, facilityDetailsByGuid_url+"/shr", guid);

    }
    public static Response facilityDetailsByGuidWrongGuidCase(String guid) {
        return positiveCaseWithStringPathParams(HttpMethod.GET, facilityDetailsByGuid_url, guid+"shr");

    }

    public static Response facilityDetailsByGuidMethodCase(String guid) {
        return positiveCaseWithStringPathParams(HttpMethod.POST, facilityDetailsByGuid_url, guid);

    }

    public static Response facilityDetailsByGuidHeaderCase(String guid, String... headers) {
        return positiveCase(HttpMethod.GET, facilityDetailsByGuid_url, guid, headers);

    }
}
