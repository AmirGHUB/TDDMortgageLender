package TDDMortgageLender;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    public void qualifyALoanApplication(){
        lender.deposit_amount(300000);
        Applicant simon =  new Applicant(25,700,150000);
        simon.setLender(lender);
        LoanResponse response = simon.apply(250000);

        assertEquals("qualified" , response.getQualification());
        assertEquals( 250000 , response.getLoanAmount());
        assertEquals(LoanStatus.APPROVED , response.getLoanStatus());
    }

    @Test
    public void processLoanQualificationBasedOnAvailableFund(){
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

        LoanResponse response = jhon.apply(150000);

        assertEquals(LoanStatus.DENIED , response.getLoanStatus());

    }

    @Test
    public void processLoanQualificationForInsufficientAvailableFund(){
        lender.deposit_amount(70000);
        Applicant jhon = new Applicant(20,730,100000);
        jhon.setLender(lender);

        LoanResponse response = jhon.apply(150000);

        assertEquals(LoanStatus.ONHOLD, response.getLoanStatus());

    }


}
