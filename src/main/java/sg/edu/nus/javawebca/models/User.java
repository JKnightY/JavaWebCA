package sg.edu.nus.javawebca.models;

public class User {
    private int id;
    private String account;
    private String password;
    private String username;
    private int role;//"0" = "admin" "1" = "staff" "2" = "manager"
    private String email;
    private int annual_leave_entitlement;
    private int medical_leave_entitlement;
    private int compensation_leave_balance;
}
