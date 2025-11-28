package org.example;
import domain.Schedule;
import repository.PasswordRepository;
import repository.StudentScheduleRepository;
import service.AuthorizationService;
import service.RegistrationService;
import service.ScheduleAndGradeService;
import ui.ConsoleUI;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        PasswordRepository passwordRepository = new PasswordRepository();
        StudentScheduleRepository studentScheduleRepository = new StudentScheduleRepository();
        RegistrationService registrationService = new RegistrationService(passwordRepository, studentScheduleRepository);
        Schedule schedule = new Schedule();
        AuthorizationService authorizationService = new AuthorizationService(passwordRepository);
        ScheduleAndGradeService scheduleAndGradeService = new ScheduleAndGradeService(studentScheduleRepository,schedule,passwordRepository);
        ConsoleUI consoleUI = new ConsoleUI(registrationService, authorizationService, scheduleAndGradeService);
        consoleUI.start();
    }
}