package utilities;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class GoogleSheetsUtils {
    private static final String APPLICATION_NAME = "Automation Project";
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
    private static final String SERVICE_ACCOUNT_FILE_PATH = System.getProperty("user.dir") + File.separator
            + "src/test/resources/credentials.json";



    private static Sheets sheetsService;

    private static Sheets getSheet() {
        if (sheetsService == null) {
            // Only create a new instance if it hasn't been created yet
            sheetsService = createSheetsService();
        }
        return sheetsService;
    }

    private static Sheets createSheetsService() {
        try {
            HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        GoogleCredentials credentials = null;

            credentials = GoogleCredentials.fromStream(new FileInputStream(SERVICE_ACCOUNT_FILE_PATH))
                    .createScoped(SCOPES);


        if (credentials.refreshAccessToken().getTokenValue() != null) {
            HttpCredentialsAdapter httpCredentialsAdapter = new HttpCredentialsAdapter(credentials);
            return new Sheets.Builder(httpTransport, new GsonFactory(), httpCredentialsAdapter)
                    .setApplicationName(APPLICATION_NAME).build();
        }
        } catch (IOException | GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static List<LinkedHashMap<String, String>> getGoogleSheetsDataAsListOfMap(
            String spreadsheetId, String sheetName, String range) throws IOException, GeneralSecurityException {
        List<LinkedHashMap<String, String>> dataFromSheets = new ArrayList<>();

        Sheets sheetsService = getSheet();
        if (sheetsService != null) {
            // Modify the range to include the sheet name
            String fullRange = sheetName + "!" + range;

            ValueRange response = sheetsService.spreadsheets().values().get(spreadsheetId, fullRange).execute();
            List<List<Object>> values = response.getValues();

            if (values != null && !values.isEmpty()) {
                List<String> allKeys = new ArrayList<>();

                for (int i = 0; i < values.size(); i++) {
                    LinkedHashMap<String, String> mapData = new LinkedHashMap<>();

                    List<Object> row = values.get(i);
                    if (i == 0) {
                        // First row contains headers, use them as keys
                        allKeys = row.stream().map(Object::toString).collect(Collectors.toList());
                    } else {
                        // Subsequent rows contain data
                        for (int j = 0; j < row.size(); j++) {
                            String cellValue = row.get(j).toString();
                            mapData.put(allKeys.get(j), cellValue);
                        }
                        dataFromSheets.add(mapData);
                    }
                }
            }
        }
        return dataFromSheets;
    }
}
