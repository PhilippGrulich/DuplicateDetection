package de.tub.duplicateDetection.mesurments;


import de.tub.duplicateDetection.Row;

/**
 * Created by philipp on 05.01.17.
 */
public interface Measurement {

    /**
     * Evaluates the similarity of tow rows.
     * @return similarity between 0-1 | 1 equal and 0 unequal
     */
    double evaluateCombination(Row rowOne, Row rowTwo);

}
