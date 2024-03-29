package utilities;

import utilities.reporting.ExtentReportManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;

import java.util.ResourceBundle;


public class RestAssuredUtils {
    public static String baseUrl=getUrl().getString("base_url");
    public static String incorrectBaseUrl=getUrl().getString("incorrect_base_url");
    //generated to create common request specifications
    public static RequestSpecification commonRequestSpecPost(Object payload, String... headers) {
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
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

    public static RequestSpecification commonRequestSpecPost(Object payload, String oAuthToken, String... headers) {
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .addHeader("Authorization", "Bearer " + oAuthToken)
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

    public static RequestSpecification commonRequestSpecGet(String... headers) {
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType(ContentType.JSON);

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

    public static RequestSpecification commonRequestSpecWithToken(String oAuthToken) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .addHeader("Authorization", "Bearer " + oAuthToken)
                .setContentType(ContentType.JSON)
                .build();
    }

    public static RequestSpecification commonRequestSpecWithoutToken() {
        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType(ContentType.JSON)
                .build();
    }
    public static RequestSpecification commonRequestSpecPost(String oAuthToken, Object payload) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .addHeader("Authorization", "Bearer " + oAuthToken)
                .setContentType(ContentType.JSON)
                .setBody(payload)
                .build();
    }

    public static RequestSpecification commonRequestSpecPost(Object payload) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType(ContentType.JSON)
                .setBody(payload)
                .build();
    }

    public static void printRequestLogInReport(String endpoint, String method, RequestSpecification requestSpecification) {
        QueryableRequestSpecification queryableRequestSpecification = SpecificationQuerier.query(requestSpecification);
        ExtentReportManager.logInfoDetailsNoMarkup("Endpoint    :   " + endpoint);
//        ExtentReportManager.logInfoDetailsNoMarkup("Endpoint    :   " + queryableRequestSpecification.getBasePath());
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
        ExtentReportManager.formatResponseAndPrint(response.getBody().prettyPrint());
    }


    // Method for JSON schema validation
    public static void validateJsonSchema(Response response, String schemaPath) {
        try {
            response.then()
                    .assertThat()
                    .body(JsonSchemaValidator.matchesJsonSchemaInClasspath(schemaPath));
            System.out.println("JSON schema validation passed.");
        } catch (AssertionError e) {
            System.err.println("JSON schema validation failed. Error: " + e.getMessage());
            System.err.println("Response Body: " + response.getBody().asString());
            throw e; // Re-throw the assertion error to mark the test as failed
        }
    }

    public static String getToken() {
        ResourceBundle routes = ResourceBundle.getBundle("logintoken");
        return routes.getString("oAuthToken");
    }

    public static String getWrongToken() {
        ResourceBundle routes = ResourceBundle.getBundle("logintoken");
        return routes.getString("wrong_oAuthToken");
    }

    public static String getValue(String resourceBundleName, String key) {
        ResourceBundle routes = ResourceBundle.getBundle(resourceBundleName);
        return routes.getString(key);
    }


    //method to get URLs from properties file
    public static ResourceBundle getUrl() {
        ResourceBundle routes = ResourceBundle.getBundle("routes");
        return routes;
    }


}
