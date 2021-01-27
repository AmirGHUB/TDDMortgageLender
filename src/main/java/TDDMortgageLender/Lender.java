package TDDMortgageLender;

import java.util.HashMap;

public class Lender {

    private double availableFund;
    private HashMap<Applicant, LoanResponse> loanApplications;

    Lender(){
        availableFund = 0;
        loanApplications = new HashMap<>();
    }

    public double getAvailableFund() {
        return this.availableFund;
    }

    public void deposit_amount(double amount) {
        this.availableFund += amount;
    }

    public LoanResponse qualifyApplicant(double requestedAmount, Applicant applicant) {
        LoanResponse response = null;
        if(applicant.getDti()<36 && applicant.getCreditScore()>620 && (requestedAmount*0.25)<=applicant.getSavingsAmount()){
            response = new LoanResponse("qualified" , requestedAmount , LoanStatus.QUALIFIED);
            response = processLoan(requestedAmount,response);
            loanApplications.put(applicant,response);
        }else if(applicant.getDti()<36 && applicant.getCreditScore()>620 && (requestedAmount*0.25)>applicant.getSavingsAmount()){
            response = new LoanResponse("partially qualified" ,  (applicant.getSavingsAmount()*4),LoanStatus.QUALIFIED);
            response = processLoan(requestedAmount,response);
            loanApplications.put(applicant,response);
        }else{
            response = new LoanResponse("not qualified" ,  0.0,LoanStatus.DENIED);
            response = processLoan(requestedAmount,response);
            loanApplications.put(applicant,response);
        }


        return response;

    }

    private LoanResponse processLoan(double requestedAmount , LoanResponse response){

        if(response.getLoanStatus()==LoanStatus.QUALIFIED){
            if(requestedAmount<=this.availableFund){
                response.setLoanStatus(LoanStatus.APPROVED);
                return response;
            }else{
                response.setLoanStatus(LoanStatus.ONHOLD);
                return response;
            }
        }else{
            return response;
        }
    }
}
