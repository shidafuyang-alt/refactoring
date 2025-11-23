package theater.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import theater.Invoice;
import theater.Performance;
import theater.Play;
import theater.calculator.AbstractPerformanceCalculator;
import theater.calculator.ComedyCalculator;
import theater.calculator.HistoryCalculator;
import theater.calculator.PastoralCalculator;
import theater.calculator.TragedyCalculator;

/**
 * Stores computed data for a statement before formatting.
 */
public class StatementData {

    private String customer;
    private final List<PerformanceData> performances = new ArrayList<>();
    private int totalAmount;
    private int totalVolumeCredits;

    /**
     * Construct a StatementData and compute all results
     * from the invoice and associated plays.
     *
     * @param invoice the invoice to compute
     * @param plays map of playID to Play objects
     */
    public StatementData(Invoice invoice, Map<String, Play> plays) {
        this.customer = invoice.getCustomer();

        for (Performance performance : invoice.getPerformances()) {
            final Play play = plays.get(performance.getPlayID());
            final AbstractPerformanceCalculator calculator =
                    createCalculator(performance, play);

            final PerformanceData pd = new PerformanceData();
            pd.setPlayName(play.getName());
            pd.setPlayType(play.getType());
            pd.setAudience(performance.getAudience());
            pd.setAmount(calculator.amount());
            pd.setVolumeCredits(calculator.volumeCredits());

            performances.add(pd);

            totalAmount += pd.getAmount();
            totalVolumeCredits += pd.getVolumeCredits();
        }
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
            case "tragedy": return new TragedyCalculator(performance, play);
            case "comedy": return new ComedyCalculator(performance, play);
            case "history": return new HistoryCalculator(performance, play);
            case "pastoral": return new PastoralCalculator(performance, play);
            default: throw new RuntimeException("unknown type: " + play.getType());
        }
    }

    /**
     * Gets the customer name for this statement.
     *
     * @return the customer name
     */
    public String getCustomer() {
        return customer;
    }

    /**
     * Sets the customer name for this statement.
     *
     * @param customer the customer name
     */
    public void setCustomer(String customer) {
        this.customer = customer;
    }

    /**
     * Gets the list of performance data lines in this statement.
     *
     * @return the performances list
     */
    public List<PerformanceData> getPerformances() {
        return performances;
    }

    /**
     * Gets the total amount owed (in cents).
     *
     * @return total amount in cents
     */
    public int getTotalAmount() {
        return totalAmount;
    }

    /**
     * Adds an amount (in cents) to the total amount owed.
     *
     * @param amount the amount to add in cents
     */
    public void addToTotalAmount(int amount) {
        totalAmount += amount;
    }

    /**
     * Gets the total volume credits earned.
     *
     * @return total volume credits
     */
    public int getTotalVolumeCredits() {
        return totalVolumeCredits;
    }

    /**
     * Adds credits to the total volume credits earned.
     *
     * @param credits the credits to add
     */
    public void addToTotalVolumeCredits(int credits) {
        totalVolumeCredits += credits;
    }
}
