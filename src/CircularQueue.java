import javax.swing.event.ChangeListener;
import java.util.*;

public class CircularQueue<E> {
    private ArrayList<E> elements;
    private int nextIndex;
    private ChangePropegator propegator;

    public CircularQueue() {
        elements = new ArrayList<>();
        nextIndex = 0;
        propegator = new ChangePropegator();
    }

    public void addChangeListener(ChangeListener l) {
        propegator.addChangeListener(l);
    }

    public void removeChangeListener(ChangeListener l) {
        propegator.removeChangeListener(l);
    }

    public void addElement(E element) {
        elements.add(element);
        propegator.fireStateChanged();
    }

    public void removeElement(E element) {
        elements.remove(element);
        propegator.fireStateChanged();
    }

    public List<E> getElements() {
        return Collections.unmodifiableList(elements);
    }

    public Optional<E> next() {
        if(elements.isEmpty()) {
            return Optional.empty();
        }

        int currentIndex = nextIndex < elements.size() ? nextIndex : 0;

        E element = elements.get(currentIndex);

        nextIndex = currentIndex + 1;

        return Optional.of(element);
    }
}
