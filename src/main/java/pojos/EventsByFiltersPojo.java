package pojos;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EventsByFiltersPojo extends BasePojo {
    private String entityId1;
    private List<Integer> entityId2;
    private Integer entityId3;
    private Integer entityId4;
    private String category1;
    private String category2;
    private String fromDate;
    private String toDate;
    private PageableRequestPojo pageableRequest;
    private String searchText;
}
