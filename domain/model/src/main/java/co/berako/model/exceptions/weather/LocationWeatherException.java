package co.berako.model.exceptions.weather;

public class LocationWeatherException extends RuntimeException {

    private final Type type;

    private LocationWeatherException(Type type) {
        super(type.message);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        INVALID_BODY_RESPONSE("The weather service doesn't return information about this location"),
        INVALID_KEYWORD("There's no information about the keyword provided");

        private String message;

        Type(String message) {
            this.message = message;
        }

        public LocationWeatherException build() {
            return new LocationWeatherException(this);
        }

        public String getMessage() {
            return message;
        }
    }
}
