package payloads;


import pojos.Accessories;
import pojos.Images;
import pojos.ReportIncidence;

import java.util.List;
import java.util.ResourceBundle;

public class ReportIncidenceDataBuilder {
    private static final ResourceBundle reportBundle = ResourceBundle.getBundle("reportIncidence");

    public static Accessories setupLaptopAccessories() {
        return Accessories
                .builder()
                .id(Integer.parseInt(reportBundle.getString("accessory1Id")))
                .name(reportBundle.getString("accessory1Name"))
                .incidenceQuantity(Integer.parseInt(reportBundle.getString("accessory1Qty")))
                .build();
    }
    public static Accessories setupChargerAccessories() {
        return Accessories
                .builder()
                .id(Integer.parseInt(reportBundle.getString("accessory2Id")))
                .name(reportBundle.getString("accessory2Name"))
                .incidenceQuantity(Integer.parseInt(reportBundle.getString("accessory2Qty")))
                .build();
    }
    public static Images setupImages() {
        return Images
                .builder()
                .thumbUrl(reportBundle.getString("image1"))
                .imageUrl(reportBundle.getString("image2"))
                .build();

    }
    public static ReportIncidence setupData() {
        return ReportIncidence
                .builder()
                .assetId(Integer.parseInt(reportBundle.getString("assetId")))
                .incidenceType(reportBundle.getString("incidenceType"))
                .assetName(reportBundle.getString("assetName"))
                .reporterRemark(reportBundle.getString("reporterRemark"))
                .incidenceSource(reportBundle.getString("incidenceSource"))
                .incidenceBelongs(reportBundle.getString("incidenceBelongs"))
                .accessories(List.of(setupLaptopAccessories(),setupChargerAccessories()))
                .images(List.of(setupImages()))
                .build();
    }
}
