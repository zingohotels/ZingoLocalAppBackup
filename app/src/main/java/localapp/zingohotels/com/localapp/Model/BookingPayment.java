package localapp.zingohotels.com.localapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ZingoHotels.com on 1/30/2018.
 */

public class BookingPayment implements Serializable {

    @SerializedName("PaymentId")
    private int PaymentId;
    @SerializedName("PaymentName")
    private String PaymentName;
    @SerializedName("Amount")
    private int Amount;
    @SerializedName("PaymentType")
    private String PaymentType;
    @SerializedName("bookings")
    private Bookings bookings;
    @SerializedName("BookingId")
    private int BookingId;

    public int getPaymentId() {
        return PaymentId;
    }

    public void setPaymentId(int paymentId) {
        PaymentId = paymentId;
    }

    public String getPaymentName() {
        return PaymentName;
    }

    public void setPaymentName(String paymentName) {
        PaymentName = paymentName;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public String getPaymentType() {
        return PaymentType;
    }

    public void setPaymentType(String paymentType) {
        PaymentType = paymentType;
    }

    public Bookings getBookings() {
        return bookings;
    }

    public void setBookings(Bookings bookings) {
        this.bookings = bookings;
    }

    public int getBookingId() {
        return BookingId;
    }

    public void setBookingId(int bookingId) {
        BookingId = bookingId;
    }
}
