package api.pojos;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class ResolveIncidence {

    private int incidenceId;
    private String resolutionStatus;
    private String isAvailable;
    private String resolverRemark;
    private List<Accessories> accessories;
    private List<Images> images;
}
