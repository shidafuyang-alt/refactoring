package theater.calculator;

import theater.Constants;
import theater.Performance;
import theater.Play;

/**
 * Calculator for tragedy plays.
 */
public class TragedyCalculator extends AbstractPerformanceCalculator {

    /**
     * Creates a tragedy calculator.
     *
     * @param performance the performance
     * @param play the play
     */
    public TragedyCalculator(Performance performance, Play play) {
        super(performance, play);
    }

    @Override
    public int amount() {
        int result = Constants.TRAGEDY_BASE_AMOUNT;
        if (getPerformance().getAudience() > Constants.TRAGEDY_AUDIENCE_THRESHOLD) {
            result += Constants.TRAGEDY_OVER_BASE_CAPACITY_PER_PERSON
                    * (getPerformance().getAudience() - Constants.TRAGEDY_BASE_CAPACITY);
        }
        return result;
    }
}
