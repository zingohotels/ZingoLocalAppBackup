package localapp.zingohotels.com.localapp.Login;


import java.util.ArrayList;

/*import app.zingo.com.hotelmanagement.Model.BankDetails;
import app.zingo.com.hotelmanagement.Model.City;
import app.zingo.com.hotelmanagement.Model.Document1;
import app.zingo.com.hotelmanagement.Model.Documents;
import app.zingo.com.hotelmanagement.Model.HotelDetails;
import app.zingo.com.hotelmanagement.Model.HotelDocuments;
import app.zingo.com.hotelmanagement.Model.Maps;
import app.zingo.com.hotelmanagement.Model.Profile;
import app.zingo.com.hotelmanagement.Model.Profile1;
import app.zingo.com.hotelmanagement.Model.RoomCategories;
import app.zingo.com.hotelmanagement.Model.Rooms;
import app.zingo.com.hotelmanagement.Util.API;*/
import localapp.zingohotels.com.localapp.Model.City;
import localapp.zingohotels.com.localapp.Model.Profile;
import localapp.zingohotels.com.localapp.Model.Profile1;
import localapp.zingohotels.com.localapp.Util.API;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by CSC on 11/3/2017.
 */

public interface IRegistrasionService {
    @GET(API.CITIES)
    Call<ArrayList<City>> fetchCities();

    @GET("Profiles")
    Call<ArrayList<Profile>> fetchProfiles();



//    @FormUrlEncoded
//    @POST(API.FETCH_ALL_LOC_NEW)
//    Call<SenderDTO> fetchLocationNew(@Field("parent_reciver_id") String parent_reciver_id);

//    @FormUrlEncoded
    @POST(API.PROFILE)
    Call<Profile1> loginApi(@Body Profile1 body /*@Field("userid") String userid, @Field("password") String password, @Field("imei_no") String imei_no*/);

    @POST(API.GET_PROFILE_BY_UNIQUE_ID)
    Call<ArrayList<Profile1>> loginApiBuUniqueId(@Body Profile1 body /*@Field("userid") String userid, @Field("password") String password, @Field("imei_no") String imei_no*/);

    @POST(API.GET_PROFILE_BY_USER_NAME_AND_PASSWORD)
    Call<ArrayList<Profile1>> loginApiByUsernamePassword(@Body Profile1 body /*@Field("userid") String userid, @Field("password") String password, @Field("imei_no") String imei_no*/);


   /* @POST(API.DOCUMENTS_ADD)
    Call<ResponseBody> uploadDocuments(@Body Document1 body);*/

    @POST(API.GET_PROFILE_BY_EMAIL)
    Call<String> getProfileByEmail(@Body Profile1 body);



    @POST(API.GET_PROFILE_BY_PHONE)
    Call<String> getProfileByPhone(@Body Profile1 body);



    @GET(API.GET_PROFILE_BY_USER_NAME+"/{UserName}")
    Call<String> getProfileByUserName(@Path("UserName") String UserName);













}
