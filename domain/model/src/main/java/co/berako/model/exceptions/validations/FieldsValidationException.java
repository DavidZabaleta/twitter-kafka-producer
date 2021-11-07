package co.berako.model.exceptions.validations;

public class FieldsValidationException extends RuntimeException {

    private final FieldsValidationException.Type type;

    private FieldsValidationException(FieldsValidationException.Type type) {
        super(type.message);
        this.type = type;
    }

    public FieldsValidationException.Type getType() {
        return type;
    }

    public enum Type {
        INVALID_JSON_STRUCTURE("The body of the message is invalid");

        private final String message;

        Type(String message) {
            this.message = message;
        }

        public FieldsValidationException build() {
            return new FieldsValidationException(this);
        }

        public String getMessage() {
            return message;
        }

    }
}
