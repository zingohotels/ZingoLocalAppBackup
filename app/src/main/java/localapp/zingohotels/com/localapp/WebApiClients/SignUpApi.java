package localapp.zingohotels.com.localapp.WebApiClients;

import java.util.ArrayList;

import localapp.zingohotels.com.localapp.Model.City;
import localapp.zingohotels.com.localapp.Model.UserProfile;
import localapp.zingohotels.com.localapp.Util.API;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by ZingoHotels.com on 1/26/2018.
 */

public interface SignUpApi {

    @POST("Profiles")
    Call<UserProfile> signupUser(@Body UserProfile userProfile);



    @POST("Profiles/GetProfileByPhone")
    Call<String> getUserByPhone(@Body UserProfile userProfile);

    @POST("Profiles/GetProfileByEmail")
    Call<String> getUserByEmail(@Body UserProfile userProfile);
}
