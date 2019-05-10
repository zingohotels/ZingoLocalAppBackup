package localapp.zingohotels.com.localapp.Activty;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.MapView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Timer;
import java.util.TimerTask;

import localapp.zingohotels.com.localapp.Adapters.ActivityAdapter;
import localapp.zingohotels.com.localapp.Adapters.AutoScrollAdapter;
import localapp.zingohotels.com.localapp.Adapters.PackageAdapter;
import localapp.zingohotels.com.localapp.CustomImplementations.SortPackageDetails;
import localapp.zingohotels.com.localapp.CustomInterfaces.RecyclerTouchListener;
import localapp.zingohotels.com.localapp.Model.ActivityModel;
import localapp.zingohotels.com.localapp.Model.PackageDetails;
import localapp.zingohotels.com.localapp.R;
import localapp.zingohotels.com.localapp.Util.Constants;
import localapp.zingohotels.com.localapp.Util.ThreadExecuter;
import localapp.zingohotels.com.localapp.Util.Util;
import localapp.zingohotels.com.localapp.Model.Maps;
import localapp.zingohotels.com.localapp.WebApiClients.MapApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;




public class ActivityDetail extends AppCompatActivity {

   AutoScrollAdapter imagePager;
   //ViewPager imagePager;
    TextView mAbout,mMore,mLess,mMCanc,mLCanc,mCancel,mName,mCate,
           mSell,mDisplay,mInterest,mBreif,mLocation,mPercent,mAddress;
    ArrayList<String> activityImages;
    ArrayList<PackageDetails> activityPackages;
   RecyclerView recyclerView;
    private ActivityModel activityList;
    Button mLetsGo;
    ViewGroup rootContainer;

    int currentPage = 0,start = 0,end = 0;
    Timer timer;
    final long DELAY_MS = 500;
    final long PERIOD_MS = 1500;
    private ProgressDialog progressDialog;

    //maps related
    private GoogleMap mMap;
    MapView mapView;
    int activityId;
    Maps maps;
    Marker marker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            activityList = (ActivityModel)bundle.getSerializable(Constants.ACTIVITY);
        }

        imagePager = (AutoScrollAdapter) findViewById(R.id.activity_images_viewpager);
        imagePager.setStopScrollWhenTouch(true);


        mAbout = (TextView)findViewById(R.id.activity_about);
        mName = (TextView)findViewById(R.id.activity_name);
        mCate = (TextView)findViewById(R.id.activity_category);
        mSell = (TextView)findViewById(R.id.activity_sell_rate);
        mPercent = (TextView)findViewById(R.id.percentage_text);
        mDisplay = (TextView)findViewById(R.id.activity_display_rate);
        mInterest = (TextView)findViewById(R.id.activity_interest);
        mAddress = (TextView)findViewById(R.id.activity_details_activity_address);
        mBreif = (TextView)findViewById(R.id.activity_brief);
        //mLocation = (TextView)findViewById(R.id.activity_location);
        mCancel = (TextView)findViewById(R.id.activity_cancellation);
        mMore = (TextView)findViewById(R.id.read_more);
        mLess = (TextView)findViewById(R.id.read_less);
        mMCanc = (TextView)findViewById(R.id.read_more_cancel);
        mLCanc = (TextView)findViewById(R.id.read_less_cancel);
        mLetsGo = (Button) findViewById(R.id.lets_go);
        mapView = (MapView) findViewById(R.id.activity_details_activity_location);
        recyclerView = (RecyclerView) findViewById(R.id.package_details_recyclerview);

        /*recyclerView.addOnItemTouchListener(new RecyclerTouchListener(ActivityDetail.this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                System.out.println("activityPackages = "+activityPackages.get(position).getName());
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));*/

        mapView.onCreate(savedInstanceState);
        mapView.onResume();

        try {
            MapsInitializer.initialize(ActivityDetail.this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;


                if (ActivityCompat.checkSelfPermission(ActivityDetail.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ActivityDetail.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
               // mMap.getUiSettings().setZoomControlsEnabled(true);
                //mMap.setMyLocationEnabled(true);
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                mMap.getProjection().getVisibleRegion().latLngBounds.getCenter();

            //getMapDetails();
                if(activityList!=null){
                    setActivty();
                }


            }
        });



        mMore.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mMore.setVisibility(View.GONE);
                mLess.setVisibility(View.VISIBLE);
                mAbout.setMaxLines(Integer.MAX_VALUE);

            }
        });

        mLess.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mLess.setVisibility(View.GONE);
                mMore.setVisibility(View.VISIBLE);
                mAbout.setMaxLines(2);

            }
        });

        mMCanc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mMCanc.setVisibility(View.GONE);
                mLCanc.setVisibility(View.VISIBLE);
                mCancel.setMaxLines(Integer.MAX_VALUE);

            }
        });

        mLCanc.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mLCanc.setVisibility(View.GONE);
                mMCanc.setVisibility(View.VISIBLE);
                mCancel.setMaxLines(2);

            }
        });

        mLetsGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent service = new Intent(ActivityDetail.this,ActivityBook.class);
                Bundle bundle1 = new Bundle();
                bundle.putSerializable(Constants.ACTIVITY,activityList);
                service.putExtras(bundle);
                startActivity(service);
            }
        });



       // getAct();
        /*setActivty();*/

        /*PackageAdapter adapter = new PackageAdapter(ActivityDetail.this);
        recyclerView.setAdapter(adapter);*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //ActivityDetail.this.overridePendingTransition(R.anim.fade_in,R.anim.fade_out);
    }

 /*   public void prepareExitTransition(Runnable finishRunnable) {
        if (isAlwaysFinishActivitiesEnabled() || !this.viewHolder.prepareExitTransition(finishRunnable)) {
            this.viewHolder.hideTourImage();
            if (VERSION.SDK_INT >= 21) {
                this.rootContainer.setTransitionGroup(true);
                getWindow().setTransitionBackgroundFadeDuration(0);
                getWindow().setReturnTransition(new Slide());
                getWindow().setSharedElementReturnTransition(null);
            }
            finishRunnable.run();
        }
    }*/


  private void setActivty(){

      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
          mAbout.setText(Html.fromHtml(activityList.getAboutTheActivity(), Html.FROM_HTML_MODE_COMPACT));
      } else {
          mAbout.setText(Html.fromHtml(activityList.getAboutTheActivity()));
      }
      //mAbout.setText(activityList.getAboutTheActivity());
      mCancel.setText(activityList.getFlexibleCancellationPolicy());
      mName.setText(activityList.getActivityName());
     /* mSell.setText("₹"+activityList.getSellingPrice());
      mDisplay.setText("₹"+activityList.getDisplayPrice());*/
      // mInterest.setText(list.get(0).getInterests());
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
          mBreif.setText(Html.fromHtml(activityList.getDescription(), Html.FROM_HTML_MODE_COMPACT));
      } else {
          mBreif.setText(Html.fromHtml(activityList.getDescription()));
      }
      //mBreif.setText(activityList.getDescription());
      //mLocation.setText(activityList.getLocation());
     /* mPercent.setText(""+activityList.getDiscountPercentage());*/
      //activityId=activityList.getActivitiesId();
      activityImages = new ArrayList<>();
      activityPackages =activityList.getPackageDetails();// new ArrayList<>();
      mAddress.setText(activityList.getAddress());
      final int size = activityList.getActivityImages().size();
      final int packageSize = activityList.getPackageDetails().size();
      System.out.println("Size=="+packageSize);

      for (int i=0;i<size;i++)
      {
          activityImages.add(activityList.getActivityImages().get(i).getImages());
      }
      /*for (int i=0;i<packageSize;i++)
      {
          activityPackages.add(activityList.getPackageDetails().get(i));
          Arrays.toString(activityPackages.toArray());
      }*/

      ActivityAdapter activityImagesadapter = new ActivityAdapter(ActivityDetail.this,activityImages);
      imagePager.setAdapter(activityImagesadapter);

      if(activityPackages != null && activityPackages.size() != 0)
      {
          Collections.sort(activityPackages,new SortPackageDetails());

          mSell.setText("₹ "+activityPackages.get(0).getSellRate());//activityList.getSellingPrice());
          mDisplay.setText("₹ "+activityPackages.get(0).getDeclaredRate());//activityList.getDisplayPrice());
          double diff = (activityPackages.get(0).getDeclaredRate() - activityPackages.get(0).getSellRate())/activityPackages.get(0).getDeclaredRate();
          double per = diff*100;
          System.out.println("per = "+per);

          PackageAdapter adapter = new PackageAdapter(ActivityDetail.this,activityList);
          recyclerView.setAdapter(adapter);
          mLetsGo.setVisibility(View.GONE);
      }

      final Handler handler = new Handler();
      final Runnable Update = new Runnable() {
          public void run() {
              if (currentPage == (size - 1)&& start == 0) {
                  currentPage = --currentPage;
                  start = 1;
                  end = 0;
              }else if(currentPage < (size - 1) && currentPage != 0 && end == 0&& start == 1){
                  currentPage = --currentPage;
              }else if(currentPage == 0 && end == 0 && start == 1){
                  currentPage = 0;
                  end = 1;
                  start = 0;
              }else if(currentPage <= (size - 1) && start == 0){

                  currentPage = ++currentPage;
              }else if(currentPage == 0&& start == 0){

                  currentPage = ++currentPage;
              }else{

              }
              imagePager.setCurrentItem(currentPage, true);
          }
      };

      timer = new Timer(); // This will create a new Thread
      timer .schedule(new TimerTask() { // task to be scheduled

          @Override
          public void run() {
              handler.post(Update);
          }
      }, DELAY_MS, PERIOD_MS);
         if(activityList.getMaps() != null && activityList.getMaps().size() != 0)
         {
             mMap.clear();

             LatLng latlng = new LatLng(Double.parseDouble(activityList.getMaps().get(0).getLatitude()),Double.parseDouble(activityList.getMaps().get(0).getLogitude()));
             marker = mMap.addMarker(new MarkerOptions()
                     .position(latlng)
                     .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
             CameraPosition cameraPosition = new CameraPosition.Builder().zoom(14).target(latlng).build();
             mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
         }

      //getMapDetails(activityList.getActivitiesId());

  }

    /*class SortPackageDetails implements Comparator<PackageDetails>
    {

        @Override
        public int compare(PackageDetails o1, PackageDetails o2) {
            if(o1.getSellRate() > o2.getSellRate())
            {
                return 1;
            }
            else if(o1.getSellRate() == o2.getSellRate())
            {
                return 0;
            }
            else
            {
                return -1;
            }
        }
    }*/


    public void getMapDetails(final int id)
    {
        final ProgressDialog dialog = new ProgressDialog(ActivityDetail.this);
        dialog.setTitle(getResources().getString(R.string.loader_message));
        dialog.setCancelable(false);
        dialog.show();

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {
                MapApi mapApi = Util.getClient().create(MapApi.class);
                Call<ArrayList<Maps>> getMapDetails = mapApi.getMapDetails(id);

                getMapDetails.enqueue(new Callback<ArrayList<Maps>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Maps>> call, Response<ArrayList<Maps>> response) {
                        if(dialog != null)
                        {
                            dialog.dismiss();
                        }

                        if(response.code() == 200 && response.body() != null && response.body().size() != 0)
                        {
                            maps = response.body().get(0);


                            mMap.clear();
                            LatLng hotelLatlng = new LatLng(Double.parseDouble(maps.getLatitude()),Double.parseDouble(maps.getLogitude()));
                            marker = mMap.addMarker(new MarkerOptions()
                                    .position(hotelLatlng)
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                            CameraPosition cameraPosition = new CameraPosition.Builder().zoom(14).target(hotelLatlng).build();
                            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        }
                        else
                        {
                            Toast.makeText(ActivityDetail.this,response.message(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Maps>> call, Throwable t) {
                        if(dialog != null)
                        {
                            dialog.dismiss();
                        }
                        Toast.makeText(ActivityDetail.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                        System.out.println(t.getMessage());
                    }
                });

            }
        });


    }


}
