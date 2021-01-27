package TDDMortgageLender;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Lender {

    private double availableFund;
    private HashMap<Applicant, LoanResponse> loanApplications;
    private double pendingFund;

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
        }else {

            response = new LoanResponse("not qualified" ,  0.0,LoanStatus.DENIED);

            response=processLoan(requestedAmount,response);
            loanApplications.put(applicant,response);

        }


        return response;

    }

    private LoanResponse processLoan(double requestedAmount , LoanResponse response)  {

        if(response.getLoanStatus()==LoanStatus.QUALIFIED){
            if(requestedAmount<=this.availableFund){

                this.availableFund-=requestedAmount;
                this.pendingFund=requestedAmount;

                response.setLoanStatus(LoanStatus.APPROVED);
                return response;
            }else{
                response.setLoanStatus(LoanStatus.ONHOLD);
                return response;
            }
        }else{
            response.setLoanStatus(LoanStatus.DENIED);

            System.out.println("Unqualified applicant, please do not proceed!");
            //throw new UnQualifiedApplicantException("Unqualified applicant, please do not proceed!");
            return response;
        }
    }

    public double getPendingFund() {
        return this.pendingFund;
    }

    public LoanResponse confirm(Applicant applicant) {
        LoanResponse response=loanApplications.get(applicant);

        response.setLoanStatus(LoanStatus.ACCEPTED);
        this.pendingFund-=response.getLoanAmount();
        return response;
    }

    public LoanResponse reject(Applicant applicant) {
        LoanResponse response=loanApplications.get(applicant);
        response.setLoanStatus(LoanStatus.REJECTED);
        this.pendingFund-=response.getLoanAmount();
        this.availableFund+=response.getLoanAmount();
        return response;
    }

    public HashMap<Applicant,LoanResponse> getLoanApplications() {
        return loanApplications;
    }


    public void checkForExpriration() {
        //today - applicationdate>3: Exprired?
        for(Applicant applicant: loanApplications.keySet()){
            if((LocalDate.now().getDayOfMonth()-loanApplications.get(applicant).getApplicationDate().getDayOfMonth())>3){
                loanApplications.get(applicant).setLoanStatus(LoanStatus.EXPIRED);
                this.pendingFund-=loanApplications.get(applicant).getLoanAmount();
                this.availableFund+=loanApplications.get(applicant).getLoanAmount();
            }
        }
    }

    public List<LoanResponse> search(LoanStatus loanStatus) {
        List<LoanResponse> loans=new ArrayList<>();

        for(LoanResponse loanResponse: loanApplications.values()){
            if(loanResponse.getLoanStatus().equals(loanStatus)){
                loans.add(loanResponse);
            }
        }
        return loans;

    }
}
