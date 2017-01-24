package de.tub.duplicateDetection.mesurments;


import de.tub.duplicateDetection.Row;
import info.debatty.java.stringsimilarity.NormalizedLevenshtein;


public class SSNEquality implements Measurement {

    NormalizedLevenshtein l = new NormalizedLevenshtein();

    @Override
    public double evaluateCombination(Row rowOne, Row rowTwo) {
        if(rowOne.SSN.isEmpty() || rowTwo.SSN.isEmpty() )
            return 0.0;
        return rowOne.SSN.equals(rowTwo.SSN) ? 1.0: 0.0;
    }
}
