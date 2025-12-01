package domain;

import domain.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data

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



}
