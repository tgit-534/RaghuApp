package app.actionnation.actionapp.Database_Content;

public class DataObject <T> {
    protected T value;

    public void setValue (T value) {
        this.value = value;
    }

    public T getValue () {
        return value;
    }
}

