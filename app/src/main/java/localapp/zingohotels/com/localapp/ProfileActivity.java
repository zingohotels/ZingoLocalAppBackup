package localapp.zingohotels.com.localapp;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import localapp.zingohotels.com.localapp.Activty.ChangePasswordActivity;
import localapp.zingohotels.com.localapp.Activty.EditProfileActivity;
import localapp.zingohotels.com.localapp.Adapters.ViewPagerAdapter;
import localapp.zingohotels.com.localapp.Model.Category;
import localapp.zingohotels.com.localapp.Model.Profile;
import localapp.zingohotels.com.localapp.Model.UserProfile;
import localapp.zingohotels.com.localapp.Util.PreferenceHandler;
import localapp.zingohotels.com.localapp.Util.ThreadExecuter;
import localapp.zingohotels.com.localapp.Util.Util;
import localapp.zingohotels.com.localapp.WebApiClients.CategoryApi;
import localapp.zingohotels.com.localapp.WebApiClients.ProfileApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    TextView mChangePassword,mUserName,mEmail,mPhoneNumber,mShareApp;
    LinearLayout mEditProfile;
    CircleImageView mUserProfileImage;

    private static final int REQUEST_CAMERA = 1,REQUEST_GALLERY = 2;

    UserProfile userProfile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setTitle("Profile");

        mChangePassword = (TextView) findViewById(R.id.change_password);
        mUserName = (TextView) findViewById(R.id.user_profile_name);
        mEmail = (TextView) findViewById(R.id.email_id);
        mPhoneNumber = (TextView) findViewById(R.id.phone_number);
        mEditProfile = (LinearLayout) findViewById(R.id.edit_profile);
        //mShareApp = (TextView) findViewById(R.id.share_app);
        mUserProfileImage = (CircleImageView) findViewById(R.id.user_profile_photo);
        //mShareApp.setVisibility(View.GONE);

        mChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changePasswordIntent = new Intent(ProfileActivity.this, ChangePasswordActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("Profile_id",userProfile.getProfileId());
                bundle.putString("Profile_Password",userProfile.getPassword());
                changePasswordIntent.putExtras(bundle);
                startActivity(changePasswordIntent);
            }
        });

        mEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editProfileIntent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                /*Bundle bundle = new Bundle();
                bundle.putSerializable("Profile",userProfile);
                editProfileIntent.putExtras(bundle);*/
                startActivity(editProfileIntent);

            }
        });

        mUserProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if(userProfile.getProfilePhoto() == null || userProfile.getProfilePhoto().isEmpty())
                {

                }
                else
                {
                    //selectImage();
                }*/
                selectImage();
            }
        });
        /*mShareApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                *//*Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Get rewards by sharing Zingo Local App");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);*//*

            }
        });*/
        if(PreferenceHandler.getInstance(ProfileActivity.this).getUserId() != 0)
        {
            getProfile();
        }
        else
        {
            Toast.makeText(ProfileActivity.this,"Please Create Profile",Toast.LENGTH_SHORT).show();
        }

    }

    public void getProfile()
    {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setTitle("Loading...");
        dialog.show();

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {
                ProfileApi categoryAPI = Util.getClient().create(ProfileApi.class);
                System.out.println(PreferenceHandler.getInstance(ProfileActivity.this).getUserId());
                Call<UserProfile> getCat = categoryAPI.getProfileById(PreferenceHandler.getInstance(ProfileActivity.this).getUserId());
                System.out.println(PreferenceHandler.getInstance(ProfileActivity.this).getUserId());

                getCat.enqueue(new Callback<UserProfile>() {
                    //@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    /*@SuppressLint("NewApi")*/
                    @Override
                    public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                        if(dialog != null)
                        {
                            dialog.dismiss();
                        }
                        if(response.code() == 200)
                        {

                            if(response.body() != null)
                            {
                                System.out.println(response.body().getFullName());
                                loadData(response.body());
                            }
//                            loadRoomCategoriesSpinner();
                        }
                        else
                        {
                            System.out.println(response.message());
                            Toast.makeText(ProfileActivity.this,response.message(),Toast.LENGTH_SHORT).show();
                        }
                    }


                    @Override
                    public void onFailure(Call<UserProfile> call, Throwable t) {
                        if(dialog != null)
                        {
                            dialog.dismiss();
                        }
                        System.out.println(t.getMessage());
                        Toast.makeText(ProfileActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }

    private void loadData(UserProfile body) {
        userProfile = body;
        mUserName.setText(body.getFullName());
        mEmail.setText(body.getEmail());
        mPhoneNumber.setText(body.getPhoneNumber());
        if(body.getProfilePhoto() != null && !body.getProfilePhoto().isEmpty())
        {
            mUserProfileImage.setImageBitmap(Util.convertToBitMap(body.getProfilePhoto()));
            mUserProfileImage.setEnabled(false);
        }
    }

    public void selectImage()
    {
        final String[] imageSelectionArray = {"Gallery","Take Photo","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
        builder.setTitle("Select Photo");
        builder.setCancelable(false);
        builder.setItems(imageSelectionArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(imageSelectionArray[which].equals("Gallery"))
                {
                    boolean result=Util.checkPermissionOfCamera(ProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE
                            ,"This App Needs Storage Permission");
                    if(result)
                        gotoGallery();
                }
                else if(imageSelectionArray[which].equals("Take Photo"))
                {
                    boolean result=Util.checkPermissionOfCamera(ProfileActivity.this,Manifest.permission.CAMERA,
                            "This Application Needs Camera Permission");
                    if(result)
                        gotoCamera();
                }
                else
                {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();

    }

    private void gotoCamera() {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent,REQUEST_CAMERA);
    }

    private void gotoGallery() {
        Intent intent = new Intent();
        intent.setType("imae/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"SELECT_FILE"),REQUEST_GALLERY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK)
        {
            if(requestCode == REQUEST_CAMERA)
            {
                onImageCaptureResult(data);
            }
            else if(requestCode == REQUEST_GALLERY)
            {
                onSelectImageFromGalleryResult(data);
            }
        }
    }

    private void onImageCaptureResult(Intent data) {
        if(data != null)
        {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,90,byteArrayOutputStream);
            if(bitmap != null)
            {
                userProfile.setProfilePhoto(Util.convertToBase64String(bitmap));
                updatePhoto(userProfile);
                mUserProfileImage.setImageBitmap(bitmap);
            }
        }
    }

    private void onSelectImageFromGalleryResult(Intent data) {

        if(data != null)
        {
            Bitmap bm = null;
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(),data.getData());
                if(bm != null)
                {
                    userProfile.setProfilePhoto(Util.convertToBase64String(bm));
                    updatePhoto(userProfile);
                    mUserProfileImage.setImageBitmap(bm);
                }
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }

        }
    }

    private void updatePhoto(UserProfile up) {

        ProfileApi profileApi = Util.getClient().create(ProfileApi.class);
        Call<String> res = profileApi.updateProfileById(up.getProfileId(),up);
        res.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code() == 204)
                {
                    Toast.makeText(ProfileActivity.this,"Image Updated",Toast.LENGTH_SHORT).show();
                    mUserProfileImage.setEnabled(false);
                }
                else
                {
                    Toast.makeText(ProfileActivity.this,response.message(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(ProfileActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id)
        {
            case android.R.id.home:
                ProfileActivity.this.finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
