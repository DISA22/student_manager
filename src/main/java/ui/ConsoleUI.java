package ui;

import domain.Lesson;
import domain.Schedule;
import domain.Student;
import domain.Teacher;
import service.AuthorizationService;
import service.RegistrationService;
import service.ScheduleAndGradeService;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private Scanner scanner = new Scanner(System.in);
    private final RegistrationService registrationService;
    private final AuthorizationService authorizationService;
    private final ScheduleAndGradeService scheduleAndGradeService;

    public ConsoleUI(RegistrationService registrationService, AuthorizationService authorizationService, ScheduleAndGradeService scheduleAndGradeService) {
        this.registrationService = registrationService;
        this.authorizationService = authorizationService;
        this.scheduleAndGradeService = scheduleAndGradeService;
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
        name = scanner.nextLine();

        System.out.print("Введите логин: ");
        login = scanner.nextLine();

        System.out.print("Введите пароль: ");
        password = scanner.nextLine();

        if (roleChoice.equals("2")) {
            System.out.print("Введите номер группы: ");
            group = scanner.nextLine();
        }


        if (roleChoice.equals("1")) {
            Teacher teacher = new Teacher(name, password);
            registrationService.registrationTeacher(login, password, teacher);
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

        String roleChoice = scanner.nextLine().trim();

        System.out.print("Введите логин: ");
        String login = scanner.nextLine();

        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();
        boolean student = false;
        boolean teacher = false;
        Student studentObj = null;
        Teacher teacherObj = null;

        if (roleChoice.equals("2")) {
            student = authorizationService.authorizationStudent(login, password);
            if (student) {
                studentObj = authorizationService.getStud(login);
            }
        } else {
            teacher = authorizationService.authorizationTeacher(login, password);
            if (teacher) {
                teacherObj = authorizationService.getTeacher(login);
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
        System.out.println("Посмотреть расписание введите цифру 1");
        String number = scanner.nextLine();
        if (number.equals("1")) {
            scheduleAndGradeService.infoSchedule(student);
        }
    }

    public void showTeacherMenu(Teacher teacher) {
        System.out.println("Введите цифру из меню");
        System.out.println("---------------------------------------");
        System.out.println("1)Создать расписание общее");
        System.out.println("2)Добавить расписание к студенту");
        System.out.println("3)Поменять расписание у студента");
        System.out.println("4)Изменить оценку у студента");
        System.out.println("5)Удалить студента с расписанием");
        System.out.println("6)Удаление предмета в расписании у студента");
        String number = scanner.nextLine().trim();

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
                Student student = authorizationService.getStudName(name);
                System.out.println("Введите предметы для расписания студента через пробел ");
                List<Lesson> lessons1 = scheduleAndGradeService.createLessonsFromInput();
                Schedule schedule = new Schedule();
                schedule.addNewSchedules(lessons1);
                scheduleAndGradeService.addStudentWithSchedules(student, schedule);
                break;

            case "3":
                System.out.println("Поменять расписание у студента");
                System.out.print("Введите имя студента: ");
                Student student1 = authorizationService.getStudName(names());
                scheduleAndGradeService.changeSchedule(student1);
                break;
            case "4":
                System.out.println("Изменить оценку у студента");
                System.out.print("Введите имя студента: ");
                Student student2 = authorizationService.getStudName(names());
                scheduleAndGradeService.updateStudentGrade(student2);
                break;
            case "5":
                System.out.println("Удалить студента с расписанием");
                System.out.print("Введите имя студента: ");
                Student studen3 = authorizationService.getStudName(names());
                scheduleAndGradeService.removeStudent(studen3);
                break;
            case "6":
                System.out.println("Удаление предмета в расписании у студента");
                System.out.print("Введите имя студента: ");
                Student studen4 = authorizationService.getStudName(names());
                scheduleAndGradeService.removeLessonUsingYourMethod(studen4);
                break;

        }

    }

    public String names() {
        return scanner.nextLine().trim();
    }
}
