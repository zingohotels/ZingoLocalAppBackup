package localapp.zingohotels.com.localapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import localapp.zingohotels.com.localapp.Activty.SlideMain;
import localapp.zingohotels.com.localapp.Login.LoginWithActivity;
import localapp.zingohotels.com.localapp.Util.PreferenceHandler;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences sp;
    private TextView subtext,sidetext;
    //TrackGPS trackGPS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);





        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        getSupportActionBar().hide();*/


        /*final PathView pathView = (PathView) findViewById(R.id.pathView);
        pathView.getPathAnimator()
                .delay(1500)
                .duration(1500)
                .interpolator(new AccelerateDecelerateInterpolator())
                .start();

        pathView.useNaturalColors();
        pathView.setFillAfter(true);*/

        subtext = (TextView) findViewById(R.id.sub_text);
        sidetext = (TextView) findViewById(R.id.left_text);
        sidetext.setRotation(-90);
        Animation logo_anim = AnimationUtils.loadAnimation(this,R.anim.transition);
        Animation pop_anim = AnimationUtils.loadAnimation(this,R.anim.pop_up);
        subtext.startAnimation(pop_anim);
        sidetext.startAnimation(pop_anim);

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                /*sp  = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
                final int userId = sp.getInt(Constants.USER_ID,0);*/
                int userId = PreferenceHandler.getInstance(SplashActivity.this).getUserId();
                //System.out.println("User id = "+userId);
                if (userId!=0){
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    //Intent i = new Intent(SplashActivity.this, MapLocation.class);
                    startActivity(i);
                    finish();
                }else{
                    Intent i = new Intent(SplashActivity.this, SlideMain.class);
                    startActivity(i);
                    finish();
                }


            }
        }, 3000);

    }
}
