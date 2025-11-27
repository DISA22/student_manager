package domain;

import domain.validation.Validator;

import java.util.UUID;

public class Teacher {
    private final String name;
    private final Role role;
    private final UUID id;
    private final String passwordHash;


    public Teacher(String name,String passwordHash) {
        this.name = Validator.validate(name);
        this.role = Role.TEACHER;
        this.passwordHash = passwordHash;
        this.id = UUID.randomUUID();
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public UUID getId() {
        return id;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    @Override
    public String toString() {
        return "Преподаватель " + name;
    }
}