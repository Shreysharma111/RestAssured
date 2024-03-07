package utilities.reporting;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.restassured.http.Header;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ExtentReportManager {

    public static ExtentReports extentReports;
    private static String reportFilePath;
    public static ExtentReports createInstance(String fileName, String reportName, String docTitle) {
        ExtentSparkReporter extentSparkReporter = new ExtentSparkReporter(fileName);
        extentSparkReporter.config().setReportName(reportName);
        extentSparkReporter.config().setDocumentTitle(docTitle);
        extentSparkReporter.config().setTheme(Theme.DARK);
        extentSparkReporter.config().setEncoding("utf-8");

        extentReports = new ExtentReports();
        extentReports.attachReporter(extentSparkReporter);
        // Store the report file path
        reportFilePath = fileName;
        return extentReports;
    }

    public static String getReportNameWithTimestamp() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy_HH.mm.ss");
        LocalDateTime localDateTime = LocalDateTime.now();
        String formattedTime = dateTimeFormatter.format(localDateTime);
        String reportName = "TestReport"+formattedTime+".html";
        return reportName;
    }

    public static void logPassDetails(String log) {
        Setup.extentTest.get().pass(MarkupHelper.createLabel(log, ExtentColor.GREEN));
    }
    public static void logFailureDetails(String log) {
        Setup.extentTest.get().fail(MarkupHelper.createLabel(log, ExtentColor.RED));
    }
    public static void logInfoDetails(String log) {
        Setup.extentTest.get().info(MarkupHelper.createLabel(log, ExtentColor.BLUE));
    }
    public static void logInfoDetailsNoMarkup(String log) {
        Setup.extentTest.get().info(log);
    }
    public static void logExceptionDetails(String log) {
        Setup.extentTest.get().fail(log);
    }
    public static void logWarningDetails(String log) {
        Setup.extentTest.get().warning(MarkupHelper.createLabel(log, ExtentColor.YELLOW));
    }
    public static void logJson(String json) {
        Setup.extentTest.get().info(MarkupHelper.createCodeBlock(json, CodeLanguage.JSON));
    }

    public static void formatResponseAndPrint(String json) {
        Setup.extentTest.get().info(MarkupHelper.createCodeBlock(json, CodeLanguage.JSON).getMarkup());
    }

    public static void logHeaders(List<Header> headersList) {
        // List to store modified headers
        List<Header> modifiedHeaders = new ArrayList<>();

        // Iterate through each header
        for (Header header : headersList) {
            String headerName = header.getName();
            String headerValue = header.getValue();

            // Check if the header is the access token
            if ("Authorization".equalsIgnoreCase(headerName) && headerValue.length()>30) {
                // Truncate the token to a manageable length, e.g., 10 characters
                String truncatedToken = headerValue.substring(0, Math.min(headerValue.length(), 20)) + "....." + headerValue.substring(headerValue.length() - 5);

                // Create a new Header with the truncated value
                Header truncatedHeader = new Header(headerName, truncatedToken);

                // Add the modified header to the list
                modifiedHeaders.add(truncatedHeader);
            } else {
                // Add other headers as they are to the list
                modifiedHeaders.add(header);
            }
        }

        // Convert modified headers to a 2D array for table creation
        String[][] arrayHeaders = modifiedHeaders.stream()
                .map(header -> new String[]{header.getName(), header.getValue()})
                .toArray(String[][]::new);

        // Log the modified headers in the Extent Report as a table
        Setup.extentTest.get().info(MarkupHelper.createTable(arrayHeaders));
    }
    public static void openReportInBrowser() {
        try {
            File reportFile = new File(reportFilePath);
            Desktop.getDesktop().browse(reportFile.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
