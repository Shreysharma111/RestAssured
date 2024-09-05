package endpoints.winchcamp;

import io.restassured.response.Response;
import pojos.UpdateCameraConfigsPojo;
import utilities.restAssuredFunctions.HttpMethod;

import static utilities.RestAssuredUtils.*;

public class UpdateCameraConfigs {
    public static String updateCameraConfigs_url = getUrl().getString("updateCameraConfigs_url");
    public static Response updateCameraConfigsPositiveCase(UpdateCameraConfigsPojo payload) {
        return positiveCaseWithPayload(HttpMethod.PUT, updateCameraConfigs_url, payload);
    }
    public static Response updateCameraConfigsWrongEndpointCase(UpdateCameraConfigsPojo payload) {
        return positiveCaseWithPayload(HttpMethod.PUT, updateCameraConfigs_url+"shr", payload);
    }
    public static Response updateCameraConfigsMethodCase(UpdateCameraConfigsPojo payload) {
        return positiveCaseWithPayload(HttpMethod.GET, updateCameraConfigs_url, payload);
    }
    public static Response updateCameraConfigsHeaderCase(UpdateCameraConfigsPojo payload, String... headers) {
        return positiveCaseWithPayload(HttpMethod.PUT, updateCameraConfigs_url, payload, headers);
    }

}
