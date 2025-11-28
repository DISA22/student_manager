package ui.validation;

import domain.validation.Validator;
import repository.exception.RegistrationException;
import service.validation.Validation;

import java.util.Scanner;

public class ConsoleValidation {

    public static String readValidatedName(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();
            try {
                return Validator.validate(input);
            } catch (domain.Exceptions.DomainException e) {
                System.out.println("Ошибка: " + e.getMessage());
                System.out.println("Пожалуйста, попробуйте еще раз:");
            }
        }
    }
//консольная валидация имени с ошибками
    public static String consoleValidationLogin(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();
            try {
                if (Validation.validationLogin(input)) {
                    return input;
                }
            } catch (RegistrationException e) {
                System.out.println("Ошибка: " + e.getMessage());
                System.out.println("Пожалуйста, попробуйте еще раз:");
            }
        }
    }

    public static String consoleValidationPassword(Scanner scanner) {
        while (true) {
            String input = scanner.nextLine();
            try {
                if (Validation.validationPassword(input)) {
                    return input;
                }
            } catch (RegistrationException e) {
                System.out.println("Ошибка: " + e.getMessage());
                System.out.println("Пожалуйста, попробуйте еще раз:");
            }
        }
    }


}

