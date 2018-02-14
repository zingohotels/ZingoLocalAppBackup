package localapp.zingohotels.com.localapp.WebApiClients;

import java.util.ArrayList;

import localapp.zingohotels.com.localapp.Model.PackageDetails;
import localapp.zingohotels.com.localapp.Util.API;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by CSC on 2/6/2018.
 */

public interface PackageApi {

    @POST(API.Packages)
    Call<PackageDetails> postPack(@Body PackageDetails body);

    @GET(API.Packages)
    Call<ArrayList<PackageDetails>> getPack();

    /*@GET(API.Packages+"/GetPackagesByActivityId/{ActivityId}")
    Call<ArrayList<PackageDetails>> getPackByActId(@Path("ActivityId") int id);*/


}

