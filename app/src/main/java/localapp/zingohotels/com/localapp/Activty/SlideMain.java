package localapp.zingohotels.com.localapp.Activty;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;


import java.util.Timer;
import java.util.TimerTask;

import localapp.zingohotels.com.localapp.Adapters.SplashSlider;
import localapp.zingohotels.com.localapp.Login.LoginNew;
import localapp.zingohotels.com.localapp.R;
import localapp.zingohotels.com.localapp.SignUpActivity;

public class SlideMain extends AppCompatActivity {
    ViewPager viewPager;
    int[] layouts = {R.layout.activity_slide_one,R.layout.activity_slide_two,R.layout.activity_slide_three};
    LinearLayout dots;
    ImageView[] dot;
    Button mSign,mLogin;

    int currentPage = 0,start = 0,end = 0;
    Timer timer;
    final long DELAY_MS = 500;
    final long PERIOD_MS = 1500;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT>=19){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }else{
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        setContentView(R.layout.activity_slide_main);
        mSign = (Button) findViewById(R.id.skipBtn);
        mLogin = (Button) findViewById(R.id.nextBtn);




        viewPager = (ViewPager) findViewById(R.id.pager_image);


        SplashSlider slider = new SplashSlider(SlideMain.this,layouts);
        // hotelImagesadapter.addFragment(new BlankFragment());
        //hotelImagesadapter.addFragment(new BlankFragments());
        viewPager.setAdapter(slider);

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == layouts.length && start == 0) {
                    currentPage = --currentPage;
                    start = 1;
                    end = 0;
                }else if(currentPage < layouts.length && currentPage != 0 && end == 0&& start == 1){
                    currentPage = --currentPage;
                }else if(currentPage == 0 && end == 0 && start == 1){
                    currentPage = 0;
                    end = 1;
                    start = 0;
                }else if(currentPage <= layouts.length&& start == 0){

                    currentPage = ++currentPage;
                }else if(currentPage == 0&& start == 0){

                    currentPage = ++currentPage;
                }else{

                }
                viewPager.setCurrentItem(currentPage, true);
                System.out.println("Count Ci"+currentPage+"st"+start+"en"+end);
            }
        };

        timer = new Timer(); // This will create a new Thread
        timer .schedule(new TimerTask() { // task to be scheduled

            @Override
            public void run() {
                handler.post(Update);
            }
        }, DELAY_MS, PERIOD_MS);
        dots = (LinearLayout) findViewById(R.id.dots_layout);

        createDot(0);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                createDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(SlideMain.this,SignUpActivity.class);
                startActivity(signup);
            }
        });

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(SlideMain.this,LoginNew.class);
                startActivity(login);
            }
        });


    }

    private void createDot(int current){
        if(dots != null){
            dots.removeAllViews();
        }
        dot = new ImageView[layouts.length];
        for (int i =0;i<layouts.length;i++){
            dot[i] = new ImageView(this);
            if(i==current){
                dot[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.active_dots));
            }else {
                dot[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.inactive_dots));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4,0,4,0);
            dots.addView(dot[i],params);
        }
    }
}
