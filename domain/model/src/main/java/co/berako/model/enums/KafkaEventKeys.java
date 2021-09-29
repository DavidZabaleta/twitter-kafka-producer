package co.berako.model.enums;

public enum KafkaEventKeys {
    WEATHER_TOPIC("weather-topic");

    private final String key;

    KafkaEventKeys(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return key;
    }
}
