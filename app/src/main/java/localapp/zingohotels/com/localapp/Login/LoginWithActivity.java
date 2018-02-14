package localapp.zingohotels.com.localapp.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.FlatButton;
/*import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.FacebookError;*/
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

/*import app.zingo.com.hotelmanagement.MainActivity;
import app.zingo.com.hotelmanagement.Model.Profile1;
import app.zingo.com.hotelmanagement.R;
import app.zingo.com.hotelmanagement.Util.Constants;
import app.zingo.com.hotelmanagement.Util.PreferenceHandler;
import app.zingo.com.hotelmanagement.Util.ThreadExecuter;
import app.zingo.com.hotelmanagement.Util.Util;*/
import localapp.zingohotels.com.localapp.MainActivity;
import localapp.zingohotels.com.localapp.Model.Profile1;
import localapp.zingohotels.com.localapp.R;
import localapp.zingohotels.com.localapp.SignUpActivity;
import localapp.zingohotels.com.localapp.Util.Constants;
import localapp.zingohotels.com.localapp.Util.PreferenceHandler;
import localapp.zingohotels.com.localapp.Util.ThreadExecuter;
import localapp.zingohotels.com.localapp.Util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginWithActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

//    private CallbackManager callbackManager;
    /*private AsyncFacebookRunner mAsyncRunner;
    private static String APP_ID = "155147378434426";
    private Facebook facebook = new Facebook(APP_ID);*/
    private SharedPreferences mPrefs;

    //google
    private GoogleApiClient mGoogleApiClient;
    private static final String TAG = "GPlusLogin";
    private int RC_SIGN_IN = 0;
    private ProgressDialog mProgressDialog;

    private ProgressDialog progressDialog;
    private TextView terms;
    private FlatButton create_account,continue_fb,continue_gmail,login_account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        logger.logPurchase(BigDecimal.valueOf(4.32), Currency.getInstance("USD"));

        setContentView(R.layout.activity_login_with);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
// options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(LoginWithActivity.this)
                .enableAutoManage(LoginWithActivity.this , LoginWithActivity.this )
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sign In");
        terms = (TextView) findViewById(R.id.terms);
        create_account = (FlatButton) findViewById(R.id.create_account);
      // continue_fb = (FlatButton) findViewById(R.id.continue_fb);
       // continue_gmail = (FlatButton) findViewById(R.id.continue_gmail);
        login_account = (FlatButton) findViewById(R.id.login_account);
        //mAsyncRunner = new AsyncFacebookRunner(facebook);
//        logoutFromFacebook();
        String text = "<font color='black'>By creating account, I agree to Zingo Hotels's </font><font color='red'>Terms and Conditions </font><font color='black'>and </font><font color='red'>Privacy Policy</font>";
        terms.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);

        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginWithActivity.this, SignUpActivity.class);
                startActivity(i);
                finish();
            }
        });
        login_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginWithActivity.this, LoginNew.class);
                startActivity(i);
                finish();
            }
        });

       /* continue_gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);

            }
        });
        continue_fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                LoginManager.getInstance().logInWithReadPermissions(LoginWithActivity.this, Arrays.asList("public_profile"*//*, "user_friends"*//*));
                loginToFacebook();
//
            }
        });*/



    }

    /**
     * Function to login into facebook
     * */
    /*public void loginToFacebook() {

        mPrefs = getPreferences(MODE_PRIVATE);
        String access_token = mPrefs.getString("access_token", null);
        long expires = mPrefs.getLong("access_expires", 0);

        if (access_token != null) {
            facebook.setAccessToken(access_token);
            Log.d("FB Sessions", "" + facebook.isSessionValid());
        }

        if (expires != 0) {
            facebook.setAccessExpires(expires);
        }

        if (!facebook.isSessionValid()) {
            facebook.authorize(this,
                    new String[] { "email","publish_actions" *//*"publish_stream"*//* },
                    new Facebook.DialogListener() {

                        @Override
                        public void onCancel() {
                            // Function to handle cancel event
                        }

                        @Override
                        public void onComplete(Bundle values) {
                            // Function to handle complete event
                            // Edit Preferences and update facebook acess_token
                            SharedPreferences.Editor editor = mPrefs.edit();
                            editor.putString("access_token",
                                    facebook.getAccessToken());
                            editor.putLong("access_expires",
                                    facebook.getAccessExpires());
                            editor.commit();

                            getProfileInformation();

                        }

                        @Override
                        public void onError(DialogError error) {
                            // Function to handle error

                        }

                        @Override
                        public void onFacebookError(FacebookError fberror) {
                            // Function to handle Facebook errors

                        }

                    });
        }
    }*/

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }else {
            facebook.authorizeCallback(requestCode, resultCode, data);

        }
//        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void showAccessTokens() {
        String access_token = facebook.getAccessToken();

        Toast.makeText(getApplicationContext(),
                "Access Token: " + access_token, Toast.LENGTH_LONG).show();
    }*/

    /**
     * Get Profile1 information by making request to Facebook Graph API
     * */
    /*public void getProfileInformation() {
        mAsyncRunner.request("me", new AsyncFacebookRunner.RequestListener() {
            @Override
            public void onComplete(String response, Object state) {
                Log.d("Profile", response);
                String json = response;
                try {
                    // Facebook Profile1 JSON data
                    JSONObject profile = new JSONObject(json);

                    // getting name of the user
                    final String name = profile.getString("name");
                    final String id = profile.getString("id");
                    String firstName = "";
                    String middleName = "";
                    String lastName = "";
                    if (name!=null && !name.isEmpty()){
                        String[] arrName = name.split(" ");
                        for (int i = 0; i<arrName.length; i++){
                            if (i==0)
                                firstName = arrName[i];
                            else if (i== arrName.length-1)
                                lastName = arrName[i];
                            else if (i==1)
                                middleName = arrName[i];
                        }
                    }

                    //check here from server if user is already registered or not ----------------------------------------------------------------------
                    // then try to login or register

                    final String firstName1 = firstName;
                    final String middleName1 = middleName;
                    final String lastName1 = lastName;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            login(true,firstName1,middleName1,lastName1,"",id);
                        }
                    });

//                    startProfileActivity(true,firstName,middleName,lastName,"",id);
//


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onIOException(IOException e, Object state) {
            }

            @Override
            public void onFileNotFoundException(FileNotFoundException e,
                                                Object state) {
            }

            @Override
            public void onMalformedURLException(MalformedURLException e,
                                                Object state) {
            }

            @Override
            public void onFacebookError(FacebookError e, Object state) {
            }
        });
    }*/

    /**
     * Function to Logout user from Facebook
     * */
    /*public void logoutFromFacebook() {
        mAsyncRunner.logout(this, new AsyncFacebookRunner.RequestListener() {
            @Override
            public void onComplete(String response, Object state) {
                Log.d("Logout from Facebook", response);
                if (Boolean.parseBoolean(response) == true) {


                }
            }

            @Override
            public void onIOException(IOException e, Object state) {
            }

            @Override
            public void onFileNotFoundException(FileNotFoundException e,
                                                Object state) {
            }

            @Override
            public void onMalformedURLException(MalformedURLException e,
                                                Object state) {
            }

            @Override
            public void onFacebookError(FacebookError e, Object state) {
            }
        });
    }*/

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    @Override
    public void onStart() {
        super.onStart();

        /*OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {

            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }*/
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading..");
            mProgressDialog.setIndeterminate(true);
        }

//        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }

    }



    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            String name = acct.getDisplayName();
            String fname = acct.getGivenName();
            String lNamme = acct.getFamilyName();
            String email = acct.getEmail();
            String id = acct.getId();
            String tocken = acct.getIdToken();

            //check here from server if user is already registered or not ----------------------------------------------------------------------
            // then try to login or register

            login(false,fname,"",lNamme,email,id);

//            startProfileActivity(false,fname,"",lNamme,email,id);

            if(acct.getPhotoUrl() != null){

            }
        }
    }

    private void startProfileActivity(boolean isFb,String fname, String mname,String lName,String email,String id){
        Intent in = new Intent(LoginWithActivity.this,EnterProfileActivity.class);
        in.putExtra("ISFB",isFb);
        in.putExtra("FIRST_NAME",fname);
        in.putExtra("FB_ID",id);
//        in.putExtra("G_ID",id);
        in.putExtra("MIDDLE_NAME",mname);
        in.putExtra("LAST_NAME",lName);
        in.putExtra("EMAIL",email);
        startActivity(in);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent in = new Intent(LoginWithActivity.this, LoginWithActivity.class);
//        startActivity(in);
    }

    private void login(final boolean isFb, final String fname, final String mname, final String lName, final String email, final String id){

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        final Profile1 p = new Profile1();
        p.setUniqueId(id);

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {


                IRegistrasionService apiService =
                        Util.getClient().create(IRegistrasionService.class);


                Call<ArrayList<Profile1>> call = apiService.loginApiBuUniqueId(p);

                call.enqueue(new Callback<ArrayList<Profile1>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Profile1>> call, Response<ArrayList<Profile1>> response) {
//                List<RouteDTO.Routes> list = new ArrayList<RouteDTO.Routes>();
                        int statusCode = response.code();
                        if (statusCode == 200 || statusCode == 201) {

                            ArrayList<Profile1> dto1 = response.body();//-------------------should not be list------------
                            if (dto1!=null && dto1.size()!=0) {
                                Profile1 dto = dto1.get(0);


                                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(LoginWithActivity.this);
                                SharedPreferences.Editor spe = sp.edit();
                                spe.putInt(Constants.USER_ID, dto.getProfileId());
                                PreferenceHandler.getInstance(LoginWithActivity.this).setUserId(dto.getProfileId());
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

//                            String status = dto.getAddress();
                                if (progressDialog != null)
                                    progressDialog.dismiss();
                                Toast.makeText(LoginWithActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(LoginWithActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }else{
                                startProfileActivity(isFb,fname,mname,lName,email,id);

                            }
                        }else {
                            if (progressDialog!=null)
                                progressDialog.dismiss();
                            Toast.makeText(LoginWithActivity.this, "Login failed due to status code:"+statusCode, Toast.LENGTH_SHORT).show();
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
