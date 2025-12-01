package domain;

import domain.validation.Validator;

import java.util.UUID;

public abstract class User {
    private final UUID id;
    private final String name;
    private final Role role;
    private final String passwordHash;

    public User(String name, Role role, String passwordHash) {
        this.id = UUID.randomUUID();
        this.name = Validator.validate(name);
        this.role = role;
        this.passwordHash = passwordHash;
    }


    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public String toString() {
        return String.format(
                "User{\n  id=%-10d\n  name='%-20s'\n  role=%-15s\n  passwordHash='%s'\n}",
                id, name, role, passwordHash
        );
    }
}
