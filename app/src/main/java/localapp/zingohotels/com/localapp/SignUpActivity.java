package localapp.zingohotels.com.localapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import localapp.zingohotels.com.localapp.Login.IRegistrasionService;
import localapp.zingohotels.com.localapp.Login.LoginActivity;
import localapp.zingohotels.com.localapp.Model.Profile1;
import localapp.zingohotels.com.localapp.Model.UserProfile;
import localapp.zingohotels.com.localapp.Model.UserRole;
import localapp.zingohotels.com.localapp.Util.Constants;
import localapp.zingohotels.com.localapp.Util.PreferenceHandler;
import localapp.zingohotels.com.localapp.Util.ThreadExecuter;
import localapp.zingohotels.com.localapp.Util.Util;
import localapp.zingohotels.com.localapp.WebApiClients.SignUpApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    EditText mUserName,mPhoneNumber,mEmail,mPassword,mConfirmPassword;
    TextView mSignUp;
    RadioButton mMr,mMrs,mMs,mMale,mFemale,mOthers;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN|
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        mMr = (RadioButton)findViewById(R.id.sign_up_mr);
        mMrs = (RadioButton)findViewById(R.id.sign_up_mrs);
        mMs = (RadioButton)findViewById(R.id.sign_up_ms);
        mMale = (RadioButton)findViewById(R.id.sign_up_male);
        mFemale = (RadioButton)findViewById(R.id.sign_up_female);
        mOthers = (RadioButton)findViewById(R.id.sign_up_other);

        mUserName = (EditText)findViewById(R.id.sign_up_user_name);
        mPhoneNumber = (EditText)findViewById(R.id.sign_up_user_phone_number);
        mEmail = (EditText)findViewById(R.id.sign_up_user_email);
        mPassword = (EditText)findViewById(R.id.sign_up_user_password);
        mConfirmPassword = (EditText)findViewById(R.id.sign_up_user_confirm_password);

        mSignUp = (TextView) findViewById(R.id.sing_up);

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Util.hideSoftKeyboard(v,SignUpActivity.this);
                validateFields();
            }
        });

       /* mMr.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mMr.isChecked())
                {
                    System.out.println("checked");
                }

            }
        });*/
    }

    private void validateFields() {

        /*if(mMr.isChecked())
        {
            System.out.println("checked");
        }*/
        String fullname = mUserName.getText().toString();
        String phonenumber = mPhoneNumber.getText().toString();
        String email = mEmail.getText().toString();
        String password = mPassword.getText().toString();
        String cnfpassword = mConfirmPassword.getText().toString();

         if(!mMr.isChecked() && !mMrs.isChecked() && !mMs.isChecked())
        {
            Toast.makeText(SignUpActivity.this,"Please Select Your Prefix",Toast.LENGTH_SHORT).show();
        }

        else if(fullname == null || fullname.isEmpty())
        {
            mUserName.setError("Should Not Be Empty");
            mUserName.requestFocus();
        }
         else if(!mMale.isChecked() && !mFemale.isChecked() && !mOthers.isChecked())
         {
             Toast.makeText(SignUpActivity.this,"Please Select Your Gender",Toast.LENGTH_SHORT).show();
         }
        else if(phonenumber == null || phonenumber.isEmpty())
        {
            mPhoneNumber.setError("Should Not Be Empty");
            mPhoneNumber.requestFocus();
        }
        else if(phonenumber.length()  != 10)
         {
             mPhoneNumber.setError(getResources().getString(R.string.phone_number_error));
             mPhoneNumber.requestFocus();
         }
        else if(email == null || email.isEmpty())
        {
            mEmail.setError("Should Not Be Empty");
            mEmail.requestFocus();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            mEmail.setError("Enter Valid Email");
            mEmail.requestFocus();
        }
        else if(password == null || password.isEmpty())
        {
            mPassword.setError("Should Not Be Empty");
            mPassword.requestFocus();
        }
        else if(cnfpassword == null || cnfpassword.isEmpty())
        {
            mConfirmPassword.setError("Should Not Be Empty");
            mConfirmPassword.requestFocus();
        }
        else if(!password.equals(cnfpassword))
        {
            mConfirmPassword.setError("Should Not Be Empty");
            mConfirmPassword.requestFocus();
        }
//
        else
        {
            UserProfile userProfile = new UserProfile();

            userProfile.setUserRoleId(1);
            userProfile.setFullName(fullname);
            userProfile.setEmail(email);
            userProfile.setPassword(password);
            userProfile.setPhoneNumber(phonenumber);
            //userProfile.setProfilePhoto("");
            if(mMr.isChecked())
            {
                userProfile.setPrefix(mMr.getText().toString());
            }
            else if(mMrs.isChecked())
            {
                userProfile.setPrefix(mMrs.getText().toString());
            }
            else if(mMs.isChecked())
            {
                userProfile.setPrefix(mMs.getText().toString());
            }

            if(mMale.isChecked())
            {
                userProfile.setGender(mMale.getText().toString());
            }
            else if(mFemale.isChecked())
            {
                userProfile.setGender(mFemale.getText().toString());
            }
            else if(mOthers.isChecked())
            {
                userProfile.setGender(mOthers.getText().toString());
            }

            checkUserByPhoneNumber(userProfile);
            /*userProfile.setPrefix("Mr");
            userProfile.setGender("Male");*/

            //signUpUser(userProfile);
            //System.out.println(userProfile.getPrefix()+","+userProfile.getGender());

        /*    if(mMr.isChecked())
        {
            //userProfile.setPrefix(mMr.getText().toString());
            System.out.println("Mr checked");
        }
        else if(mMrs.isChecked())
        {
            //userProfile.setPrefix(mMrs.getText().toString());
            System.out.println("Mrs checked");
        }
        else if(mMs.isChecked())
        {
            //userProfile.setPrefix(mMs.getText().toString());
            System.out.println("Ms checked");
        }
            if(mMale.isChecked())
            {
                //userProfile.setGender(mMale.getText().toString());
                System.out.println("mMale checked");
            }
            else if(mFemale.isChecked())
            {
                //userProfile.setGender(mFemale.getText().toString());
                System.out.println("mFemale checked");
            }
            else if(mOthers.isChecked())
            {
                //userProfile.setGender(mOthers.getText().toString());
                System.out.println("mOthers checked");
            }*/

        }
    }

    private void signUpUser(final UserProfile userProfile, final ProgressDialog dialog){

        /*progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();*/


        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {


                SignUpApi apiService =
                        Util.getClient().create(SignUpApi.class);


                Call<UserProfile> call = apiService.signupUser(userProfile);

                call.enqueue(new Callback<UserProfile>() {
                    @Override
                    public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
//                List<RouteDTO.Routes> list = new ArrayList<RouteDTO.Routes>();
                        int statusCode = response.code();
                        if (dialog != null)
                            dialog.dismiss();
                            if(statusCode == 200 || statusCode == 204 || statusCode == 201)
                            {
                                if(response.body() != null)// && response.body().size() != 0)
                                {
                                    UserProfile userProfile1 = response.body();//.get(0);
                                    PreferenceHandler.getInstance(SignUpActivity.this).setUserId(userProfile1.getProfileId());
                                    PreferenceHandler.getInstance(SignUpActivity.this).setUserFullName(userProfile1.getFullName());
                                    PreferenceHandler.getInstance(SignUpActivity.this).setUserEmail(userProfile1.getEmail());
                                    PreferenceHandler.getInstance(SignUpActivity.this).setUserPhone(userProfile1.getPhoneNumber());
                                    Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                                    /*intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);*/
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                                else
                                    {
                                        Toast.makeText(SignUpActivity.this,response.message(),Toast.LENGTH_SHORT).show();
                                    }
                            }
                            else
                            {
                                /*UserProfile userProfile1 = response.body();//.get(0);
                                Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);*/
                                Toast.makeText(SignUpActivity.this,response.message(),Toast.LENGTH_SHORT).show();
                            }
//                callGetStartEnd();
                    }

                    @Override
                    public void onFailure(Call<UserProfile> call, Throwable t) {
                        // Log error here since request failed
                        if (dialog!=null)
                            dialog.dismiss();
                        Log.e("TAG", t.toString());
                    }
                });
            }
        });
    }

    private void checkUserByPhoneNumber(final UserProfile userProfile){

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();


        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {


                SignUpApi apiService =
                        Util.getClient().create(SignUpApi.class);

//"Profile Exist"
                Call<String> call = apiService.getUserByPhone(userProfile);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
//                List<RouteDTO.Routes> list = new ArrayList<RouteDTO.Routes>();
                        int statusCode = response.code();

                        if(statusCode == 200 || statusCode == 204)
                        {
                            if(response.body() != null && response.body().equals("Profile Exist") )
                            {
                                //UserProfile userProfile1 = response.body().get(0);
                                /*Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);*/
                                if (progressDialog != null)
                                    progressDialog.dismiss();
                                Toast.makeText(SignUpActivity.this,"Phone Number Already Exist",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                //Toast.makeText(SignUpActivity.this,response.message(),Toast.LENGTH_SHORT).show();
                                checkUserByEmailId(userProfile,progressDialog);
                            }
                        }
                        else
                        {
                            if (progressDialog != null)
                                progressDialog.dismiss();
                            Toast.makeText(SignUpActivity.this,response.message(),Toast.LENGTH_SHORT).show();
                        }
//                callGetStartEnd();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        // Log error here since request failed
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        Log.e("TAG", t.toString());
                    }
                });
            }
        });
    }


    private void checkUserByEmailId(final UserProfile userProfile, final ProgressDialog progressDialog1){

        /*progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();*/


        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {


                SignUpApi apiService =
                        Util.getClient().create(SignUpApi.class);

//"Profile Exist"
                Call<String> call = apiService.getUserByEmail(userProfile);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
//                List<RouteDTO.Routes> list = new ArrayList<RouteDTO.Routes>();
                        int statusCode = response.code();

                        if(statusCode == 200 || statusCode == 204)
                        {
                            if(response.body() != null && response.body().equals("Profile Exist") )
                            {
                                //UserProfile userProfile1 = response.body().get(0);
                                /*Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);*/
                                if (progressDialog != null)
                                    progressDialog.dismiss();
                                Toast.makeText(SignUpActivity.this,"Email Id Already Exist",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                /*Toast.makeText(SignUpActivity.this,response.message(),Toast.LENGTH_SHORT).show();*/
                                signUpUser(userProfile,progressDialog1);
                            }
                        }
                        else
                        {
                            if (progressDialog1!=null)
                                progressDialog1.dismiss();
                            Toast.makeText(SignUpActivity.this,response.message(),Toast.LENGTH_SHORT).show();
                        }
//                callGetStartEnd();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        // Log error here since request failed
                        if (progressDialog1!=null)
                            progressDialog1.dismiss();
                        Log.e("TAG", t.toString());
                    }
                });
            }
        });
    }
}
