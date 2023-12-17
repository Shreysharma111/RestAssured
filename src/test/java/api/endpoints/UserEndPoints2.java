package api.endpoints;

import api.pojos.*;
import api.utilities.JwtTokenUtil;
import api.utilities.reporting.Setup;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.ResourceBundle;

import static api.utilities.JwtTokenUtil.tokenChange;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

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
                .addHeader("X-HMAC-FROM", "WEB")
                .addHeader("X-APP-DEVICE-ID", "device1")
                .addHeader("X-APP-OS", "Windows-11")
                .addHeader("X-APP-VERSION", "11")
                .setContentType(ContentType.JSON)
                .setBody(payload)
                .build();
    }
    public static RequestSpecification commonRequestSpec(String jwtToken) {
        return new RequestSpecBuilder()
                .addHeader("X-HMAC-FROM", "WEB")
                .addHeader("X-APP-DEVICE-ID", "device1")
                .addHeader("X-APP-OS", "Windows-11")
                .addHeader("X-APP-VERSION", "11")
                .addHeader("x-authorization", jwtToken)
                .setContentType(ContentType.JSON)
                .build();
    }
    public static RequestSpecification commonRequestSpecWithBody(String jwtToken, Object payload) {
        return new RequestSpecBuilder()
                .addHeader("X-HMAC-FROM", "WEB")
                .addHeader("X-APP-DEVICE-ID", "device1")
                .addHeader("X-APP-OS", "Windows-11")
                .addHeader("X-APP-VERSION", "11")
                .addHeader("x-authorization", jwtToken)
                .setContentType(ContentType.JSON)
                .setBody(payload)
                .build();
    }

    // Method for JSON schema validation
    protected static void validateJsonSchema(Response response, String schemaPath) {
        response.then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath(schemaPath));
    }

    public static Response login(Login payload)
    {
        String login_url = getUrl().getString("base_url")+getUrl().getString("login_url");

        response = given()
                .spec(commonRequestSpecWithoutToken(payload))
                .when()
                .post(login_url);

//        validateJsonSchema(response, "loginSchema.json");
        //log details and verify status code in extent report
        System.out.println("Response Body: " + response.getBody().asString());
        Setup.logApiDetails(response);
        Setup.logResultAndDetails(response);

//        JwtTokenUtil.jwtToken =response.jsonPath().getString("jwtToken");
//        jwtToken =response.jsonPath().getString("jwtToken");
        tokenChange();
        return response;
    }
    public static Response login(LoginPoiji payload)
    {
        String login_url = getUrl().getString("base_url")+getUrl().getString("login_url");

        response = given()
                .spec(commonRequestSpecWithoutToken(payload))
                .when()
                .post(login_url);

//        validateJsonSchema(response, "loginSchema.json");
        //log details and verify status code in extent report
        System.out.println("Response Body: " + response.getBody().asString());
        Setup.logApiDetails(response);
        Setup.logResultAndDetails(response);

//        JwtTokenUtil.jwtToken =response.jsonPath().getString("jwtToken");
//        jwtToken =response.jsonPath().getString("jwtToken");
        tokenChange();
        return response;
    }
    public static Response login(LoginScenerioPojo payload)
    {
        String login_url = getUrl().getString("base_url")+getUrl().getString("login_url");

        response = given()
                .spec(commonRequestSpecWithoutToken(payload))
                .when()
                .post(login_url);

//        validateJsonSchema(response, "loginSchema.json");
        //log details and verify status code in extent report
        System.out.println("Response Body: " + response.getBody().asString());
        Setup.logApiDetails(response);
        Setup.logResultAndDetails(response);

//        JwtTokenUtil.jwtToken =response.jsonPath().getString("jwtToken");
//        jwtToken =response.jsonPath().getString("jwtToken");
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
//        validateJsonSchema(response, "inventoryListing.json");

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
    public static Response incidenceDetails(int assetId, int incidenceId)
    {
        String incidence_details_url = getUrl().getString("base_url")+getUrl().getString("incidence_details_url");

        response = given()
                .spec(commonRequestSpec(jwtToken))
                .queryParam("assetId", assetId)
                .queryParam("incidenceId", incidenceId)
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
        validateJsonSchema(response, "reportIncidence.json");

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
