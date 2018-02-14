package localapp.zingohotels.com.localapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import localapp.zingohotels.com.localapp.Activty.ActivityDetail;
import localapp.zingohotels.com.localapp.Adapters.CategoryListAdapter;
import localapp.zingohotels.com.localapp.Adapters.ViewPagerAdapter;
import localapp.zingohotels.com.localapp.CustomInterfaces.RecyclerTouchListener;
import localapp.zingohotels.com.localapp.Model.Category;
import localapp.zingohotels.com.localapp.Util.Constants;
import localapp.zingohotels.com.localapp.Util.ThreadExecuter;
import localapp.zingohotels.com.localapp.Util.Util;
import localapp.zingohotels.com.localapp.WebApiClients.CategoryApi;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryListActivity extends AppCompatActivity {

    RecyclerView mCategoryList;
    ArrayList<Category> categoryArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        setTitle("Category List");

        mCategoryList =(RecyclerView) findViewById(R.id.all_category_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CategoryListActivity.this);
        mCategoryList.setLayoutManager(linearLayoutManager);


        /*CategoryListAdapter adapter = new CategoryListAdapter(CategoryListActivity.this,new ArrayList());
        mCategoryList.setAdapter(adapter);*/
        mCategoryList.addOnItemTouchListener(new RecyclerTouchListener(CategoryListActivity.this, mCategoryList, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                /*Intent intent = new Intent(CategoryListActivity.this, ListOfEventsActivity.class);
                *//*Bundle bundle = new Bundle();
                bundle.putSerializable(Constants.ACTIVITY,ActivityArrayList.get(position));
                intent.putExtras(bundle);*//*
                startActivity(intent);*/
                Intent intent = new Intent(CategoryListActivity.this, ListOfEventsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("title",categoryArrayList.get(position).getCategoriesName());
                bundle.putInt("cat_id",categoryArrayList.get(position).getCategoriesId());
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        Bundle bundle = getIntent().getExtras();
        /*if(bundle != null)
        {
            categoryArrayList = (ArrayList<Category>) bundle.getSerializable("Categories");
            if(categoryArrayList != null && categoryArrayList.size() != 0)
            {
                CategoryListAdapter adapter = new CategoryListAdapter(CategoryListActivity.this,categoryArrayList);
                mCategoryList.setAdapter(adapter);
            }
        }*/
        getCategories();

    }


    public void getCategories()
    {
        final ProgressDialog dialog = new ProgressDialog(CategoryListActivity.this);
        dialog.setCancelable(false);
        dialog.show();

        new ThreadExecuter().execute(new Runnable() {
            @Override
            public void run() {
                final CategoryApi categoryAPI = Util.getClient().create(CategoryApi.class);
                Call<ArrayList<Category>> getCat = categoryAPI.getCategories();

                getCat.enqueue(new Callback<ArrayList<Category>>() {
                    //@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    /*@SuppressLint("NewApi")*/
                    @Override
                    public void onResponse(Call<ArrayList<Category>> call, Response<ArrayList<Category>> response) {
                        if(dialog != null)
                        {
                            dialog.dismiss();
                        }
                        if(response.code() == 200)
                        {
                            if(response.body() != null && response.body().size() != 0)
                            {
                                categoryArrayList = response.body();
                                System.out.println("categories = "+categoryArrayList.size());
                                CategoryListAdapter adapter = new CategoryListAdapter(CategoryListActivity.this,categoryArrayList);
                                mCategoryList.setAdapter(adapter);
                                /*ViewPagerAdapter adapter = new ViewPagerAdapter(MainActivity.this,categories);
                                vpPager.setAdapter(adapter);

                                if(categories.size() == 1)
                                {
                                    mFirstBanner.setImageBitmap(convertToBitMap(categories.get(0).getCategoriesImage()));
                                    //mFirstCategory.setAlpha(0.8f);
                                    mFirstCategory.setText(categories.get(0).getCategoriesName());
                                    mSecondBannerFrame.setVisibility(View.GONE);
                                    mThirdBannerFrame.setVisibility(View.GONE);
                                    mMoreBannerFrame.setVisibility(View.GONE);
                                }
                                else if(categories.size()  == 2)
                                {

                                    mFirstBanner.setImageBitmap(convertToBitMap(categories.get(0).getCategoriesImage()));
                                    mFirstCategory.setText(categories.get(0).getCategoriesName());

                                    mSecondBanner.setImageBitmap(convertToBitMap(categories.get(1).getCategoriesImage()));
                                    mSecondCategory.setText(categories.get(1).getCategoriesName());
                                    mThirdBannerFrame.setVisibility(View.GONE);
                                    mMoreBannerFrame.setVisibility(View.GONE);

                                }
                                else if(categories.size() == 3)
                                {
                                    mFirstBanner.setImageBitmap(convertToBitMap(categories.get(0).getCategoriesImage()));
                                    mFirstCategory.setText(categories.get(0).getCategoriesName());

                                    mSecondBanner.setImageBitmap(convertToBitMap(categories.get(1).getCategoriesImage()));
                                    mSecondCategory.setText(categories.get(1).getCategoriesName());

                                    mThirdBanner.setImageBitmap(convertToBitMap(categories.get(2).getCategoriesImage()));
                                    mThirdCategory.setText(categories.get(2).getCategoriesName());

                                    mMoreBannerFrame.setVisibility(View.GONE);
                                }
                                else if(categories.size() > 3)
                                {
                                    mFirstBanner.setImageBitmap(convertToBitMap(categories.get(0).getCategoriesImage()));
                                    mFirstCategory.setText(categories.get(0).getCategoriesName());

                                    mSecondBanner.setImageBitmap(convertToBitMap(categories.get(1).getCategoriesImage()));
                                    mSecondCategory.setText(categories.get(1).getCategoriesName());

                                    mThirdBanner.setImageBitmap(convertToBitMap(categories.get(2).getCategoriesImage()));
                                    mThirdCategory.setText(categories.get(2).getCategoriesName());

                                    //mMoreBannerFrame.setVisibility(View.GONE);
                                }
*/
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
//                            loadRoomCategoriesSpinner();
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Category>> call, Throwable t) {
                        if(dialog != null)
                        {
                            dialog.dismiss();
                        }
                        Toast.makeText(CategoryListActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
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
                CategoryListActivity.this.finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
