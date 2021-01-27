package TDDMortgageLender;

public class LoanResponse {

    private String qualification;
    private double loanAmount;
    private LoanStatus status;


    public LoanResponse(String qualification, double loanAmount, LoanStatus status) {
        this.qualification = qualification;
        this.loanAmount = loanAmount;
        this.status = status;
    }

    public String getQualification() {
        return this.qualification;
    }

    public double getLoanAmount() {
        return this.loanAmount;
    }

    public LoanStatus getLoanStatus() {
        return this.status;
    }

    public void setLoanStatus(LoanStatus status) {
        this.status = status;
    }
}
