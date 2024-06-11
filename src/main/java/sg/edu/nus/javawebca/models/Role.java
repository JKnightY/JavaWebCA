package sg.edu.nus.javawebca.models;

public class Role {
    private int id;//"0" = "admin" "1" = "staff" "2" = "manager"
    private String role_name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }
}
