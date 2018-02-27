package an.external.library;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class CouponRequest {
    private final String couponCode;

    public CouponRequest(String couponCode) {
        this.couponCode = couponCode;
    }

    public int validateCoupon() {
        throw new NotImplementedException();
    }

    public boolean invalidateCoupon() {
        throw new NotImplementedException();
    }
}
