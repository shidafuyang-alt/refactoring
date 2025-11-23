package theater;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Map;

import theater.data.PerformanceData;
import theater.data.StatementData;

/**
 * Statement printer that outputs HTML.
 */
public class HTMLStatementPrinter extends StatementPrinter {

    /**
     * Creates an HTMLStatementPrinter for the given invoice and plays.
     *
     * @param invoice the invoice to print
     * @param plays the map of play information
     */
    public HTMLStatementPrinter(Invoice invoice, Map<String, Play> plays) {
        super(invoice, plays);
    }

    /**
     * Generates an HTML statement for this invoice.
     *
     * @return HTML formatted statement
     */
    @Override
    public String statement() {
        final StatementData data = getStatementData();

        final StringBuilder result = new StringBuilder(
                String.format("<h1>Statement for %s</h1>%n", data.getCustomer()));

        result.append("<table>").append(System.lineSeparator());
        result.append("<tr><th>play</th><th>seats</th><th>cost</th></tr>")
                .append(System.lineSeparator());

        for (final PerformanceData pd : data.getPerformances()) {
            result.append(String.format(
                    "<tr><td>%s</td><td>%s</td><td>%s</td></tr>%n",
                    pd.getPlayName(),
                    pd.getAudience(),
                    formatCurrency(pd.getAmount())));
        }

        result.append("</table>").append(System.lineSeparator());
        result.append(String.format(
                "<p>Amount owed is <em>%s</em></p>%n",
                formatCurrency(data.getTotalAmount())));

        result.append(String.format(
                "<p>You earned <em>%s</em> credits</p>%n",
                data.getTotalVolumeCredits()));

        return result.toString();
    }

    /**
     * Formats cents into a USD currency string.
     *
     * @param amountInCents amount in cents
     * @return formatted USD string
     */
    private String formatCurrency(int amountInCents) {
        final NumberFormat frmt = NumberFormat.getCurrencyInstance(Locale.US);
        return frmt.format(amountInCents / Constants.PERCENT_FACTOR);
    }
}
