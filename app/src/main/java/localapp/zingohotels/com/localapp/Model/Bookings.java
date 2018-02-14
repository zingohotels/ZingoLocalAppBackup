package localapp.zingohotels.com.localapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ZingoHotels.com on 1/30/2018.
 */

public class Bookings implements Serializable{

    @SerializedName("BookingId")
    private int BookingId;
    @SerializedName("BookingNumber")
    private String BookingNumber;
    @SerializedName("CouponCode")
    private String CouponCode;
    @SerializedName("TravellerId")
    private int TravellerId;
    @SerializedName("travellerList")
    private ArrayList<Travellers> travellerList;
    @SerializedName("BookingDate")
    private String BookingDate;
    @SerializedName("ActivityDate")
    private String ActivityDate;//doubt
    @SerializedName("BookingTimeSlot")
    private String BookingTimeSlot;
    @SerializedName("OptCheckInDate")
    private String OptCheckInDate;//doubt
    @SerializedName("CheckInDate")
    private String CheckInDate;//doubt
    @SerializedName("NoOfAdults")
    private int NoOfAdults;
    @SerializedName("profiles")
    private UserProfile profiles;
    @SerializedName("ProfileId")
    private int ProfileId;
    @SerializedName("activities")
    private ActivityModel activities;
    @SerializedName("DeclaredRate")
    private int DeclaredRate;
    @SerializedName("SellRate")
    private int SellRate;
    @SerializedName("ExtraCharges")
    private int ExtraCharges;
    @SerializedName("Discount")
    private int Discount;
    @SerializedName("DiscountAmount")
    private int DiscountAmount;//doubt
    @SerializedName("TotalAmount")
    private int TotalAmount;
    @SerializedName("paymentList")
    private ArrayList<BookingPayment> paymentList;

    public int getBookingId() {
        return BookingId;
    }

    public void setBookingId(int bookingId) {
        BookingId = bookingId;
    }

    public String getBookingNumber() {
        return BookingNumber;
    }

    public void setBookingNumber(String bookingNumber) {
        BookingNumber = bookingNumber;
    }

    public String getCouponCode() {
        return CouponCode;
    }

    public void setCouponCode(String couponCode) {
        CouponCode = couponCode;
    }

    public int getTravellerId() {
        return TravellerId;
    }

    public void setTravellerId(int travellerId) {
        TravellerId = travellerId;
    }

    public ArrayList<Travellers> getTravellerList() {
        return travellerList;
    }

    public void setTravellerList(ArrayList<Travellers> travellerList) {
        this.travellerList = travellerList;
    }

    public String getBookingDate() {
        return BookingDate;
    }

    public void setBookingDate(String bookingDate) {
        BookingDate = bookingDate;
    }

    public String getActivityDate() {
        return ActivityDate;
    }

    public void setActivityDate(String activityDate) {
        ActivityDate = activityDate;
    }

    public String getBookingTimeSlot() {
        return BookingTimeSlot;
    }

    public void setBookingTimeSlot(String bookingTimeSlot) {
        BookingTimeSlot = bookingTimeSlot;
    }

    public String getOptCheckInDate() {
        return OptCheckInDate;
    }

    public void setOptCheckInDate(String optCheckInDate) {
        OptCheckInDate = optCheckInDate;
    }

    public String getCheckInDate() {
        return CheckInDate;
    }

    public void setCheckInDate(String checkInDate) {
        CheckInDate = checkInDate;
    }

    public int getNoOfAdults() {
        return NoOfAdults;
    }

    public void setNoOfAdults(int noOfAdults) {
        NoOfAdults = noOfAdults;
    }

    public UserProfile getProfiles() {
        return profiles;
    }

    public void setProfiles(UserProfile profiles) {
        this.profiles = profiles;
    }

    public int getProfileId() {
        return ProfileId;
    }

    public void setProfileId(int profileId) {
        ProfileId = profileId;
    }

    public ActivityModel getActivities() {
        return activities;
    }

    public void setActivities(ActivityModel activities) {
        this.activities = activities;
    }

    public int getDeclaredRate() {
        return DeclaredRate;
    }

    public void setDeclaredRate(int declaredRate) {
        DeclaredRate = declaredRate;
    }

    public int getSellRate() {
        return SellRate;
    }

    public void setSellRate(int sellRate) {
        SellRate = sellRate;
    }

    public int getExtraCharges() {
        return ExtraCharges;
    }

    public void setExtraCharges(int extraCharges) {
        ExtraCharges = extraCharges;
    }

    public int getDiscount() {
        return Discount;
    }

    public void setDiscount(int discount) {
        Discount = discount;
    }

    public int getDiscountAmount() {
        return DiscountAmount;
    }

    public void setDiscountAmount(int discountAmount) {
        DiscountAmount = discountAmount;
    }

    public int getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        TotalAmount = totalAmount;
    }

    public ArrayList<BookingPayment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(ArrayList<BookingPayment> paymentList) {
        this.paymentList = paymentList;
    }
}
