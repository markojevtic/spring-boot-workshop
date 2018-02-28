package pd.worksop.testwithboot.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import pd.worksop.testwithboot.service.DiscountService;

@RunWith( SpringRunner.class )
@SpringBootTest
public class DiscountServiceImplTest {

    @Autowired
    private DiscountService discountService;

    @Test
    public void discountServiceReturnsProperValueOfDiscountForValidCoupon() {
        //Given a valid coupon,
        // and external service returns 10% of discount for the coupon
        //when service calculate discount on price of 110$,
        //then result is 11.00
    }

    @Test
    public void discountServiceThrowsApplicationExceptionWhenCouponServiceFail() {
        //Given an external service that throw exception on execute
        //when service calculate discount on price of 110$,
        //then result is an ApplicationException
    }


    @Test
    public void discountServiceThrowsInvalidCouponException() {
        //Given an invalid coupon,
        //and external service return -1 for the given coupon
        //when service calculate discount on price of 110$,
        //then result is an InvalidCouponException
    }

    @Test
    public void discountServiceExecutesInvalidationRequestForAmountGreaterThanZero() {
        //Given an valid coupon,
        //and discount amount 10$
        //when service uses coupon,
        //then request has been executed once.
    }

    @Test
    public void discountServiceDoesNotExecuteInvalidationRequestForAmountZero() {
        //Given an valid coupon,
        //and discount amount 0$
        //when service uses coupon,
        //then remote services has been called 0 times.
    }
}