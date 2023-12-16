package api.pojos;

import com.poiji.annotation.ExcelCellName;
import lombok.ToString;
@ToString
public class LoginPoiji {

    @ExcelCellName("username")
    private String username;
    @ExcelCellName("password")
    private String password;

}
