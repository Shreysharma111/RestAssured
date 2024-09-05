package endpoints.winchcamp;

import io.restassured.response.Response;
import utilities.restAssuredFunctions.HttpMethod;

import static utilities.RestAssuredUtils.getUrl;
import static utilities.RestAssuredUtils.positiveCase;

public class GetFacilitiesByRole {
    private static String getFacilitiesByRole_url = getUrl().getString("getFacilitiesByRole_url");

    public static Response getFacilitiesByRolePositiveCase() {
        return positiveCase(HttpMethod.GET, getFacilitiesByRole_url);
    }
    public static Response getFacilitiesByRoleWrongEndpointCase() {
        return positiveCase(HttpMethod.GET, getFacilitiesByRole_url+"shr");
    }
    public static Response getFacilitiesByRoleMethodCase() {
        return positiveCase(HttpMethod.POST, getFacilitiesByRole_url);
    }
    public static Response getFacilitiesByRoleHeaderCase(String... headers) {
        return positiveCase(HttpMethod.GET, getFacilitiesByRole_url, headers);
    }
}
