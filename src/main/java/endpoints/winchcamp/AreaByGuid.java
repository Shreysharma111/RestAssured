package endpoints.winchcamp;

import io.restassured.response.Response;
import utilities.restAssuredFunctions.HttpMethod;

import static utilities.RestAssuredUtils.*;

public class AreaByGuid {
    public static String areaByGuid_url = getUrl().getString("areaByGuid_url");
    public static Response areaByGuidPositiveCase(String... pathParams) {
        return positiveCaseWithStringPathParams(HttpMethod.GET, areaByGuid_url, pathParams);
    }
    public static Response areaByGuidWrongEndpointCase(String... pathParams) {
        return positiveCaseWithStringPathParams(HttpMethod.GET, areaByGuid_url+"/shr", pathParams);
    }
    public static Response areaByGuidMethodCase(String... pathParams) {
        return positiveCaseWithStringPathParams(HttpMethod.POST, areaByGuid_url, pathParams);
    }
    public static Response areaByGuidHeaderCase(String pathParams, String... headers) {
        return positiveCase(HttpMethod.GET, areaByGuid_url, pathParams, headers);
    }
}
