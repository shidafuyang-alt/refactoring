package theater;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

import theater.calculator.AbstractPerformanceCalculator;
import theater.calculator.ComedyCalculator;
import theater.calculator.HistoryCalculator;
import theater.calculator.PastoralCalculator;
import theater.calculator.TragedyCalculator;
import theater.data.PerformanceData;
import theater.data.StatementData;

/**
 * This class generates a statement for a given invoice of performances.
 */
public class StatementPrinter {

    private final StatementData statementData;
    private final Map<String, Play> plays;

    /**
     * Creates a StatementPrinter for the given invoice and plays.
     * All calculation is done inside StatementData.
     *
     * @param invoice the invoice to print
     * @param plays the map of play information
     */
    public StatementPrinter(Invoice invoice, Map<String, Play> plays) {
        this.plays = plays;
        this.statementData = new StatementData(invoice, plays);
    }

    /**
     * Generates a plain-text statement for this invoice.
     *
     * @return the formatted statement
     */
    public String statement() {
        return renderPlainText(statementData);
    }

    /**
     * Render a formatted plain text statement using precomputed data.
     *
     * @param data the computed statement data
     * @return formatted plain-text statement
     */
    private String renderPlainText(StatementData data) {
        final StringBuilder result = new StringBuilder();
        result.append("Statement for ")
                .append(data.getCustomer())
                .append(System.lineSeparator());

        for (final PerformanceData pd : data.getPerformances()) {
            result.append(String.format("  %s: %s (%s seats)%n",
                    pd.getPlayName(),
                    formatCurrency(pd.getAmount()),
                    pd.getAudience()));
        }

        result.append(String.format(
                "Amount owed is %s%n",
                formatCurrency(data.getTotalAmount())));

        result.append(String.format(
                "You earned %s credits%n",
                data.getTotalVolumeCredits()));

        return result.toString();
    }

    /**
     * Formats cents into USD currency string.
     *
     * @param amountInCents amount in cents
     * @return formatted USD string
     */
    private String formatCurrency(int amountInCents) {
        final NumberFormat frmt = NumberFormat.getCurrencyInstance(Locale.US);
        return frmt.format(amountInCents / Constants.PERCENT_FACTOR);
    }

    /**
     * Looks up the play for a given performance.
     *
     * @param performance the performance
     * @return the corresponding Play
     */
    private Play getPlay(Performance performance) {
        return plays.get(performance.getPlayID());
    }

    /**
     * Calculates amount for a performance.
     *
     * @param performance the performance
     * @return amount in cents
     */
    private int getAmount(Performance performance) {
        return createCalculator(performance, getPlay(performance)).amount();
    }

    /**
     * Calculates volume credits for a performance.
     *
     * @param performance the performance
     * @return volume credits
     */
    private int getVolumeCredits(Performance performance) {
        return createCalculator(performance, getPlay(performance)).volumeCredits();
    }

    /**
     * Formats cents into USD currency string.
     *
     * @param amountInCents amount in cents
     * @return formatted USD string
     */
    private String usd(int amountInCents) {
        return formatCurrency(amountInCents);
    }

    /**
     * Total amount owed (in cents).
     *
     * @return total amount in cents
     */
    private int getTotalAmount() {
        return statementData.getTotalAmount();
    }

    /**
     * Total volume credits earned.
     *
     * @return total volume credits
     */
    private int getTotalVolumeCredits() {
        return statementData.getTotalVolumeCredits();
    }

    /**
     * Selects the correct calculator implementation for the given play type.
     *
     * @param performance the performance being evaluated
     * @param play the play corresponding to this performance
     * @return the appropriate calculator implementation
     * @throws RuntimeException if the play type is unknown
     */
    private AbstractPerformanceCalculator createCalculator(Performance performance, Play play) {
        switch (play.getType()) {
            case "tragedy":
                return new TragedyCalculator(performance, play);
            case "comedy":
                return new ComedyCalculator(performance, play);
            case "history":
                return new HistoryCalculator(performance, play);
            case "pastoral":
                return new PastoralCalculator(performance, play);
            default:
                throw new RuntimeException("unknown type: " + play.getType());
        }
    }

    /**
     * Provides access to computed statement data for subclasses.
     *
     * @return the computed statement data
     */
    protected StatementData getStatementData() {
        return statementData;
    }
}
