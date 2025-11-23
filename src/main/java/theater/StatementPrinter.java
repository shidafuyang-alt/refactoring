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
    private final Invoice invoice;
    private final Map<String, Play> plays;

    /**
     * Creates a StatementPrinter for the given invoice and plays.
     *
     * @param invoice the invoice to print
     * @param plays the play map
     */
    public StatementPrinter(Invoice invoice, Map<String, Play> plays) {
        this.invoice = invoice;
        this.plays = plays;
    }

    /**
     * Generates a plain-text statement for this invoice.
     *
     * @return the formatted statement
     */
    public String statement() {
        final StatementData data = createStatementData();
        return renderPlainText(data);
    }

    /**
     * Create a data object containing the calculation results.
     *
     * @return statement data
     */
    private StatementData createStatementData() {
        final StatementData data = new StatementData();
        data.setCustomer(invoice.getCustomer());

        for (final Performance performance : invoice.getPerformances()) {
            final PerformanceData pd = new PerformanceData();
            final Play play = getPlay(performance);
            final AbstractPerformanceCalculator calc = createCalculator(performance, play);

            pd.setPlayName(play.getName());
            pd.setPlayType(play.getType());
            pd.setAudience(performance.getAudience());
            pd.setAmount(calc.amount());
            pd.setVolumeCredits(calc.volumeCredits());

            data.getPerformances().add(pd);
            data.addToTotalAmount(pd.getAmount());
            data.addToTotalVolumeCredits(pd.getVolumeCredits());
        }
        return data;
    }

    private Play getPlay(Performance performance) {
        return plays.get(performance.getPlayID());
    }

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
                throw new RuntimeException(
                        String.format("unknown type: %s", play.getType()));
        }
    }

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

        result.append(String.format("Amount owed is %s%n",
                formatCurrency(data.getTotalAmount())));
        result.append(String.format("You earned %s credits%n",
                data.getTotalVolumeCredits()));
        return result.toString();
    }

    private String formatCurrency(int amountInCents) {
        final NumberFormat frmt = NumberFormat.getCurrencyInstance(Locale.US);
        return frmt.format(amountInCents / Constants.PERCENT_FACTOR);
    }
}
