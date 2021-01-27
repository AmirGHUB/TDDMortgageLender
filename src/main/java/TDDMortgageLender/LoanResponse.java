package TDDMortgageLender;

import java.time.LocalDate;

public class LoanResponse {

    private String qualification;
    private double loanAmount;
    private LoanStatus status;
    private LocalDate applicationDate;

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }




    public LoanResponse(String qualification, double loanAmount, LoanStatus status) {
        this.qualification = qualification;
        this.loanAmount = loanAmount;
        this.status = status;
        this.applicationDate=LocalDate.now();
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
