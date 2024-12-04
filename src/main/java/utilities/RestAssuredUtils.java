package utilities;

import com.aventstack.extentreports.Status;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.json.JSONArray;
import org.json.JSONObject;
import utilities.reporting.ExtentReportManager;
import utilities.restAssuredFunctions.HttpMethod;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static utilities.reporting.Setup.extentTest;


public class RestAssuredUtils {
    private static Response response;
    public static String baseUrl=BaseSetup.getBaseUrl();
    private static String accessToken;

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
    public static RequestSpecification commonRequestSpec(String oAuthToken, String... headers) {
        RequestSpecBuilder builder = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .addHeader("Authorization", "Bearer " + oAuthToken)
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
        Properties properties = new Properties();
        try (FileInputStream inputStream = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\logintoken.properties")) {
            properties.load(inputStream);
            return properties.getProperty("oAuthToken");
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Handle exception or return a default token value
        }
    }


    public static String getWrongToken() {
        ResourceBundle routes = ResourceBundle.getBundle("logintoken");
        return routes.getString("wrong_oAuthToken");
    }

//    public static String getValueUsingResourceBundle(String resourceBundleName, String key) {
//        ResourceBundle routes = ResourceBundle.getBundle(resourceBundleName);
//        return routes.getString(key);
//    }

    // Method to get a value from a properties file
    public static String getValue(String propertiesFileName, String key) {
        Properties properties = new Properties();
        String value = null;

        try {
            // Construct the path to the properties file
            String propertiesFilePath = System.getProperty("user.dir") + "\\src\\test\\resources\\" + propertiesFileName + ".properties";

            // Load the properties file
            FileInputStream fileInputStream = new FileInputStream(propertiesFilePath);
            properties.load(fileInputStream);
            fileInputStream.close();

            // Retrieve the value associated with the key
            value = properties.getProperty(key);
        } catch (IOException e) {
            System.err.println("Error reading properties file: " + e.getMessage());
        }

        return value;
    }

    //method to get URLs from properties file
    public static ResourceBundle getUrl() {
        return ResourceBundle.getBundle("routes");
    }

    // Method to get a specific URL by its name from the properties file
    public static String getUrl(String urlKey) {
        Properties properties = new Properties();

        try {
            // Construct the path to the properties file
            String propertiesFilePath = System.getProperty("user.dir") + "\\src\\test\\resources\\routes.properties";

            // Load the properties file
            FileInputStream fileInputStream = new FileInputStream(propertiesFilePath);
            properties.load(fileInputStream);
            fileInputStream.close();

            // Return the value of the specified URL key
            return properties.getProperty(urlKey);

        } catch (IOException e) {
            System.err.println("Error reading properties file: " + e.getMessage());
            return null; // return null in case of an error
        }
    }

    //
    public static Response queryParamsPositiveCase(HttpMethod method, String api_url, String accessToken, String... queryParams) {

        // Send a request using the obtained access token
        RequestSpecification requestSpec = RestAssured.given().spec(commonRequestSpecParam(accessToken, queryParams));// Set access token as Bearer token
        // Switch based on the HTTP method type
        Response response;
        switch (method) {
            case GET:
                response = requestSpec.when().get(api_url);
                break;
            case POST:
                response = requestSpec.when().post(api_url);
                break;
            case PUT:
                response = requestSpec.when().put(api_url);
                break;
            case DELETE:
                response = requestSpec.when().delete(api_url);
                break;
            case PATCH:
                response = requestSpec.when().patch(api_url);
                break;
            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }
        //log details and verify status code in extent report
        printRequestLogInReport(api_url, method.name(), commonRequestSpecParam(accessToken, queryParams));
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
        printRequestLogInReport(api_url, "POST", commonRequestSpecParam(accessToken, queryParams));
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }

    public static Response queryParamsHeaderCase(HttpMethod method, String api_url, String paramKey, int paramValue, String... headers) {
        // Send a request using the obtained access token
        RequestSpecification requestSpec = RestAssured.given().spec(commonRequestSpecGet(headers));
        // Switch based on the HTTP method type
        Response response;
        switch (method) {
            case GET:
                response = requestSpec.queryParam(paramKey, paramValue).when().get(api_url);
                break;
            case POST:
                response = requestSpec.queryParam(paramKey, paramValue).when().post(api_url);
                break;
            case PUT:
                response = requestSpec.queryParam(paramKey, paramValue).when().put(api_url);
                break;
            case DELETE:
                response = requestSpec.queryParam(paramKey, paramValue).when().delete(api_url);
                break;
            case PATCH:
                response = requestSpec.queryParam(paramKey, paramValue).when().patch(api_url);
                break;
            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }

        // Log details and verify status code in Extent Report
        printRequestLogInReport(api_url, method.name(), requestSpec);
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }

    public static Response queryParamsHeaderCase(HttpMethod method, String api_url, String paramKey, String paramValue, String... headers) {
        // Send a request using the obtained access token
        RequestSpecification requestSpec = RestAssured.given().spec(commonRequestSpecGet(headers));
        // Switch based on the HTTP method type
        Response response;
        switch (method) {
            case GET:
                response = requestSpec.queryParam(paramKey, paramValue).when().get(api_url);
                break;
            case POST:
                response = requestSpec.queryParam(paramKey, paramValue).when().post(api_url);
                break;
            case PUT:
                response = requestSpec.queryParam(paramKey, paramValue).when().put(api_url);
                break;
            case DELETE:
                response = requestSpec.queryParam(paramKey, paramValue).when().delete(api_url);
                break;
            case PATCH:
                response = requestSpec.queryParam(paramKey, paramValue).when().patch(api_url);
                break;
            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }

        // Log details and verify status code in Extent Report
        printRequestLogInReport(api_url, method.name(), requestSpec);
        ExtentReportManager.logInfoDetails("Assertions :");
        return response;
    }
    public static Response positiveCaseGet(String api_url) {
        accessToken = getToken();
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
        accessToken = getToken();
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
        accessToken = getToken();
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



    // Dynamic method to handle different HTTP methods
    public static Response positiveCase(HttpMethod method, String api_url, int... pathParams) {
        accessToken = getToken();
        // Ensure that api_url contains the correct number of placeholders (e.g., /v1/user/facilities/{regionId}/{zoneId})
        // Example: api_url should be "/v1/user/facilities/{regionId}/{zoneId}" if expecting two path parameters

        // Get the common request specification with the token
        RequestSpecification requestSpec = RestAssured.given().spec(commonRequestSpecWithToken(accessToken));

        // Convert pathParams to a Map if needed for named placeholders
        Map<String, Object> pathParamsMap = new LinkedHashMap<>();
        for (int i = 0; i < pathParams.length; i++) {
            pathParamsMap.put("param" + i, pathParams[i]);
        }

        // Switch based on the HTTP method type
        Response response;
        switch (method) {
            case GET:
                response = requestSpec.pathParams(pathParamsMap).when().get(api_url);
                break;
            case POST:
                response = requestSpec.pathParams(pathParamsMap).when().post(api_url);
                break;
            case PUT:
                response = requestSpec.pathParams(pathParamsMap).when().put(api_url);
                break;
            case DELETE:
                response = requestSpec.pathParams(pathParamsMap).when().delete(api_url);
                break;
            case PATCH:
                response = requestSpec.pathParams(pathParamsMap).when().patch(api_url);
                break;
            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }
        // Format the URL for logging
        String formattedUrl = api_url;
        for (int i = 0; i < pathParams.length; i++) {
            formattedUrl = formattedUrl.replace("{param" + i + "}", String.valueOf(pathParams[i]));
        }


        // Log details and verify status code in Extent Report
        printRequestLogInReport(formattedUrl, method.name(), requestSpec);
        ExtentReportManager.logInfoDetails("Assertions :");

        return response;
    }
    // Dynamic method to handle different HTTP methods
    public static Response positiveCaseWithStringPathParams(HttpMethod method, String api_url, String... pathParams) {
        accessToken = getToken();
        // Ensure that api_url contains the correct number of placeholders (e.g., /v1/user/facilities/{regionId}/{zoneId})
        // Example: api_url should be "/v1/user/facilities/{regionId}/{zoneId}" if expecting two path parameters

        // Get the common request specification with the token
        RequestSpecification requestSpec = RestAssured.given().spec(commonRequestSpecWithToken(accessToken));

        // Convert pathParams to a Map if needed for named placeholders
        Map<String, Object> pathParamsMap = new LinkedHashMap<>();
        for (int i = 0; i < pathParams.length; i++) {
            pathParamsMap.put("param" + i, pathParams[i]);
        }

        // Switch based on the HTTP method type
        Response response;
        switch (method) {
            case GET:
                response = requestSpec.pathParams(pathParamsMap).when().get(api_url);
                break;
            case POST:
                response = requestSpec.pathParams(pathParamsMap).when().post(api_url);
                break;
            case PUT:
                response = requestSpec.pathParams(pathParamsMap).when().put(api_url);
                break;
            case DELETE:
                response = requestSpec.pathParams(pathParamsMap).when().delete(api_url);
                break;
            case PATCH:
                response = requestSpec.pathParams(pathParamsMap).when().patch(api_url);
                break;
            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }
        // Format the URL for logging
        String formattedUrl = api_url;
        for (int i = 0; i < pathParams.length; i++) {
            formattedUrl = formattedUrl.replace("{param" + i + "}", String.valueOf(pathParams[i]));
        }


        // Log details and verify status code in Extent Report
        printRequestLogInReport(formattedUrl, method.name(), requestSpec);
        ExtentReportManager.logInfoDetails("Assertions :");

        return response;
    }

    // Dynamic method to handle different HTTP methods
    public static Response positiveCase(HttpMethod method, String api_url, int pathParams, String... headers) {

        // Get the common request specification with the token
        RequestSpecification requestSpec = RestAssured.given().spec(commonRequestSpecGet(headers));

        // Switch based on the HTTP method type
        Response response;
        switch (method) {
            case GET:
                response = requestSpec.pathParam("param0", pathParams).when().get(api_url);
                break;
            case POST:
                response = requestSpec.pathParam("param0", pathParams).when().post(api_url);
                break;
            case PUT:
                response = requestSpec.pathParam("param0", pathParams).when().put(api_url);
                break;
            case DELETE:
                response = requestSpec.pathParam("param0", pathParams).when().delete(api_url);
                break;
            case PATCH:
                response = requestSpec.pathParam("param0", pathParams).when().patch(api_url);
                break;
            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }
        // Format the URL for logging
        String formattedUrl = api_url.replace("{param0}", String.valueOf(pathParams));


        // Log details and verify status code in Extent Report
        printRequestLogInReport(formattedUrl, method.name(), requestSpec);
        ExtentReportManager.logInfoDetails("Assertions :");

        return response;
    }
    // Dynamic method to handle different HTTP methods
    public static Response positiveCase(HttpMethod method, String api_url, String pathParams, String... headers) {

        // Get the common request specification with the token
        RequestSpecification requestSpec = RestAssured.given().spec(commonRequestSpecGet(headers));

        // Switch based on the HTTP method type
        Response response;
        switch (method) {
            case GET:
                response = requestSpec.pathParam("param0", pathParams).when().get(api_url);
                break;
            case POST:
                response = requestSpec.pathParam("param0", pathParams).when().post(api_url);
                break;
            case PUT:
                response = requestSpec.pathParam("param0", pathParams).when().put(api_url);
                break;
            case DELETE:
                response = requestSpec.pathParam("param0", pathParams).when().delete(api_url);
                break;
            case PATCH:
                response = requestSpec.pathParam("param0", pathParams).when().patch(api_url);
                break;
            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }
        // Format the URL for logging
        String formattedUrl = api_url.replace("{param0}", String.valueOf(pathParams));


        // Log details and verify status code in Extent Report
        printRequestLogInReport(formattedUrl, method.name(), requestSpec);
        ExtentReportManager.logInfoDetails("Assertions :");

        return response;
    }

    public static Response positiveCase(HttpMethod method, String api_url) {
        accessToken = getToken();
        RequestSpecification requestSpec = RestAssured.given().spec(commonRequestSpecWithToken(accessToken));

        // Switch based on the HTTP method type
        Response response= executeRequest(method, requestSpec, api_url);

        // Log details and verify status code in Extent Report
        printRequestLogInReport(api_url, method.name(), requestSpec);
        ExtentReportManager.logInfoDetails("Assertions :");

        return response;
    }
    public static Response positiveCase(HttpMethod method, String api_url, String... headers) {
        RequestSpecification requestSpec = RestAssured.given().spec(commonRequestSpecGet(headers));

        // Switch based on the HTTP method type
        Response response= executeRequest(method, requestSpec, api_url);

        // Log details and verify status code in Extent Report
        printRequestLogInReport(api_url, method.name(), requestSpec);
        ExtentReportManager.logInfoDetails("Assertions :");

        return response;
    }

    public static Response positiveCaseWithPayload(HttpMethod method, String api_url, Object payload) {
        accessToken = getToken();
        // Create the request specification with the common token setup
        RequestSpecification requestSpec = RestAssured.given().spec(commonRequestSpecWithToken(accessToken));

        // Add payload to the request if it's not null
        if (payload != null) {
            requestSpec.body(payload);
        }

        // Switch based on the HTTP method type
        Response response= executeRequest(method, requestSpec, api_url);

        // Log details and verify status code in Extent Report
        printRequestLogInReport(api_url, method.name(), requestSpec);
        ExtentReportManager.logInfoDetails("Assertions :");

        return response;
    }
    public static Response positiveCaseWithPayload(HttpMethod method, String api_url, Object payload, String... headers) {
        // Create the request specification with the common token setup
        RequestSpecification requestSpec = RestAssured.given().spec(commonRequestSpecGet(headers));

        // Add payload to the request if it's not null
        if (payload != null) {
            requestSpec.body(payload);
        }
        // Switch based on the HTTP method type
        Response response= executeRequest(method, requestSpec, api_url);

        // Log details and verify status code in Extent Report
        printRequestLogInReport(api_url, method.name(), requestSpec);
        ExtentReportManager.logInfoDetails("Assertions :");

        return response;
    }

    //common method designed to use switch case based on th HTTP method type
    public static Response executeRequest(HttpMethod method, RequestSpecification requestSpec, String apiUrl) {
        // Switch based on the HTTP method type
        switch (method) {
            case GET:
                return requestSpec.when().get(apiUrl);
            case POST:
                return requestSpec.when().post(apiUrl);
            case PUT:
                return requestSpec.when().put(apiUrl);
            case DELETE:
                return requestSpec.when().delete(apiUrl);
            case PATCH:
                return requestSpec.when().patch(apiUrl);
            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }
    }
    public static void validateFields(Response response, String guidField, String guidValue, Map<String, Object> conditions) {
        // Extract the JSON array from the response
        JSONArray dataArray = new JSONArray(response.getBody().asString());

        // Iterate through each object in the array
        for (int i = 0; i < dataArray.length(); i++) {
            JSONObject obj = dataArray.getJSONObject(i);

            // Check if the guidField matches the guidValue
            if (obj.getString(guidField).equals(guidValue)) {
                // Validate each condition
                boolean allConditionsMet = true;

                for (Map.Entry<String, Object> entry : conditions.entrySet()) {
                    String field = entry.getKey();
                    Object expectedValue = entry.getValue();

                    if (obj.has(field)) {
                        Object actualValue = obj.get(field);

                        // Check if the expected value is an Integer
                        if (expectedValue instanceof Integer) {
                            // Convert the expected code to the corresponding string status
                            String expectedStatus = Values.getStatusFromCode((Integer) expectedValue);

                            if (actualValue instanceof String && actualValue.equals(expectedStatus)) {
                                // Validation passed
                                continue;
                            } else {
                                System.out.println("Validation failed for field: " + field);
                                System.out.println("Expected: " + expectedStatus + ", Found: " + actualValue);
                                allConditionsMet = false;
                            }
                        } else if (expectedValue instanceof String) {
                            // Direct comparison for string values
                            if (actualValue instanceof String && actualValue.equals(expectedValue)) {
                                // Validation passed
                                continue;
                            } else {
                                System.out.println("Validation failed for field: " + field);
                                System.out.println("Expected: " + expectedValue + ", Found: " + actualValue);
                                allConditionsMet = false;
                            }
                        } else {
                            System.out.println("Unexpected type for expectedValue: " + expectedValue.getClass().getName());
                            allConditionsMet = false;
                        }
                    } else {
                        System.out.println("Field not found in response: " + field);
                        allConditionsMet = false;
                    }
                }

                if (allConditionsMet) {
                    System.out.println("All conditions met for guid: " + guidValue);
                }
                return; // Exit method after validating the expected guid
            }
        }

        System.out.println("Guid not found in the response: " + guidValue);
    }

    public static Object findFieldByGuid(Response response, String jsonPath, String fieldName, String fieldValue, String returnField) {
        JsonPath path = response.jsonPath();
        Object data = path.get(jsonPath);

        if (data instanceof List) {
            // Handle case where 'data' is an array
            List<Map<String, Object>> dataList = (List<Map<String, Object>>) data;
            for (Map<String, Object> obj : dataList) {
                if (obj.containsKey(fieldName) && obj.get(fieldName).equals(fieldValue)) {
                    return obj.get(returnField);
                }
            }
        } else if (data instanceof Map) {
            // Handle case where 'data' is a single object
            Map<String, Object> dataMap = (Map<String, Object>) data;
            if (dataMap.containsKey(fieldName) && dataMap.get(fieldName).equals(fieldValue)) {
                return dataMap.get(returnField);
            }
        }
        throw new NoSuchElementException("No match found for " + fieldName + ": " + fieldValue);
    }

    public static void assertConditionsByGuid(Response response, String jsonPath, String fieldName, String fieldValue, Map<String, Object> conditions) {
        JsonPath path = response.jsonPath();
        Object data = path.get(jsonPath);
        boolean allConditionsPassed = true; // To track overall result

        if (data instanceof List) {
            List<Map<String, Object>> dataList = (List<Map<String, Object>>) data;

            for (Map<String, Object> obj : dataList) {
                if (obj.containsKey(fieldName) && obj.get(fieldName).equals(fieldValue)) {
                    // Validate conditions for this object
                    allConditionsPassed = checkConditions(obj, conditions);
                    break; // Stop searching after the first match
                }
            }
        } else if (data instanceof Map) {
            Map<String, Object> dataMap = (Map<String, Object>) data;
            if (dataMap.containsKey(fieldName) && dataMap.get(fieldName).equals(fieldValue)) {
                allConditionsPassed = checkConditions(dataMap, conditions);
            }
        }

        // Log the final result
        if (allConditionsPassed) {
            extentTest.get().log(Status.PASS, "All conditions are satisfied.");
        } else {
            extentTest.get().log(Status.FAIL, "One or more conditions failed.");
        }
    }

    private static boolean checkConditions(Map<String, Object> obj, Map<String, Object> conditions) {
        boolean allConditionsPassed = true;

        for (Map.Entry<String, Object> condition : conditions.entrySet()) {
            String key = condition.getKey();
            Object expectedValue = condition.getValue();

            if (!obj.containsKey(key) || !obj.get(key).equals(expectedValue)) {
                extentTest.get().log(Status.FAIL, "Condition failed for field: " + key + ". Expected: " + expectedValue + " but found: " + obj.get(key));
                allConditionsPassed = false; // Mark as failed if any condition doesn't match
            } else {
                extentTest.get().log(Status.PASS, "Condition passed for field: " + key + " with value: " + expectedValue);
            }
        }

        return allConditionsPassed; // Return overall result
    }

    // Method to get today's date in "dd-MM-yyyy" format
    public static String getCurrentDate() {
        // Get the current date
        LocalDate today = LocalDate.now();

        // Define the desired date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        // Return the formatted date as a string
        return today.format(formatter);
    }

}
