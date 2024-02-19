package api.payloads;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class BaseDataBuilder {
    public static Map<String, String> buildHeaders(LinkedHashMap<String, String> data, String... headerKeys) {
        Map<String, String> headers = new HashMap<>();

        for (String key : headerKeys) {
            if (!data.get(key).equalsIgnoreCase("NO_DATA")) {
                headers.put(getHeaderName(key), data.get(key));
            }
        }

        return headers;
    }

    // Additional method to convert keys to actual header names
    public static String getHeaderName(String key) {
        switch (key) {
            case "header1":
                return "X-HMAC-FROM";
            case "header2":
                return "X-APP-DEVICE-ID";
            case "header3":
                return "X-APP-OS";
            case "header4":
                return "X-APP-VERSION";
            default:
                throw new IllegalArgumentException("Unknown header key: " + key);
        }
    }
}
