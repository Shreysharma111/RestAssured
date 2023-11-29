package api.endpoints;

import api.payload.Login;
import api.payload.ReportIncidence;
import api.payload.ResolveIncidence;
import api.utilities.JwtTokenUtil;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.ResourceBundle;

import static api.utilities.JwtTokenUtil.jwtToken;
import static api.utilities.JwtTokenUtil.tokenChange;
import static io.restassured.RestAssured.given;

/*
Created to perform CRUD requests to the incidence APIs
 */
public class UserEndPoints2 {
    private static Response response;

    //method to get URLs from properties file
    static ResourceBundle getUrl() {
        ResourceBundle routes = ResourceBundle.getBundle("routes");
        return routes;
    }

    public static Response login(Login payload)
    {
        String login_url = getUrl().getString("login_url");

         response = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(login_url);
        JwtTokenUtil.jwtToken =response.path("jwtToken");
        tokenChange();
        return response;
    }
    public static Response inventoryListing()
    {
        String inventory_listing_url = getUrl().getString("inventory_listing_url");

         response = given()
                .header("x-authorization", jwtToken)
                .when()
                .get(inventory_listing_url);

        return response;
    }
    public static Response incidenceList()
    {
        String incidence_list_url = getUrl().getString("incidence_list_url");

         response = given()
                .header("x-authorization", jwtToken)
                .when()
                .get(incidence_list_url);

        return response;
    }
    public static Response incidenceDetails(int assetId)
    {
        String incidence_details_url = getUrl().getString("incidence_details_url");

         response = given()
                .pathParam("assetId", assetId)
                .header("x-authorization", jwtToken)
                .when()
                .get(incidence_details_url);

        return response;
    }
    public static Response incidenceHistory(int assetId)
    {
        String incidence_history_url = getUrl().getString("incidence_history_url");

         response = given()
                .pathParam("assetId", assetId)
                .header("x-authorization", jwtToken)
                .when()
                .get(incidence_history_url);

        return response;
    }
    public static Response reportIncidence(ReportIncidence payload)
    {
        String report_incidence_url = getUrl().getString("report_incidence_url");

         response = given()
                .header("x-authorization", jwtToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(report_incidence_url);

        return response;
    }
    public static Response resolveIncidence(ResolveIncidence payload)
    {
        String resolve_incidence_url = getUrl().getString("resolve_incidence_url");

         response = given()
                .header("x-authorization", jwtToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(resolve_incidence_url);
        JwtTokenUtil.responseIncId =payload.getIncidenceId();
        tokenChange();

        return response;
    }
    public static Response reporterResolverDetails(int incidenceId)
    {
        String reporter_resolver_url = getUrl().getString("reporter_resolver_url");

         response = given()
                .header("x-authorization", jwtToken)
                .pathParam("incidenceId", incidenceId)
                .when()
                .get(reporter_resolver_url);

        return response;
    }

}
