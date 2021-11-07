package co.berako.model.events;

public interface Event<T> {
    String key();

    T getValue();
}
