package endpoints.winchcamp;

import io.restassured.response.Response;
import utilities.restAssuredFunctions.HttpMethod;

import static utilities.RestAssuredUtils.*;


public class DeleteCameraConfigsByConfigId {
    public static String deleteCameraConfigsByConfigId_url = getUrl().getString("deleteCameraConfigsByConfigId_url");
    public static Response deleteCameraConfigsByConfigIdPositiveCase(int... pathParams) {
        return positiveCase(HttpMethod.DELETE, deleteCameraConfigsByConfigId_url, pathParams);
    }
    public static Response deleteCameraConfigsByConfigIdWrongEndpointCase(int... pathParams) {
        return positiveCase(HttpMethod.DELETE, deleteCameraConfigsByConfigId_url+"/shr", pathParams);
    }
    public static Response deleteCameraConfigsByConfigIdMethodCase(int... pathParams) {
        return positiveCase(HttpMethod.GET, deleteCameraConfigsByConfigId_url, pathParams);
    }
    public static Response deleteCameraConfigsByConfigIdHeaderCase(int pathParams, String... headers) {
        return positiveCase(HttpMethod.DELETE, deleteCameraConfigsByConfigId_url, pathParams, headers);
    }
}
