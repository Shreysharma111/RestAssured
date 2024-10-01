package endpoints.winchcamp;

import io.restassured.response.Response;
import utilities.restAssuredFunctions.HttpMethod;

import static utilities.RestAssuredUtils.*;

public class DeactivateCamera {
    private static String paramKey;
    private static Response response;
    private static String accessToken = getToken();
    public static String deactivateCamera_url = getUrl().getString("deactivateCamera_url");

    public static Response deactivateCameraPositiveCase(String... queryParams) {
        return queryParamsPositiveCase(HttpMethod.POST, deactivateCamera_url, accessToken, queryParams);
    }
    public static Response deactivateCameraWrongEndpointCase(String... queryParams) {
        return queryParamsWrongEndpointCase(deactivateCamera_url, accessToken, queryParams);
    }
    public static Response deactivateCameraMethodCase(String... queryParams) {
        return queryParamsMethodCase(deactivateCamera_url, accessToken, queryParams);
    }
    public static Response deactivateCameraHeaderCase(int idValue, String... headers) {
        paramKey="id";
        return queryParamsHeaderCase(HttpMethod.POST, deactivateCamera_url, paramKey, idValue, headers);
    }
    public static Response deactivateCameraQueryParamCase(String... queryParams) {
        return queryParamsQueryParamCase(deactivateCamera_url,accessToken, queryParams);
    }
}
