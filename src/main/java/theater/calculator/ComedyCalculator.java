package theater.calculator;

import theater.Constants;
import theater.Performance;
import theater.Play;

/**
 * Calculator for comedy plays.
 */
public class ComedyCalculator extends AbstractPerformanceCalculator {

    /**
     * Creates a comedy calculator.
     *
     * @param performance the performance
     * @param play the play
     */
    public ComedyCalculator(Performance performance, Play play) {
        super(performance, play);
    }

    @Override
    public int amount() {
        int result = Constants.COMEDY_BASE_AMOUNT;

        if (getPerformance().getAudience() > Constants.COMEDY_AUDIENCE_THRESHOLD) {
            result += Constants.COMEDY_OVER_BASE_CAPACITY_AMOUNT
                    + Constants.COMEDY_OVER_BASE_CAPACITY_PER_PERSON
                    * (getPerformance().getAudience() - Constants.COMEDY_AUDIENCE_THRESHOLD);
        }

        result += Constants.COMEDY_AMOUNT_PER_AUDIENCE * getPerformance().getAudience();
        return result;
    }

    @Override
    public int volumeCredits() {
        return super.volumeCredits()
                + getPerformance().getAudience() / Constants.COMEDY_EXTRA_VOLUME_FACTOR;
    }
}
