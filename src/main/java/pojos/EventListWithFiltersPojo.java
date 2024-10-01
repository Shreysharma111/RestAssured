package pojos;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EventListWithFiltersPojo extends BasePojo {
    private String entityId1;
    private List<Integer> entityId2;
    private Integer entityId3;
    private String category1;
    private String category2;
}
