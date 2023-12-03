package api.endpoints;

import api.pojos.Login;
import api.pojos.ReportIncidence;
import api.pojos.ResolveIncidence;
import api.utilities.JwtTokenUtil;
import api.utilities.reporting.Setup;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.util.ResourceBundle;

import static api.utilities.JwtTokenUtil.tokenChange;
import static io.restassured.RestAssured.given;

/*
Created to perform CRUD requests to the incidence APIs
 */
public class UserEndPoints2 {
    private static Response response;
    private static String jwtToken;

    //method to get URLs from properties file
    static ResourceBundle getUrl() {
        ResourceBundle routes = ResourceBundle.getBundle("routes");
        return routes;
    }

    //generated to create common request specifications
    public static RequestSpecification commonRequestSpecWithoutToken(Object payload) {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBody(payload)
                .build();
    }
    public static RequestSpecification commonRequestSpec(String jwtToken) {
        return new RequestSpecBuilder()
                .addHeader("x-authorization", jwtToken)
                .setContentType(ContentType.JSON)
                .build();
    }
    public static RequestSpecification commonRequestSpecWithBody(String jwtToken, Object payload) {
        return new RequestSpecBuilder()
                .addHeader("x-authorization", jwtToken)
                .setContentType(ContentType.JSON)
                .setBody(payload)
                .build();
    }

    public static Response login(Login payload)
    {
        String login_url = getUrl().getString("base_url")+getUrl().getString("login_url");

        response = given()
                .spec(commonRequestSpecWithoutToken(payload))
                .when()
                .post(login_url);

        //log details and verify status code in extent report
        Setup.logApiDetails(response);
        Setup.logResultAndDetails(response);

        JwtTokenUtil.jwtToken =response.path("jwtToken");
        jwtToken =response.path("jwtToken");
        tokenChange();
        return response;
    }
    public static Response inventoryListing()
    {
        String inventory_listing_url = getUrl().getString("base_url")+getUrl().getString("inventory_listing_url");

        response = given()
                .spec(commonRequestSpec(jwtToken))
                .when()
                .get(inventory_listing_url);

        //log details and verify status code in extent report
        Setup.logApiDetails(response);
        Setup.logResultAndDetails(response);
        return response;
    }
    public static Response incidenceList()
    {
        String incidence_list_url = getUrl().getString("base_url")+getUrl().getString("incidence_list_url");

        response = given()
                .spec(commonRequestSpec(jwtToken))
                .when()
                .get(incidence_list_url);

        //log details and verify status code in extent report
        Setup.logApiDetails(response);
        Setup.logResultAndDetails(response);
        return response;
    }
    public static Response incidenceDetails(int assetId)
    {
        String incidence_details_url = getUrl().getString("base_url")+getUrl().getString("incidence_details_url");

        response = given()
                .spec(commonRequestSpec(jwtToken))
                .pathParam("assetId", assetId)
                .when()
                .get(incidence_details_url);

        //log details and verify status code in extent report
        Setup.logApiDetails(response);
        Setup.logResultAndDetails(response);
        return response;
    }
    public static Response incidenceHistory(int assetId)
    {
        String incidence_history_url = getUrl().getString("base_url")+getUrl().getString("incidence_history_url");

        response = given()
                .spec(commonRequestSpec(jwtToken))
                .pathParam("assetId", assetId)
                .when()
                .get(incidence_history_url);

        //log details and verify status code in extent report
        Setup.logApiDetails(response);
        Setup.logResultAndDetails(response);
        return response;
    }
    public static Response reportIncidence(ReportIncidence payload)
    {
        String report_incidence_url = getUrl().getString("base_url")+getUrl().getString("report_incidence_url");

        response = given()
                .spec(commonRequestSpecWithBody(jwtToken, payload))
                .when()
                .post(report_incidence_url);

        //log details and verify status code in extent report
        Setup.logApiDetails(response);
        Setup.logResultAndDetails(response);
        return response;
    }
    public static Response resolveIncidence(ResolveIncidence payload)
    {
        String resolve_incidence_url = getUrl().getString("base_url")+getUrl().getString("resolve_incidence_url");

        response = given()
                .spec(commonRequestSpecWithBody(jwtToken, payload))
                .when()
                .post(resolve_incidence_url);
        JwtTokenUtil.responseIncId =payload.getIncidenceId();
        tokenChange();

        //log details and verify status code in extent report
        Setup.logApiDetails(response);
        Setup.logResultAndDetails(response);
        return response;
    }
    public static Response reporterResolverDetails(int incidenceId)
    {
        String reporter_resolver_url = getUrl().getString("base_url")+getUrl().getString("reporter_resolver_url");

        response = given()
                .spec(commonRequestSpec(jwtToken))
                .pathParam("incidenceId", incidenceId)
                .when()
                .get(reporter_resolver_url);

        //log details and verify status code in extent report
        Setup.logApiDetails(response);
        Setup.logResultAndDetails(response);
        return response;
    }

}
