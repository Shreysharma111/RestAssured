package pojos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Images {
    private String thumbUrl;
    private String imageUrl;

}
