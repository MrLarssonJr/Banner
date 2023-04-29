import javax.swing.*;
import java.awt.*;

public class ConstantBannerItem extends BannerItem {
    private JSpinner splitIndexSpinner;
    private JTextField textField;
    private JLabel currentTextLabel;

    public ConstantBannerItem(String text) {
        super();

        splitIndexSpinner = new JSpinner(new SpinnerNumberModel(0, 0, text.length(), 1));
        splitIndexSpinner.setEditor(new JSpinner.NumberEditor(splitIndexSpinner));
        splitIndexSpinner.setPreferredSize(new Dimension(50, splitIndexSpinner.getHeight()));

        JLabel label = new JLabel("Current text:");
        label.setHorizontalAlignment(JLabel.RIGHT);

        textField = new JTextField(text);

        currentTextLabel = new JLabel(text);

        JButton commitButton = new JButton("Commit text");
        commitButton.addActionListener(l -> {
            currentTextLabel.setText(textField.getText());
            ((SpinnerNumberModel) splitIndexSpinner.getModel()).setMaximum(getText().length());
            if((int) splitIndexSpinner.getModel().getValue() >= getText().length()) {
                splitIndexSpinner.getModel().setValue(getText().length());
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.add(splitIndexSpinner);
        this.add(label);
        this.add(currentTextLabel);
        this.add(textField);
        this.add(commitButton);
    }

    private String getText() {
        return currentTextLabel.getText();
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
}
