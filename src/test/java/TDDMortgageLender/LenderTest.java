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


}
