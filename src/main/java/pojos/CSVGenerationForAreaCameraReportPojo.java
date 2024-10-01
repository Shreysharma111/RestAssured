package pojos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CSVGenerationForAreaCameraReportPojo extends BasePojo {
    private Integer regionId;
    private Integer zoneId;
    private Integer facilityId;
    private String fromDate;
    private String toDate;
    private Integer monitoringStatus;
    private String allEntries;
}
