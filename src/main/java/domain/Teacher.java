package domain;

import domain.validation.Validator;

import java.util.UUID;

public class Teacher extends User {

    public Teacher(String name, String passwordHash) {
        super(name, Role.TEACHER, passwordHash);
    }

    @Override
    public String toString() {
        return "Перподаватель " + getName();
    }
}