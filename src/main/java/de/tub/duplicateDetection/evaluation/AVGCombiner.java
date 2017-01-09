package de.tub.duplicateDetection.evaluation;

/**
 * This combiner creates the average over all measurements.
 */
public class AVGCombiner implements MeasurementCombiner {

    int count = 0;
    double sum = 0;

    @Override
    public void addMeasurement(double measurementResult) {
        count++;
        sum+=measurementResult;
    }

    @Override
    public double getResult() {
        return sum/count;
    }

    @Override
    public MeasurementCombiner getNewInstance() {
        return new AVGCombiner();
    }
}