package localapp.zingohotels.com.localapp.WebApiClients;

import localapp.zingohotels.com.localapp.Model.Availablity;
import localapp.zingohotels.com.localapp.Model.Bookings;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by ZingoHotels.com on 2/16/2018.
 */

public interface BookingApi {

    @POST("Bookings")
    Call<Bookings> postBookings(@Body Bookings bookings);

    @POST("Bookings/PostActivityAvaiableSlotForTheDate")
    Call<Integer> getAvailablity(@Body Availablity availablity);



    @GET("Bookings/{id}")
    Call<Bookings> getBooking(@Path("id") int id);
}
