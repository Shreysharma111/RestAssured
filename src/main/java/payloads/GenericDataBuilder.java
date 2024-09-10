package payloads;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;

public class GenericDataBuilder {

    // Generic method to build POJO from Google Sheets data
    public static <T> T buildDataFromMap(LinkedHashMap<String, String> data, Class<T> pojoClass) {
        try {
            T pojoInstance = pojoClass.getDeclaredConstructor().newInstance();

            for (Field field : pojoClass.getDeclaredFields()) {
                field.setAccessible(true);

                String fieldName = field.getName();
                String value = data.get(fieldName);

//                if (value != null && !value.equalsIgnoreCase("NO_DATA")) {
                    // Convert the value to the correct type and set it on the field
                if (!value.equalsIgnoreCase("NO_DATA")) {
                    Object convertedValue = convertValue(field.getType(), value);
                    field.set(pojoInstance, convertedValue);
                }
            }

            return pojoInstance;
        } catch (Exception e) {
            throw new RuntimeException("Error building data for class: " + pojoClass.getName(), e);
        }
    }

    // Helper method to convert string values to the correct field type
    private static Object convertValue(Class<?> fieldType, String value) {
        if (fieldType == Integer.class || fieldType == int.class) {
            return Integer.parseInt(value);
        } else if (fieldType == Boolean.class || fieldType == boolean.class) {
            return Boolean.parseBoolean(value);
        } else {
            return value; // Default to string
        }
    }
}
