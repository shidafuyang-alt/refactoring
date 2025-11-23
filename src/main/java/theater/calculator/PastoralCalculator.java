package theater.calculator;

import theater.Constants;
import theater.Performance;
import theater.Play;

/**
 * Calculator for pastoral plays.
 */
public class PastoralCalculator extends AbstractPerformanceCalculator {

    /**
     * Creates a pastoral calculator.
     *
     * @param performance the performance
     * @param play the play
     */
    public PastoralCalculator(Performance performance, Play play) {
        super(performance, play);
    }

    @Override
    public int amount() {
        int result = Constants.PASTORAL_BASE_AMOUNT;
        if (getPerformance().getAudience() > Constants.PASTORAL_AUDIENCE_THRESHOLD) {
            result += Constants.PASTORAL_OVER_BASE_CAPACITY_PER_PERSON
                    * (getPerformance().getAudience() - Constants.PASTORAL_AUDIENCE_THRESHOLD);
        }
        return result;
    }

    @Override
    public int volumeCredits() {
        return Math.max(
                getPerformance().getAudience()
                        - Constants.PASTORAL_VOLUME_CREDIT_THRESHOLD, 0)
                + getPerformance().getAudience() / Constants.PASTORAL_VOLUME_CREDIT_DIVISOR;
    }
}
