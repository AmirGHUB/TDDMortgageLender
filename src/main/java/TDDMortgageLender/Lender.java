package TDDMortgageLender;

public class Lender {

    private double availableFund;

    public double getAvailableFund() {
        return this.availableFund;
    }

    public void deposit_amount(double amount) {
        this.availableFund += amount;
    }
}
