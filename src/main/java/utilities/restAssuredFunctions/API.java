package utilities.restAssuredFunctions;

import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;
import static utilities.reporting.Setup.extentTest;


public class API {

    RequestSpecification reqSpec;
    Method method;
    String url;
    static Response resp;

    public void init(String url, Method method) {
        this.url = url;
        this.method = method;
        reqSpec = given();
    }

    public void initBase(String baseConst) {
//        Helper getHelp = new Helper();
//        getHelp.set_path("src/main/resources/Constants.properties");
//        try {
//            RestAssured.baseURI = getHelp.loadProperties(baseConst);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        reqSpec = RestAssured.given();
    }

    public void setHeader(String[][] head) {
        for (int row = 0; row < head.length; row++)
            for (int col = 0; col < head[row].length; col++)
                reqSpec.header(head[row][col], head[row][col + 1]);
    }

    public void setHeader(String head, String val) {
        reqSpec.header(head, val);
    }

    public void setBody(String body) {
        reqSpec.body(body);
    }

    public void setFormParam(String key, String val) {
        reqSpec.formParam(key, val);
    }

    public void setQueryParam(String key, String val) {
        reqSpec.queryParam(key, val);
    }

    public String callIt() {
        if (method.toString().equalsIgnoreCase("get")) {
            resp = reqSpec.get(url);
            return resp.asString();
        } else if (method.toString().equalsIgnoreCase("post")) {
            resp = reqSpec.post(url);
            return resp.asString();
        } else if (method.toString().equalsIgnoreCase("patch")) {
            resp = reqSpec.patch(url);
            return resp.asString();
        } else if (method.toString().equalsIgnoreCase("put")) {
            resp = reqSpec.put(url);
            return resp.asString();
        } else if (method.toString().equalsIgnoreCase("delete")) {
            resp = reqSpec.delete(url);
            return resp.asString();
        }
        return "invalid method set for API";
    }

    // Method to assert a key's value in the response
    public static void assertKeyValue(Response response, String key, String expectedValue) {
        // Extracting the actual value of the key from the response
        String actualValue = response.jsonPath().getString(key);

        // Asserting the actual value against the expected value
        if (actualValue.equals(expectedValue) || actualValue.contains(expectedValue)) {
            extentTest.get().log(Status.PASS, "Assertion passed! " + key + " in response body has the expected value: " + expectedValue);
        } else {
            extentTest.get().log(Status.FAIL, "Assertion failed! " + key + " in response body does not have the expected value. Expected: " +
                    expectedValue + ", Actual: " + actualValue);
        }
    }

    public void assertIt(List<List<Object>> data) {
        for (List<Object> li : data) {
            switch (((Method) li.get(2)).toString()) {
                case "EQUALS":
                    resp.then().body(((String) li.get(0)), equalTo((String) li.get(1)));
                    break;

                case "KEY_PRESENTS":
                    resp.then().body(((String) li.get(0)), hasKey((String) li.get(1)));
                    break;

                case "HAS_ALL":
                    break;
            }
        }
    }

    // Method for asserting the status code
    public static void assertStatusCode(Response response, int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        try {
            assertEquals(actualStatusCode, expectedStatusCode, "Status code is not as expected");
            extentTest.get().log(Status.PASS, "Status code assertion passed! Expected: " + expectedStatusCode +
                    ", Actual: " + actualStatusCode);
            // You can log this information to your report here.
        } catch (AssertionError e) {
            extentTest.get().log(Status.FAIL, "Status code assertion failed! Expected: " + expectedStatusCode +
                    ", Actual: " + actualStatusCode);
            throw e; // Re-throw the AssertionError to propagate the failure up the call stack.
        }
    }

    // Method for asserting that a particular field is present and not empty in the response body
    public static void assertFieldIsPresentAndNotEmpty(Response response, String fieldName) {
        String fieldValue = response.jsonPath().getString(fieldName);
        try {
            Assert.assertNotNull(fieldValue, "Field '" + fieldName + "' is not present in the response body");
            Assert.assertFalse(fieldValue.isEmpty(), "Field '" + fieldName + "' is empty");
            extentTest.get().log(Status.PASS, "Assertion passed! Field '" + fieldName + "' is present in the response body " +
                    "and is not empty");

        } catch (AssertionError e) {
            extentTest.get().log(Status.FAIL, "Assertion failed! " + e.getMessage());
            throw e; // Re-throw the AssertionError to propagate the failure up the call stack.
        }
    }

    public static void assertFieldIsPresentAndEmpty(Response response, String fieldName) {
        String fieldValue = response.jsonPath().getString(fieldName);
        try {
            Assert.assertNotNull(fieldValue, "Field '" + fieldName + "' is not present in the response body");
            Assert.assertTrue(fieldValue.isEmpty(), "Field '" + fieldName + "' is not empty");

            // Log the assertion pass
            extentTest.get().log(Status.PASS, "Assertion passed! Field '" + fieldName + "' is present in the response body and is empty");

        } catch (AssertionError e) {
            // Log the assertion fail
            extentTest.get().log(Status.FAIL, "Assertion failed! " + e.getMessage());
            throw e; // Re-throw the AssertionError to propagate the failure up the call stack.
        }
    }

    public static void assertArrayIsPresentAndEmpty(Response response, String arrayPath) {
        try {
            // Convert the response to JsonPath
            JsonPath jsonPath = response.jsonPath();

            // Get the array from the JSON response using JSONPath
            Object arrayObject = jsonPath.get(arrayPath);

            // Assert that the array is present and empty
            Assert.assertNotNull(arrayObject, "Expected array at path '" + arrayPath + "' to be present.");
            Assert.assertTrue(arrayObject instanceof List && ((List<?>) arrayObject).isEmpty(),
                    "Expected array at path '" + arrayPath + "' to be empty.");

            // Log the assertion pass
            extentTest.get().log(Status.PASS, "Assertion passed! Array at path '" + arrayPath + "' is present in the response body and is empty");
        } catch (AssertionError e) {
            // Log the assertion fail
            extentTest.get().log(Status.FAIL, "Assertion failed! " + e.getMessage());
            throw e; // Re-throw the AssertionError to propagate the failure up the call stack.
        }
    }


    // Method to assert the response time
    public static void assertResponseTime(Response response) {
        long responseTime = response.getTime();

        // Create a SoftAssert instance
        SoftAssert softAssert = new SoftAssert();

        // Check if the response time is less than 500 milliseconds
        softAssert.assertTrue(responseTime <= 800, "Response time is less than 800 milliseconds: " + responseTime + "ms");

        try {
            // Perform all the soft assertions
            softAssert.assertAll();

            // If no exceptions are thrown, it means all assertions passed
            extentTest.get().log(Status.PASS, "Assertion passed! Response time is less than 800 milliseconds: " + responseTime + "ms");
        } catch (AssertionError e) {
            // Log the failure and re-throw the AssertionError
            extentTest.get().log(Status.FAIL, "Assertion failed! Response time is greater than 800 milliseconds: " + responseTime + "ms");
            throw e;
        }
    }

    public static void assertFieldsExistInEachObject(Response response, String... fieldNames) {
        // Parse the response body to JsonPath object
        JsonPath jsonPath = response.jsonPath();

        // Extract the list of objects under the "data" field
        List<Map<String, Object>> dataList = jsonPath.getList("data");

        // Loop through each object in the list
        for (Map<String, Object> item : dataList) {
            // Check if each specified field exists
            for (String fieldName : fieldNames) {
                Object fieldValue = item.get(fieldName);

                // Assert that the field is not null, meaning it exists
                Assert.assertNotNull(fieldValue, "Field '" + fieldName + "' does not exist in one of the objects.");
            }
        }

        // Logging success message
        extentTest.get().log(Status.PASS,"All objects contain the fields: " + String.join(", ", fieldNames));
    }
    public static void validateDatesWithinRange(Response response, String fromDate, String toDate) {
        // Parse the response body to JsonPath object
        JsonPath jsonPath = response.jsonPath();

        // Extract the list of objects under the "data" field
        List<Map<String, Object>> dataList = jsonPath.getList("data");

        // Define the date format used in the response and request
        SimpleDateFormat partDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat requestDateFormat = new SimpleDateFormat("dd-MM-yyyy");

        // Parse the fromDate and toDate from the request
        Date from = null;
        Date to = null;
        try {
            from = requestDateFormat.parse(fromDate);
            to = requestDateFormat.parse(toDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        boolean allDatesValid = true;  // Flag to track if all dates are within range

        // Loop through each object in the list and validate the partDate field
        for (Map<String, Object> item : dataList) {
            String partDateStr = (String) item.get("partDate");
            Date eventDate = null;
            try {
                eventDate = partDateFormat.parse(partDateStr);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            // Assert that the partDate is within the specified range
            if (eventDate.compareTo(from) >= 0 && eventDate.compareTo(to) <= 0) {
                extentTest.get().log(Status.PASS, "PartDate '" + partDateStr + "' is within the expected range of '" + fromDate + "' and '" + toDate + "'.");
            } else {
                allDatesValid = false;
                extentTest.get().log(Status.FAIL, "PartDate '" + partDateStr + "' is NOT within the expected range of '" + fromDate + "' and '" + toDate + "'.");
            }
        }

        // Logging a final success message if all dates are valid
        if (allDatesValid) {
            extentTest.get().log(Status.PASS, "All objects contain partDate within the expected range.");
        } else {
            extentTest.get().log(Status.FAIL, "One or more objects contain partDate outside the expected range.");
        }
    }

    public static void assertFieldsWithExpectedValues(Response response, Map<String, Object> expectedFieldValues) {
        // Parse the response body to JsonPath object
        JsonPath jsonPath = response.jsonPath();

        // Extract the list of objects under the "data" field
        List<Map<String, Object>> dataList = jsonPath.getList("data");

        // Loop through each object in the list
        for (Map<String, Object> item : dataList) {
            // Check if each specified field exists and matches the expected value
            for (Map.Entry<String, Object> entry : expectedFieldValues.entrySet()) {
                String fieldName = entry.getKey();
                Object expectedValue = entry.getValue();

                Object actualValue = item.get(fieldName);

                // Assert that the field is not null (exists) and that its value matches the expected value
                Assert.assertNotNull(actualValue, "Field '" + fieldName + "' does not exist in one of the objects.");
                Assert.assertEquals(actualValue, expectedValue, "Field '" + fieldName + "' does not match the expected value. Expected: " + expectedValue + ", but got: " + actualValue);
            }
        }

        // Logging success message
        extentTest.get().log(Status.PASS, "All objects contain the fields with expected values: " + expectedFieldValues.toString());
    }

    public String extractString(String path) { return resp.jsonPath().getString(path);}

    public int extractInt(String path) { return resp.jsonPath().getInt(path);}

    public List<?> extractList(String path) { return resp.jsonPath().getList(path);}

    public Object extractIt(String path) { return resp.jsonPath().get(path); }

    public String extractHeader(String header_name) { return resp.header(header_name);}

    public String getResponseString() { return resp.asString();}

    public void fileUpload(String key, String path, String mime) {
        reqSpec.multiPart(key, new File(path), mime);
    }

    public void multiPartString(String key, String input) { reqSpec.multiPart(key,input);}

    public void printResp() { resp.print();}

    public String getCookieValue(String cookieName) { return resp.getCookie(cookieName); }

    public API assertIt(int code, int optionalCode) {
        resp.then().statusCode(anyOf(equalTo(code),equalTo(optionalCode)));
        return this;
    }

    public void setRedirection(boolean followRedirects) {
        reqSpec.when().redirects().follow(followRedirects);
    }

    public void ListResponseHeaders()
    {
        // Get all the headers. Return value is of type Headers.
        Headers allHeaders = resp.headers();
        // Iterate over all the Headers
        for(Header header : allHeaders)
        {
            System.out.println("Key: " + header.getName() + " Value: " + header.getValue());
        }
    }

    public int getStatusCode() { return resp.getStatusCode(); }

    public Headers getAllHeaders() {return resp.getHeaders();}
}
