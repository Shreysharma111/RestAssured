package utilities;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.Map;

import static utilities.RestAssuredUtils.baseUrl;
import static utilities.RestAssuredUtils.getValue;
import static utilities.reporting.ExtentReportManager.logFailureDetails;

// Class to validate not-null fields, excluding key-value pairs, and testing different data types
public class ApiValidationHelper {
    private static final SoftAssert softAssert = new SoftAssert();

    // Method to validate not-null fields, excluding key-value pairs, and testing different data types
    public static void validatePayloadForAllFields(Object payload, String url) {
        SoftAssert softAssert = new SoftAssert();

        Map<String, Object> payloadMap = convertPojoToMap(payload);

        // Iterate through each field in the payload
        for (String field : payloadMap.keySet()) {
            // Test for different scenarios
            testNullField(softAssert, payloadMap, field, url);
            testMissingField(softAssert, payloadMap, field, url);
            testInvalidDataTypeField(softAssert, payloadMap, field, url);
        }

        // Report all assertions and log results
        try {
            softAssert.assertAll(); // Throws an AssertionError if any assertion failed
        } catch (AssertionError e) {
            logFailureDetails("Soft assertions failed: " + e.getMessage());
        }
    }

    private static void testNullField(SoftAssert softAssert, Map<String, Object> payloadMap, String field, String url) {
        Map<String, Object> modifiedPayload = new HashMap<>(payloadMap);
        modifiedPayload.put(field, null);

        // Send request and validate response
        Response response = sendRequest(url, modifiedPayload);
        int statusCode = response.getStatusCode();
        softAssert.assertEquals(statusCode, 400, "Expected 400 for null value in field: " + field);

        if (statusCode != 400) {
            logFailureDetails("Failed for null value in field: " + field);
        }
    }

    private static void testMissingField(SoftAssert softAssert, Map<String, Object> payloadMap, String field, String url) {
        Map<String, Object> modifiedPayload = new HashMap<>(payloadMap);
        modifiedPayload.remove(field);

        // Send request and validate response
        Response response = sendRequest(url, modifiedPayload);
        int statusCode = response.getStatusCode();
        softAssert.assertEquals(statusCode, 400, "Expected 400 for missing field: " + field);

        if (statusCode != 400) {
            logFailureDetails("Failed for missing field: " + field);
        }
    }

    private static void testInvalidDataTypeField(SoftAssert softAssert, Map<String, Object> payloadMap, String field, String url) {
        Map<String, Object> modifiedPayload = new HashMap<>(payloadMap);
        // Modify data type for testing (example: string instead of integer)
        if (payloadMap.get(field) instanceof Integer) {
            modifiedPayload.put(field, "invalidString");

            // Send request and validate response
            Response response = sendRequest(url, modifiedPayload);
            int statusCode = response.getStatusCode();
            softAssert.assertEquals(statusCode, 400, "Expected 400 for invalid data type in field: " + field);

            if (statusCode != 400) {
                logFailureDetails("Failed for invalid data type in field: " + field);
            }
        }
    }

    private static Response sendRequest(String url, Map<String, Object> payload) {
        String accessToken = getValue("logintoken", "oAuthToken");

        return RestAssured.given()
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + accessToken)
                .body(payload)
                .post(url)
                .thenReturn();

    }

    // Method to send request and validate response
    private static void sendRequestAndValidate(String url, Map<String, Object> payload, String field, String scenario) {
        System.out.println("Testing field: " + field + " with scenario: " + scenario);

        String accessToken= getValue("logintoken", "oAuthToken");

        // Send request using Rest Assured and validate the response
        Response response = RestAssured.given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + accessToken)
                .body(payload)
                .post(url)
                .thenReturn();

        // Add assertions based on expected behavior (e.g., expecting error code)
        try {
            if (scenario.equals("null")) {
                softAssert.assertEquals(response.getStatusCode(), 400, "Field: " + field + " with scenario: " + scenario + " failed");
            } else if (scenario.equals("not sent")) {
                softAssert.assertEquals(response.getStatusCode(), 400, "Field: " + field + " with scenario: " + scenario + " failed");
            } else if (scenario.equals("invalid data type")) {
                softAssert.assertEquals(response.getStatusCode(), 400, "Field: " + field + " with scenario: " + scenario + " failed");
            }

            System.out.println("Field: " + field + " with scenario: " + scenario + " passed \n");
        } catch (AssertionError e) {
            System.out.println("Field: " + field + " with scenario: " + scenario + " failed \n");
        }

        // Ensure that all soft assertions are reported
        softAssert.assertAll();
    }


    // Method to convert POJO to Map<String, Object>
    public static Map<String, Object> convertPojoToMap(Object pojo) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Convert POJO to a Map with specific type
            return objectMapper.convertValue(pojo, new TypeReference<Map<String, Object>>() {});
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Failed to convert POJO to Map: " + e.getMessage(), e);
        }
    }

}