package api.test;

import api.payload.Accessories;
import api.payload.Images;
import api.payload.ReportIncidence;
import api.payload.ResolveIncidence;
import org.testng.annotations.BeforeClass;

import java.util.List;
import java.util.ResourceBundle;

public class ResolveIncidenceTests {
    ResolveIncidence resolvePayload;

    private int incidenceId;
    private String resolutionStatus;
    private String isAvailable;
    private String resolverRemark;
    private String accessoryId1;
    private String accessoryName1;
    private String resolvedQuantity1;
    private String accessoryId2;
    private String accessoryName2;
    private String resolvedQuantity2;
    private String status;
    private String image1;
    private String image2;

    @BeforeClass
    public void setupData() {
        resolvePayload = new ResolveIncidence();
        ResourceBundle resolveBundle = ResourceBundle.getBundle("resolveIncidence");
        incidenceId = Integer.parseInt(resolveBundle.getString("incidenceId"));
        resolutionStatus = resolveBundle.getString("resolutionStatus");
        isAvailable = resolveBundle.getString("isAvailable");
        resolverRemark = resolveBundle.getString("resolverRemark");
        accessoryId1 = resolveBundle.getString("accessoryId1");
        accessoryName1 = resolveBundle.getString("accessoryName1");
        resolvedQuantity1 = resolveBundle.getString("resolvedQuantity1");
        accessoryId2 = resolveBundle.getString("accessoryId2");
        accessoryName2 = resolveBundle.getString("accessoryName2");
        resolvedQuantity2 = resolveBundle.getString("resolvedQuantity2");
        status = resolveBundle.getString("status");
        image1 = resolveBundle.getString("image1");
        image2 = resolveBundle.getString("image2");

        // Set values for accessories
        Accessories laptopAccessory = new Accessories();
        laptopAccessory.setId(Integer.parseInt(accessoryId1));
        laptopAccessory.setName(accessoryName1);
        laptopAccessory.setResolvedQuantity(resolvedQuantity1);
        laptopAccessory.setStatus(status);

        Accessories chargerAccessory = new Accessories();
        chargerAccessory.setId(Integer.parseInt(accessoryId2));
        chargerAccessory.setName(accessoryName2);
        chargerAccessory.setResolvedQuantity(resolvedQuantity2);
        chargerAccessory.setStatus(status);

        resolvePayload.setAccessories(List.of(laptopAccessory, chargerAccessory));

        // Set values for images
        Images image = new Images();
        image.setThumbUrl(image1);
        image.setImageUrl(image2);

        reportPayload.setImages(List.of(image));

    }
}