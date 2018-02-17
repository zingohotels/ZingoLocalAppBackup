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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dd.processbutton.FlatButton;

import java.util.ArrayList;

import localapp.zingohotels.com.localapp.CustomViews.CustomFontTextView;
import localapp.zingohotels.com.localapp.MainActivity;
import localapp.zingohotels.com.localapp.Model.Profile1;
import localapp.zingohotels.com.localapp.Model.UserProfile;
import localapp.zingohotels.com.localapp.Model.UserRole;
import localapp.zingohotels.com.localapp.R;
import localapp.zingohotels.com.localapp.SignUpActivity;
import localapp.zingohotels.com.localapp.Util.Constants;
import localapp.zingohotels.com.localapp.Util.PreferenceHandler;
import localapp.zingohotels.com.localapp.Util.ThreadExecuter;
import localapp.zingohotels.com.localapp.Util.Util;
import localapp.zingohotels.com.localapp.WebApiClients.ProfileApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by CSC on 1/26/2018.
 */

public class LoginNew extends AppCompatActivity {

    private EditText login_email,login_pwd;
    private CustomFontTextView login,signup;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN|
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);*/
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN|
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

       /* getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("Login");*/

        login_email = (EditText) findViewById(R.id.login_emailid);
        login_pwd = (EditText) findViewById(R.id.login_password);
        login = (CustomFontTextView) findViewById(R.id.loginBtn);
        signup = (CustomFontTextView) findViewById(R.id.signBtn);

    }

    @Override
    protected void onResume() {
        super.onResume();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(LoginWithActivity.this, EnterProfileActivity.class);
//                startActivity(i);
//                finish();
                Util.hideSoftKeyboard(v,LoginNew.this);
                if (!login_email.getText().toString().trim().isEmpty() &&
                        !login_pwd.getText().toString().trim().isEmpty())
                    login(login_email.getText().toString(),login_pwd.getText().toString());
                else Toast.makeText(LoginNew.this, "Fields should not be empty", Toast.LENGTH_SHORT).show();

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent i = new Intent(LoginWithActivity.this, EnterProfileActivity.class);
//                startActivity(i);
//                finish();
                Intent i = new Intent(LoginNew.this, SignUpActivity.class);
                startActivity(i);
                finish();
            }
        });
    }


    private void login( final String username, final String password){

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {

                final UserProfile p = new UserProfile();
                System.out.println("User==="+username+" "+password);
                p.setEmail(username);
                p.setPassword(password);



                ProfileApi apiService =
                        Util.getClient().create(ProfileApi.class);
                System.out.println(p);


                Call<ArrayList<UserProfile>> call = apiService.signUpByEmailPwd(p);

                call.enqueue(new Callback<ArrayList<UserProfile>>() {
                    @Override
                    public void onResponse(Call<ArrayList<UserProfile>> call, Response<ArrayList<UserProfile>> response) {
//                List<RouteDTO.Routes> list = new ArrayList<RouteDTO.Routes>();
                        int statusCode = response.code();
                        if (progressDialog != null)
                            progressDialog.dismiss();
                        if (statusCode == 200 || statusCode == 201) {

                            ArrayList<UserProfile> dto1 = response.body();//-------------------should not be list------------
                            if (dto1!=null && dto1.size()!=0) {
                                UserProfile dto = dto1.get(0);

                                PreferenceHandler.getInstance(LoginNew.this).setUserId(dto.getProfileId());
                                PreferenceHandler.getInstance(LoginNew.this).setUserFullName(dto.getFullName());
                                PreferenceHandler.getInstance(LoginNew.this).setUserEmail(dto.getEmail());
                                PreferenceHandler.getInstance(LoginNew.this).setUserPhone(dto.getPhoneNumber());
                                PreferenceHandler.getInstance(LoginNew.this).setUserPrefix(dto.getPrefix());
                                PreferenceHandler.getInstance(LoginNew.this).setUserSex(dto.getGender());
                                /*SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(LoginNew.this);
                                SharedPreferences.Editor spe = sp.edit();
                                System.out.println("User id"+dto.getProfileId());
                                spe.putInt(Constants.USER_ID, dto.getProfileId());

                                spe.putString("Prefix",dto.getPrefix());
                                spe.putString("FullName",dto.getFullName());
                                spe.putString("Password", dto.getPassword());
                                spe.putString("Gender", dto.getGender());
                                spe.putString("Email", dto.getEmail());
                                spe.putString("PhoneNumber", dto.getPhoneNumber());
                                spe.putInt("UserRoleId", dto.getUserRoleId());
                                spe.putString("ProfilePhoto",dto.getProfilePhoto());
                                spe.apply();*/

                                //PreferenceHandler.getInstance(LoginNew.this).setUserRoleUniqueID("Luci-FrontOffice");
//                            String status = dto.getAddress();
                                //getUserRole(dto.getUserRoleId());
                                UserRole userRole = dto.getUserRoles();
                                /*if(userRole != null)
                                {
                                    *//*System.out.println("Unique id = "+userRole.getUserRoleUniqueId());*//*
                                    PreferenceHandler.getInstance(LoginNew.this).setUserRoleUniqueID(userRole.getUserRoleUniqueId());
                                }*/

                                Toast.makeText(LoginNew.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LoginNew.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }else{
//                                startProfileActivity(isFb,fname,mname,lName,email,id);
                                /*if (progressDialog != null)
                                    progressDialog.dismiss();*/
                                Toast.makeText(LoginNew.this, "Login credentials are wrong..", Toast.LENGTH_SHORT).show();

                            }
                        }else {
                            if (progressDialog!=null)
                                progressDialog.dismiss();
                            Toast.makeText(LoginNew.this, "Login failed due to status code:"+statusCode, Toast.LENGTH_SHORT).show();
                        }
//                callGetStartEnd();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<UserProfile>> call, Throwable t) {
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



                                Toast.makeText(LoginNew.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LoginNew.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }else{
//                                startProfileActivity(isFb,fname,mname,lName,email,id);
                                *//*if (progressDialog != null)
                                    progressDialog.dismiss();*//*
                                Toast.makeText(LoginNew.this, "Login credentials are wrong..", Toast.LENGTH_SHORT).show();

                            }
                        }else {
                            if (progressDialog!=null)
                                progressDialog.dismiss();
                            Toast.makeText(LoginNew.this, "Login failed due to status code:"+statusCode, Toast.LENGTH_SHORT).show();
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
