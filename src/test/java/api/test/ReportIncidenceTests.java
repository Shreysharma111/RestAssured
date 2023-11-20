package api.test;

import api.endpoints.UserEndPoints;
import api.payload.Accessories;
import api.payload.Images;
import api.payload.ReportIncidence;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.ResourceBundle;

public class ReportIncidenceTests {
    ReportIncidence reportPayload;

    private int assetId;
    private String incidenceType;
    private String assetName;
    String reporterRemark;
    int accessory1Id;
    int accessory2Id;
    String accessory1Name;
    String accessory2Name;
    int accessory1Qty;
    int accessory2Qty;
    String incidenceSource;
    String incidenceBelongs;
    String image1;
    String image2;

    @BeforeClass
    public void setupData() {
        reportPayload=new ReportIncidence();
        ResourceBundle usersBundle = ResourceBundle.getBundle("reportIncidence");
        assetId = Integer.parseInt(usersBundle.getString("assetId"));
        incidenceType = usersBundle.getString("incidenceType");
        assetName = usersBundle.getString("assetName");
        reporterRemark = usersBundle.getString("reporterRemark");
        incidenceSource = usersBundle.getString("incidenceSource");
        incidenceBelongs = usersBundle.getString("incidenceBelongs");
        accessory1Id = Integer.parseInt(usersBundle.getString("accessory1Id"));
        accessory2Id = Integer.parseInt(usersBundle.getString("accessory2Id"));
        accessory1Name = usersBundle.getString("accessory1Name");
        accessory2Name = usersBundle.getString("accessory2Name");
        accessory1Qty = Integer.parseInt(usersBundle.getString("accessory1Qty"));
        accessory2Qty = Integer.parseInt(usersBundle.getString("accessory2Qty"));
        image1 = usersBundle.getString("image1");
        image2 = usersBundle.getString("image2");


        // Set values for accessories
        Accessories laptopAccessory = new Accessories();
        laptopAccessory.setId(accessory1Id);
        laptopAccessory.setName(accessory1Name);
        laptopAccessory.setIncidenceQuantity(accessory1Qty);

        Accessories chargerAccessory = new Accessories();
        chargerAccessory.setId(accessory2Id);
        chargerAccessory.setName(accessory2Name);
        chargerAccessory.setIncidenceQuantity(accessory2Qty);

        reportPayload.setAccessories(List.of(laptopAccessory, chargerAccessory));

        // Set values for images
        Images image = new Images();
        image.setThumbUrl(image1);
        image.setImageUrl(image2);

        reportPayload.setImages(List.of(image));

        reportPayload.setAssetId(assetId);
        reportPayload.setIncidenceType(incidenceType);
        reportPayload.setAssetName(assetName);
        reportPayload.setReporterRemark(reporterRemark);
        reportPayload.setIncidenceSource(incidenceSource);
        reportPayload.setIncidenceBelongs(incidenceBelongs);
    }

    @Test(priority = 1)
    public void testIncidenceReport() {
        Response response = UserEndPoints.reportIncidence(reportPayload);
// Verify keys in the response body
        response.then()
                .assertThat()
                .statusCode(200);
        System.out.println("Response time : "+response.getTime()+"ms");

    }
}
