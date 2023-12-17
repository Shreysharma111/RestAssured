package api.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Getter
@Setter
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
