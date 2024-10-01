package endpoints.winchcamp;

import io.restassured.response.Response;
import utilities.restAssuredFunctions.HttpMethod;

import static utilities.RestAssuredUtils.*;

public class ActivateCamera {
    private static String paramKey;
    private static String accessToken = getToken();
    public static String activateCamera_url = getUrl("activateCamera_url");

    public static Response activateCameraPositiveCase(String... queryParams) {
        return queryParamsPositiveCase(HttpMethod.POST, activateCamera_url, accessToken, queryParams);
    }
    public static Response activateCameraWrongEndpointCase(String... queryParams) {
        return queryParamsPositiveCase(HttpMethod.POST, activateCamera_url+"shr", accessToken, queryParams);
    }
    public static Response activateCameraMethodCase(String... queryParams) {
        return queryParamsPositiveCase(HttpMethod.GET, activateCamera_url, accessToken, queryParams);
    }
    public static Response activateCameraHeaderCase(int idValue, String... headers) {
        paramKey="id";
        return queryParamsHeaderCase(HttpMethod.POST, activateCamera_url, paramKey, idValue, headers);
    }
    public static Response activateCameraQueryParamCase(String... queryParams) {
        return queryParamsQueryParamCase(activateCamera_url,accessToken, queryParams);
    }

}
