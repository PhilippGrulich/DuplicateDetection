package de.tub.duplicateDetection.mesurments;


import de.tub.duplicateDetection.Row;
import info.debatty.java.stringsimilarity.NormalizedLevenshtein;


public class NameLevenshteinSimilarity implements Measurement {

    NormalizedLevenshtein l = new NormalizedLevenshtein();

    @Override
    public double evaluateCombination(Row rowOne, Row rowTwo) {

        return l.similarity(rowOne.FirstName,rowTwo.FirstName)*
               l.similarity(rowOne.LastName,rowTwo.LastName)*
               l.similarity(rowOne.MiddleName,rowTwo.MiddleName);
    }
}
