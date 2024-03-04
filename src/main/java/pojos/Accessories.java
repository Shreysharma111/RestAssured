package pojos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Accessories {
    private int id;
    private String name;
    private int incidenceQuantity;
    private String resolvedQuantity;
    private String status;

}
