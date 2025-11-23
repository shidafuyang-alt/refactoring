package theater;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

import theater.data.PerformanceData;
import theater.data.StatementData;

/**
 * This class generates a statement for a given invoice of performances.
 */
public class StatementPrinter {

    private final StatementData statementData;

    /**
     * Creates a StatementPrinter for the given invoice and plays.
     * All calculation is done inside StatementData.
     *
     * @param invoice the invoice to print
     * @param plays the map of play information
     */
    public StatementPrinter(Invoice invoice, Map<String, Play> plays) {
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

        // If you named these getTotalAmount/getTotalVolumeCredits, swap them back.
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
     * Provides access to computed statement data for subclasses.
     *
     * @return the computed statement data
     */
    protected StatementData getStatementData() {
        return statementData;
    }

}
