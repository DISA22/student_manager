package domain.Exceptions;

public class DomainException extends RuntimeException {
    public DomainException(String message) {
        super(message);
    }
}