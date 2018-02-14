package localapp.zingohotels.com.localapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ZingoHotels.com on 1/30/2018.
 */

public class Coupons implements Serializable {

    @SerializedName("CouponId")
    private int CouponId;
    @SerializedName("categories")
    private Category categories;
    @SerializedName("CategoriesId")
    private int CategoriesId;
    @SerializedName("CouponCode")
    private String CouponCode;
    @SerializedName("CouponValue")
    private int CouponValue;
    @SerializedName("ValidFrom")
    private String ValidFrom;
    @SerializedName("ValidTo")
    private String ValidTo;

    public int getCouponId() {
        return CouponId;
    }

    public void setCouponId(int couponId) {
        CouponId = couponId;
    }

    public Category getCategories() {
        return categories;
    }

    public void setCategories(Category categories) {
        this.categories = categories;
    }

    public int getCategoriesId() {
        return CategoriesId;
    }

    public void setCategoriesId(int categoriesId) {
        CategoriesId = categoriesId;
    }

    public String getCouponCode() {
        return CouponCode;
    }

    public void setCouponCode(String couponCode) {
        CouponCode = couponCode;
    }

    public int getCouponValue() {
        return CouponValue;
    }

    public void setCouponValue(int couponValue) {
        CouponValue = couponValue;
    }

    public String getValidFrom() {
        return ValidFrom;
    }

    public void setValidFrom(String validFrom) {
        ValidFrom = validFrom;
    }

    public String getValidTo() {
        return ValidTo;
    }

    public void setValidTo(String validTo) {
        ValidTo = validTo;
    }
}
