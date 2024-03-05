package endpoints;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import pojos.ReportIncidence;
import pojos.ResolveIncidence;
import utilities.RestAssuredUtils;
import utilities.JwtTokenUtil;

import java.util.ResourceBundle;

import static utilities.JwtTokenUtil.tokenChange;

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

        response = RestAssured.given()
                .spec(RestAssuredUtils.commonRequestSpecWithToken(getToken()))
                .when()
                .get(inventory_listing_url);

        //log details and verify status code in extent report
        RestAssuredUtils.printRequestLogInReport(inventory_listing_url, "GET", RestAssuredUtils.commonRequestSpecWithToken(getToken()));
        RestAssuredUtils.printResponseLogInReport(response);
        return response;
    }
    public static Response incidenceList()
    {
        String incidence_list_url = getUrl().getString("base_url")+getUrl().getString("incidence_list_url");

        response = RestAssured.given()
                .spec(RestAssuredUtils.commonRequestSpecWithToken(getToken()))
                .when()
                .get(incidence_list_url);

        //log details and verify status code in extent report
        RestAssuredUtils.printRequestLogInReport(incidence_list_url, "GET", RestAssuredUtils.commonRequestSpecWithToken(getToken()));
        RestAssuredUtils.printResponseLogInReport(response);
        return response;
    }
    public static Response incidenceDetails(int assetId, int incidenceId)
    {
        String incidence_details_url = getUrl().getString("base_url")+getUrl().getString("incidence_details_url");

        response = RestAssured.given()
                .spec(RestAssuredUtils.commonRequestSpecWithToken(getToken()))
                .queryParam("assetId", assetId)
                .queryParam("incidenceId", incidenceId)
                .when()
                .get(incidence_details_url);

        //log details and verify status code in extent report
        RestAssuredUtils.printRequestLogInReport(incidence_details_url, "GET", RestAssuredUtils.commonRequestSpecWithToken(getToken()));
        RestAssuredUtils.printResponseLogInReport(response);
        return response;
    }
    public static Response incidenceHistory(int assetId)
    {
        String incidence_history_url = getUrl().getString("base_url")+getUrl().getString("incidence_history_url");

        response = RestAssured.given()
                .spec(RestAssuredUtils.commonRequestSpecWithToken(getToken()))
                .pathParam("assetId", assetId)
                .when()
                .get(incidence_history_url);

        //log details and verify status code in extent report
        RestAssuredUtils.printRequestLogInReport(incidence_history_url, "GET", RestAssuredUtils.commonRequestSpecWithToken(getToken()));
        RestAssuredUtils.printResponseLogInReport(response);
        return response;
    }
    public static Response reportIncidence(ReportIncidence payload)
    {
        String report_incidence_url = getUrl().getString("base_url")+getUrl().getString("report_incidence_url");

        response = RestAssured.given()
                .spec(RestAssuredUtils.commonRequestSpecWithToken(getToken(), payload))
                .when()
                .post(report_incidence_url);
        RestAssuredUtils.validateJsonSchema(response, "reportIncidence.json");

        //log details and verify status code in extent report
        RestAssuredUtils.printRequestLogInReport(report_incidence_url, "POST", RestAssuredUtils.commonRequestSpecWithToken(getToken(), payload));
        RestAssuredUtils.printResponseLogInReport(response);
        return response;
    }
    public static Response resolveIncidence(ResolveIncidence payload)
    {
        String resolve_incidence_url = getUrl().getString("base_url")+getUrl().getString("resolve_incidence_url");

        response = RestAssured.given()
                .spec(RestAssuredUtils.commonRequestSpecWithToken(getToken(), payload))
                .when()
                .post(resolve_incidence_url);
        JwtTokenUtil.responseIncId =payload.getIncidenceId();
        tokenChange();

        //log details and verify status code in extent report
        RestAssuredUtils.printRequestLogInReport(resolve_incidence_url, "POST", RestAssuredUtils.commonRequestSpecWithToken(getToken(), payload));
        RestAssuredUtils.printResponseLogInReport(response);
        return response;
    }
    public static Response reporterResolverDetails(int incidenceId)
    {
        String reporter_resolver_url = getUrl().getString("base_url")+getUrl().getString("reporter_resolver_url");

        response = RestAssured.given()
                .spec(RestAssuredUtils.commonRequestSpecWithToken(getToken()))
                .pathParam("incidenceId", incidenceId)
                .when()
                .get(reporter_resolver_url);

        //log details and verify status code in extent report
        RestAssuredUtils.printRequestLogInReport(reporter_resolver_url, "GET", RestAssuredUtils.commonRequestSpecWithToken(getToken()));
        RestAssuredUtils.printResponseLogInReport(response);
        return response;
    }

}
