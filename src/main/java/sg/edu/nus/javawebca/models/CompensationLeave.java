package sg.edu.nus.javawebca.models;

import java.time.format.DateTimeFormatter;

public class CompensationLeave {
    private int id;
    private int employeeId;
    private DateTimeFormatter claim_date;
    private double hours_worked;
    private int status;//（1:applied、2:approved、3:rejected)
    private DateTimeFormatter create_at;
    private DateTimeFormatter update_at;
}
