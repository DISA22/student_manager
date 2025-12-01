package domain;

import domain.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.FileWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Data

public class Schedule {
    private final List<Lesson> schedules = new ArrayList<>(); // Просто расписание
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Schedule() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }




    //новое расписание
    public void addNewSchedules(List<Lesson> lessons) {
        schedules.clear();
        schedules.addAll(lessons);
        this.updatedAt = LocalDateTime.now();

    }

    //расписание добовляем
    public void addLesson(Lesson lesson) {
        schedules.add(lesson);
        this.updatedAt = LocalDateTime.now();
    }

    //безапасное удаление
    public void deliteLesson(String name) {
        Iterator<Lesson> iterator = schedules.iterator();
        while (iterator.hasNext()) {
            Lesson lesson1 = iterator.next();
            if (lesson1.getName().equals(name)) {
                iterator.remove();
                this.updatedAt = LocalDateTime.now();
                break;
            }
        }
    }

    // Просто расписание (все уроки)
    public String getSchedule() {
        StringBuilder result = new StringBuilder("Расписание:");
        for (Lesson lesson : schedules) {
            result.append("\n").append(lesson.getName());
        }
        return result.toString();
    }

    // Все уроки с оценками (если нет оценки - показывает "-")
    public String getAllLessonsWithGrades() {
        StringBuilder result = new StringBuilder("Все уроки:");
        for (Lesson lesson : schedules) {
            result.append("\n")
                    .append(lesson.getName())
                    .append(" Оценка ")
                    .append(lesson.getEstimation() != null ? lesson.getEstimation() : "-");
        }
        return result.toString();
    }
}
