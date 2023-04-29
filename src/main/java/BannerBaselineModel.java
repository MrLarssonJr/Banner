import javax.swing.*;

public class BannerBaselineModel extends AbstractSpinnerModel {
    private int baseline;

    public BannerBaselineModel() {
        baseline = 20;
    }

    @Override
    public Integer getValue() {
        return baseline;
    }

    @Override
    public void setValue(Object value) {
        if(value instanceof Integer) {
            baseline = (Integer) value;
            fireStateChanged();
        }
    }

    @Override
    public Integer getNextValue() {
        return baseline + 1;
    }

    @Override
    public Integer getPreviousValue() {
        return baseline - 1 >= 0 ? baseline - 1 : 0;
    }
}