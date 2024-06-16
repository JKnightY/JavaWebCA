package sg.edu.nus.javawebca.models;

import jakarta.validation.constraints.NotNull;

public class User {
    private Integer id;
    @NotNull(message = "account must be provided")
    private String account;
    private String password;
    @NotNull(message = "username must be provided")
    private String username;
    @NotNull(message = "role type must be provided")
    private int role;//"0" = "admin" "1" = "staff" "2" = "manager"
    private String email;
    private int annual_leave_entitlement;
    private int medical_leave_entitlement;
    private int compensation_leave_balance;

    public User(){}

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAnnual_leave_entitlement() {
        return annual_leave_entitlement;
    }

    public void setAnnual_leave_entitlement(int annual_leave_entitlement) {
        this.annual_leave_entitlement = annual_leave_entitlement;
    }

    public int getMedical_leave_entitlement() {
        return medical_leave_entitlement;
    }

    public void setMedical_leave_entitlement(int medical_leave_entitlement) {
        this.medical_leave_entitlement = medical_leave_entitlement;
    }

    public int getCompensation_leave_balance() {
        return compensation_leave_balance;
    }

    public void setCompensation_leave_balance(int compensation_leave_balance) {
        this.compensation_leave_balance = compensation_leave_balance;
    }
}
