package localapp.zingohotels.com.localapp.WebApiClients;

import java.util.ArrayList;

import localapp.zingohotels.com.localapp.Model.Profile;
import localapp.zingohotels.com.localapp.Model.UserProfile;
import localapp.zingohotels.com.localapp.Util.API;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by CSC on 1/26/2018.
 */

public interface ProfileApi {

    @POST(API.PROFILES)
    Call<Profile> signUpProfile(@Body Profile body /*@Field("userid") String userid, @Field("password") String password, @Field("imei_no") String imei_no*/);

    @POST(API.PROFILES+"/GetProfileByNameAndEmail")
    Call<Profile> signUpByNameEmail(@Body Profile body /*@Field("userid") String userid, @Field("password") String password, @Field("imei_no") String imei_no*/);

    @POST(API.PROFILES+"/GetProfileByPhone")
    Call<Profile> signUpByPhone(@Body Profile body /*@Field("userid") String userid, @Field("password") String password, @Field("imei_no") String imei_no*/);

    @POST(API.PROFILES+"/GetProfileByEmailAndPassword")
    Call<ArrayList<UserProfile>> signUpByEmailPwd(@Body UserProfile body /*@Field("userid") String userid, @Field("password") String password, @Field("imei_no") String imei_no*/);

    @POST(API.PROFILES)
    Call<ArrayList<Profile>> getProfile(@Body Profile body);

    @POST(API.GET_PROFILE_BY_PHONE)
    Call<String> getProfileByPhone(@Body Profile body);


    @GET("Profiles/{id}")
    Call<UserProfile> getProfileById(@Path("id") int id);
    //Profiles/{id}

    @PUT("Profiles/{id}")
    Call<String> updateProfileById(@Path("id") int id,@Body UserProfile userProfile);

}
