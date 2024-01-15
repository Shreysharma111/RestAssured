package api.endpoints;

import api.utilities.reporting.ExtentReportManager;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;

public class BaseEndPoints {

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
}
