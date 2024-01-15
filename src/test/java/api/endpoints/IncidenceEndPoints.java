package api.endpoints;

import api.pojos.*;
import api.utilities.JwtTokenUtil;
import io.restassured.response.Response;
import java.util.ResourceBundle;
import static api.endpoints.BaseEndPoints.*;
import static api.utilities.JwtTokenUtil.tokenChange;
import static io.restassured.RestAssured.given;

/*
Created to perform CRUD requests to the incidence APIs
 */
public class IncidenceEndPoints {
    static String getToken() {
        ResourceBundle routes = ResourceBundle.getBundle("logintoken");
        return routes.getString("IT");
    }
    private static Response response;
//    private static String jwtToken;

    //method to get URLs from properties file
    static ResourceBundle getUrl() {
        ResourceBundle routes = ResourceBundle.getBundle("routes");
        return routes;
    }

    public static Response inventoryListing()
    {
        String inventory_listing_url = getUrl().getString("base_url")+getUrl().getString("inventory_listing_url");

        response = given()
                .spec(commonRequestSpec(getToken()))
                .when()
                .get(inventory_listing_url);

        //log details and verify status code in extent report
        printRequestLogInReport(inventory_listing_url, "GET", commonRequestSpec(getToken()));
        printResponseLogInReport(response);
        return response;
    }
    public static Response incidenceList()
    {
        String incidence_list_url = getUrl().getString("base_url")+getUrl().getString("incidence_list_url");

        response = given()
                .spec(commonRequestSpec(getToken()))
                .when()
                .get(incidence_list_url);

        //log details and verify status code in extent report
        printRequestLogInReport(incidence_list_url, "GET", commonRequestSpec(getToken()));
        printResponseLogInReport(response);
        return response;
    }
    public static Response incidenceDetails(int assetId, int incidenceId)
    {
        String incidence_details_url = getUrl().getString("base_url")+getUrl().getString("incidence_details_url");

        response = given()
                .spec(commonRequestSpec(getToken()))
                .queryParam("assetId", assetId)
                .queryParam("incidenceId", incidenceId)
                .when()
                .get(incidence_details_url);

        //log details and verify status code in extent report
        printRequestLogInReport(incidence_details_url, "GET", commonRequestSpec(getToken()));
        printResponseLogInReport(response);
        return response;
    }
    public static Response incidenceHistory(int assetId)
    {
        String incidence_history_url = getUrl().getString("base_url")+getUrl().getString("incidence_history_url");

        response = given()
                .spec(commonRequestSpec(getToken()))
                .pathParam("assetId", assetId)
                .when()
                .get(incidence_history_url);

        //log details and verify status code in extent report
        printRequestLogInReport(incidence_history_url, "GET", commonRequestSpec(getToken()));
        printResponseLogInReport(response);
        return response;
    }
    public static Response reportIncidence(ReportIncidence payload)
    {
        String report_incidence_url = getUrl().getString("base_url")+getUrl().getString("report_incidence_url");

        response = given()
                .spec(commonRequestSpecWithBody(getToken(), payload))
                .when()
                .post(report_incidence_url);
        validateJsonSchema(response, "reportIncidence.json");

        //log details and verify status code in extent report
        printRequestLogInReport(report_incidence_url, "POST", commonRequestSpecWithBody(getToken(), payload));
        printResponseLogInReport(response);
        return response;
    }
    public static Response resolveIncidence(ResolveIncidence payload)
    {
        String resolve_incidence_url = getUrl().getString("base_url")+getUrl().getString("resolve_incidence_url");

        response = given()
                .spec(commonRequestSpecWithBody(getToken(), payload))
                .when()
                .post(resolve_incidence_url);
        JwtTokenUtil.responseIncId =payload.getIncidenceId();
        tokenChange();

        //log details and verify status code in extent report
        printRequestLogInReport(resolve_incidence_url, "POST", commonRequestSpecWithBody(getToken(), payload));
        printResponseLogInReport(response);
        return response;
    }
    public static Response reporterResolverDetails(int incidenceId)
    {
        String reporter_resolver_url = getUrl().getString("base_url")+getUrl().getString("reporter_resolver_url");

        response = given()
                .spec(commonRequestSpec(getToken()))
                .pathParam("incidenceId", incidenceId)
                .when()
                .get(reporter_resolver_url);

        //log details and verify status code in extent report
        printRequestLogInReport(reporter_resolver_url, "GET", commonRequestSpec(getToken()));
        printResponseLogInReport(response);
        return response;
    }

}
