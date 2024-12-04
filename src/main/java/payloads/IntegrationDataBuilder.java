package payloads;

import io.restassured.response.Response;
import lombok.Getter;
import pojos.*;

import java.util.Iterator;

import static endpoints.winchcamp.CSVGenerationForAreaCameraReport.csvGenerationForAreaCameraReportPositiveCase;
import static endpoints.winchcamp.EventsByFilters.eventsByFiltersPositiveCase;
import static endpoints.winchcamp.SaveCameraConfigs.saveCameraConfigsPositiveCase;
import static endpoints.winchcamp.SaveEvents.saveEventsPositiveCase;
import static endpoints.winchcamp.UpdateCameraConfigs.updateCameraConfigsPositiveCase;
import static payloads.CSVGenerationForAreaCameraReportDataBuilder.csvGenerationForAreaCameraReportIngnData;
import static payloads.CSVGenerationForAreaCameraReportDataBuilder.csvGenerationForAreaCameraReportSingleData;
import static payloads.EventsByFiltersDataBuilder.getEventsByFiltersIngnData;
import static payloads.SaveCameraConfigsDataBuilder.saveCameraConfigsIngnData;
import static payloads.SaveEventsDataBuilder.saveEventsIngnData;
import static payloads.UpdateCameraConfigsDataBuilder.updateCameraConfigsIngnData;

public class IntegrationDataBuilder {
    private static Response response;

    @Getter
    private static SaveCameraConfigsPojo saveCameraConfigData;
    @Getter
    private static UpdateCameraConfigsPojo updateCameraConfigsData;
    @Getter
    private static SaveEventsPojo saveEventsData;
    @Getter
    private static EventsByFiltersPojo eventsByFiltersData;

    public static Response saveCameraConfigIntegrationCase(Integer cameraId, String rtsp, Integer monitoringStatus) {
        // Logic to use payload data from saveCameraConfigsIngnData data provider
        Iterator<SaveCameraConfigsPojo> eventsIterator = saveCameraConfigsIngnData(cameraId, rtsp, monitoringStatus);
        while (eventsIterator.hasNext()) {
            saveCameraConfigData = eventsIterator.next();

            response = saveCameraConfigsPositiveCase(saveCameraConfigData);
        }
        return response;
    }

    public static Response csvGenerationForAreaCameraReportIntegrationCase() {
        // Logic to use payload data from csvGenerationForAreaCameraReportSingleData data provider
        Iterator<CSVGenerationForAreaCameraReportPojo> eventsIterator = csvGenerationForAreaCameraReportSingleData();
        while (eventsIterator.hasNext()) {
            CSVGenerationForAreaCameraReportPojo eventData = eventsIterator.next();

            response = csvGenerationForAreaCameraReportPositiveCase(eventData);
        }
        return response;
    }
    public static Response csvGenerationForAreaCameraReportIntegrationCase(Integer monitoringStatus) {
        // Logic to use payload data from csvGenerationForAreaCameraReportSingleData data provider
        Iterator<CSVGenerationForAreaCameraReportPojo> eventsIterator = csvGenerationForAreaCameraReportIngnData(monitoringStatus);
        while (eventsIterator.hasNext()) {
            CSVGenerationForAreaCameraReportPojo eventData = eventsIterator.next();

            response = csvGenerationForAreaCameraReportPositiveCase(eventData);
        }
        return response;
    }
    public static Response updateCameraConfigsIntegrationCase(Integer dynamicId, Integer cameraId, String guid, Integer monitoringStatus) {
        // Logic to use payload data from updateCameraConfigsSingleData data provider
        Iterator<UpdateCameraConfigsPojo> eventsIterator = updateCameraConfigsIngnData(dynamicId, cameraId, guid, monitoringStatus);
        while (eventsIterator.hasNext()) {
            updateCameraConfigsData = eventsIterator.next();

            response = updateCameraConfigsPositiveCase(updateCameraConfigsData);
        }
        return response;
    }
    public static Response saveEventsIntegrationCase(String entityId1, String entityName1) {
        // Logic to use payload data from updateCameraConfigsSingleData data provider
        Iterator<SaveEventsPojo> eventsIterator = saveEventsIngnData(entityId1, entityName1);
        while (eventsIterator.hasNext()) {
            saveEventsData = eventsIterator.next();

            response = saveEventsPositiveCase(saveEventsData);
        }
        return response;
    }
    public static Response eventsByFiltersIntegrationCase(String entityId1) {
        // Logic to use payload data from updateCameraConfigsSingleData data provider
        Iterator<EventsByFiltersPojo> eventsIterator = getEventsByFiltersIngnData(entityId1);
        while (eventsIterator.hasNext()) {
            eventsByFiltersData = eventsIterator.next();

            response = eventsByFiltersPositiveCase(eventsByFiltersData);
        }
        return response;
    }

}