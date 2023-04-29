import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class CountDownBannerItem extends BannerItem {
    private int value;
    private Timer timer;

    private JTextField prefixInput, postfixInput, doneInput;
    private JSpinner valueInput, delayInput, splitIndexSpinner;
    private JLabel currentPrefix, currentValue, currentPostfix, currentDelay, currentDone;

    public CountDownBannerItem(String done, String prefix, String postfix, int value, int delayBetweenUpdates) {
        super();

        this.value = value;

        timer = new Timer(delayBetweenUpdates, l -> tick());
        timer.start();

        this.setLayout(new GridLayout(4,6));


        JLabel currentHeading = new JLabel("Current");
        currentHeading.setHorizontalAlignment(JLabel.RIGHT);
        currentPrefix = new JLabel(prefix);
        currentPrefix.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        currentValue = new JLabel(Integer.toString(value));
        currentValue.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        currentValue.setHorizontalAlignment(JLabel.RIGHT);
        currentPostfix = new JLabel(postfix);
        currentPostfix.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        currentDelay = new JLabel(Integer.toString(timer.getDelay()));
        currentDelay.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
        currentDelay.setHorizontalAlignment(JLabel.RIGHT);
        currentDone = new JLabel(done);
        currentDone.setBorder(new EtchedBorder(EtchedBorder.LOWERED));

        JLabel inputHeading = new JLabel("Input");
        inputHeading.setHorizontalAlignment(JLabel.RIGHT);
        prefixInput = new JTextField(prefix);
        valueInput = new JSpinner(new SpinnerNumberModel(value, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
        valueInput.setEditor(new JSpinner.NumberEditor(valueInput));
        postfixInput = new JTextField(postfix);
        delayInput = new JSpinner(new SpinnerNumberModel(timer.getDelay(), 0, Integer.MAX_VALUE, 1));
        delayInput.setEditor(new JSpinner.NumberEditor(delayInput));
        doneInput = new JTextField(done);

        JButton doneCommitButton = new JButton("Commit");
        doneCommitButton.addActionListener(l -> {
            currentDone.setText(doneInput.getText());
            textChanged();
        });
        JButton prefixCommitButton = new JButton("Commit");
        prefixCommitButton.addActionListener(l -> {
            currentPrefix.setText(prefixInput.getText());
            textChanged();
        });
        JButton valueCommitButton = new JButton("Commit");
        valueCommitButton.addActionListener(l -> {
            setValue((Integer) valueInput.getValue());
            textChanged();
        });
        JButton postfixCommitButton = new JButton("Commit");
        postfixCommitButton.addActionListener(l -> {
            currentPostfix.setText(postfixInput.getText());
            textChanged();
        });
        JButton delayCommitButton = new JButton("Commit");
        delayCommitButton.addActionListener(l -> setDelay((Integer) delayInput.getValue()));
        JToggleButton pause = new JToggleButton("Pause");
        pause.addActionListener(l -> {
            if(pause.isSelected()) {
                timer.stop();
                pause.setText("Resume");
            } else {
                timer.start();
                pause.setText("Pause");
            }
        });


        splitIndexSpinner = new JSpinner(new SpinnerNumberModel(0, 0, getText().length(), 1));
        splitIndexSpinner.setEditor(new JSpinner.NumberEditor(splitIndexSpinner));

        this.add(splitIndexSpinner);
        this.add(new JLabel("Done text"));
        this.add(new JLabel("Prefix"));
        this.add(new JLabel("Value"));
        this.add(new JLabel("Postfix"));
        this.add(new JLabel("Delay (ms)"));
        this.add(currentHeading);
        this.add(currentDone);
        this.add(currentPrefix);
        this.add(currentValue);
        this.add(currentPostfix);
        this.add(currentDelay);
        this.add(inputHeading);
        this.add(doneInput);
        this.add(prefixInput);
        this.add(valueInput);
        this.add(postfixInput);
        this.add(delayInput);
        this.add(pause);
        this.add(doneCommitButton);
        this.add(prefixCommitButton);
        this.add(valueCommitButton);
        this.add(postfixCommitButton);
        this.add(delayCommitButton);
    }

    private void setValue(int newValue) {
        value = newValue;
        currentValue.setText(Integer.toString(value));
        textChanged();
    }

    private void setDelay(int newDelay) {
        timer.setDelay(newDelay);
        currentDelay.setText(Integer.toString(newDelay));
    }

    private int getValue() {
        return value;
    }

    public String getText() {
        if(value == 0) {
            return currentDone.getText();
        } else {
            return currentPrefix.getText() + value + currentPostfix.getText();
        }
    }

    private void textChanged() {
        ((SpinnerNumberModel) splitIndexSpinner.getModel()).setMaximum(getText().length());
        if((int) splitIndexSpinner.getModel().getValue() >= getText().length()) {
            splitIndexSpinner.getModel().setValue(getText().length());
        }
    }

    @Override
    public String getFirstLine() {
        return getText().substring(0, (int) splitIndexSpinner.getValue());
    }

    @Override
    public String getSecondLine() {
        return getText().substring((int) splitIndexSpinner.getValue());
    }

    @Override
    public void setSplitIndex(int index) {
        splitIndexSpinner.setValue(index);
    }

    private void tick() {
        setValue(value == 0 ? value : value - 1);
    }
}
