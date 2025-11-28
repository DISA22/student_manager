package service;

import domain.Lesson;
import domain.Schedule;
import domain.Student;
import domain.User;
import repository.PasswordRepository;
import repository.StudentScheduleRepository;
import repository.exception.ScheduleAndGradeServiceException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ScheduleAndGradeService {
    private final StudentScheduleRepository studentScheduleRepository;
    private final Schedule schedule; // пришлось сделать потому что общее расписание
    private Scanner scanner = new Scanner(System.in);
    private final PasswordRepository passwordRepository;


    public ScheduleAndGradeService(StudentScheduleRepository studentScheduleRepository, Schedule schedule, PasswordRepository passwordRepository) {
        this.studentScheduleRepository = studentScheduleRepository;
        this.schedule = schedule;
        this.passwordRepository = passwordRepository;
    }

    //показать всех зарегистрированных пользователей
    public void infoAllUsers() {
        List<User> users = passwordRepository.getAllUser();
        StringBuilder sb = new StringBuilder();

        for (User user : users) {
            sb.append("ID: ").append(user.getId())
                    .append(" | Имя: ").append(user.getName())
                    .append(" | Роль: ").append(user.getRole())
                    .append(" | Hash: ").append(user.getPasswordHash().substring(0, 8)).append("...")
                    .append("\n");
        }

        System.out.println(sb.toString());
        System.out.println("Всего пользователей: " + users.size());
    }

    //обновляем общее расписание
    public void createSchedule(List<Lesson> lessons) {
        schedule.addNewSchedules(lessons);
    }

    //связываем студента с его расписанием
    public void addStudentWithSchedules(User user, Schedule schedule) {
        if (user == null || schedule == null) {
            throw new ScheduleAndGradeServiceException("Студент и расписание не могут быть пусты");
        }
        UUID id = user.getId();
        studentScheduleRepository.save(id, schedule.getSchedules());
    }

    //поменять расписание у студента
    public void changeSchedule(User user) {
        // List<Lesson> lessons = studentScheduleRepository.getStudentSchedule(student.getId());
        List<Lesson> nowLessons = createLessonsFromInput();
        studentScheduleRepository.save(user.getId(), nowLessons);
    }

    //новое расписание
    public List<Lesson> createLessonsFromInput() {
        System.out.print("Введите уроки через пробел: ");
        String[] lessonNames = scanner.nextLine().split(" ");
        List<Lesson> lessons = new ArrayList<>();
        for (String name : lessonNames) {
            lessons.add(new Lesson(name));
        }
        return lessons;
    }

    //изменение оценки
    public void updateStudentGrade(User user) {
        List<Lesson> lessons = studentScheduleRepository.getStudentSchedule(user.getId());
        if (lessons.isEmpty()) {
            System.out.println("У студента нет предметов в расписании");
            return;
        }
        System.out.println("Текущее расписание студента:");
        for (int i = 0; i < lessons.size(); i++) {
            Lesson lesson = lessons.get(i);
            System.out.printf("   %d. %s - Оценка: %s\n",
                    i + 1,
                    lesson.getName(),
                    lesson.getEstimation() != null ? lesson.getEstimation() : "нет"
            );
        }
        System.out.print("\n Введите предмет для изменения оценки: ");

        String subject = scanner.nextLine().trim();

        System.out.print("Введите новую оценку: ");
        String grade = scanner.nextLine().trim();

        boolean found = false;
        for (Lesson lesson : lessons) {
            if (lesson.getName().equalsIgnoreCase(subject)) {
                lesson.setEstimation(grade);
                System.out.printf(" Оценка по предмету '%s' изменена на: %s\n", subject, grade);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.printf("Предмет '%s' не найден в расписании\n", subject);
        }
        studentScheduleRepository.save(user.getId(), lessons);
    }

    //Удаление студента
    public void removeStudent(User user) {
        if (user != null) {
            studentScheduleRepository.removal(user.getId());
        }
    }


    //удаление предмета в расписании у студента
    public void removeLessonUsingYourMethod(User user) {
        List<Lesson> lessons = studentScheduleRepository.getStudentSchedule(user.getId());
        if (lessons == null || lessons.isEmpty()) {
            System.out.println("У студента нет расписания");
            return;
        }
        infoSchedule(user);//
        System.out.print("\n Введите предмет для удаления: ");
        String subjectToRemove = scanner.nextLine().trim();
        studentScheduleRepository.removeLesson(user.getId(), subjectToRemove);
    }

    //расписание студента
    public void infoSchedule(User user) {
        List<Lesson> lessons = studentScheduleRepository.getStudentSchedule(user.getId());

        if (lessons == null || lessons.isEmpty()) {
            System.out.println(" У студента нет расписания");
            return;
        }

        System.out.println("Текущее расписание студента:");
        for (int i = 0; i < lessons.size(); i++) {
            Lesson lesson = lessons.get(i);
            System.out.printf("   %d. %s - Оценка: %s\n",
                    i + 1,
                    lesson.getName(),
                    lesson.getEstimation() != null ? lesson.getEstimation() : "нет"
            );
        }
    }
}