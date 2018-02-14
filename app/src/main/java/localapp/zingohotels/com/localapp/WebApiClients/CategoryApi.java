package localapp.zingohotels.com.localapp.WebApiClients;

import java.util.ArrayList;

import localapp.zingohotels.com.localapp.Model.Category;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ZingoHotels.com on 1/30/2018.
 */

public interface CategoryApi {

    @GET("Categories")
    Call<ArrayList<Category>> getCategories();
}
