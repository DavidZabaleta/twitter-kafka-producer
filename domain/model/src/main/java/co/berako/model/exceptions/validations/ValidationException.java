package co.berako.model.exceptions.validations;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}
