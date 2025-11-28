package service;


import domain.Student;
import domain.Teacher;
import domain.User;
import repository.PasswordRepository;
import repository.StudentScheduleRepository;
import service.validation.Validation;

public class RegistrationService {
    private PasswordRepository passwordRepository;

    public RegistrationService(PasswordRepository passwordRepository,
                               StudentScheduleRepository studentScheduleRepository) {
        this.passwordRepository = passwordRepository;
    }

    public void registration(String login, String password, User user) {
        Validation.validateRegistation(login, password, user);
        if (passwordRepository.addUser(login, password, user)) {
            StringBuilder result = new StringBuilder();
            result.append("Регистрация прошла успешно \n");
            result.append("Ваш логин: ").append(login).append("\n");
            result.append("Ваш пароль: ").append(password).append("\n");
            result.append("ID: ").append(user.getId()).append("\n");
            result.append("Имя: ").append(user.getName()).append("\n");
            System.out.println(result.toString());
        } else {
            System.out.println("Логин уже существует, введите другой логин");
        }
    }


}
