package utilities;

public enum Values {
    MONITORINGSTATUSENABLED("ENABLED", 1),
    MONITORINGSTATUSDISABLED("DISABLED", 0),
    STATUS("ACTIVE", 1),
    DELETED("DELETED", 2),
    DISABLED("DISABLED", 0);
    // Add more statuses as needed

    private final String status;
    private final int code;

    Values(String status, int code) {
        this.status = status;
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public int getCode() {
        return code;
    }

    // Method to get status from code
    public static String getStatusFromCode(int code) {
        for (Values status : values()) {
            if (status.getCode() == code) {
                return status.getStatus();
            }
        }
        throw new IllegalArgumentException("Unknown code: " + code);
    }
    }
