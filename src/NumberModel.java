import javax.swing.*;
import java.util.Optional;

public class NumberModel extends AbstractSpinnerModel {
    private double value;
    private double step;
    private Optional<Double> optionalMax, optionalMin;

    public NumberModel(double value, double step, double max, double min) {
        this.value = value;
        this.step = step;
        setMax(max);
        setMin(min);
    }

    NumberModel(double value, double step) {
        this.value = value;
        this.step = step;
        clearMax();
        clearMin();
    }

    void setMax(double optionalMax) {
        this.optionalMax = Optional.of(optionalMax);
    }

    void setMin(double min) {
        this.optionalMin = Optional.of(min);
    }

    void clearMax() {
        this.optionalMax = Optional.empty();
    }

    void clearMin() {
        this.optionalMin = Optional.empty();
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        if(value instanceof Double) {
            double newValue = ((Double) value);
            if(okValue(newValue)) {
                this.value = newValue;
                fireStateChanged();
            }
        }
    }

    @Override
    public Double getNextValue() {
        double nextValue = value + step;
        nextValue = okValue(nextValue) ? nextValue : value;
        return nextValue;
    }

    @Override
    public Double getPreviousValue() {
        double nextValue = value - step;
        nextValue = okValue(nextValue) ? nextValue : value;
        return nextValue;
    }

    private boolean okValue(double value) {
        boolean lowerBoundOk, upperBoundOk;

        upperBoundOk = optionalMax.map(max -> value <= max).orElse(true);
        lowerBoundOk = optionalMin.map(min -> min <= value).orElse(true);

        return lowerBoundOk && upperBoundOk;
    }
}
