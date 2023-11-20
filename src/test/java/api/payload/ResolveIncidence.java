package api.payload;

import java.util.List;

public class ResolveIncidence {

    private int incidenceId;
    private String resolutionStatus;
    private String isAvailable;
    private String resolverRemark;
    private List<Accessories> accessories;
    private List<Images> images;
    public int getIncidenceId() {
        return incidenceId;
    }

    public void setIncidenceId(int incidenceId) {
        this.incidenceId = incidenceId;
    }

    public String getResolutionStatus() {
        return resolutionStatus;
    }

    public void setResolutionStatus(String resolutionStatus) {
        this.resolutionStatus = resolutionStatus;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getResolverRemark() {
        return resolverRemark;
    }

    public void setResolverRemark(String resolverRemark) {
        this.resolverRemark = resolverRemark;
    }

    public List<Accessories> getAccessories() {
        return accessories;
    }

    public void setAccessories(List<Accessories> accessories) {
        this.accessories = accessories;
    }

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }

}
