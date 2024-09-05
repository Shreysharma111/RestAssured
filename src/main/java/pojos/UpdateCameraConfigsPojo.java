package pojos;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UpdateCameraConfigsPojo extends BasePojo {
    private Integer id;
    private Integer areaId;
    private int cameraId;
    private Integer facilityId;
    private String rtspUrl;
    private String guid;
    private int monitoringStatus;
    private List<String> eventId;
}
