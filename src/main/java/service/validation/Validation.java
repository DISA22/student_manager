package service.validation;

import domain.Student;
import domain.Teacher;
import domain.User;
import repository.exception.RegistrationException;

public class Validation {

    public static void validateRegistation(String login, String password, User user) {
        validationLogin(login);
        validationPassword(password);
        validationUser(user);
    }

    public static boolean validationLogin(String login) {
        if (login == null || login.trim().isEmpty()) {
            throw new RegistrationException("Логин не может быть пустым");
        } else if (login.length() < 3) {
            throw new RegistrationException("Логин должен содержать минимум 3 символа");
        } else if (!login.matches("[a-zA-Z0-9_]+")) {
            throw new RegistrationException("Логин может содержать только буквы, цифры и подчеркивание");
        }
        return true;
    }

    public static boolean validationPassword(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new RegistrationException("Пароль не может быть пустым");
        } else if (password.length() < 6) {
            throw new RegistrationException("Пароль должен содержать минимум 6 символов");
        }
        return true;
    }

    public static boolean validationUser(User user) {
        if (user == null) {
            throw new RegistrationException("Пользователь не может быть null");
        } else {
            if (user.getName() == null || user.getName().trim().isEmpty()) {
                throw new RegistrationException("Имя не может быть пустым");
            }
            if (user.getId() == null) {
                throw new RegistrationException("Пользователь должен иметь ID");
            }
        }
        return true;
    }

}