package api.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Map;

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
    @JsonIgnore
    private Map<String, String> headers;
}
