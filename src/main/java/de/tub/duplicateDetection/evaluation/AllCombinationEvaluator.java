package de.tub.duplicateDetection.evaluation;

import de.tub.duplicateDetection.Row;
import de.tub.duplicateDetection.mesurments.Measurement;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Simple Evaluator which evaluates all combinations, so (n^2+n)/2 evaluations
 */
public class AllCombinationEvaluator implements Evaluator {

    public List<Evaluator.EvaluationResult> evaluate(List<Row> rows, Map<Measurement, Double> measurementWeightMap, MeasurementCombiner combiner, double threshold) {

        List<Evaluator.EvaluationResult> evaluationResults = new LinkedList<>();

        for (int firstRowIndex = 0; firstRowIndex < rows.size(); firstRowIndex++) {
            if(firstRowIndex%100==0)
                System.out.println("Evaluate Row: " + firstRowIndex + " - from " + rows.size());
            Row firstRow = rows.get(firstRowIndex);
            for (int secondRowIndex = firstRowIndex + 1; secondRowIndex < rows.size(); secondRowIndex++) {
                Row secondRow = rows.get(secondRowIndex);
                double evaluationResult = evaluateCombination(firstRow, secondRow, measurementWeightMap, combiner);
                if (evaluationResult>= threshold)
                     evaluationResults.add(new Evaluator.EvaluationResult(firstRow, secondRow, evaluationResult));
            }
        }


        return evaluationResults;
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
}
