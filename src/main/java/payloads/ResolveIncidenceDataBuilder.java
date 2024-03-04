package payloads;

import api.pojos.Accessories;
import api.pojos.Images;
import api.pojos.ResolveIncidence;

import java.util.List;
import java.util.ResourceBundle;

public class ResolveIncidenceDataBuilder {
    private static final ResourceBundle resolveBundle = ResourceBundle.getBundle("resolveIncidence");

    public static Accessories setupLaptopAccessories() {
        return Accessories
                .builder()
                .id(Integer.parseInt(resolveBundle.getString("accessoryId1")))
                .name(resolveBundle.getString("accessoryName1"))
                .resolvedQuantity(resolveBundle.getString("resolvedQuantity1"))
                .status(resolveBundle.getString("status"))
                .build();
    }
//    public static Accessories setupChargerAccessories() {
//        return Accessories
//                .builder()
//                .id(Integer.parseInt(resolveBundle.getString("accessoryId2")))
//                .name(resolveBundle.getString("accessoryName2"))
//                .resolvedQuantity(resolveBundle.getString("resolvedQuantity2"))
//                .status(resolveBundle.getString("status"))
//                .build();
//    }
    public static Images setupImages() {
        return Images
                .builder()
                .thumbUrl(resolveBundle.getString("image1"))
                .imageUrl(resolveBundle.getString("image2"))
                .build();

    }
    public static ResolveIncidence setupData() {
        return ResolveIncidence
                .builder()
                .incidenceId(Integer.parseInt(resolveBundle.getString("incidenceId")))
                .resolutionStatus(resolveBundle.getString("resolutionStatus"))
                .isAvailable(resolveBundle.getString("isAvailable"))
                .resolverRemark(resolveBundle.getString("resolverRemark"))
                .accessories(List.of(setupLaptopAccessories()))
                .images(List.of(setupImages()))
                .build();
    }
}
