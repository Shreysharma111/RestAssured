package endpoints.winchcamp;

import io.restassured.response.Response;
import pojos.CSVGenerationForAreaCameraReportPojo;
import utilities.restAssuredFunctions.HttpMethod;

import static utilities.RestAssuredUtils.getUrl;
import static utilities.RestAssuredUtils.positiveCaseWithPayload;

public class CSVGenerationForAreaCameraReport {
    public static String csvGenerationForAreaCameraReport_url = getUrl().getString("csvGenerationForAreaCameraReport_url");
    public static Response csvGenerationForAreaCameraReportPositiveCase(CSVGenerationForAreaCameraReportPojo payload) {
        return positiveCaseWithPayload(HttpMethod.POST, csvGenerationForAreaCameraReport_url, payload);
    }
    public static Response csvGenerationForAreaCameraReportWrongEndpointCase(CSVGenerationForAreaCameraReportPojo payload) {
        return positiveCaseWithPayload(HttpMethod.POST, csvGenerationForAreaCameraReport_url+"shr", payload);
    }
    public static Response csvGenerationForAreaCameraReportMethodCase(CSVGenerationForAreaCameraReportPojo payload) {
        return positiveCaseWithPayload(HttpMethod.GET, csvGenerationForAreaCameraReport_url, payload);
    }
    public static Response csvGenerationForAreaCameraReportHeaderCase(CSVGenerationForAreaCameraReportPojo payload, String... headers) {
        return positiveCaseWithPayload(HttpMethod.POST, csvGenerationForAreaCameraReport_url, payload, headers);
    }
}
