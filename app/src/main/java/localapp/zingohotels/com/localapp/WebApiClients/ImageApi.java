package localapp.zingohotels.com.localapp.WebApiClients;

import java.util.ArrayList;

import localapp.zingohotels.com.localapp.Model.ActivityImages;
import localapp.zingohotels.com.localapp.Util.API;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by CSC on 2/1/2018.
 */

public interface ImageApi {

    @POST(API.ActivityImages)
    Call<ActivityImages> postImage(@Body ActivityImages body);

    @GET(API.ActivityImages+"/GetActivityImagesByActivityId/{ActivityId}")
    Call<ActivityImages> getImageByActId(@Path("ActivityId") int id);
}
