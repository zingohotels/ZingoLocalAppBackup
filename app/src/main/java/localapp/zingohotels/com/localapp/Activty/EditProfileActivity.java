package localapp.zingohotels.com.localapp.Activty;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;
import localapp.zingohotels.com.localapp.Model.UserProfile;
import localapp.zingohotels.com.localapp.ProfileActivity;
import localapp.zingohotels.com.localapp.R;
import localapp.zingohotels.com.localapp.Util.PreferenceHandler;
import localapp.zingohotels.com.localapp.Util.ThreadExecuter;
import localapp.zingohotels.com.localapp.Util.Util;
import localapp.zingohotels.com.localapp.WebApiClients.ProfileApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    EditText mFullName,mEmail,mMobileNumber;
    CircleImageView profileImage;
    TextView mEditProfileBtn;

    UserProfile profile;
    private static final int REQUEST_CAMERA = 1,REQUEST_GALLERY = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setTitle("Edit Profile");

        mFullName = (EditText) findViewById(R.id.change_full_name);
        mEmail = (EditText) findViewById(R.id.change_email);
        mMobileNumber = (EditText) findViewById(R.id.change_phone_number);
        mEditProfileBtn = (TextView) findViewById(R.id.edit_profile_btn);
        profileImage  = (CircleImageView) findViewById(R.id.edit_profile_image);

        Bundle bundle = getIntent().getExtras();

        /*if(bundle != null)
        {
            UserProfile userProfile = (UserProfile) bundle.getSerializable("Profile");
            if(userProfile != null)
            {
                mFullName.setText(userProfile.getFullName());
                mEmail.setText(userProfile.getEmail());
                mMobileNumber.setText(userProfile.getPhoneNumber());
            }

        }*/

        mEditProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields();
            }
        });
        if(PreferenceHandler.getInstance(EditProfileActivity.this).getUserId() != 0)
        {
            getProfile();
        }
        else
        {
            Toast.makeText(EditProfileActivity.this,"Please Create Profile",Toast.LENGTH_SHORT).show();
        }

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    public void selectImage()
    {
        final String[] imageSelectionArray = {"Gallery","Take Photo","Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfileActivity.this);
        builder.setTitle("Select Photo");
        builder.setCancelable(false);
        builder.setItems(imageSelectionArray, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(imageSelectionArray[which].equals("Gallery"))
                {
                    boolean result=Util.checkPermissionOfCamera(EditProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE
                            ,"This App Needs Storage Permission");
                    if(result)
                        gotoGallery();
                }
                else if(imageSelectionArray[which].equals("Take Photo"))
                {
                    boolean result=Util.checkPermissionOfCamera(EditProfileActivity.this,Manifest.permission.CAMERA,
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
                //userProfile.setProfilePhoto(Util.convertToBase64String(bitmap));
                //updatePhoto(userProfile);
                profileImage.setImageBitmap(bitmap);
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
                    //userProfile.setProfilePhoto(Util.convertToBase64String(bm));
                    //updatePhoto(userProfile);
                    profileImage.setImageBitmap(bm);
                }
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }

        }
    }
    private void validateFields() {

        String name = mFullName.getText().toString();
        String email = mEmail.getText().toString();
        String number = mMobileNumber.getText().toString();

        if(name == null || name.isEmpty())
        {
            mFullName.setError("Please Enter Name");
            mFullName.requestFocus();
        }
        else if(email == null || email.isEmpty())
        {
            mEmail.setError("Please Enter Valid Email");
            mEmail.requestFocus();
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            mEmail.setError("Please Enter Valid Email");
            mEmail.requestFocus();
        }
        else if(number == null || number.isEmpty())
        {
            mMobileNumber.setError("Please Enter Valid Mobile Number");
            mMobileNumber.requestFocus();
        }
        else if(number.length() != 10)
        {
            mMobileNumber.setError("Please Enter Valid Mobile Number");
            mMobileNumber.requestFocus();
        }
        else
        {
            UserProfile uprofile = new UserProfile();
            uprofile.setProfileId(profile.getProfileId());
            uprofile.setFullName(name);
            uprofile.setEmail(email);
            uprofile.setPhoneNumber(number);
            uprofile.setGender(profile.getGender());
            uprofile.setPrefix(profile.getPrefix());
            System.out.println();
            uprofile.setProfilePhoto(Util.convertToBase64String(((BitmapDrawable)(profileImage.getDrawable())).getBitmap()));
            updateProfile(uprofile);
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
                System.out.println(PreferenceHandler.getInstance(EditProfileActivity.this).getUserId());
                Call<UserProfile> getCat = categoryAPI.getProfileById(PreferenceHandler.getInstance(EditProfileActivity.this).getUserId());

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
                                profile = response.body();
                                System.out.println(response.body().getFullName());
                                loadData(response.body());
                            }
//                            loadRoomCategoriesSpinner();
                        }
                        else
                        {
                            System.out.println(response.message());
                            Toast.makeText(EditProfileActivity.this,response.message(),Toast.LENGTH_SHORT).show();
                        }
                    }


                    @Override
                    public void onFailure(Call<UserProfile> call, Throwable t) {
                        if(dialog != null)
                        {
                            dialog.dismiss();
                        }
                        Toast.makeText(EditProfileActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
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
                            Toast.makeText(EditProfileActivity.this,"Success",Toast.LENGTH_SHORT).show();
                            EditProfileActivity.this.finish();
                            //mUserProfileImage.setEnabled(false);
                        }
                        else
                        {
                            Toast.makeText(EditProfileActivity.this,response.message(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        if(dialog != null)
                        {
                            dialog.dismiss();
                        }
                        Toast.makeText(EditProfileActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    private void loadData(UserProfile userProfile) {

        mFullName.setText(userProfile.getFullName());
        mEmail.setText(userProfile.getEmail());
        mMobileNumber.setText(userProfile.getPhoneNumber());
        profileImage.setImageBitmap(Util.convertToBitMap(userProfile.getProfilePhoto()));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id)
        {
            case android.R.id.home:
                EditProfileActivity.this.finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
