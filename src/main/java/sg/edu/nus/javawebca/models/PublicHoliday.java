package sg.edu.nus.javawebca.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class PublicHoliday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDate holiday_date;
    private String description;

    public PublicHoliday() {
    }

        public PublicHoliday(LocalDate holiday_date, String description) {
        this.holiday_date = holiday_date;
        this.description = description;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getHoliday_date() {
        return holiday_date;
    }

    public void setHoliday_date(LocalDate holiday_date) {
        this.holiday_date = holiday_date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
