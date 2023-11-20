package api.payload;

import java.util.List;

public class ReportIncidence {

     private int assetId;
     private String incidenceType;
     private String assetName;
     private String reporterRemark;
     private List<Accessories> accessories;
     private String incidenceSource;
     private String incidenceBelongs;
     private List<Images> images;

     public int getAssetId() {
          return assetId;
     }

     public void setAssetId(int assetId) {
          this.assetId = assetId;
     }

     public String getIncidenceType() {
          return incidenceType;
     }

     public void setIncidenceType(String incidenceType) {
          this.incidenceType = incidenceType;
     }

     public String getAssetName() {
          return assetName;
     }

     public void setAssetName(String assetName) {
          this.assetName = assetName;
     }

     public String getReporterRemark() {
          return reporterRemark;
     }

     public void setReporterRemark(String reporterRemark) {
          this.reporterRemark = reporterRemark;
     }

     public List<Accessories> getAccessories() {
          return accessories;
     }

     public void setAccessories(List<Accessories> accessories) {
          this.accessories = accessories;
     }

     public String getIncidenceSource() {
          return incidenceSource;
     }

     public void setIncidenceSource(String incidenceSource) {
          this.incidenceSource = incidenceSource;
     }

     public String getIncidenceBelongs() {
          return incidenceBelongs;
     }

     public void setIncidenceBelongs(String incidenceBelongs) {
          this.incidenceBelongs = incidenceBelongs;
     }

     public List<Images> getImages() {
          return images;
     }

     public void setImages(List<Images> images) {
          this.images = images;
     }
}
