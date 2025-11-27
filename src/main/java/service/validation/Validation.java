package service.validation;

import domain.Student;
import domain.Teacher;
import repository.exception.RegistrationException;

public class Validation {

    public static void validateStudentRegistration(String login, String password, Student student) {
        if (login == null || login.trim().isEmpty()) {
            throw new RegistrationException("Логин не может быть пустым");
        } else if (login.length() < 3) {
            throw new RegistrationException("Логин должен содержать минимум 3 символа");
        } else if (!login.matches("[a-zA-Z0-9_]+")) {
            throw new RegistrationException("Логин может содержать только буквы, цифры и подчеркивание");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new RegistrationException("Пароль не может быть пустым");
        } else if (password.length() < 6) {
            throw new RegistrationException("Пароль должен содержать минимум 6 символов");
        }

        // Проверка студента
        if (student == null) {
            throw new RegistrationException("Студент не может быть null");
        } else {
            if (student.getName() == null || student.getName().trim().isEmpty()) {
                throw new RegistrationException("Имя студента не может быть пустым");
            }
            if (student.getId() == null) {
                throw new RegistrationException("Студент должен иметь ID");
            }
        }
    }

    public  static void validateTeacherRegistration(String login, String password, Teacher teacher) {
        if (login == null || login.trim().isEmpty()) {
            throw new RegistrationException("Логин не может быть пустым");
        } else if (login.length() < 3) {
            throw new RegistrationException("Логин должен содержать минимум 3 символа");
        } else if (!login.matches("[a-zA-Z0-9_]+")) {
            throw new RegistrationException("Логин может содержать только буквы, цифры и подчеркивание");
        }

        if (password == null || password.trim().isEmpty()) {
            throw new RegistrationException("Пароль не может быть пустым");
        } else if (password.length() < 6) {
            throw new RegistrationException("Пароль должен содержать минимум 6 символов");
        }

        // Проверка учителя
        if (teacher == null) {
            throw new RegistrationException("Учитель не может быть null");
        } else {
            if (teacher.getName() == null || teacher.getName().trim().isEmpty()) {
                throw new RegistrationException("Имя учителя не может быть пустым");
            }
            if (teacher.getId() == null) {
                throw new RegistrationException("Учитель должен иметь ID");
            }
        }
    }


}