package sg.edu.nus.javawebca.models;

import java.time.format.DateTimeFormatter;

public class LeaveHistory {
    private int id;
    private int employeeId;
    private int leaveapplicationId;
    private int leaveType;
    private DateTimeFormatter start_date;
    private DateTimeFormatter end_date;
    private int status;
    private DateTimeFormatter create_date;
}
