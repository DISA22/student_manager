package domain;

public enum Role {
    STUDENT("Ученик"),
    TEACHER("Учитель");

    String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
