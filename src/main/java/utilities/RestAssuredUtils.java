package utilities;

import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import utilities.reporting.ExtentReportManager;

import java.util.ResourceBundle;

import static utilities.reporting.Setup.extentTest;


public class RestAssuredUtils {
    private static Response response;
    public static String baseUrl=getUrl().getString("base_url");
    private static String accessToken = getToken();
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

    public static RequestSpecification commonRequestSpecParam(String oAuthToken, String... queryParams) {
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + oAuthToken);

        for (String queryParam : queryParams) {
            String[] paramParts = queryParam.split(":");
            if (paramParts.length == 2) {
                builder.addQueryParam(paramParts[0].trim(), paramParts[1].trim());
            } else {
                // Handle invalid header format, log or throw an exception as needed
                System.err.println("Invalid query param format: " + queryParam);
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

        if(!queryableRequestSpecification.getQueryParams().isEmpty()) {
            ExtentReportManager.logInfoDetails("Query Params : ");
            ExtentReportManager.logQueryParamsAsTable(queryableRequestSpecification);
        }
        if(queryableRequestSpecification.getBody()!=null) {
            ExtentReportManager.logInfoDetails("Request body : ");
            ExtentReportManager.logJson(queryableRequestSpecification.getBody());
        }
    }

    public static void printResponseLogInReport(Response response) {
//        ExtentReportManager.logInfoDetails("Response status code : " + response.getStatusCode());
//        ExtentReportManager.logInfoDetails("Response time (milliseconds) : " + response.getTimeIn(TimeUnit.MILLISECONDS));
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
            extentTest.get().log(Status.PASS,"JSON schema validation passed!");
        } catch (AssertionError e) {
            extentTest.get().log(Status.FAIL,"JSON schema validation failed! Error: " + e.getMessage());
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

    //
    public static Response queryParamsPositiveCase(String api_url, String accessToken, String... queryParams) {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecParam(accessToken, queryParams))// Set access token as Bearer token
                .when()
                .post(api_url);

        //log details and verify status code in extent report
        printRequestLogInReport(api_url, "POST", commonRequestSpecParam(accessToken, queryParams));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response queryParamsWrongEndpointCase(String api_url, String accessToken, String... queryParams) {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecParam(accessToken, queryParams))// Set access token as Bearer token
                .when()
                .post(api_url+"shr");

        //log details and verify status code in extent report
        printRequestLogInReport(api_url+"shr", "POST", commonRequestSpecParam(accessToken, queryParams));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }

    public static Response queryParamsMethodCase(String api_url, String accessToken, String... queryParams) {
        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecParam(accessToken, queryParams))// Set access token as Bearer token
                .when()
                .get(api_url);

        //log details and verify status code in extent report
        printRequestLogInReport(api_url, "GET", commonRequestSpecParam(accessToken, queryParams));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response queryParamsQueryParamCase(String api_url, String accessToken, String... queryParams) {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecParam(accessToken, queryParams))// Don't set access token as Bearer token
                .when()
                .post(api_url);

        //log details and verify status code in extent report
        printRequestLogInReport(api_url, "GET", commonRequestSpecParam(accessToken, queryParams));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }

    public static Response queryParamsHeaderCase(String api_url, String paramKey, String paramValue, String... headers) {
        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecGet(headers))// Don't set access token as Bearer token
                .queryParam(paramKey,paramValue)
                .when()
                .post(api_url);

        //log details and verify status code in extent report
        printRequestLogInReport(api_url, "POST", commonRequestSpecGet(headers));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }




    public static Response positiveCaseGet(String api_url) {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .get(api_url);

        //log details and verify status code in extent report
        printRequestLogInReport(api_url, "GET", commonRequestSpecWithToken(accessToken));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response wrongEndpointCaseGet(String api_url) {

        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .get(api_url+"shr");

        //log details and verify status code in extent report
        printRequestLogInReport(api_url+"shr", "GET", commonRequestSpecWithToken(accessToken));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response methodCaseGet(String api_url) {
        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecWithToken(accessToken))// Set access token as Bearer token
                .when()
                .post(api_url);

        //log details and verify status code in extent report
        printRequestLogInReport(api_url, "POST", commonRequestSpecWithToken(accessToken));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response headerCaseGet(String api_url, String... headers) {
        // Send a request using the obtained access token
        response = RestAssured.given()
                .spec(commonRequestSpecGet(headers))// Don't set access token as Bearer token
                .when()
                .get(api_url);

        //log details and verify status code in extent report
        printRequestLogInReport(api_url, "GET", commonRequestSpecGet(headers));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }

}
