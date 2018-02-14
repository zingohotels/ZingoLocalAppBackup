package localapp.zingohotels.com.localapp.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.FlatButton;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.util.ArrayList;
import java.util.List;

/*import app.zingo.com.hotelmanagement.MainActivity;
import app.zingo.com.hotelmanagement.Model.City;
import app.zingo.com.hotelmanagement.Model.Profile1;
import app.zingo.com.hotelmanagement.Model.UserRole;
import app.zingo.com.hotelmanagement.R;
import app.zingo.com.hotelmanagement.Util.Constants;
import app.zingo.com.hotelmanagement.Util.PreferenceHandler;
import app.zingo.com.hotelmanagement.Util.ThreadExecuter;
import app.zingo.com.hotelmanagement.Util.Util;
import app.zingo.com.hotelmanagement.WebApi.UserRoleApi;*/
import localapp.zingohotels.com.localapp.MainActivity;
import localapp.zingohotels.com.localapp.Model.City;
import localapp.zingohotels.com.localapp.Model.Profile1;
import localapp.zingohotels.com.localapp.Model.UserRole;
import localapp.zingohotels.com.localapp.R;
import localapp.zingohotels.com.localapp.Util.Constants;
import localapp.zingohotels.com.localapp.Util.PreferenceHandler;
//import okhttp3.internal.Util;
import localapp.zingohotels.com.localapp.Util.ThreadExecuter;
import localapp.zingohotels.com.localapp.Util.Util;
import localapp.zingohotels.com.localapp.WebApiClients.UserRoleApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnterProfileActivity extends AppCompatActivity {

    private EditText first_name,middle_name,last_name,email_id,phone_no,address,pin_code,user_name,password,confirm_password,mUserGender;
    private TextView city;
    private RadioButton mProfileMale,mProfileFemale,mProfileOthers;
    private CardView password_card,confirm_password_card;
    private FlatButton submit;
    private Profile1 p;
    private List<City> listCities;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private String uniqueId;
    private int roleId = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_profile);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Complete Profile");

        listCities = new ArrayList<>();
        getCities();

        first_name = (EditText) findViewById(R.id.first_name);
        middle_name = (EditText) findViewById(R.id.middle_name);
        last_name = (EditText) findViewById(R.id.last_name);
        email_id = (EditText) findViewById(R.id.email_id);
        phone_no = (EditText) findViewById(R.id.phone_no);
        phone_no.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        city = (TextView) findViewById(R.id.city);
        address = (EditText) findViewById(R.id.address);
        pin_code = (EditText) findViewById(R.id.pin_code);
        pin_code.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        user_name = (EditText) findViewById(R.id.user_name);
        password = (EditText) findViewById(R.id.password);
        confirm_password = (EditText) findViewById(R.id.confirm_password);
        /*password_card = (CardView) findViewById(R.id.password_card);
        confirm_password_card = (CardView) findViewById(R.id.confirm_password_card);*/
       /* password_card = (CardView) findViewById(R.id.password_card);
        confirm_password_card = (CardView) findViewById(R.id.confirm_password_card);*/
        submit = (FlatButton) findViewById(R.id.submit);
        mProfileFemale = (RadioButton) findViewById(R.id.profile_female);
        mProfileMale = (RadioButton) findViewById(R.id.profile_male);
        mProfileOthers = (RadioButton) findViewById(R.id.profile_transgender);
        //mUserGender = (EditText) findViewById(R.id.user_gender);

        Intent in = getIntent();
        boolean isFb = in.getBooleanExtra("ISFB",false);

        first_name.setText(in.getStringExtra("FIRST_NAME"));
        middle_name.setText(in.getStringExtra("MIDDLE_NAME"));
        last_name.setText(in.getStringExtra("LAST_NAME"));
        uniqueId = in.getStringExtra("FB_ID");
        email_id.setText(in.getStringExtra("EMAIL"));

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>
//                (this, android.R.layout.simple_list_item_1, PLACES);

//        first_name.setText(in.getStringExtra(""));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validate();

            }
        });


        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY/*MODE_FULLSCREEN*/)
                                    .build(EnterProfileActivity.this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                    e.printStackTrace();
                }
            }
        });
//
    }

    private String TAG = "PlaceSeach";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);

                city.setText(place.getName());
                address.setText(place.getAddress());
                Log.i(TAG, "Place: " + place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i(TAG, status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }

    public void validate()
    {
        /*String male = mProfileMale.getText().toString();
        String female = mProfileFemale.getText().toString();
        String others = mProfileOthers.getText().toString();*/
        String lName = last_name.getText().toString();
        String fName = first_name.getText().toString();
        String uName = user_name.getText().toString();
        String email = email_id.getText().toString();
        String phoneNumber = phone_no.getText().toString();
        String pPlace = city.getText().toString();
        String pAddress = address.getText().toString();
        String pPassword = password.getText().toString();
        String pPincode = pin_code.getText().toString();
        String pEnterPassword = confirm_password.getText().toString();


        if(fName == null || fName.isEmpty())
        {
            first_name.setError(getResources().getString(R.string.first_name_validation_message));
            first_name.requestFocus();
        }
        else if(lName == null || lName.isEmpty())
        {
            last_name.setError(getResources().getString(R.string.last_name_validation_message));
            last_name.requestFocus();
        }
         else if(!mProfileMale.isChecked() && !mProfileFemale.isChecked() && !mProfileOthers.isChecked())
        {
            Toast.makeText(EnterProfileActivity.this,getResources().getString(R.string.gender_validation_message),Toast.LENGTH_SHORT).show();
        }
        else if(uName == null || uName.isEmpty())
        {
            user_name.setError(getResources().getString(R.string.unique_name_validation_message));
            user_name.requestFocus();
        }
        else if(email == null || email.isEmpty() )
        {
            email_id.setError(getResources().getString(R.string.email_validation_message));
            email_id.requestFocus();
        }
        else if(!isValidEmail(email))
        {
            email_id.setError(getResources().getString(R.string.email_validation_message));
            email_id.requestFocus();
        }
        else if(phoneNumber == null || phoneNumber.isEmpty() || phoneNumber.length() != 10)
        {
            phone_no.setError(getResources().getString(R.string.phone_number_validation_message));
            phone_no.requestFocus();
        }
        else if(pPlace == null || pPlace.isEmpty())
        {
            city.setError("Please Enter Your Place");
            city.requestFocus();
        }
        else if(pAddress == null || pAddress.isEmpty())
        {
            address.setError("Please Enter Your Address");
            address.requestFocus();
        }
        else if(pPincode == null || pPincode.isEmpty())
        {
            pin_code.setError("Please Enter Pin Code");
            pin_code.requestFocus();
        }
        else if(pPassword == null || pPassword.isEmpty())
        {
            password.setError("Please Enter Password");
            password.requestFocus();
        }
        else if(pEnterPassword == null || pEnterPassword.isEmpty())
        {
            confirm_password.setError("Please Enter Confirm Password");
            confirm_password.requestFocus();
        }
        else if(!pPassword.equals(pEnterPassword))
        {
            confirm_password.setError("Password and Confirm Password Should be Same");
            confirm_password.requestFocus();
        }
        else
        {
            register();
        }


    }

    private ProgressDialog progressDialog;

    public void register() {

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("please wait..");
        progressDialog.setCancelable(false);
        progressDialog.show();


        if (isValidEmail(email_id.getText().toString())&& isValidUserName(user_name.getText().toString()) &&
                isValidPhone(phone_no.getText().toString()) && isValidPin(pin_code.getText().toString())
                && isValidPassword(password.getText().toString())) {
            if (isValidConfirmPass(password.getText().toString(),confirm_password.getText().toString())) {
//                checkEmailAvailablity();
//                checkPhoneAvailablity();
//                loginThreadCall();
//                if (!){
                boolean res1 = checkEmailAvailablity();
//                }else Toast.makeText(EnterProfileActivity.this,"Email already available.",Toast.LENGTH_SHORT).show();

            }
            else {
                progressDialog.dismiss();
                Toast.makeText(EnterProfileActivity.this,"Password dose not match.",Toast.LENGTH_SHORT).show();
            }
        }else {
            progressDialog.dismiss();
            Toast.makeText(EnterProfileActivity.this,"Please Enter valid credentials.",Toast.LENGTH_SHORT).show();
        }

    }

    private void getCities(){
        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {
                IRegistrasionService apiService =
                        Util.getClient().create(IRegistrasionService.class);
                Call<ArrayList<City>> call = apiService.fetchCities();

                call.enqueue(new Callback<ArrayList<City>>() {
                    @Override
                    public void onResponse(Call<ArrayList<City>> call, Response<ArrayList<City>> response) {
//                List<RouteDTO.Routes> list = new ArrayList<RouteDTO.Routes>();
                        int statusCode = response.code();
                        if (statusCode == 200) {

                            listCities = response.body();
//                            Object dto = response.body();
//                            listCities.add(dto);
                            if (progressDialog!=null)
                                progressDialog.dismiss();


                        }else {
                            if (progressDialog!=null)
                                progressDialog.dismiss();
                            Toast.makeText(EnterProfileActivity.this, "Login failed due to status code:"+statusCode, Toast.LENGTH_SHORT).show();
                        }
//                callGetStartEnd();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<City>> call, Throwable t) {
                        // Log error here since request failed
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        Log.e("TAG", t.toString());
                    }
                });
            }


        });
    }

    private void loginThreadCall(){

        p = new Profile1();

        p.setFirstName(first_name.getText().toString());
        p.setMiddleName(middle_name.getText().toString());
        p.setLastName(last_name.getText().toString());
        p.setEmail(email_id.getText().toString());
        p.setPhoneNumber(phone_no.getText().toString());
        if(mProfileMale.isChecked())
        {
            p.setUserGender("Male");
        }
        else if(mProfileFemale.isChecked())
        {
            p.setUserGender("Female");
        }
        else if(mProfileOthers.isChecked())
        {
            p.setUserGender("Others");
        }
//        p.set_city(city.getText().toString());
//        p.setPinCode(pin_code.getText().toString());
        p.setAddress(address.getText().toString());
        p.setPinCode(pin_code.getText().toString());
        p.setUniqueId(uniqueId);
        City c = new City();
        String cN = city.getText().toString();
        p.setPlaceName(cN);
        c.setCityName(cN);

        int cityId = 0;
        for(City d : listCities){
            if(d.getCityName() != null && d.getCityName().contains(cN)){
                cityId = d.getCityId();
            }
            //something here
        }
        c.setCityId(cityId);
        p.setCityId(1);
        p.setUserRoleId(roleId);//-----------------------------hard coded-------------------
        p.setPassword(password.getText().toString());
        p.setUserName(user_name.getText().toString());
        p.setProfilePhoto("test");
        p.setFrontSidePhoto("test");
        p.setBackSidePhoto("test");


//          gson = new Gson();
//        final String j = gson.toJson(p);

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {


                IRegistrasionService apiService =
                        Util.getClient().create(IRegistrasionService.class);


                Call<Profile1> call = apiService.loginApi(p);

                call.enqueue(new Callback<Profile1>() {
                    @Override
                    public void onResponse(Call<Profile1> call, Response<Profile1> response) {
//                List<RouteDTO.Routes> list = new ArrayList<RouteDTO.Routes>();
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        int statusCode = response.code();
                        if (statusCode == 200 || statusCode == 201) {


                            Profile1 dto = response.body();
                            SharedPreferences sp  = PreferenceManager.getDefaultSharedPreferences(EnterProfileActivity.this);
                            SharedPreferences.Editor spe = sp.edit();
                            spe.putInt(Constants.USER_ID,dto.getProfileId());
                            PreferenceHandler.getInstance(EnterProfileActivity.this).setUserId(dto.getProfileId());
                            spe.putString("FirstName",dto.getFirstName());
                            spe.putString("MiddleName",dto.getMiddleName());
                            spe.putString("LastName",dto.getLastName());
                            spe.putString("UserName",dto.getUserName());
                            spe.putString("Password",dto.getPassword());
                            spe.putString("UniqueId",dto.getUniqueId());
                            spe.putInt("CityId",dto.getCityId());
                            spe.putString("PlaceName",dto.getPlaceName());
                            spe.putString("Email",dto.getEmail());
                            spe.putString("PhoneNumber",dto.getPhoneNumber());
                            spe.putString("Address",dto.getAddress());
                            spe.putString("PinCode",dto.getPinCode());
                            spe.putInt("UserRoleId",dto.getUserRoleId());
                            spe.apply();

//                            String status = dto.getAddress();
                            UserRole userRole = dto.get_userRole();
                            if(userRole != null)
                            {
                                System.out.println("Unique id = "+userRole.getUserRoleUniqueId());
                                //PreferenceHandler.getInstance(EnterProfileActivity.this).setUserRoleUniqueID(userRole.getUserRoleUniqueId());
                                Toast.makeText(EnterProfileActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(EnterProfileActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }
                            else {
                                getUserRole(dto.getUserRoleId());
                            }

                            /*Toast.makeText(EnterProfileActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(EnterProfileActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();*/
                        }else {

                            Toast.makeText(EnterProfileActivity.this, "Registration failed due to status code:"+statusCode, Toast.LENGTH_SHORT).show();
                        }
//                callGetStartEnd();
                    }

                    @Override
                    public void onFailure(Call<Profile1> call, Throwable t) {
                        // Log error here since request failed
                        if (progressDialog!=null)
                            progressDialog.dismiss();
                        Log.e("TAG", t.toString());
                    }
                });
            }
        });
    }

    private void getUserRole(final int i) {

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle(getResources().getString(R.string.loader_message));
        dialog.setCancelable(false);
        dialog.show();

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {


                UserRoleApi apiService =
                        Util.getClient().create(UserRoleApi.class);


                Call<UserRole> call = apiService.apiGetRolesById(i);

                call.enqueue(new Callback<UserRole>() {
                    @Override
                    public void onResponse(Call<UserRole> call, Response<UserRole> response) {
//                List<RouteDTO.Routes> list = new ArrayList<RouteDTO.Routes>();
                        if (dialog!=null)
                            dialog.dismiss();
                        int statusCode = response.code();
                        if (statusCode == 200 || statusCode == 201) {



//                            String status = dto.getAddress();
                            UserRole userRole = response.body();
                            if(userRole != null)
                            {
                                System.out.println("Unique id = "+userRole.getUserRoleUniqueId());
                                //PreferenceHandler.getInstance(EnterProfileActivity.this).setUserRoleUniqueID(userRole.getUserRoleUniqueId());
                                Toast.makeText(EnterProfileActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                Intent i = new Intent(EnterProfileActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }



                        }else {
                            /*if (progressDialog!=null)
                                progressDialog.dismiss();*/
                            Toast.makeText(EnterProfileActivity.this, "Registration failed due to status code:"+statusCode, Toast.LENGTH_SHORT).show();
                        }
//                callGetStartEnd();
                    }

                    @Override
                    public void onFailure(Call<UserRole> call, Throwable t) {
                        // Log error here since request failed
                        if (dialog!=null)
                            dialog.dismiss();
                        Log.e("TAG", t.toString());
                    }
                });
            }
        });


    }


    public final static boolean isValidEmail(CharSequence target)
    {
        if (TextUtils.isEmpty(target))
        {
            return false;
        } else {
            System.out.println(Patterns.EMAIL_ADDRESS.matcher(target).matches());
            return Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public final static boolean isValidName(String target)
    {
        if (TextUtils.isEmpty(target))
        {
            return false;
        } else {
            return target.matches("[A-Za-z][^&*:;<>.,/]*");
        }
    }

    public final static boolean isValidPhone(String target)
    {
        if (TextUtils.isEmpty(target))
        {
            return false;
        } else if (target.length()==10){
            if (Patterns.PHONE.matcher(target).matches()){
                return target.matches("[0-9][^-. ]*");
            }
            return false;
        }else return false;
    }
    public final static boolean isValidPin(String target)
    {
        if (TextUtils.isEmpty(target))
        {
            return false;
        } else if (target.length()==6){
            return target.matches("^[0-9]*$");
        }else return false;
    }

    public final static boolean isValidUserName(String target)
    {
        if (TextUtils.isEmpty(target))
        {
            return false;
        } else {
            return target.matches("^[a-zA-Z][^/ ]*$");
        }//else return false;
    }

    public final static boolean isValidPassword(String target)
    {
        if (TextUtils.isEmpty(target))
        {
            return false;
        } else return true;
    }

    public final static boolean isValidConfirmPass(String target,String target2)
    {
        if (TextUtils.isEmpty(target) && TextUtils.isEmpty(target2))
        {
            return false;
        } else if (target.equals(target2)){
            return true;
        }else return false;
    }

    public boolean validate(EditText _emailText,EditText _passwordText ) {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
//          _emailText.requestFocus();
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    @Override
    protected void onResume() {
        super.onResume();

        email_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

//                isValidEmail();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                /*if(!isValidEmail(email_id.getText().toString()))
                {
                    email_id.requestFocus();
                    email_id.setError(getResources().getString(R.string.email_validation_message));
                }*/
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        first_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                isValidEmail();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!isValidName(first_name.getText().toString()))
                {
                    first_name.requestFocus();
                    first_name.setError("Enter Correct Name ..!!");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        phone_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                isValidEmail();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!isValidPhone(phone_no.getText().toString()))
                {
                    phone_no.requestFocus();
                    phone_no.setError("Enter Correct Phone ..!!");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        pin_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                isValidEmail();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!isValidPin(pin_code.getText().toString()))
                {
                    pin_code.requestFocus();
                    pin_code.setError("Enter Correct Pin Code ..!!");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        user_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                isValidEmail();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!isValidUserName(user_name.getText().toString()))
                {
                    user_name.requestFocus();
                    user_name.setError("Enter valid user name ..!!");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                isValidEmail();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!isValidPassword(password.getText().toString()))
                {
                    password.requestFocus();
                    password.setError("Should not be empty ..!!");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        confirm_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                isValidEmail();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!isValidConfirmPass(password.getText().toString(),confirm_password.getText().toString()))
                {
                    confirm_password.requestFocus();
                    confirm_password.setError("Password Does Not Match");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    boolean response_str ;

    private boolean checkEmailAvailablity(){


        final Profile1 p1 = new Profile1();

        p1.setEmail(email_id.getText().toString());

//        new ThreadExecuter().execute(new Runnable() {
//            @Override
//            public void run() {
        IRegistrasionService apiService =
                Util.getClient().create(IRegistrasionService.class);
        Call<String> call = apiService.getProfileByEmail(p1);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
//                List<RouteDTO.Routes> list = new ArrayList<RouteDTO.Routes>();
                int statusCode = response.code();
                if (statusCode == 200) {

                    String  s = response.body();

                    if (s.equals("Profile Exist")){
                        email_id.requestFocus();
                        email_id.setError("Email Already exist..!!");
                        response_str = true;
                    }else {
                        boolean res2 = checkPhoneAvailablity();
                        response_str = false;
                    }
//                            Object listCities = response.body();
//                            Object dto = response.body();
//                            listCities.add(dto);
                    if (progressDialog!=null)
                        progressDialog.dismiss();


                }else {
                    if (progressDialog!=null)
                        progressDialog.dismiss();
                    Toast.makeText(EnterProfileActivity.this, "checking failed due to status code:"+statusCode, Toast.LENGTH_SHORT).show();
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
//            }


//        });


        return response_str;
    }

    private boolean checkPhoneAvailablity(){

        final Profile1 p1 = new Profile1();

        p1.setPhoneNumber(phone_no.getText().toString());

//        new ThreadExecuter().execute(new Runnable() {
//            @Override
//            public void run() {
        IRegistrasionService apiService =
                Util.getClient().create(IRegistrasionService.class);
        Call<String> call = apiService.getProfileByPhone(p1);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
//                List<RouteDTO.Routes> list = new ArrayList<RouteDTO.Routes>();
                int statusCode = response.code();
                if (statusCode == 200) {
                    String  s = response.body();

                    if (s.equals("Profile Exist")){
                        phone_no.requestFocus();
                        phone_no.setError("Phone Number Already exist..!!");
                        response_str = true;
                    }else {
                        boolean res3 = checkUserNameAvailability();
                        response_str = false;
                    }
//                            Object listCities = response.body();
//                            Object dto = response.body();
//                            listCities.add(dto);
                    if (progressDialog!=null)
                        progressDialog.dismiss();


                }else {
                    if (progressDialog!=null)
                        progressDialog.dismiss();
                    Toast.makeText(EnterProfileActivity.this, "checking failed due to status code:"+statusCode, Toast.LENGTH_SHORT).show();
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
//            }


//        });


        return response_str;
    }

    private boolean checkUserNameAvailability(){


        final Profile1 p1 = new Profile1();

        p1.setUserName(user_name.getText().toString());

//        new ThreadExecuter().execute(new Runnable() {
//            @Override
//            public void run() {
        IRegistrasionService apiService =
                Util.getClient().create(IRegistrasionService.class);
        Call<String> call = apiService.getProfileByUserName(user_name.getText().toString());

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
//                List<RouteDTO.Routes> list = new ArrayList<RouteDTO.Routes>();
                int statusCode = response.code();
                if (statusCode == 200 || statusCode == 201) {

                    String  s = response.body();

                    if (s.equals("Profile Exist")){
                        user_name.requestFocus();
                        user_name.setError("User Already exist..!!");
                        response_str = true;
                    }else {
                        response_str = false;
                        loginThreadCall();
                    }
//                            Object listCities = response.body();
//                            Object dto = response.body();
//                            listCities.add(dto);
                    if (progressDialog!=null)
                        progressDialog.dismiss();


                }else {
                    if (progressDialog!=null)
                        progressDialog.dismiss();
                    Toast.makeText(EnterProfileActivity.this, "checking failed due to status code:"+statusCode, Toast.LENGTH_SHORT).show();
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
//            }


//        });


        return response_str;
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

    /*button_resetPassword.setOnClickListener(new View.OnClickListener()
    {
        @Override
        public void onClick(View v)
        {
            CharSequence temp_emilID=username.getText().toString();//here username is the your edittext object...
            if(!isValidEmail(temp_emilID))
            {
                username.requestFocus();
                username.setError("Enter Correct Mail_ID ..!!");
                     or
                Toast.makeText(getApplicationContext(), "Enter Correct Mail_ID", Toast.LENGTH_SHORT).show();

            }
            else
           {
              correctMail..
             //Your action...

           }

         });*/
}
