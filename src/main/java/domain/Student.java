package domain;

import domain.validation.Validator;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;
@ToString
@Getter
public class Student extends User {
    private final String group;

    public Student(String name, String group, String passwordHash) {
        super(name, Role.STUDENT, passwordHash);
        this.group = group;
    }

}