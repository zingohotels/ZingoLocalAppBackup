package localapp.zingohotels.com.localapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import localapp.zingohotels.com.localapp.Activty.ActivityDetail;
import localapp.zingohotels.com.localapp.CustomImplementations.SortPackageDetails;
import localapp.zingohotels.com.localapp.Model.ActivityModel;
import localapp.zingohotels.com.localapp.Model.PackageDetails;
import localapp.zingohotels.com.localapp.R;
import localapp.zingohotels.com.localapp.Util.Util;

/**
 * Created by ZingoHotels.com on 23-01-2018.
 */

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ActivityModel> list;


    public EventListAdapter(Context context,ArrayList<ActivityModel> list) {

        this.context = context;
        this.list = list;
        System.out.println(list.size());

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.categorywise_list_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ActivityModel activityModel = list.get(position);

        Date date = new Date();
        long todaymiliseconds = date.getTime();
        /*try {
            Date activitydate = new SimpleDateFormat("MM/dd/yyyy").parse(activityModel.getValidTo());*/

            /*long activitymiliseconds = activitydate.getTime();
            if(activitymiliseconds <= todaymiliseconds)
            {*/
                if(activityModel.getActivityImages().size() != 0 && activityModel.getActivityImages().get(0) != null)
                {
                    //holder.mActivityImage.setImageBitmap(Util.convertToBitMap(activityModel.getActivityImages().get(0).getImages()));
                    String image = activityModel.getActivityImages().get(0).getImages();
                    if(image != null) {
                        Picasso.with(context).load(image).placeholder(R.drawable.no_image).error(R.drawable.no_image).into(holder.mActivityImage);
                    }
                }

       /* holder.activity_discount.setText(activityModel.getDiscountPercentage()+" %");
        holder.mDisplayPrice.setText("₹ "+activityModel.getDisplayPrice()+"");
        holder.top_event_selling_price.setText("₹ "+activityModel.getSellingPrice());*/
                holder.top_event_name.setText(activityModel.getActivityName());
                holder.top_event_place.setText(activityModel.getAddress());
                holder.no_of_units_left.setText(activityModel.getAvailability()+" Slots left");
                holder.top_no_of_sellings.setVisibility(View.GONE);
                holder.no_of_units_left.setText("30 tickets left");
                ArrayList<PackageDetails> activityPackages = activityModel.getPackageDetails();
                if(activityPackages != null && activityPackages.size() != 0) {
                    Collections.sort(activityPackages, new SortPackageDetails());

                        holder.activity_discount.setText(activityPackages.get(0).getDiscount()+" % Discount");

                    holder.mDisplayPrice.setText("₹ "+activityPackages.get(0).getDeclaredRate()+"");
                    holder.top_event_selling_price.setText("₹ "+activityPackages.get(0).getSellRate());
                }
            //}
       /* } catch (ParseException e) {
            e.printStackTrace();
        }*/

               /* holder
        holder*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


   public class ViewHolder extends RecyclerView.ViewHolder  {


        TextView mDisplayPrice,top_event_name,top_event_place,top_event_selling_price,no_of_units_left,
                top_no_of_sellings,activity_discount;
        ImageView mActivityImage;
        RatingBar ratingBar;
//

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            itemView.setClickable(true);
            //itemView.setOnClickListener(this);

            mDisplayPrice = itemView.findViewById(R.id.event_display_price);
            //TextView mDisplayPrice = (TextView)itemView.findViewById(R.id.top_event_display_price);
             top_event_name = (TextView)itemView.findViewById(R.id.event_name);
             top_event_place = (TextView)itemView.findViewById(R.id.event_place);
             top_event_selling_price = (TextView)itemView.findViewById(R.id.event_selling_price);
             no_of_units_left = (TextView)itemView.findViewById(R.id.event_no_of_units_left);
             top_no_of_sellings = (TextView)itemView.findViewById(R.id.no_of_sellings);
             activity_discount = (TextView)itemView.findViewById(R.id.activity_discount);
             mActivityImage = (ImageView)itemView.findViewById(R.id.event_banner_image);
             ratingBar = (RatingBar)itemView.findViewById(R.id.top_rating_bar);


        }

        /*@Override
        public void onClick(View v) {


            Intent detail = new Intent(context, ActivityDetail.class);
            context.startActivity(detail);
        }*/
    }

}
