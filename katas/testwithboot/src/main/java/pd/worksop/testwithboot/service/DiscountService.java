package pd.worksop.testwithboot.service;

import java.math.BigDecimal;

public interface DiscountService {
    BigDecimal getDiscountAmount(BigDecimal price, String discountCoupon);
    void useCoupon(BigDecimal discount, String coupon);
}
