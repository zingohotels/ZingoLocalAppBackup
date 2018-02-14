package localapp.zingohotels.com.localapp.WebApiClients;

import java.util.ArrayList;

import localapp.zingohotels.com.localapp.Model.ActivityModel;
import localapp.zingohotels.com.localapp.Util.API;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by ZingoHotels.com on 2/2/2018.
 */

public interface ActivityApi {

    @GET("Activities")
    Call<ArrayList<ActivityModel>> getActivities();

    @GET(API.Activities+"/{id}")
    Call<ActivityModel> getActById(@Path("id") int id);



    @GET("GetActivitiesByCategoryId/{id}")
    Call<ArrayList<ActivityModel>> getActivityByCategoryId(@Path("id") int id);
}
