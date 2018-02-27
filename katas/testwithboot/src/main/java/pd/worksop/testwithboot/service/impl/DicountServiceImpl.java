package pd.worksop.testwithboot.service.impl;

import an.external.library.CouponRequest;
import java.math.BigDecimal;
import static java.math.BigDecimal.ZERO;
import static java.math.MathContext.DECIMAL32;
import org.springframework.stereotype.Service;
import pd.worksop.testwithboot.exception.ApplicationException;
import pd.worksop.testwithboot.exception.InvalidCouponException;
import pd.worksop.testwithboot.service.DiscountService;


@Service
public class DicountServiceImpl implements DiscountService {

    @Override
    public BigDecimal getDiscountAmount( BigDecimal price, String discountCoupon ) {

        try {
            BigDecimal discounPercent = getCouponDiscount( discountCoupon );
            if( discounPercent.compareTo( ZERO ) < 0 ) {
                throw new InvalidCouponException();
            }
            return price.multiply( discounPercent, DECIMAL32 )
                    .divide( new BigDecimal( 100 ), DECIMAL32 )
                    .setScale( 2 );
        } catch( Exception ex ) {
            throw new ApplicationException();
        }
    }

    private BigDecimal getCouponDiscount( String discountCoupon ) {
        CouponRequest couponRequest = new CouponRequest( discountCoupon );
        return new BigDecimal( couponRequest.validateCoupon() );
    }

    @Override
    public void useCoupon(BigDecimal amount, String coupon ) {
        if(amount.compareTo( ZERO ) > 0 ) {
            callInvalidateCouponService( coupon );
        }
    }

    private boolean callInvalidateCouponService( String coupon ) {
        CouponRequest couponRequest = new CouponRequest( coupon );
        return couponRequest.invalidateCoupon();
    }
}
