package domain;

import domain.validation.Validator;

import java.util.UUID;

public class Student extends User {
    private final String group;

    public Student(String name, String group, String passwordHash) {
        super(name, Role.STUDENT, passwordHash);
        this.group = group;
    }

    public String getGroup() {
        return group;
    }

    public String toString() {
        return String.format(
                "Студент id=%s name=%s role=%s group=%s passwordHash=%s",
                getId(), getName(), getRole(), getGroup(), getPasswordHash()
        );
    }
}