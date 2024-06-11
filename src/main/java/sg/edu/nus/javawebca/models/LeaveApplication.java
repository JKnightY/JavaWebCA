package sg.edu.nus.javawebca.models;

import java.time.format.DateTimeFormatter;

public class LeaveApplication {
    private int id;
    private int employeeId;
    private char leaveType;//"A" = "annual_leave" "M" = "medical_leave" "C" = "compensation_leave"
    private DateTimeFormatter start_date;
    private DateTimeFormatter end_date;
    private String reason;
    private int status;//（1:applied、2:approved、3:rejected、4:cancel、5:updated、6:deleted）
    private DateTimeFormatter created_at;
    private DateTimeFormatter updated_at;
}
