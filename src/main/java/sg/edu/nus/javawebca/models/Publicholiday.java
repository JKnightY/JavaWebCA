package sg.edu.nus.javawebca.models;

import java.time.LocalDateTime;

public class Publicholiday {
    private int id;
    private LocalDateTime holiday_date;
    private String description;

    public Publicholiday() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getHoliday_date() {
        return holiday_date;
    }

    public void setHoliday_date(LocalDateTime holiday_date) {
        this.holiday_date = holiday_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
