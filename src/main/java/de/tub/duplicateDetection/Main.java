package de.tub.duplicateDetection;

import de.tub.duplicateDetection.evaluation.AVGCombiner;
import de.tub.duplicateDetection.evaluation.AllCombinationEvaluator;
import de.tub.duplicateDetection.evaluation.Evaluator;
import de.tub.duplicateDetection.mesurments.Measurement;
import de.tub.duplicateDetection.mesurments.NameLevenshteinSimilarity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a simple duplicate detection tool for the third data integration assignment.
 * Currently its hard bounded to the given dataset.
 * It offers some flexibility in terms of similarity measurement and evaluation strategy.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        // Read input CSV file
        InputReader ir = new InputReader("inputDB.csv");
        List<Row> rows = ir.readFile();


        // Initialise similarity measurements
        NameLevenshteinSimilarity nls = new NameLevenshteinSimilarity();


        // Add measurement functions to hashmap with wights
        Map<Measurement, Double> weightMeasurements = new HashMap<>();
        weightMeasurements.put(nls,1.0);

        // Create Evaluator and execute evaluation
        Evaluator e = new AllCombinationEvaluator();
        List<Evaluator.EvaluationResult> results = e.evaluate(rows, weightMeasurements, new AVGCombiner(),0.7);

        results.forEach(System.out::println);

    }



}
