package api.pojos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ReportIncidence {

     private int assetId;
     private String incidenceType;
     private String assetName;
     private String reporterRemark;
     private List<Accessories> accessories;
     private String incidenceSource;
     private String incidenceBelongs;
     private List<Images> images;

}
