package TDDMortgageLender;

public class Applicant {

    private int dti;
    private int creditScore;
    private double savingsAmount;
    private Lender lender;


    public Applicant(int dti, int creditScore, double savingsAmount) {
        this.dti = dti;
        this.creditScore = creditScore;
        this.savingsAmount = savingsAmount;
    }

    public int getDti() {
        return dti;
    }

    public void setDti(int dti) {
        this.dti = dti;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }

    public double getSavingsAmount() {
        return savingsAmount;
    }

    public void setSavingsAmount(double savingsAmount) {
        this.savingsAmount = savingsAmount;
    }

    public Lender getLender() {
        return lender;
    }

    public void setLender(Lender lender) {
        this.lender = lender;
    }

    public LoanResponse apply(double requestedAmount) {
        return this.lender.qualifyApplicant(requestedAmount,this);
    }
}
