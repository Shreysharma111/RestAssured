package endpoints.winchcamp;

import io.restassured.response.Response;
import utilities.restAssuredFunctions.HttpMethod;

import static utilities.RestAssuredUtils.*;

public class GetAllTenantCameraConfig {
    private static Response response;
    private static String accessToken = getToken();
    public static String getAllTenantCameraConfig_url = getUrl().getString("getAllTenantCameraConfig_url");

    public static Response getAllTenantCameraConfigPositiveCase() {
        return positiveCase(HttpMethod.GET, getAllTenantCameraConfig_url);

    }
    public static Response getAllTenantCameraConfigWrongEndpointCase() {
        return positiveCase(HttpMethod.GET, getAllTenantCameraConfig_url+"shr");

    }
    public static Response getAllTenantCameraConfigMethodCase() {
        return positiveCase(HttpMethod.POST, getAllTenantCameraConfig_url);

    }
    public static Response getAllTenantCameraConfigHeaderCase(String... headers) {
        return positiveCase(HttpMethod.GET, getAllTenantCameraConfig_url, headers);

    }
}
