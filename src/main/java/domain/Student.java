package domain;

import domain.validation.Validator;

import java.util.UUID;

public class Student {
    private final UUID id;
    private final String name;
    private final String nameGroup;
    private final Role role;
    private final String passwordHash;


    public Student(String name, String nameGroup,String passwordHash) {
        this.role = Role.STUDENT;
        this.passwordHash = passwordHash;
        this.id = UUID.randomUUID();
        this.name = Validator.validate(name);
        this.nameGroup = nameGroup;
    }


    public String getPasswordHash() {
        return passwordHash;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "Имя '" + name + '\'' +
               ", Группа '" + nameGroup + '\n';

    }
}