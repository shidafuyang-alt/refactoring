package theater.calculator;

import theater.Constants;
import theater.Performance;
import theater.Play;

/**
 * Calculator for amount and credits for a performance.
 */
public abstract class AbstractPerformanceCalculator {

    private final Performance performance;
    private final Play play;

    /**
     * Creates a calculator for the given performance and play.
     *
     * @param performance the performance
     * @param play the play being performed
     */
    protected AbstractPerformanceCalculator(Performance performance, Play play) {
        this.performance = performance;
        this.play = play;
    }

    /**
     * Provides access for subclasses to the performance.
     *
     * @return the performance
     */
    protected Performance getPerformance() {
        return performance;
    }

    /**
     * Provides access for subclasses to the play.
     *
     * @return the play
     */
    protected Play getPlay() {
        return play;
    }

    /**
     * Calculates the amount owed for this performance (in cents).
     *
     * @return amount in cents
     */
    public abstract int amount();

    /**
     * Calculates the volume credits earned for this performance.
     *
     * @return volume credits
     */
    public int volumeCredits() {
        return Math.max(
                performance.getAudience() - Constants.BASE_VOLUME_CREDIT_THRESHOLD,
                0);
    }
}
