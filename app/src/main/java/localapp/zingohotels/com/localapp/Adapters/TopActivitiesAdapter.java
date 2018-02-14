package localapp.zingohotels.com.localapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

import localapp.zingohotels.com.localapp.Activty.ActivityDetail;
import localapp.zingohotels.com.localapp.ListOfEventsActivity;
import localapp.zingohotels.com.localapp.Model.ActivityModel;
import localapp.zingohotels.com.localapp.Model.Interests;
import localapp.zingohotels.com.localapp.Model.PagerModel;
import localapp.zingohotels.com.localapp.R;
import localapp.zingohotels.com.localapp.Util.Constants;
import localapp.zingohotels.com.localapp.Util.Util;

/**
 * Created by ZingoHotels.com on 1/29/2018.
 */

public class TopActivitiesAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<ActivityModel> interestsArrayList;
    private LayoutInflater inflater;


    public TopActivitiesAdapter(Context context,ArrayList<ActivityModel> interestsArrayList)//,ArrayList<PagerModel> bgImages)
    {
        this.context = context;
        this.interestsArrayList = interestsArrayList;
        //this.bgImages = bgImages;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {

        if(interestsArrayList.size() < 10)
        {
            return interestsArrayList.size();
        }
        else
        {
            return 10;
        }

        /*bgImages.size();*/
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        ActivityModel activityModel = interestsArrayList.get(position);
        View view = inflater.inflate(R.layout.top_activities_adapter_layout, container, false);
        TextView mDisplayPrice = (TextView)view.findViewById(R.id.top_event_display_price);
        TextView top_event_name = (TextView)view.findViewById(R.id.top_event_name);
        TextView top_event_place = (TextView)view.findViewById(R.id.top_event_place);
        TextView top_event_selling_price = (TextView)view.findViewById(R.id.top_event_selling_price);
        TextView no_of_units_left = (TextView)view.findViewById(R.id.no_of_units_left);
        TextView top_no_of_sellings = (TextView)view.findViewById(R.id.top_no_of_sellings);
        TextView activity_discount = (TextView)view.findViewById(R.id.activity_discount);
        ImageView mActivityImage = (ImageView)view.findViewById(R.id.event_banner_image);
        RatingBar ratingBar = (RatingBar)view.findViewById(R.id.top_rating_bar);

        if(activityModel.getActivityImages().size() != 0 && activityModel.getActivityImages().get(0) != null)
        {
            mActivityImage.setImageBitmap(Util.convertToBitMap(activityModel.getActivityImages().get(0).getImages()));
        }

        mDisplayPrice.setText("₹ "+activityModel.getDisplayPrice()+"");
        top_event_name.setText(activityModel.getActivityName()+"");
        top_event_place.setText(activityModel.getAddress()+"");
        top_event_selling_price.setText("₹ "+activityModel.getSellingPrice()+"");
        //mDisplayPrice.setText(activityModel.getDisplayPrice()+"");
        //mDisplayPrice.setText(activityModel.getDisplayPrice()+"");
        /*double discount = ((activityModel.getDisplayPrice()- activityModel.getSellingPrice())/activityModel.getDisplayPrice())*100;
        System.out.println("discount = "+discount);*/
        NumberFormat df = new DecimalFormat("##");
        activity_discount.setText(df.format(activityModel.getDiscountPercentage())+"% Discunt");
        ratingBar.setRating((float) activityModel.getRatings());

        //mDisplayPrice.setBakgroundResource(R.drawable.text_striker);
        /*mDisplayPrice.setPaintFlags(mDisplayPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);*/

        /*ImageView imageView = (ImageView) view.findViewById(R.id.category_banner);
        TextView categoryName = (TextView) view.findViewById(R.id.banner_category_name);
        TextView noofexperience = (TextView) view.findViewById(R.id.no_of_experience);

        imageView.setImageResource(bgImages.get(position).getImage());
        categoryName.setText(bgImages.get(position).getCategoryName());
        noofexperience.setText(bgImages.get(position).getNoofExperience());

        if (view != null) {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ListOfEventsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("title", bgImages.get(position).getCategoryName());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }*/

        if(view != null)
        {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ActivityDetail.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constants.ACTIVITY,interestsArrayList.get(position));
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}
