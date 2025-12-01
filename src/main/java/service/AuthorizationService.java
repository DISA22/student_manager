package service;

import domain.Role;
import domain.Student;
import domain.Teacher;
import domain.User;
import repository.PasswordRepository;

public class AuthorizationService {
    private final PasswordRepository passwordRepository;

    public AuthorizationService(PasswordRepository passwordRepository) {
        this.passwordRepository = passwordRepository;
    }


    public boolean authorization(String login, String password) {
        User user = passwordRepository.getUserLogin(login);
        if (user.getPasswordHash().equals(password)) {
            return true;
        }
        return false;
    }

    //возврат по логину
    public User getUser(String login) {
        return passwordRepository.getUserLogin(login);
    }

    public User getUserStudName(String name) {
        User user = passwordRepository.getOnliStudName(name);
        if (user.getRole().equals(Role.STUDENT)) {
            return user;
        }
        return null;
    }
}
