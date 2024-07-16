package pojos;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SaveCameraConfigsPojo extends BasePojo {
    private int areaId;
    private int cameraId;
    private int facilityId;
    private String rtspUrl;
    private int monitoringStatus;
    private List<String> eventId;
}
