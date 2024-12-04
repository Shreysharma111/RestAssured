package pojos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SaveFCMTokenPojo extends BasePojo {
    private String oldToken;
    private String newToken;
}
