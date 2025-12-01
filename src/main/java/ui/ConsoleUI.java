package ui;

import domain.*;
import service.AuthorizationService;
import service.FileService;
import service.RegistrationService;
import service.ScheduleAndGradeService;
import ui.validation.ConsoleValidation;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private Scanner scanner = new Scanner(System.in);
    private final RegistrationService registrationService;
    private final AuthorizationService authorizationService;
    private final ScheduleAndGradeService scheduleAndGradeService;
    private final FileService fileService;

    public ConsoleUI(RegistrationService registrationService, AuthorizationService authorizationService, ScheduleAndGradeService scheduleAndGradeService, FileService fileService) {
        this.registrationService = registrationService;
        this.authorizationService = authorizationService;
        this.scheduleAndGradeService = scheduleAndGradeService;
        this.fileService = fileService;
    }

    public void start() {
        while (true) {
            System.out.println("\n=== ДОБРО ПОЖАЛОВАТЬ ===");
            System.out.println("1) Зарегистрироваться");
            System.out.println("2) Войти в личный кабинет");
            System.out.println("3) Выйти");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    registerUser();
                    break;
                case "2":
                    loginUser();
                    break;
                case "3":
                    System.out.println("До свидания!");
                    return;
                default:
                    System.out.println("❌ Неверный выбор!");
            }
        }
    }

    public void registerUser() {
        System.out.println("\n=== РЕГИСТРАЦИЯ ===");
        System.out.println("Вы преподаватель или студент?");
        System.out.println("1) Преподаватель");
        System.out.println("2) Студент");
        System.out.print("Выберите роль: ");

        String roleChoice = scanner.nextLine().trim();
        String name, login, password, group = "";

        System.out.print("Введите имя: ");
        name = ConsoleValidation.readValidatedName(scanner);

        System.out.print("Введите логин: ");
        login = ConsoleValidation.consoleValidationLogin(scanner);


        System.out.print("Введите пароль: ");
        password = ConsoleValidation.consoleValidationPassword(scanner);

        if (roleChoice.equals("2")) {
            System.out.print("Введите номер группы: ");
            group = scanner.nextLine();
        }


        if (roleChoice.equals("1")) {
            Teacher teacher = new Teacher(name, password);
            registrationService.registration(login, password, teacher);
        } else if (roleChoice.equals("2")) {
            Student student = new Student(name, group, password);
            registrationService.registration(login, password, student);
        } else {
            System.out.println("Неверный выбор роли!");
            return;
        }
    }

    public void loginUser() {
        System.out.println("\n=== ВХОД В СИСТЕМУ ===");

        System.out.println("Вы преподаватель или студент?");
        System.out.println("1) Преподаватель");
        System.out.println("2) Студент");
        System.out.print("Выберите роль: ");

        String roleChoice, login, password;
        roleChoice = scanner.nextLine().trim();

        System.out.print("Введите логин: ");
        login = ConsoleValidation.consoleValidationLogin(scanner);

        System.out.print("Введите пароль: ");
        password = ConsoleValidation.consoleValidationPassword(scanner);

        boolean student = false;
        boolean teacher = false;
        Student studentObj = null;
        Teacher teacherObj = null;

        if (roleChoice.equals("2")) {
            student = authorizationService.authorization(login, password);
            if (student) {
                studentObj = (Student) authorizationService.getUser(login);
            }
        } else {
            teacher = authorizationService.authorization(login, password);
            if (teacher) {
                teacherObj = (Teacher) authorizationService.getUser(login);
            }
        }

        if (student) {
            System.out.println("Добро пожаловать,  студент " + studentObj.getName());
            showStudentMenu(studentObj);
        } else if (teacher) {
            System.out.println("Добро пожаловать, учитель " + teacherObj.getName());
            showTeacherMenu(teacherObj);
        }
    }


    public void showStudentMenu(Student student) {
        System.out.println("1)Посмотреть расписание общее");
        System.out.println("2)Посмотреть расписание");
        String number = scanner.nextLine();
        switch (number) {
            case "1":
                scheduleAndGradeService.allSchedule();
                break;
            case "2":
                scheduleAndGradeService.infoSchedule(student);
                break;
        }
    }

    public void showTeacherMenu(Teacher teacher) {
        while (true) {
            System.out.println("Введите цифру из меню");
            System.out.println("---------------------------------------");
            System.out.println("1)Создать расписание общее");
            System.out.println("2)Добавить расписание к студенту");
            System.out.println("3)Поменять расписание у студента");
            System.out.println("4)Изменить оценку у студента");
            System.out.println("5)Удалить студента с расписанием");
            System.out.println("6)Удаление предмета в расписании у студента");
            System.out.println("7)Показать всех зарегистрированных пользователей");
            System.out.println("8)Выгрузить всех зарегестрированных в файл");
            System.out.println("9)Выгрузить общее расписание в файл");
            System.out.println("10)Выгрузить расписание с студентами");
            System.out.println("0)Выйти из меню учителя");

            String number = scanner.nextLine().trim();
            if (number.equals("0")) {
                break;
            }
            switch (number) {
                case "1":
                    System.out.println("Создать расписание общее");
                    List<Lesson> lessons = scheduleAndGradeService.createLessonsFromInput();
                    scheduleAndGradeService.createSchedule(lessons);
                    break;
                case "2":
                    System.out.println("Добавить расписание к студенту");
                    System.out.print("Введите имя студента: ");
                    String name = names();
                    User user = authorizationService.getUserStudName(name);
                    System.out.println("Введите предметы для расписания студента через пробел ");
                    List<Lesson> lessons1 = scheduleAndGradeService.createLessonsFromInput();
                    Schedule schedule = new Schedule();
                    schedule.addNewSchedules(lessons1);
                    scheduleAndGradeService.addStudentWithSchedules(user, schedule);
                    break;

                case "3":
                    System.out.println("Поменять расписание у студента");
                    System.out.print("Введите имя студента: ");
                    User user1 = authorizationService.getUserStudName(names());
                    scheduleAndGradeService.changeSchedule(user1);
                    break;
                case "4":
                    System.out.println("Изменить оценку у студента");
                    System.out.print("Введите имя студента: ");
                    User user2 = authorizationService.getUserStudName(names());
                    scheduleAndGradeService.updateStudentGrade(user2);
                    break;
                case "5":
                    System.out.println("Удалить студента с расписанием");
                    System.out.print("Введите имя студента: ");
                    User user3 = authorizationService.getUserStudName(names());
                    scheduleAndGradeService.removeStudent(user3);
                    break;
                case "6":
                    System.out.println("Удаление предмета в расписании у студента");
                    System.out.print("Введите имя студента: ");
                    User user4 = authorizationService.getUserStudName(names());
                    scheduleAndGradeService.removeLessonUsingYourMethod(user4);
                    break;
                case "7":
                    scheduleAndGradeService.infoAllUsers();
                    break;
                case "8":
                    fileService.saveUsersToFile();
                case "9":
                    fileService.saveToFileSchedule();
                case "10":
                    fileService.saveUserScheduleToFile();
            }
        }
    }

    public String names() {
        return scanner.nextLine().trim();
    }
}
