package api.payloads;

import api.pojos.Login;
import api.utilities.ExcelUtils;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.util.*;

public class LoginDataBuilder {
    private static final ResourceBundle usersBundle = ResourceBundle.getBundle("users");

    public static Login setupDataIT() {

        return Login
                .builder()
                .username(usersBundle.getString("IT"))
                .password(usersBundle.getString("password"))
                .build();

    }
    public static Login setupDataDr() {

        return Login
                .builder()
                .username(usersBundle.getString("doctor"))
                .password(usersBundle.getString("password"))
                .build();

    }


}
