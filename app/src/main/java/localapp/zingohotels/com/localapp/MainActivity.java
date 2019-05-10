package localapp.zingohotels.com.localapp;

import android.*;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import localapp.zingohotels.com.localapp.Activty.SlideMain;
import localapp.zingohotels.com.localapp.Adapters.EventListAdapter;
import localapp.zingohotels.com.localapp.Adapters.NavigationListAdapter;
import localapp.zingohotels.com.localapp.Adapters.TopActivitiesAdapter;
import localapp.zingohotels.com.localapp.Adapters.ViewPagerAdapter;
import localapp.zingohotels.com.localapp.Login.LoginWithActivity;
import localapp.zingohotels.com.localapp.Model.ActivityModel;
import localapp.zingohotels.com.localapp.Model.Category;
import localapp.zingohotels.com.localapp.Model.NavBarItems;
import localapp.zingohotels.com.localapp.Model.PagerModel;
import localapp.zingohotels.com.localapp.Util.PreferenceHandler;
import localapp.zingohotels.com.localapp.Util.ThreadExecuter;
import localapp.zingohotels.com.localapp.Util.Util;
import localapp.zingohotels.com.localapp.WebApiClients.ActivityApi;
import localapp.zingohotels.com.localapp.WebApiClients.CategoryApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v4.view.GravityCompat.START;

public class MainActivity extends AppCompatActivity {
    //ViewPager vpPager;
    String TAG = "MainActivity";
    ViewPager vpPager,mtop_activities_viewpager;
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;
    TextView mPickInterest,moreCategories,mFirstCategory,mSecondCategory,mThirdCategory,mMoreCategory,mUserName,mGreetings;
    ImageView mFirstBanner,mSecondBanner,mThirdBanner,mMoreBanner;
    DrawerLayout drawer;
    ListView navbar;
    FrameLayout mFirstBannerFrame,mSecondBannerFrame,mThirdBannerFrame,mMoreBannerFrame;
    LinearLayout mUserBrifeProfile;


    ArrayList<Category> categories;
    public static final int MY_PERMISSIONS_REQUEST_RESULT = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

      //  StatusBarUtil.setTransparent(MainActivity.this);
        //StatusBarUtil.setTransparent(MainActivity.this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN|
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navbar = (ListView) findViewById(R.id.navbar_list);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        /*collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle("Bangalore");*/

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        /*SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);*/
        checkPermission();


        vpPager = (ViewPager) findViewById(R.id.pager);
        vpPager.setClipToPadding(false);
        vpPager.setPageMargin(15);

        mtop_activities_viewpager = (ViewPager) findViewById(R.id.top_activities_viewpager);
        mtop_activities_viewpager.setClipToPadding(false);
        mtop_activities_viewpager.setPageMargin(12);

        mPickInterest = (TextView) findViewById(R.id.interest);
        mUserName = (TextView) findViewById(R.id.main_user_name);
        mGreetings = (TextView) findViewById(R.id.main_greetings);
        getTimeFromAndroid();
        mUserName.setText(PreferenceHandler.getInstance(MainActivity.this).getUserFullName());
        //moreCategories = (TextView) findViewById(R.id.more_category);
        mFirstCategory = (TextView) findViewById(R.id.first_banner_category_name);
        mSecondCategory = (TextView) findViewById(R.id.second_banner_category_name);
        mThirdCategory = (TextView) findViewById(R.id.third_banner_category_name);
        mMoreCategory = (TextView) findViewById(R.id.more_banner_category_name);

        mFirstBanner = (ImageView) findViewById(R.id.first_category_banner);
        mSecondBanner = (ImageView) findViewById(R.id.second_category_banner);
        mThirdBanner = (ImageView) findViewById(R.id.third_category_banner);
        mMoreBanner = (ImageView) findViewById(R.id.more_category_banner);

        mFirstBannerFrame = (FrameLayout) findViewById(R.id.first_banner_category_frame);
        mSecondBannerFrame = (FrameLayout) findViewById(R.id.second_banner_category_frame);
        mThirdBannerFrame = (FrameLayout) findViewById(R.id.third_banner_category_frame);
        mMoreBannerFrame = (FrameLayout) findViewById(R.id.more_banner_category_frame);

        mUserBrifeProfile = (LinearLayout) findViewById(R.id.main_user_profile);
        mUserBrifeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(drawer.isDrawerOpen())
                if(drawer != null)
                    drawer.closeDrawer(START);

                Intent intent = new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });
        mFirstBannerFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListOfEventsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title",mFirstCategory.getText().toString());
                bundle.putInt("cat_id",categories.get(0).getCategoriesId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        mSecondBannerFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListOfEventsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title",mSecondCategory.getText().toString());
                bundle.putInt("cat_id",categories.get(1).getCategoriesId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        mThirdBannerFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListOfEventsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title",mThirdCategory.getText().toString());
                bundle.putInt("cat_id",categories.get(2).getCategoriesId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        mPickInterest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,InterestActivity.class);
                startActivity(intent);
            }
        });
        mMoreBannerFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mintent = new Intent(MainActivity.this,CategoryListActivity.class);
                /*Bundle bundle = new Bundle();
                bundle.putSerializable("Categories",categories);
                mintent.putExtras(bundle);*/
                startActivity(mintent);
            }
        });
        //TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        //tabLayout.setupWithViewPager(vpPager);
        //vpPager.setClipToPadding(false);
        //vpPager.setPageMargin(12);

        setup();
        setUpNavigationDrawerforOwner();


        //vpPager.setOn
    }

    private void getTimeFromAndroid() {
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        int hours = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);

        if(hours>=1 && hours<=12){
            Toast.makeText(this, "Good Morning "+PreferenceHandler.getInstance(MainActivity.this).getUserFullName(), Toast.LENGTH_SHORT).show();
            mGreetings.setText("Good Morning");
        }else if(hours>=12 && hours<=16){
            Toast.makeText(this, "Good Afternoon "+PreferenceHandler.getInstance(MainActivity.this).getUserFullName(), Toast.LENGTH_SHORT).show();
            mGreetings.setText("Good Afternoon");
        }else if(hours>=16 && hours<=21){
            mGreetings.setText("Good Evening");
            Toast.makeText(this, "Good Evening "+PreferenceHandler.getInstance(MainActivity.this).getUserFullName(), Toast.LENGTH_SHORT).show();
        }else if(hours>=21 && hours<=24){
            mGreetings.setText("Good Night");
            Toast.makeText(this, "Good Night "+PreferenceHandler.getInstance(MainActivity.this).getUserFullName(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setup() {

       /* String[] categories = getResources().getStringArray(R.array.categories);
        int[] banners = {R.drawable.water_sports,R.drawable.holiday_packages,R.drawable.eat_and_drink
                        ,R.drawable.culture,R.drawable.health_wellnes,R.drawable.events,R.drawable.arts};
        ArrayList<PagerModel> pagerModelArrayList = new ArrayList<>();
        for(int i=0;i<banners.length;i++)
        {
            pagerModelArrayList.add(new PagerModel(banners[i],categories[i],"1 Experience"));
        }

        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(MainActivity.this,pagerModelArrayList);
        vpPager.setAdapter(pagerAdapter);*/
        getCategories();
        getActivities();



    }

    private void setUpNavigationDrawerforOwner() {

        TypedArray icons = getResources().obtainTypedArray(R.array.navnar_item_images);
        String[] title  = getResources().getStringArray(R.array.navbar_items);

        final ArrayList<NavBarItems> navBarItemsList = new ArrayList<>();

        for (int i=0;i<title.length;i++)
        {
            NavBarItems navbarItem = new NavBarItems(title[i],icons.getResourceId(i, -1));
            navBarItemsList.add(navbarItem);
        }
        //final ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(title));
        NavigationListAdapter adapter = new NavigationListAdapter(getApplicationContext(),navBarItemsList);
        navbar.setAdapter(adapter);
        navbar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                displayView(navBarItemsList.get(position).getTitle());
            }
        });

    }

    public void getCategories()
    {
        final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        dialog.setCancelable(false);
        dialog.show();

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(TAG+" thread started");
                final CategoryApi categoryAPI = Util.getClient().create(CategoryApi.class);
                Call<ArrayList<Category>> getCat = categoryAPI.getCategories();

                getCat.enqueue(new Callback<ArrayList<Category>>() {
                    //@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    /*@SuppressLint("NewApi")*/
                    //System.out.println("thread inside on response");
                    @Override
                    public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                        if(dialog != null)
                        {
                            dialog.dismiss();
                        }
                        System.out.println(TAG+" thread inside on response");
                        if(response.code() == 200)
                        {
                            if(response.body() != null && response.body().size() != 0)
                            {
                                categories = response.body();
                                System.out.println("categories = "+categories.size());
                                ViewPagerAdapter adapter = new ViewPagerAdapter(MainActivity.this,categories);
                                vpPager.setAdapter(adapter);

                                if(categories.size() == 1)
                                {
                                    //mFirstBanner.setImageBitmap(Util.convertToBitMap(categories.get(0).getCategoriesImage()));
                                    Picasso.with(getApplicationContext()).load(categories.get(0).getCategoriesImage()).placeholder(R.drawable.no_image).error(R.drawable.no_image).into(mFirstBanner);
                                    //mFirstCategory.setAlpha(0.8f);
                                    mFirstCategory.setText(categories.get(0).getCategoriesName());
                                    mSecondBannerFrame.setVisibility(View.GONE);
                                    mThirdBannerFrame.setVisibility(View.GONE);
                                    mMoreBannerFrame.setVisibility(View.GONE);
                                }
                                else if(categories.size()  == 2)
                                {

                                   // mFirstBanner.setImageBitmap(Util.convertToBitMap(categories.get(0).getCategoriesImage()));
                                    Picasso.with(getApplicationContext()).load(categories.get(0).getCategoriesImage()).placeholder(R.drawable.no_image).error(R.drawable.no_image).into(mFirstBanner);

                                    mFirstCategory.setText(categories.get(0).getCategoriesName());

                                   // mSecondBanner.setImageBitmap(Util.convertToBitMap(categories.get(1).getCategoriesImage()));
                                    Picasso.with(getApplicationContext()).load(categories.get(1).getCategoriesImage()).placeholder(R.drawable.no_image).error(R.drawable.no_image).into(mSecondBanner);
                                    mSecondCategory.setText(categories.get(1).getCategoriesName());
                                    mThirdBannerFrame.setVisibility(View.GONE);
                                    mMoreBannerFrame.setVisibility(View.GONE);

                                }
                                else if(categories.size() == 3)
                                {
                                    //mFirstBanner.setImageBitmap(Util.convertToBitMap(categories.get(0).getCategoriesImage()));
                                    Picasso.with(getApplicationContext()).load(categories.get(0).getCategoriesImage()).placeholder(R.drawable.no_image).error(R.drawable.no_image).into(mFirstBanner);
                                    mFirstCategory.setText(categories.get(0).getCategoriesName());

                                    //mSecondBanner.setImageBitmap(Util.convertToBitMap(categories.get(1).getCategoriesImage()));
                                    Picasso.with(getApplicationContext()).load(categories.get(1).getCategoriesImage()).placeholder(R.drawable.no_image).error(R.drawable.no_image).into(mSecondBanner);
                                    mSecondCategory.setText(categories.get(1).getCategoriesName());

                                    //mThirdBanner.setImageBitmap(Util.convertToBitMap(categories.get(2).getCategoriesImage()));
                                    Picasso.with(getApplicationContext()).load(categories.get(2).getCategoriesImage()).placeholder(R.drawable.no_image).error(R.drawable.no_image).into(mThirdBanner);
                                    mThirdCategory.setText(categories.get(2).getCategoriesName());

                                    mMoreBannerFrame.setVisibility(View.GONE);
                                }
                                else if(categories.size() > 3)
                                {
                                   // mFirstBanner.setImageBitmap(Util.convertToBitMap(categories.get(0).getCategoriesImage()));
                                    Picasso.with(getApplicationContext()).load(categories.get(0).getCategoriesImage()).placeholder(R.drawable.no_image).error(R.drawable.no_image).into(mFirstBanner);
                                    mFirstCategory.setText(categories.get(0).getCategoriesName());

                                    //mSecondBanner.setImageBitmap(Util.convertToBitMap(categories.get(1).getCategoriesImage()));
                                    Picasso.with(getApplicationContext()).load(categories.get(1).getCategoriesImage()).placeholder(R.drawable.no_image).error(R.drawable.no_image).into(mSecondBanner);
                                    mSecondCategory.setText(categories.get(1).getCategoriesName());

                                    //mThirdBanner.setImageBitmap(Util.convertToBitMap(categories.get(2).getCategoriesImage()));
                                    Picasso.with(getApplicationContext()).load(categories.get(2).getCategoriesImage()).placeholder(R.drawable.no_image).error(R.drawable.no_image).into(mThirdBanner);
                                    mThirdCategory.setText(categories.get(2).getCategoriesName());

                                    //mMoreBannerFrame.setVisibility(View.GONE);
                                }

                                /*if(updaterooms != null)
                                {
                                    for (int i=0;i<categories.size();i++)
                                    {
                                        if(categories.get(i).getCategoriesId() == updaterooms.getRoomCategoryId())
                                        {
                                            room_category_spinner.setSelection(i);
                                            break;
                                        }
                                    }
                                }*/
                            }
                            else
                            {
                                mMoreBannerFrame.setVisibility(View.GONE);
                                mThirdBannerFrame.setVisibility(View.GONE);
                                mSecondBannerFrame.setVisibility(View.GONE);
                                mFirstBannerFrame.setVisibility(View.GONE);
                            }
//                            loadRoomCategoriesSpinner();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                        if(dialog != null)
                        {
                            dialog.dismiss();
                        }
                        System.out.println(TAG+" thread inside on fail");
                        Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });


                //System.out.println(TAG+" thread started");

            }

        });

    }

    public void getActivities()
    {
        final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
        dialog.setCancelable(false);
        dialog.show();

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {
                System.out.println(TAG+" thread started");
                final ActivityApi activityApi = Util.getClient().create(ActivityApi.class);
                Call<ArrayList<ActivityModel>> getCat = activityApi.getActivities();

                getCat.enqueue(new Callback<ArrayList<ActivityModel>>() {
                    //@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    /*@SuppressLint("NewApi")*/
                    //System.out.println("thread inside on response");
                    @Override
                    public void onResponse(Call<ArrayList<ActivityModel>> call, Response<ArrayList<ActivityModel>> response) {
                        if(dialog != null)
                        {
                            dialog.dismiss();
                        }
                        System.out.println(TAG+" thread inside on response");
                        if (response.code() == 200)
                        {
                            try
                            {
                                System.out.println(response.body().size());
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                ArrayList<ActivityModel> selectedActivities = response.body();
                                ArrayList<ActivityModel> ActivityArrayList = new ArrayList<>();
                                for (int i=0;i<response.body().size();i++)
                                {
                                    Date activitydate = simpleDateFormat.parse(selectedActivities.get(i).getValidTo());
                                    String stdate = simpleDateFormat.format(new Date());
                                    Date tdate = simpleDateFormat.parse(stdate);
                                    //long activitymiliseconds = activitydate.getTime();
                                    ActivityArrayList.add(selectedActivities.get(i));

                                    if(activitydate.after(tdate) || activitydate.equals(tdate))
                                    {

                                    }
                                }
                                TopActivitiesAdapter eventpagerAdapter = new TopActivitiesAdapter(MainActivity.this,ActivityArrayList);//,pagerModelArrayList);
                                mtop_activities_viewpager.setAdapter(eventpagerAdapter);
                            }
                            catch (ParseException ex)
                            {
                                ex.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<ActivityModel>> call, Throwable t) {
                        if(dialog != null)
                        {
                            dialog.dismiss();
                        }
                        System.out.println(TAG+" thread inside on fail");
                        Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });


                //System.out.println(TAG+" thread started");

            }

        });
    }
    /*public Bitmap convertToBitMap(String base64Str)
    {
        byte[] imagebytes = Base64.decode(base64Str,Base64.DEFAULT);
        Bitmap catimage = BitmapFactory.decodeByteArray(imagebytes,0,imagebytes.length);
        return catimage;
    }*/


    public void displayView(String i)
    {
        if(drawer != null)
            drawer.closeDrawer(START);


        switch (i)
        {
            case "Profile":
                Intent profileIntent = new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(profileIntent);
                break;

            case "Share App":
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.send_to));
                sendIntent.setType("text/plain");
                //startActivity(sendIntent);
                startActivity(Intent.createChooser(sendIntent,"Zingo Local" ));
                break;

            case "About Zingo":
                Toast.makeText(MainActivity.this,"Coming soon",Toast.LENGTH_SHORT).show();
                break;

            case "Wallet":
                Toast.makeText(MainActivity.this,"Coming soon",Toast.LENGTH_SHORT).show();
                break;
            case "Logout":
                //PreferenceHandler.getInstance(this).clear();
                PreferenceHandler.getInstance(MainActivity.this).clear();

                Intent log = new Intent(MainActivity.this, SlideMain.class);
                log.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                log.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(log);
                finish();
                break;

        }
    }

    public boolean checkPermission() {
        if ((ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                ) {
        if ((ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION))
                && (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE))
                && (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE))) {

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{
                                android.Manifest.permission.ACCESS_FINE_LOCATION,
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                android.Manifest.permission.READ_EXTERNAL_STORAGE
                                },
                        MY_PERMISSIONS_REQUEST_RESULT);
                Log.d("checkPermission if","false");

            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{
                                android.Manifest.permission.ACCESS_FINE_LOCATION,
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                android.Manifest.permission.READ_EXTERNAL_STORAGE
                                },
                        MY_PERMISSIONS_REQUEST_RESULT);
                Log.d("checkPermission else","true");

            }
            return false;
        } else {
            Log.d("checkPermission else","trur");
            //mobileNumber = mCountryCode.getText().toString()+mobileNumber;
            //System.out.println("+++++"+mobileNumber);

            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_RESULT: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0) {
                    Log.d("grantResults length", grantResults.length + "");
                    boolean sendSMsPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean accessLocation = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean writeToStarage= grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean readStorage = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean call = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                    if(sendSMsPermission)
                    {
                        //mobileNumber = mCountryCode.getText().toString()+mobileNumber;
                       /* SharedPreferenceHandler.getInstance(UserAuthenticationActivity.this).setUserPhoneNumber(mobileNumber);
                        Intent goToHomeIntent = new Intent(this,MainActivity.class);
                        startActivity(goToHomeIntent);*/

                    }
                }

                return;
            }
        }
    }

}
