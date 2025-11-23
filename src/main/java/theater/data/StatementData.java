package theater.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores computed data for a statement before formatting.
 */
public class StatementData {

    private String customer;
    private final List<PerformanceData> performances = new ArrayList<>();
    private int totalAmount;
    private int totalVolumeCredits;

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
