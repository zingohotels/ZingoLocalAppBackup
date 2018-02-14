package localapp.zingohotels.com.localapp.WebApiClients;

import java.util.ArrayList;

import localapp.zingohotels.com.localapp.Model.Maps;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by CSC on 2/2/2018.
 */

public interface MapApi {

    @POST("Maps")
    Call<Maps> addMapDetails(@Body Maps maps);

    @GET("Maps/GetMapByActivityId/{ActivityId}")
    Call<ArrayList<Maps>> getMapDetails(@Path("ActivityId") int HotelId);

}
