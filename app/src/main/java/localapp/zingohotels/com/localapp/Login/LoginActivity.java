package localapp.zingohotels.com.localapp.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.dd.processbutton.FlatButton;

import java.util.ArrayList;

/*import app.zingo.com.hotelmanagement.MainActivity;
import app.zingo.com.hotelmanagement.Model.Profile1;
import app.zingo.com.hotelmanagement.Model.UserRole;
import app.zingo.com.hotelmanagement.R;
import app.zingo.com.hotelmanagement.Util.Constants;
import app.zingo.com.hotelmanagement.Util.PreferenceHandler;
import app.zingo.com.hotelmanagement.Util.ThreadExecuter;
import app.zingo.com.hotelmanagement.Util.Util;*/
import localapp.zingohotels.com.localapp.MainActivity;
import localapp.zingohotels.com.localapp.Model.Profile1;
import localapp.zingohotels.com.localapp.Model.UserRole;
import localapp.zingohotels.com.localapp.R;
import localapp.zingohotels.com.localapp.Util.Constants;
import localapp.zingohotels.com.localapp.Util.PreferenceHandler;
import localapp.zingohotels.com.localapp.Util.ThreadExecuter;
import localapp.zingohotels.com.localapp.Util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private EditText input_num_signup,input_pass_signup;
    private FlatButton ok_signup;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN|
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Login");

        /*input_num_signup = (EditText) findViewById(R.id.input_num_signup);
        input_pass_signup = (EditText) findViewById(R.id.input_pass_signup);
        ok_signup = (FlatButton) findViewById(R.id.ok_signup);*/

    }

    @Override
    protected void onResume() {
        super.onResume();
        ok_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(LoginWithActivity.this, EnterProfileActivity.class);
//                startActivity(i);
//                finish();
                if (!input_num_signup.getText().toString().trim().isEmpty() &&
                        !input_pass_signup.getText().toString().trim().isEmpty())
                    login(input_num_signup.getText().toString(),input_pass_signup.getText().toString());
                else Toast.makeText(LoginActivity.this, "Fields should not be empty", Toast.LENGTH_SHORT).show();

            }
        });
    }


    private void login( final String username, final String password){

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        final Profile1 p = new Profile1();
        p.setUserName(username);
        p.setPassword(password);

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {


                IRegistrasionService apiService =
                        Util.getClient().create(IRegistrasionService.class);


                Call<ArrayList<Profile1>> call = apiService.loginApiByUsernamePassword(p);

                call.enqueue(new Callback<ArrayList<Profile1>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Profile1>> call, Response<ArrayList<Profile1>> response) {
//                List<RouteDTO.Routes> list = new ArrayList<RouteDTO.Routes>();
                        int statusCode = response.code();
                        if (progressDialog != null)
                            progressDialog.dismiss();
                        if (statusCode == 200 || statusCode == 201) {

                            ArrayList<Profile1> dto1 = response.body();//-------------------should not be list------------
                            if (dto1!=null && dto1.size()!=0) {
                                Profile1 dto = dto1.get(0);


                                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                                SharedPreferences.Editor spe = sp.edit();
                                spe.putInt(Constants.USER_ID, dto.getProfileId());
                                PreferenceHandler.getInstance(LoginActivity.this).setUserId(dto.getProfileId());
                                PreferenceHandler.getInstance(LoginActivity.this).setUserFullName(dto.getFirstName());
                                System.out.println("User name=="+dto.getFirstName());
                                spe.putString("FirstName", dto.getFirstName());
                                spe.putString("MiddleName", dto.getMiddleName());
                                spe.putString("LastName", dto.getLastName());
                                spe.putString("UserName", dto.getUserName());
                                spe.putString("Password", dto.getPassword());
                                spe.putString("UniqueId", dto.getUniqueId());
                                spe.putInt("CityId", dto.getCityId());
                                spe.putString("PlaceName", dto.getPlaceName());
                                spe.putString("Email", dto.getEmail());
                                spe.putString("PhoneNumber", dto.getPhoneNumber());
                                spe.putString("Address", dto.getAddress());
                                spe.putString("PinCode", dto.getPinCode());
                                spe.putInt("UserRoleId", dto.getUserRoleId());
                                spe.apply();

                                //PreferenceHandler.getInstance(LoginActivity.this).setUserRoleUniqueID("Luci-FrontOffice");
//                            String status = dto.getAddress();
                                //getUserRole(dto.getUserRoleId());
                                UserRole userRole = dto.get_userRole();
                                /*if(userRole != null)
                                {
                                    *//*System.out.println("Unique id = "+userRole.getUserRoleUniqueId());*//*
                                    PreferenceHandler.getInstance(LoginActivity.this).setUserRoleUniqueID(userRole.getUserRoleUniqueId());
                                }*/

                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }else{
//                                startProfileActivity(isFb,fname,mname,lName,email,id);
                                /*if (progressDialog != null)
                                    progressDialog.dismiss();*/
                                Toast.makeText(LoginActivity.this, "Login credentials are wrong..", Toast.LENGTH_SHORT).show();

                            }
                        }else {
                            if (progressDialog!=null)
                                progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Login failed due to status code:"+statusCode, Toast.LENGTH_SHORT).show();
                        }
//                callGetStartEnd();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Profile1>> call, Throwable t) {
                        // Log error here since request failed
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        Log.e("TAG", t.toString());
                    }
                });
            }
        });
    }

    /*private void getUserRole(int userRoleId) {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();


        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {


                UserRoleApi apiService =
                        Util.getClient().create(UserRoleApi.class);


                Call<ArrayList<Profile1>> call = apiService.apiGetRolesByUniqueId(p);

                call.enqueue(new Callback<ArrayList<Profile1>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Profile1>> call, Response<ArrayList<Profile1>> response) {
//                List<RouteDTO.Routes> list = new ArrayList<RouteDTO.Routes>();
                        int statusCode = response.code();
                        if (progressDialog != null)
                            progressDialog.dismiss();
                        if (statusCode == 200 || statusCode == 201) {



                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }else{
//                                startProfileActivity(isFb,fname,mname,lName,email,id);
                                *//*if (progressDialog != null)
                                    progressDialog.dismiss();*//*
                                Toast.makeText(LoginActivity.this, "Login credentials are wrong..", Toast.LENGTH_SHORT).show();

                            }
                        }else {
                            if (progressDialog!=null)
                                progressDialog.dismiss();
                            Toast.makeText(LoginActivity.this, "Login failed due to status code:"+statusCode, Toast.LENGTH_SHORT).show();
                        }
//                callGetStartEnd();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Profile1>> call, Throwable t) {
                        // Log error here since request failed
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        Log.e("TAG", t.toString());
                    }
                });
            }
        });
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id)
        {
            case android.R.id.home:
                this.finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
