import java.util.Arrays;
import java.util.Locale;

public class AggregatedData {
    private double[] values;

    public AggregatedData(double[] values) {
        this.values = values;
    }

    public double getAverage() {
        double sum = 0;
        for (double value : values) {
            sum += value;
        }
        return sum / values.length;
    }

    public double getMedian() {
        double[] orderedValues = Arrays.stream(values).sorted().toArray();
        if (orderedValues.length % 2 == 1) {
            return orderedValues[orderedValues.length / 2 + 1];
        }
        return (orderedValues[orderedValues.length / 2] + orderedValues[orderedValues.length / 2 - 1]) / 2d;
    }

    public double getMax() {
        double max = Double.MIN_VALUE;
        for (double value : values) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public double getMin() {
        double min = Double.MAX_VALUE;
        for (double value : values) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH,", Average: %.2f, Median: %.2f, Max: %.2f, Min: %.2f",getAverage(),getMedian(),getMax(),getMin());
    }
}
