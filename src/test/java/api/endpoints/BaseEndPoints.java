package api.endpoints;

import api.utilities.JwtTokenUtil;
import api.utilities.reporting.ExtentReportManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import io.restassured.RestAssured;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthJSONAccessTokenResponse;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import java.util.ResourceBundle;

import static api.utilities.JwtTokenUtil.tokenChange;

public class BaseEndPoints {

    //generated to create common request specifications

    public static RequestSpecification commonRequestSpecWithoutToken(Object payload, String... headers) {
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBody(payload);

        for (String header : headers) {
            String[] headerParts = header.split(":");
            if (headerParts.length == 2) {
                builder.addHeader(headerParts[0].trim(), headerParts[1].trim());
            } else {
                // Handle invalid header format, log or throw an exception as needed
                System.err.println("Invalid header format: " + header);
            }
        }

        return builder.build();
    }
    public static RequestSpecification commonRequestSpec(String oAuthToken) {
        return new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer " + oAuthToken)
                .setContentType(ContentType.JSON)
                .build();
    }
    public static RequestSpecification commonRequestSpecWithBody(String oAuthToken, Object payload) {
        return new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer " + oAuthToken)
                .setContentType(ContentType.JSON)
                .setBody(payload)
                .build();
    }

    public static void printRequestLogInReport(String endpoint, String method, RequestSpecification requestSpecification) {
        QueryableRequestSpecification queryableRequestSpecification = SpecificationQuerier.query(requestSpecification);
        ExtentReportManager.logInfoDetailsNoMarkup("Endpoint    :   " + endpoint);
        ExtentReportManager.logInfoDetailsNoMarkup("Method   :   " + method);
        ExtentReportManager.logInfoDetails("Headers : ");
        ExtentReportManager.logHeaders(queryableRequestSpecification.getHeaders().asList());
        if(queryableRequestSpecification.getBody()!=null) {
            ExtentReportManager.logInfoDetails("Request body : ");
            ExtentReportManager.logJson(queryableRequestSpecification.getBody());
        }
    }

    public static void printResponseLogInReport(Response response) {
        ExtentReportManager.logInfoDetails("Response status : " + response.getStatusCode());
//        ExtentReportManager.logInfoDetails("Response Headers are ");
//        ExtentReportManager.logHeaders(response.getHeaders().asList());
        ExtentReportManager.logInfoDetails("Response body : ");
        ExtentReportManager.logJson(response.getBody().prettyPrint());
    }


    // Method for JSON schema validation
    public static void validateJsonSchema(Response response, String schemaPath) {
        response.then()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
    }

    static String getToken() {
        ResourceBundle routes = ResourceBundle.getBundle("logintoken");
        return routes.getString("oAuthToken");
    }


    //method to get URLs from properties file
    static ResourceBundle getUrl() {
        ResourceBundle routes = ResourceBundle.getBundle("routes");
        return routes;
    }


}
