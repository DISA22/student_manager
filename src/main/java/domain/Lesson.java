
package domain;

import domain.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data

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
}
