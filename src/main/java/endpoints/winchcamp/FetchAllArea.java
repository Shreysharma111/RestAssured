package endpoints.winchcamp;

import io.restassured.response.Response;

import static utilities.RestAssuredUtils.*;

public class FetchAllArea {

    private static Response response;
    private static String accessToken = getToken();
    public static String fetchAllArea_url = getUrl().getString("fetchAllArea_url");

    public static Response fetchAllAreaPositiveCase() {
        return positiveCaseGet(fetchAllArea_url);
    }
    public static Response fetchAllAreaWrongEndpointCase() {
        return wrongEndpointCaseGet(fetchAllArea_url);
    }
    public static Response fetchAllAreaMethodCase() {
        return methodCaseGet(fetchAllArea_url);
    }
    public static Response fetchAllAreaHeaderCase(String... headers) {
        return headerCaseGet(fetchAllArea_url, headers);
    }

}
