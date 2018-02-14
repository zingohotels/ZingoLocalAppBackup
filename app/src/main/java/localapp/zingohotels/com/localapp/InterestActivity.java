package localapp.zingohotels.com.localapp;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import localapp.zingohotels.com.localapp.Adapters.InterestAdapter;
import localapp.zingohotels.com.localapp.Adapters.ViewPagerAdapter;
import localapp.zingohotels.com.localapp.CustomViews.CustomGridView;
import localapp.zingohotels.com.localapp.Model.Category;
import localapp.zingohotels.com.localapp.Model.Interests;
import localapp.zingohotels.com.localapp.Util.ThreadExecuter;
import localapp.zingohotels.com.localapp.Util.Util;
import localapp.zingohotels.com.localapp.WebApiClients.CategoryApi;
import localapp.zingohotels.com.localapp.WebApiClients.InterestApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InterestActivity extends AppCompatActivity {

    ImageView mCloseActivityImg;
    CustomGridView customGridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest);

        /*getSupportActionBar().setLogo(R.drawable.close_button);
        getSupportActionBar().setDisplayUseLogoEnabled(true);*/

        mCloseActivityImg = (ImageView) findViewById(R.id.tv_header_title);
        setTitle("Interest");

        customGridView = (CustomGridView) findViewById(R.id.interest_grid_view);



        getInterests();
        mCloseActivityImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InterestActivity.this.finish();
            }
        });
    }

    private void getInterests() {
        final ProgressDialog dialog = new ProgressDialog(InterestActivity.this);
        dialog.setCancelable(false);
        dialog.show();

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {
                //System.out.println(TAG+" thread started");
                final InterestApi interestApi = Util.getClient().create(InterestApi.class);
                Call<ArrayList<Interests>> getCat = interestApi.getInterests();

                getCat.enqueue(new Callback<ArrayList<Interests>>() {
                    //@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    /*@SuppressLint("NewApi")*/
                    //System.out.println("thread inside on response");
                    @Override
                    public void onResponse(Call<ArrayList<Interests>> call, Response<ArrayList<Interests>> response) {
                        if(dialog != null)
                        {
                            dialog.dismiss();
                        }

                        int responsecode = response.code();

                        if(responsecode == 200)
                        {
                            //System.out.println(response.body().size());

                            if(response.body() != null && response.body().size() != 0)
                            {
                                InterestAdapter adapter = new InterestAdapter(InterestActivity.this,response.body());
                                customGridView.setAdapter(adapter);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Interests>> call, Throwable t) {
                        if(dialog != null)
                        {
                            dialog.dismiss();
                        }
                        //System.out.println(TAG+" thread inside on fail");
                        Toast.makeText(InterestActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });


                //System.out.println(TAG+" thread started");

            }

        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id)
        {
            case android.R.id.home:
                InterestActivity.this.finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
