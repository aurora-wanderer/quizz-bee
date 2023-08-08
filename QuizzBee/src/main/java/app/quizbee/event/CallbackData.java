package app.quizbee.event;

public interface CallbackData<T> {

    void call(T data);
}
