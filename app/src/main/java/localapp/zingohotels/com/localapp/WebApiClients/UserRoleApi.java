package localapp.zingohotels.com.localapp.WebApiClients;



import java.util.ArrayList;

/*import app.zingo.com.hotelmanagement.Model.UserRole;
import app.zingo.com.hotelmanagement.Util.API;*/
import localapp.zingohotels.com.localapp.Model.UserRole;
import localapp.zingohotels.com.localapp.Util.API;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by ZingoHotels.com on 08-12-2017.
 */

public interface UserRoleApi {

    @PUT(API.USER_ROLES+"/UserRoles/{id}")
    Call<UserRole> apiUpdateRole(@Path("id") int id, @Body UserRole userRole);

    @POST(API.USER_ROLES+"/AddUserRole")
    Call<UserRole> apiAddRole(@Body UserRole userRole);

    @GET(API.USER_ROLES+"/GetUserRoleByUniqueId/{UserRoleUniqueId}")
    Call<UserRole> apiGetRolesByUniqueId(@Path("UserRoleUniqueId") String uniqueId);

    @GET(API.USER_ROLES)
    Call<ArrayList<UserRole>> apiGetRoles();

    @GET(API.USER_ROLES+"/{id}")
    Call<UserRole> apiGetRolesById(@Path("id") int id);

    @DELETE(API.USER_ROLES+"/DeleteUserRole/{id}")
    Call<UserRole> apiDeleteUserRole(@Path("id") int id);
}
