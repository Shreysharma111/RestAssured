package api.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentManager {
    private static ExtentReports extent;
    public static synchronized ExtentReports getInstance() {
        if (extent == null) {
            String timestamp = new SimpleDateFormat("yyyy.MM.dd.hh.mm.ss").format(new Date());
            String reportName = "TestReport-"+timestamp+".html";

            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "//Reports//" + reportName);
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
        }
        return extent;
    }
}
