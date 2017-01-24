package de.tub.duplicateDetection;

import au.com.bytecode.opencsv.CSVWriter;
import de.tub.duplicateDetection.evaluation.AVGCombiner;
import de.tub.duplicateDetection.evaluation.AllCombinationEvaluator;
import de.tub.duplicateDetection.evaluation.Evaluator;
import de.tub.duplicateDetection.evaluation.SortedNeighborhoodEvaluator;
import de.tub.duplicateDetection.mesurments.Measurement;
import de.tub.duplicateDetection.mesurments.NameLevenshteinSimilarity;
import de.tub.duplicateDetection.mesurments.SSNEquality;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Stream;

/**
 * This is a simple duplicate detection tool for the third data integration assignment.
 * Currently its hard bounded to the given dataset.
 * It offers some flexibility in terms of similarity measurement and evaluation strategy.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        // Read input CSV file
        InputReader ir = new InputReader("cleanedData.csv");
        List<Row> rows = ir.readFile();

        int clusterID = 1;
        for(Row r : rows){
            r.ClusterID = clusterID++;
        }




        // Initialise similarity measurements
        NameLevenshteinSimilarity nls = new NameLevenshteinSimilarity();
        SSNEquality ssnEquality = new SSNEquality();


        // Add measurement functions to hashmap with wights
        Map<Measurement, Double> weightMeasurements = new HashMap<>();
        weightMeasurements.put(nls,1.5);
        weightMeasurements.put(ssnEquality,10.0);

        int[] windows = {5,30,50};
        double[] trashholds = {.9,.6,.4,.2};
        for(int window : windows) {
            for(double trashhold :trashholds) {
                // Create Evaluator and execute evaluation
                //
                Evaluator e = new SortedNeighborhoodEvaluator(row -> row.FirstName+ row.LastName, 5);
                e.evaluate(rows, weightMeasurements, new AVGCombiner(), 0.9);
                e = new SortedNeighborhoodEvaluator(row -> row.SSN, window);
                e.evaluate(rows, weightMeasurements, new AVGCombiner(), trashhold);



                CheckResult result = checkResults(rows);
                System.out.println("WINDOW:" + window + "  -  " + trashhold + " " + result );


            }
        }
    }

    private static List<Row> copyList(List<Row> rows){
        ArrayList<Row> localRows = new ArrayList<Row>(rows.size());
        for(Row p : rows) {
            localRows.add(new Row(p));
        }
        return localRows;
    }


    private static CheckResult checkResults(List<Row> rows) throws IOException {
        Stream<String> rowStream = rows
                .stream()
                .sorted(Comparator.comparingInt(x -> x.ClusterID))
                .map(x -> x.ClusterID + "," + x.RecID)
                ;

        String head = "cluster_id, record_id";
        ArrayList<String> headerList = new ArrayList<>();
        headerList.add(head);
        Files.write(Paths.get("result.csv"), headerList);

        Files.write(Paths.get("result.csv"), (Iterable<String>)rowStream::iterator, StandardOpenOption.APPEND );

        // Run a java app in a separate system process
        Process proc = Runtime.getRuntime().exec("java -jar evaluator.jar numbers.csv result.csv");
        // Then retreive the process output
        InputStream stream = proc.getInputStream();
        BufferedReader bs = new BufferedReader(new InputStreamReader(stream));
        StringBuilder responseData = new StringBuilder();
        String tp = bs.readLine().split(":")[1].trim();
        String fp = bs.readLine().split(":")[1].trim();
        String precision = bs.readLine().split(":")[1].trim();
        String fn = bs.readLine().split(":")[1].trim();
        String recall = bs.readLine().split(":")[1].trim();

        return new CheckResult(Integer.valueOf(tp),Integer.valueOf(fp),Double.valueOf(precision),Integer.valueOf(fn),Double.valueOf(recall));
    }


    private static class CheckResult {
        final int tp;
        final int fp;
        final double precision;
        final int fn;
        final double recall;

        public CheckResult(int tp, int fp, double precision, int fn, double recall) {
            this.tp = tp;
            this.fp = fp;
            this.precision = precision;
            this.fn = fn;
            this.recall = recall;
        }

        @Override
        public String toString() {
            return "CheckResult{" +
                    "tp=" + tp +
                    ", fp=" + fp +
                    ", precision=" + precision +
                    ", fn=" + fn +
                    ", recall=" + recall +
                    '}';
        }
    }
}
