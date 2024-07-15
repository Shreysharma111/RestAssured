package pojos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SaveEventsPojo extends BasePojo {
    private int application_id;
    private String entity_id1;
    private String entity_name1;
    private int entity_id2;
    private String entity_name2;
    private int entity_id4;
    private String entity_name4;
    private int entity_id3;
    private String category1;
    private String category2;
    private String category3;
    private String event_version;
    private String event_type;
    private String image_name;
    private String image_created_at;
    private String created_gmt;
    private String title;
    private String message;
    private String description;
    private String result;
}
