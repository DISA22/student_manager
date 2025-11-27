package repository;

import domain.Student;
import domain.Teacher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PasswordRepository {
    private final Map<String, Student> mapStudentPas = new HashMap<>();
    private final Map<String, Teacher> mapTeacherPas = new HashMap<>();

    public boolean addStudent(String login, String password, Student student) {
        if (mapStudentPas.containsKey(login)) {
            return false;
        }
        mapStudentPas.put(login, student);
        return true;
    }

    public boolean addTeacher(String login, String password, Teacher teacher) {
        if (mapTeacherPas.containsKey(login)) {
            return false;
        }
        mapTeacherPas.put(login, teacher);
        return true;
    }


    public void removeStudent(String login) {
        if (login != null) {
            mapStudentPas.remove(login);
        }
    }


    public void removeTeacher(String login) {
        if (login != null) {
            mapTeacherPas.remove(login);
        }
    }


    public Student getStudent(String login) {
        return mapStudentPas.get(login);
    }


    public Teacher getTeacher(String login) {
        return mapTeacherPas.get(login);
    }


    public List<Student> getAllStudents() {
        return new ArrayList<>(mapStudentPas.values());
    }


    public List<Teacher> getAllTeachers() {
        return new ArrayList<>(mapTeacherPas.values());
    }


    public Student getNameStudent(String name) {
        for (Map.Entry<String, Student> entry : mapStudentPas.entrySet()) {
            String key = entry.getKey();
            Student student = entry.getValue();
            if (student.getName().equals(name)) {
                return student;
            }
        }
        return null;
    }
}
