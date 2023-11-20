package api.payload;

public class Accessories {
    private int id;
    private String name;
    private int incidenceQuantity;
    private String resolvedQuantity;
    private String status;

    public String getResolvedQuantity() {
        return resolvedQuantity;
    }

    public void setResolvedQuantity(String resolvedQuantity) {
        this.resolvedQuantity = resolvedQuantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIncidenceQuantity() {
        return incidenceQuantity;
    }

    public void setIncidenceQuantity(int incidenceQuantity) {
        this.incidenceQuantity = incidenceQuantity;
    }
}
