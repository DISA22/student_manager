package service;

import domain.*;
import repository.PasswordRepository;
import repository.StudentScheduleRepository;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class FileService {
    private final PasswordRepository passwordRepository;
    private final StudentScheduleRepository studentScheduleRepository;
    private final Schedule schedule;
    private final String USERS_FILE = "users.txt";
    private final String ALLCHEDULE_FILE = "allSchedule.txt";
    private final String USERSCHEDULE_FILE = "userSchedulefile.txt";

    public FileService(PasswordRepository passwordRepository, StudentScheduleRepository studentScheduleRepository, Schedule schedule) {
        this.passwordRepository = passwordRepository;
        this.studentScheduleRepository = studentScheduleRepository;
        this.schedule = schedule;
        createFileIfNotExists(ALLCHEDULE_FILE);
    }

    //создаем файл c пользователями чтобы был
    public void createFileIfNotExists(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //сохраняем в файл пользователей
    public void saveUsersToFile() {
        createFileIfNotExists(USERS_FILE);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(USERS_FILE))) {
            List<User> users = passwordRepository.getAllUser();
            for (User user : users) {
                String login = passwordRepository.getKey(user);
                bufferedWriter.write(login + " " + user.toString());
                bufferedWriter.newLine();
            }
            System.out.println("Сохранено пользователей: " + users.size());
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении пользователей: " + e.getMessage());
        }
    }

    //загружаем в бд пользователей
    public void inUsersToBs() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains("Учитель")) {
                    parsTeacher(line);
                } else if (line.contains("Студент")) {
                    parsStudent(line);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //парсеры пользователей
    public void parsStudent(String line) {
        String[] lines = line.split(" ");
        String login = lines[0];
        String id = lines[2].substring("id=".length());
        String name = lines[3].substring("name=".length());
        String role = lines[4].substring("role=".length());
        String group = lines[5].substring("group=".length());
        String passwordHash = lines[6].substring("passwordHash=".length());

        Student student = new Student(name, group, passwordHash);
        passwordRepository.addUser(login, passwordHash, student);
    }

    public void parsTeacher(String line) {
        String[] lines = line.split(" ");
        String login = lines[0];
        String id = lines[2].substring("id=".length());
        String name = lines[3].substring("name=".length());
        String role = lines[4].substring("role=".length());
        String passwordHash = lines[5].substring("passwordHash=".length());
        Teacher teacher = new Teacher(name, passwordHash);
        passwordRepository.addUser(login, passwordHash, teacher);
    }

    //сохранить общее расписание в файл
    public void saveToFileSchedule() {
        createFileIfNotExists(ALLCHEDULE_FILE);
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(ALLCHEDULE_FILE))) {
            String[] lines = schedule.getSchedule().split(",");
            for (String line : lines) {
                bufferedWriter.write(line + "\n");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //сохраняю расписание студлента в файл
    public void saveUserScheduleToFile() {
        createFileIfNotExists(USERSCHEDULE_FILE);
        List<User> users = passwordRepository.getAllUser();
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(USERSCHEDULE_FILE))) {
            for (User user : users) {
                if (user.getRole().equals(Role.STUDENT)) {
                    UUID id = user.getId();
                    String name = user.getName();
                    List<Lesson> lessons = studentScheduleRepository.getStudentSchedule(id);

                    bufferedWriter.write("ID " + id);
                    bufferedWriter.newLine();
                    bufferedWriter.write("Студент " + name);
                    bufferedWriter.newLine();
                    bufferedWriter.write("------------- Расписание -------------");
                    bufferedWriter.newLine();

                    if (lessons != null && !lessons.isEmpty()) {
                        for (Lesson lesson : lessons) {
                            bufferedWriter.write("  - " + lesson.toString());
                            bufferedWriter.newLine();
                        }
                    } else {
                        bufferedWriter.write("  Расписание отсутствует");
                        bufferedWriter.newLine();
                    }

                    bufferedWriter.write("=====================================");
                    bufferedWriter.newLine();
                    bufferedWriter.newLine();
                } else {
                    continue;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}




