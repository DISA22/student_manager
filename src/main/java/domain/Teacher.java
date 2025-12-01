package domain;

import domain.validation.Validator;

import java.util.UUID;

public class Teacher extends User {

    public Teacher(String name, String passwordHash) {
        super(name, Role.TEACHER, passwordHash);
    }

    public String toString() {
        return String.format(
                "Учитель id=%s name=%s role=%s passwordHash=%s",
                getId(), getName(), getRole(), getPasswordHash()
        );
    }
}