import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.LinkedList;

public class ChangePropegator {
    private LinkedList<ChangeListener> changeListeners;

    public ChangePropegator() {
        changeListeners = new LinkedList<>();
    }

    public void addChangeListener(ChangeListener l) {
        changeListeners.add(l);
    }

    public void removeChangeListener(ChangeListener l) {
        changeListeners.remove(l);
    }

    protected void fireStateChanged() {
        ChangeEvent e = new ChangeEvent(this);
        for(ChangeListener listener : changeListeners) {
            listener.stateChanged(e);
        }
    }
}
