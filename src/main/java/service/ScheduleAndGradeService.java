package service;

import domain.Lesson;
import domain.Schedule;
import domain.Student;
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


    public ScheduleAndGradeService(StudentScheduleRepository studentScheduleRepository, Schedule schedule) {
        this.studentScheduleRepository = studentScheduleRepository;
        this.schedule = schedule;
    }

    //обновляем общее расписание
    public void createSchedule(List<Lesson> lessons) {
        schedule.addNewSchedules(lessons);
    }

    //связываем студента с его расписанием
    public void addStudentWithSchedules(Student student, Schedule schedule) {
        if (student == null || schedule == null) {
            throw new ScheduleAndGradeServiceException("Студент и расписание не могут быть пусты");
        }
        UUID id = student.getId();
        studentScheduleRepository.save(id, schedule.getSchedules());
    }

    //поменять расписание у студента
    public void changeSchedule(Student student) {
        // List<Lesson> lessons = studentScheduleRepository.getStudentSchedule(student.getId());
        List<Lesson> nowLessons = createLessonsFromInput();
        studentScheduleRepository.save(student.getId(), nowLessons);
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
    public void updateStudentGrade(Student student) {
        List<Lesson> lessons = studentScheduleRepository.getStudentSchedule(student.getId());
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
        studentScheduleRepository.save(student.getId(), lessons);
    }

    //Удаление студента
    public void removeStudent(Student student) {
        if (student != null) {
            studentScheduleRepository.removal(student.getId());
        }
    }


    //удаление предмета в расписании у студента
    public void removeLessonUsingYourMethod(Student student) {
        List<Lesson> lessons = studentScheduleRepository.getStudentSchedule(student.getId());
        if (lessons == null || lessons.isEmpty()) {
            System.out.println("У студента нет расписания");
            return;
        }
        infoSchedule(student);//
        System.out.print("\n Введите предмет для удаления: ");
        String subjectToRemove = scanner.nextLine().trim();
        studentScheduleRepository.removeLesson(student.getId(), subjectToRemove);
    }

    //расписание студента
    public void infoSchedule(Student student) {
        List<Lesson> lessons = studentScheduleRepository.getStudentSchedule(student.getId());

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