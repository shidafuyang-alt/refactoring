package theater.calculator;

import theater.Constants;
import theater.Performance;
import theater.Play;

/**
 * Calculator for history performances.
 */
public class HistoryCalculator extends AbstractPerformanceCalculator {

    /**
     * Creates a HistoryCalculator.
     *
     * @param performance the performance
     * @param play the play
     */
    public HistoryCalculator(Performance performance, Play play) {
        super(performance, play);
    }

    /**
     * Calculates amount owed for a history play.
     *
     * @return amount in cents
     */
    @Override
    public int amount() {
        int result = Constants.HISTORY_BASE_AMOUNT;
        if (getPerformance().getAudience() > Constants.HISTORY_AUDIENCE_THRESHOLD) {
            result += Constants.HISTORY_OVER_BASE_CAPACITY_PER_PERSON
                    * (getPerformance().getAudience() - Constants.HISTORY_AUDIENCE_THRESHOLD);
        }
        return result;
    }

    /**
     * Calculates volume credits for a history play.
     *
     * @return volume credits
     */
    @Override
    public int volumeCredits() {
        return Math.max(getPerformance().getAudience() - Constants.HISTORY_VOLUME_CREDIT_THRESHOLD, 0);
    }
}
