package localapp.zingohotels.com.localapp.Activty;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import localapp.zingohotels.com.localapp.Model.UserProfile;
import localapp.zingohotels.com.localapp.ProfileActivity;
import localapp.zingohotels.com.localapp.R;
import localapp.zingohotels.com.localapp.Util.ThreadExecuter;
import localapp.zingohotels.com.localapp.Util.Util;
import localapp.zingohotels.com.localapp.WebApiClients.ProfileApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText mOldPassword,mNewPassword,mConfirmPassword;
    TextView mEditProfileBtn;


    String userProfilePassword;
    int userProfileid;
    UserProfile userProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setTitle("Change Password");

        mOldPassword = (EditText) findViewById(R.id.change_password_old_password);
        mNewPassword = (EditText) findViewById(R.id.change_password_new_password);
        mConfirmPassword = (EditText) findViewById(R.id.change_password_confirm_password);
        mEditProfileBtn = (TextView) findViewById(R.id.change_password_btn);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
            userProfileid = (int) bundle.getInt("Profile_id");
            getProfile(userProfileid);
            //userProfilePassword = (String) bundle.getString("Profile_Password");
            /*if(userProfile != null)
            {
                mFullName.setText(userProfile.getFullName());
                mEmail.setText(userProfile.getEmail());
                mMobileNumber.setText(userProfile.getPhoneNumber());
            }*/

        }

        mEditProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields();
            }
        });

    }

    private void validateFields() {

        String oldPassword = mOldPassword.getText().toString();
        String newPassword = mNewPassword.getText().toString();
        String cnewPassword = mConfirmPassword.getText().toString();

        if(oldPassword == null || oldPassword.isEmpty())
        {
            mOldPassword.setError("Please Enter Old Password");
            mOldPassword.requestFocus();
        }
        else if(newPassword == null || newPassword.isEmpty())
        {
            mNewPassword.setError("Please Enter New Password");
            mNewPassword.requestFocus();
        }
        else if(cnewPassword == null || cnewPassword.isEmpty())
        {
            mConfirmPassword.setError("Please Enter Confirm Password");
            mConfirmPassword.requestFocus();
        }
        else if(!newPassword.equals(cnewPassword))
        {
            mConfirmPassword.setError("Password and Confirm Password Should be same");
            mConfirmPassword.requestFocus();
        }
        else
        {
            UserProfile profile = userProfile;
            profile.setPassword(newPassword);
            updateProfile(profile);
        }
    }

    public void updateProfile(final UserProfile up)
    {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setTitle("Updating...");
        dialog.show();

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {

                ProfileApi profileApi = Util.getClient().create(ProfileApi.class);
                Call<String> res = profileApi.updateProfileById(up.getProfileId(),up);
                res.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(dialog != null)
                        {
                            dialog.dismiss();
                        }
                        if(response.code() == 204)
                        {
                            Toast.makeText(ChangePasswordActivity.this,"Success",Toast.LENGTH_SHORT).show();
                            ChangePasswordActivity.this.finish();
                            //mUserProfileImage.setEnabled(false);
                        }
                        else
                        {
                            Toast.makeText(ChangePasswordActivity.this,response.message(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        if(dialog != null)
                        {
                            dialog.dismiss();
                        }
                        Toast.makeText(ChangePasswordActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    public void getProfile(final int id)
    {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setTitle("Updating...");
        dialog.show();

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {

                ProfileApi profileApi = Util.getClient().create(ProfileApi.class);
                Call<UserProfile> res = profileApi.getProfileById(id);
                res.enqueue(new Callback<UserProfile>() {
                    @Override
                    public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                        if(dialog != null)
                        {
                            dialog.dismiss();
                        }
                        if(response.code() == 204||response.code() == 200)
                        {
                            //Toast.makeText(ChangePasswordActivity.this,"Success",Toast.LENGTH_SHORT).show();
                            //ChangePasswordActivity.this.finish();
                            //mUserProfileImage.setEnabled(false);
                            if(response.body() != null)
                            {
                                userProfile = response.body();
                            }
                        }
                        else
                        {
                            Toast.makeText(ChangePasswordActivity.this,response.message(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserProfile> call, Throwable t) {
                        if(dialog != null)
                        {
                            dialog.dismiss();
                        }
                        Toast.makeText(ChangePasswordActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id)
        {
            case android.R.id.home:
                ChangePasswordActivity.this.finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
