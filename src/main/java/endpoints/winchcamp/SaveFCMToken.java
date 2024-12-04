package endpoints.winchcamp;

import io.restassured.response.Response;
import pojos.SaveFCMTokenPojo;
import utilities.restAssuredFunctions.HttpMethod;

import static utilities.RestAssuredUtils.getUrl;
import static utilities.RestAssuredUtils.positiveCaseWithPayload;

public class SaveFCMToken {
    public static String saveFCMToken_url = getUrl().getString("saveFCMToken_url");

    public static Response saveFCMTokenPositiveCase(SaveFCMTokenPojo payload) {
        return positiveCaseWithPayload(HttpMethod.POST, saveFCMToken_url, payload);
    }
    public static Response saveFCMTokenWrongEndpointCase(SaveFCMTokenPojo payload) {
        return positiveCaseWithPayload(HttpMethod.POST, saveFCMToken_url+"shr", payload);
    }
    public static Response saveFCMTokenMethodCase(SaveFCMTokenPojo payload) {
        return positiveCaseWithPayload(HttpMethod.GET, saveFCMToken_url, payload);
    }
    public static Response saveFCMTokenHeaderCase(SaveFCMTokenPojo payload, String... headers) {
        return positiveCaseWithPayload(HttpMethod.POST, saveFCMToken_url, payload, headers);
    }

}
