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

import java.util.ArrayList;

import localapp.zingohotels.com.localapp.Activty.ActivityDetail;
import localapp.zingohotels.com.localapp.Model.ActivityModel;
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

        if(activityModel.getActivityImages().size() != 0 && activityModel.getActivityImages().get(0) != null)
        {
            holder.mActivityImage.setImageBitmap(Util.convertToBitMap(activityModel.getActivityImages().get(0).getImages()));
        }

        holder.activity_discount.setText(activityModel.getDiscountPercentage()+" %");
        holder.mDisplayPrice.setText("₹ "+activityModel.getDisplayPrice()+"");
        holder.top_event_selling_price.setText("₹ "+activityModel.getSellingPrice());
        holder.top_event_name.setText(activityModel.getActivityName());
        holder.top_event_place.setText(activityModel.getAddress());
                holder.top_no_of_sellings.setVisibility(View.GONE);
        holder.no_of_units_left.setText("30 tickets left");
               /* holder
        holder*/
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder  {


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
