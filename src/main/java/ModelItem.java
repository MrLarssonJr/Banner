import javax.swing.event.ChangeListener;

public class ModelItem<T> {
    private T value;
    private ChangePropegator propegator;

    public void addChangeListener(ChangeListener l) {
        propegator.addChangeListener(l);
    }

    public void removeChangeListener(ChangeListener l) {
        propegator.removeChangeListener(l);
    }

    public ModelItem(T value) {
        propegator = new ChangePropegator();
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
        propegator.fireStateChanged();
    }
}
