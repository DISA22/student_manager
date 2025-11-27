package service;

import domain.Student;
import domain.Teacher;
import repository.PasswordRepository;

public class AuthorizationService {
    private final PasswordRepository passwordRepository;

    public AuthorizationService(PasswordRepository passwordRepository) {
        this.passwordRepository = passwordRepository;
    }


    public boolean authorizationStudent(String login, String password) {
        Student student1 = passwordRepository.getStudent(login);
        if (student1.getPasswordHash().equals(password)) {
            return true;
        }
        return false;
    }

    public Student getStud(String name) {
        return passwordRepository.getStudent(name);
    }

    public boolean authorizationTeacher(String login, String password) {
        Teacher teacher1 = passwordRepository.getTeacher(login);
        if (teacher1.getPasswordHash().equals(password)) {
            return true;
        }
        return false;
    }

    public Teacher getTeacher(String login) {
        return passwordRepository.getTeacher(login);
    }

    public Student getStudName(String name) {
        return passwordRepository.getNameStudent(name);
    }
}
