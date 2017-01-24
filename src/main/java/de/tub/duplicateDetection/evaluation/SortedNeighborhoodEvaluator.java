package de.tub.duplicateDetection.evaluation;

import de.tub.duplicateDetection.Row;
import de.tub.duplicateDetection.mesurments.Measurement;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by philipp on 1/21/17.
 */
public class SortedNeighborhoodEvaluator implements Evaluator {


    private SortKeyGenerator sortKeyGenerator;
    private int windowLength;

    public SortedNeighborhoodEvaluator(SortKeyGenerator sortKeyGenerator, int windowLength) {
        this.sortKeyGenerator = sortKeyGenerator;
        this.windowLength = windowLength;
    }


    public List<Evaluator.EvaluationResult> evaluate(List<Row> rows, Map<Measurement, Double> measurementWeightMap, MeasurementCombiner combiner, double threshold) {
        System.out.println("start evaluation");

        rows.sort(Comparator.comparing(o -> sortKeyGenerator.generateKey(o)));



        for (int firstRowIndex = 0; firstRowIndex < rows.size(); firstRowIndex++) {

            Row firstRow = rows.get(firstRowIndex);
            for (int secondRowIndex = firstRowIndex + 1; secondRowIndex < firstRowIndex + windowLength +1 && secondRowIndex < rows.size(); secondRowIndex++) {
                Row secondRow = rows.get(secondRowIndex);
                double evaluationResult = evaluateCombination(firstRow, secondRow, measurementWeightMap, combiner);
                if (evaluationResult>= threshold)
                   secondRow.ClusterID = firstRow.ClusterID;
            }
        }


        return null;
    }


    private double evaluateCombination(Row firstRow, Row secondRow, Map<Measurement, Double> measurementWeightMap, MeasurementCombiner combiner){
        MeasurementCombiner c = combiner.getNewInstance();
        for (Map.Entry<Measurement, Double> entry : measurementWeightMap.entrySet()) {
            Measurement measurementFunction = entry.getKey();
            double weight = entry.getValue();
            double measurementResult = measurementFunction.evaluateCombination(firstRow, secondRow) * weight;
            c.addMeasurement(measurementResult);
        }
        return c.getResult();
    }

    public interface SortKeyGenerator {

        String generateKey(Row row);

    }
}
