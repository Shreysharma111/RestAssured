package api.payloads;

import api.pojos.Login;
import api.pojos.LoginPoiji;
import api.utilities.ExcelUtils;
import com.poiji.bind.Poiji;
import org.testng.annotations.DataProvider;

import java.io.File;
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

    //general data driven testing approach 1
    @DataProvider(name = "getLoginData")
    public Iterator<Login> getLoginData() {
        List<LinkedHashMap<String, String>> excelDataAsListOfMap = null;
        try {
            excelDataAsListOfMap = ExcelUtils.getExcelDataAsListOfMap("Login", "Sheet1");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(excelDataAsListOfMap);
        List<Login> loginData = new ArrayList<>();
        for(LinkedHashMap<String,String> data : excelDataAsListOfMap) {
            Login login = Login.builder()
                    .username(data.get("username"))
                    .password(data.get("password"))
                    .build();
            loginData.add(login);
        }
        return loginData.iterator();
    }

    //poiji data driven testing approach 2
    public static List<LoginPoiji> getPoijiData() {
        List<LoginPoiji> data = Poiji.fromExcel(new File("src/test/resources/testdata/Login.xlsx"), LoginPoiji.class);
        return data;
    }

}
