package sg.edu.nus.javawebca.models;

import java.time.LocalDateTime;

public class Report {
    private int id;
    private String type;
    private User generated_by;
    private String report_data;
    private LocalDateTime generated_at;

    public Report() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User getGenerated_by() {
        return generated_by;
    }

    public void setGenerated_by(User generated_by) {
        this.generated_by = generated_by;
    }

    public String getReport_data() {
        return report_data;
    }

    public void setReport_data(String report_data) {
        this.report_data = report_data;
    }

    public LocalDateTime getGenerated_at() {
        return generated_at;
    }

    public void setGenerated_at(LocalDateTime generated_at) {
        this.generated_at = generated_at;
    }
}
