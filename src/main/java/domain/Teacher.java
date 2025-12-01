package domain;

import domain.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

@ToString

public class Teacher extends User {

    public Teacher(String name, String passwordHash) {
        super( name, Role.TEACHER, passwordHash);
    }

}