package localapp.zingohotels.com.localapp.WebApiClients;

import localapp.zingohotels.com.localapp.Model.BookingPayment;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by ZingoHotels.com on 2/16/2018.
 */

public interface BookingPaymentApi {

    @POST("BookingPayments")
    Call<BookingPayment> postBookingPayment(@Body BookingPayment bookingPayment);
}
