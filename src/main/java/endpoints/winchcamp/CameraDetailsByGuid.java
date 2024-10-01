package endpoints.winchcamp;

import io.restassured.response.Response;
import utilities.restAssuredFunctions.HttpMethod;

import static utilities.RestAssuredUtils.*;

public class CameraDetailsByGuid {
    public static String cameraDetailsByGuid_url = getUrl().getString("cameraDetailsByGuid_url");


    public static Response cameraDetailsByGuidPositiveCase(String guid) {
        return positiveCaseWithStringPathParams(HttpMethod.GET, cameraDetailsByGuid_url, guid);
    }
    public static Response cameraDetailsByGuidWrongEndpointCase(String guid) {
        return positiveCaseWithStringPathParams(HttpMethod.GET, cameraDetailsByGuid_url+"/shr", guid);
    }
    public static Response cameraDetailsByGuidWrongGuidCase(String guid) {
        return positiveCaseWithStringPathParams(HttpMethod.GET, cameraDetailsByGuid_url, guid+"shr");
    }

    public static Response cameraDetailsByGuidMethodCase(String guid) {
        return positiveCaseWithStringPathParams(HttpMethod.POST, cameraDetailsByGuid_url, guid);
    }

    public static Response cameraDetailsByGuidHeaderCase(String guid, String... headers) {
        return positiveCase(HttpMethod.GET, cameraDetailsByGuid_url, guid, headers);
    }
}
