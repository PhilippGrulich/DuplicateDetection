package de.tub.duplicateDetection.evaluation;

/**
 * Created by philipp on 08.01.17.
 */
public interface MeasurementCombiner {
    void addMeasurement(double measurementResult);
    double getResult();
    MeasurementCombiner getNewInstance();
}