package theater.calculator;

import theater.Constants;
import theater.Performance;
import theater.Play;

/**
 * Calculator for history plays.
 */
public class HistoryCalculator extends AbstractPerformanceCalculator {

    /**
     * Creates a history calculator.
     *
     * @param performance the performance
     * @param play the play
     */
    public HistoryCalculator(Performance performance, Play play) {
        super(performance, play);
    }

    @Override
    public int amount() {
        int result = Constants.HISTORY_BASE_AMOUNT;
        if (getPerformance().getAudience() > Constants.HISTORY_AUDIENCE_THRESHOLD) {
            result += Constants.HISTORY_OVER_BASE_CAPACITY_PER_PERSON
                    * (getPerformance().getAudience() - Constants.HISTORY_AUDIENCE_THRESHOLD);
        }
        return result;
    }

    @Override
    public int volumeCredits() {
        return super.volumeCredits()
                + Math.max(getPerformance().getAudience()
                - Constants.HISTORY_VOLUME_CREDIT_THRESHOLD, 0);
    }
}
