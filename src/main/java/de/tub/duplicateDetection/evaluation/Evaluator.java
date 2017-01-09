package de.tub.duplicateDetection.evaluation;

import de.tub.duplicateDetection.Row;
import de.tub.duplicateDetection.mesurments.Measurement;

import java.util.List;
import java.util.Map;


/**
 * The Evaluator evaluates the similarity of a list of rows.
 * The implementations of this interface offer special strategies to reduce the complexity of this step.
 */
public interface Evaluator {


    /**
     * @param rows List of Rows
     * @param measurementWeightMap A {@link java.util.HashMap} with a measurement function and a weight for this function
     * @param combiner A combiner which combine multiple measurement results in one metric.
     * @param threshold Only values with a higher similarity are added to the similarity set.
     * @return A List of {@link EvaluationResult} which contain a combination of two rows and their similarity
     */
    List<EvaluationResult> evaluate(List<Row> rows, Map<Measurement, Double> measurementWeightMap, MeasurementCombiner combiner, double threshold);

    class EvaluationResult {
        public final Row row;
        public final Row row1;
        public final double similarity;

        public EvaluationResult(Row row, Row row1, double similarity) {
            this.row = row;
            this.row1 = row1;
            this.similarity = similarity;
        }

        @Override
        public String toString() {
            return "EvaluationResult{" +
                    "row=" + row +
                    ", row1=" + row1 +
                    ", similarity=" + similarity +
                    '}';
        }
    }

}
