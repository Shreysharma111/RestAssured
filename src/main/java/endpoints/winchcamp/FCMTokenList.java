package endpoints.winchcamp;

import io.restassured.response.Response;
import utilities.restAssuredFunctions.HttpMethod;

import static utilities.RestAssuredUtils.*;

public class FCMTokenList {
    public static String fcmTokenList_url = getUrl().getString("fcmTokenList_url");

    public static Response fcmTokenListPositiveCase(String... pathParams) {
        return positiveCaseWithStringPathParams(HttpMethod.GET, fcmTokenList_url, pathParams);
    }
    public static Response fcmTokenListWrongEndpointCase(String... pathParams) {
        return positiveCaseWithStringPathParams(HttpMethod.GET, fcmTokenList_url+"shr", pathParams);
    }
    public static Response fcmTokenListMethodCase(String... pathParams) {
        return positiveCaseWithStringPathParams(HttpMethod.POST, fcmTokenList_url, pathParams);
    }
    public static Response fcmTokenListHeaderCase(String pathParams, String... headers) {
        return positiveCase(HttpMethod.GET, fcmTokenList_url, pathParams, headers);
    }
}



