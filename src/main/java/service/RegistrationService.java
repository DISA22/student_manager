package service;


import domain.Student;
import domain.Teacher;
import repository.PasswordRepository;
import repository.StudentScheduleRepository;
import service.validation.Validation;

public class RegistrationService {
    private PasswordRepository passwordRepository;

    public RegistrationService(PasswordRepository passwordRepository,
                               StudentScheduleRepository studentScheduleRepository) {
        this.passwordRepository = passwordRepository;
    }

    public void registration(String login, String password, Student student) {
        Validation.validateStudentRegistration(login, password, student);
        if (passwordRepository.addStudent(login, password, student)) {
            StringBuilder result = new StringBuilder();
            result.append("Регистрация студента прошла успешно \n");
            result.append("Ваш логин: ").append(login).append("\n");
            result.append("Ваш пароль: ").append(password).append("\n");
            result.append("ID: ").append(student.getId()).append("\n");
            result.append("Имя: ").append(student.getName()).append("\n");
            result.append("Группа: ").append(student.getNameGroup());
            System.out.println(result.toString());
        } else {
            System.out.println("Логин уже существует, введите другой логин");
        }
    }

    public void registrationTeacher(String login, String password, Teacher teacher) {
        Validation.validateTeacherRegistration(login, password, teacher);
        if (passwordRepository.addTeacher(login, password, teacher)) {
            StringBuilder result = new StringBuilder();
            result.append("Регистрация учителя прошла успешно \n");
            result.append("Ваш логин: ").append(login).append("\n");
            result.append("Ваш пароль: ").append(password).append("\n");
            result.append("ID: ").append(teacher.getId()).append("\n");
            result.append("Имя: ").append(teacher.getName()).append("\n");
            System.out.println(result.toString());
        } else {
            System.out.println("Логин уже существует, введите другой логин");
        }
    }


}
