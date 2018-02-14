package localapp.zingohotels.com.localapp.WebApiClients;

import java.util.ArrayList;

import localapp.zingohotels.com.localapp.Model.Interests;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ZingoHotels.com on 2/2/2018.
 */

public interface InterestApi {

    @GET("interest")
    Call<ArrayList<Interests>> getInterests();
}
