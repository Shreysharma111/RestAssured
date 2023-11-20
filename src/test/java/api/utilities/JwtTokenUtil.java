package api.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class JwtTokenUtil {
    public static String jwtToken;
    public static void tokenChange()
    {
        // Update the properties file
        try {
            String propertiesFilePath = System.getProperty("user.dir") + "\\src\\test\\resources\\logintoken.properties";
            Properties properties = getProperties(propertiesFilePath);

            // Save the updated properties to the file
            FileOutputStream fileOutputStream = new FileOutputStream(propertiesFilePath);
            properties.store(fileOutputStream, null);
            fileOutputStream.close();

        } catch (FileNotFoundException e) {
            System.err.println("Properties file not found: " + e.getMessage());
            // Handle the case where the properties file is not found
        } catch (IOException e) {
            System.err.println("Error reading/writing properties file: " + e.getMessage());
            // Handle other IO exceptions
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            // Handle any other unexpected exceptions
        }
    }

    public static Properties getProperties(String propertiesFilePath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(propertiesFilePath);
        Properties properties = new Properties();
        properties.load(fileInputStream);
        fileInputStream.close();

        // Check if the IT property already exists
        if (!properties.containsKey("IT")) {
            // If it doesn't, add a new IT property with the value of the generated JWT token
            properties.setProperty("IT", jwtToken);
        } else {
            // If it does, update the IT property value
            properties.setProperty("IT", jwtToken);
        }
        return properties;
    }
}
