
package domain;

import domain.validation.Validator;

public class Lesson {
    private final String name;
    private String estimation;

    public Lesson(String name, String estimation) {
        this.name = Validator.validate(name);
        this.estimation = estimation;
    }

    public Lesson(String name) {
        this(name, null);

    }

    public String getEstimation() {
        return estimation;
    }

    public String getName() {
        return name;
    }

    public void setEstimation(String estimation) {
        this.estimation = estimation;
    }

    public String toString() {
        return new StringBuilder()
                .append("\n")
                .append(name)
                .append(estimation != null ? " Оценка: " + estimation : " (нет оценки)")
                .toString();
    }
}
