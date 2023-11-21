package api.endpoints;

import static api.utilities.JwtTokenUtil.jwtToken;
import static api.utilities.JwtTokenUtil.tokenChange;
import static io.restassured.RestAssured.given;

import api.payload.Login;
import api.payload.ReportIncidence;
import api.payload.ResolveIncidence;
import api.utilities.JwtTokenUtil;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

/*
Created to perform CRUD requests to the incidence APIs
 */
public class UserEndPoints {

    public static Response login(Login payload)
    {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(Routes.login_url);
        JwtTokenUtil.jwtToken =response.path("jwtToken");
        tokenChange();
        return response;
    }
    public static Response inventoryListing()
    {
        Response response = given()
                .header("x-authorization", jwtToken)
                .when()
                .get(Routes.inventory_listing_url);

        return response;
    }
    public static Response incidenceList()
    {
        Response response = given()
                .header("x-authorization", jwtToken)
                .when()
                .get(Routes.incidence_list_url);

        return response;
    }
    public static Response incidenceDetails(int assetId)
    {
        Response response = given()
                .pathParam("assetId", assetId)
                .header("x-authorization", jwtToken)
                .when()
                .get(Routes.incidence_details_url);

        return response;
    }
    public static Response incidenceHistory(int assetId)
    {
        Response response = given()
                .pathParam("assetId", assetId)
                .header("x-authorization", jwtToken)
                .when()
                .get(Routes.incidence_history_url);

        return response;
    }
    public static Response reportIncidence(ReportIncidence payload)
    {
        Response response = given()
                .header("x-authorization", jwtToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(Routes.report_incidence_url);

        return response;
    }
    public static Response resolveIncidence(ResolveIncidence payload)
    {
        Response response = given()
                .header("x-authorization", jwtToken)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post(Routes.resolve_incidence_url);
        JwtTokenUtil.responseIncId =payload.getIncidenceId();
        tokenChange();

        return response;
    }
    public static Response reporterResolverDetails(int incidenceId)
    {
        Response response = given()
                .header("x-authorization", jwtToken)
                .pathParam("incidenceId", incidenceId)
                .when()
                .get(Routes.reporter_resolver_url);

        return response;
    }

}
