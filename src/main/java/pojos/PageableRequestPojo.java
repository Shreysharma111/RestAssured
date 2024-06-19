package pojos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PageableRequestPojo {
    private Integer draw;
    private int page;
    private int size;
    private String sortBy;
    private String sortType;
}
