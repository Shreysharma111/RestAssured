package api.test;

import api.utilities.ExcelUtils;

import java.io.IOException;

public class ExcelTest {
    public static void main(String[] args) {
        try {
            System.out.println(ExcelUtils.getExcelDataAsListOfMap("Login", "Sheet1"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
