package repository;


import domain.Lesson;
import domain.Schedule;
import domain.Student;
import repository.exception.ScheduleAndGradeServiceException;

import java.util.*;

public class StudentScheduleRepository {
    private final Map<UUID, List<Lesson>> repository = new HashMap<>();

    //сохранить
    public void save(UUID id, List<Lesson> lessons) {
        if (id == null || lessons == null) {
            throw new ScheduleAndGradeServiceException("Студент и расписание не могут быть null");
        }
        repository.put(id, lessons);
    }

    // ПОЛУЧИТЬ
    public List<Lesson> getStudentSchedule(UUID id) {
        if (id == null) {
            throw new ScheduleAndGradeServiceException("Студент не может быть null");
        }
        return repository.getOrDefault(id, new ArrayList<>());
    }

    //удалить по id студента
    public void removal(UUID id) {
        if (id != null) {
            repository.remove(id);
        }
    }

    //удаление урока в расписании
    public void removeLesson(UUID id, String name) {
        List<Lesson> lessons = getStudentSchedule(id);
        Iterator<Lesson> iterator = lessons.iterator();
        while (iterator.hasNext()) {
            Lesson lesson = iterator.next();
            if (lesson.getName().equals(name)) {
                iterator.remove();
                break;
            }
        }
        save(id, lessons);
    }


}
