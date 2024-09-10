package pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EventsByFilterPojo extends BasePojo {
    private Integer facilityId;
    private Integer regionId;
    private Integer areaId;
    private Integer zoneId;
    private String event;
    private String status;
    private String toDate;
    private String fromDate;
    private PageableRequestPojo page;
    @JsonProperty("isCSV")
    private boolean isCSV;

    // Getter and Setter with @JsonProperty annotations
    @JsonProperty("isCSV")
    public boolean isCSV() {
        return isCSV;
    }
    @JsonProperty("isCSV")
    public void setCSV(boolean isCSV) {
        this.isCSV = isCSV;
    }


}
