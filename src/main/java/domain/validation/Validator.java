package domain.validation;

import domain.Exceptions.DomainException;

public class Validator {


    public static String validate(String name) {
        if (name == null || name.isBlank()) {
            throw new DomainException("Имя не может быть пустым");
        }
        String normalized = name.trim().replaceAll("\\s+", " ");

        if (normalized.length() < 2) {
            throw new DomainException("Имя должно содержать минимум 2 символа");
        }

        if (normalized.length() > 50) {
            throw new DomainException("Имя не может превышать 50 символов");
        }

        if (!normalized.matches("^[a-zA-Zа-яА-ЯёЁ\\s\\-]+$")) {
            throw new DomainException("Имя может содержать только буквы, пробелы и дефисы");
        }

        return normalized;
    }
}