package endpoints.winchcamp;

import io.restassured.response.Response;
import utilities.restAssuredFunctions.HttpMethod;

import static utilities.RestAssuredUtils.*;

public class CreateCamera {
    private static String paramKey;
    private static Response response;
    private static String accessToken = getToken();
    public static String createCamera_url = getUrl().getString("createCamera_url");

    public static Response createCameraPositiveCase(String... queryParams) {
        return queryParamsPositiveCase(HttpMethod.POST, createCamera_url, accessToken, queryParams);
    }
    public static Response createCameraWrongEndpointCase(String... queryParams) {
        return queryParamsPositiveCase(HttpMethod.POST, createCamera_url+"shr", accessToken, queryParams);

    }

    public static Response createCameraMethodCase(String... queryParams) {
        return queryParamsPositiveCase(HttpMethod.GET, createCamera_url, accessToken, queryParams);

    }

    public static Response createCameraHeaderCase(String cameraNameValue, String... headers) {
        paramKey="cameraName";
        return queryParamsHeaderCase(HttpMethod.POST, createCamera_url, paramKey, cameraNameValue, headers);
    }

    public static Response createCameraQueryParamCase(String... queryParams) {
        return queryParamsQueryParamCase(createCamera_url,accessToken, queryParams);
    }

}
