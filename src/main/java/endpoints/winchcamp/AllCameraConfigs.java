package endpoints.winchcamp;

import io.restassured.response.Response;
import utilities.restAssuredFunctions.HttpMethod;

import static utilities.RestAssuredUtils.*;

public class AllCameraConfigs {
    public static String allCameraConfigs_url = getUrl("allCameraConfigs_url");

    public static Response allCameraConfigsPositiveCase() {
        return positiveCase(HttpMethod.GET, allCameraConfigs_url);
    }
    public static Response allCameraConfigsWrongEndpointCase() {
        return positiveCase(HttpMethod.GET, allCameraConfigs_url+"shr");
    }
    public static Response allCameraConfigsMethodCase() {
        return positiveCase(HttpMethod.POST, allCameraConfigs_url);
    }
    public static Response allCameraConfigsHeaderCase(String... headers) {
        return positiveCase(HttpMethod.GET, allCameraConfigs_url, headers);
    }


}
