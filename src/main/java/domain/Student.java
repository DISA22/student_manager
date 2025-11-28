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

    @Override
    public String toString() {
        return "Студент " + getName() + " (группа: " + group + ")";
    }

}