package api.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
public class BasePojo {
    @JsonIgnore
    private String scenerioId;
    @JsonIgnore
    private String scenerioDesc;
    @JsonIgnore
    private int expectedStatusCode;
    @JsonIgnore
    private String expectedMessage;
}
