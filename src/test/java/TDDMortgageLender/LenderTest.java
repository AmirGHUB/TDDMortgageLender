package TDDMortgageLender;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class LenderTest {

    private Lender lender;

    @BeforeEach
    public void init(){
        lender = new Lender();
    }

    @Test
    public void checkAvailableFunds(){
        assertEquals(0, lender.getAvailableFund());
    }

    @Test
    public void depositMoneyToCurrentAmount(){
        assertEquals(0, lender.getAvailableFund());
        lender.deposit_amount(100000);
        assertEquals(100000 , lender.getAvailableFund());
    }

    @Test
    public void qualifyALoanApplication()  {
        lender.deposit_amount(300000);
        Applicant simon =  new Applicant(25,700,150000);
        simon.setLender(lender);
        LoanResponse response = simon.apply(250000);

        assertEquals("qualified" , response.getQualification());
        assertEquals( 250000 , response.getLoanAmount());
        assertEquals(LoanStatus.APPROVED , response.getLoanStatus());
    }

    @Test
    public void processLoanQualificationBasedOnAvailableFund() {
        lender.deposit_amount(300000);
        Applicant jhon = new Applicant(20,730,100000);
        jhon.setLender(lender);

        LoanResponse response = jhon.apply(150000);

        assertEquals(LoanStatus.APPROVED , response.getLoanStatus());

    }

    //do the same test for on hold and DENIED loan applications
    @Test
    public void processLoanQualificationForUnqualifiedApplicant(){
        lender.deposit_amount(300000);
        Applicant jhon = new Applicant(40,600,100000);
        jhon.setLender(lender);

        LoanResponse response =jhon.apply(900000);


        assertEquals(LoanStatus.DENIED, response.getLoanStatus());

    }

    @Test
    public void processLoanQualificationForInsufficientAvailableFund() {
        lender.deposit_amount(70000);
        Applicant jhon = new Applicant(20,730,100000);
        jhon.setLender(lender);

        LoanResponse response = jhon.apply(150000);

        assertEquals(LoanStatus.ONHOLD, response.getLoanStatus());

    }

    @Test
    public void checkPendingAndAvailableFundOnApprovedApplicants()  {
        lender.deposit_amount(500000);
        Applicant jhon = new Applicant(20,730,100000);
        jhon.setLender(lender);
        LoanResponse response = jhon.apply(150000);

        assertEquals(150000,lender.getPendingFund());
        assertEquals(350000, lender.getAvailableFund());

    }

    @Test
    public void takeLoanAcceptanceAndUpdateFunds(){
        lender.deposit_amount(500000);
        Applicant jhon = new Applicant(20,730,100000);
        jhon.setLender(lender);

        jhon.apply(150000);
        jhon.confirm();


        assertEquals(0,lender.getPendingFund());
        assertEquals(350000,lender.getAvailableFund());
    }

    @Test
    public void takeLoanRejectionAndUpdateFunds()  {
        lender.deposit_amount(500000);
        Applicant jhon = new Applicant(20,730,100000);
        jhon.setLender(lender);

        jhon.apply(150000);
        jhon.reject();


        assertEquals(0,lender.getPendingFund());
        assertEquals(500000,lender.getAvailableFund());
    }

    @Test
    public void checkApprovedLoansForExpiration() {
        lender.deposit_amount(500000);
        Applicant jhon = new Applicant(20,730,100000);
        jhon.setLender(lender);

        jhon.apply(150000);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        String c = "20/01/2021";


        lender.getLoanApplications().get(jhon).setApplicationDate(LocalDate.parse(c,formatter));

        lender.checkForExpriration();




        assertEquals(LoanStatus.EXPIRED , lender.getLoanApplications().get(jhon).getLoanStatus());

    }

    @Test
    public void searchLoansByStatus(){

        //denied, on hold, approved, accepted, rejected, expired

        List<LoanResponse> actual=new ArrayList<>();
        List<LoanResponse> expected=new ArrayList<>();

        /*--------------accepted----------*/
        lender.deposit_amount(500000);
        Applicant jhon = new Applicant(20,830,200000);
        jhon.setLender(lender);
        jhon.apply(50000);
        LoanResponse loanResponse1=jhon.confirm();


        /*--------------denied----------*/
        Applicant amir = new Applicant(40,600,5000);
        amir.setLender(lender);
        LoanResponse loanResponse2=amir.apply(2000000);



        /*--------------rejected----------*/
        Applicant simon = new Applicant(10,800,150000);
        simon.setLender(lender);
        simon.apply(10000);
        LoanResponse loanResponse3=simon.reject();



        /*--------------approved----------*/
        Applicant peter = new Applicant(20,730,40000);
        peter.setLender(lender);
        LoanResponse loanResponse4=peter.apply(100000);


        /*--------------denied----------*/
        Applicant sam = new Applicant(10,400,5000);
        sam.setLender(lender);
        LoanResponse loanResponse5=sam.apply(2000000);


        /*--------------approved----------*/
        Applicant bob = new Applicant(20,730,40000);
        bob.setLender(lender);
        LoanResponse loanResponse6=bob.apply(100000);


        expected.add(loanResponse2);
        expected.add(loanResponse5);


        actual=lender.search(LoanStatus.DENIED);



        for(LoanResponse loanResponse: actual){
            assertTrue(expected.contains(loanResponse));
        }
        assertEquals(2,actual.size());

    }
}
