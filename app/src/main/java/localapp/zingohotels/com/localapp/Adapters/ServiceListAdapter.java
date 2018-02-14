package localapp.zingohotels.com.localapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import localapp.zingohotels.com.localapp.Activty.ActivityBook;
import localapp.zingohotels.com.localapp.R;

/**
 * Created by CSC on 1/30/2018.
 */

public class ServiceListAdapter extends RecyclerView.Adapter<ServiceListAdapter.ViewHolder> {
    private Context context;
    /*private ArrayList<Rooms> list;*/
    public ServiceListAdapter(Context context) {

        this.context = context;
        //this.list = list;

    }

    @Override
    public ServiceListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.service_list_recycler, parent, false);
        ServiceListAdapter.ViewHolder viewHolder = new ServiceListAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ServiceListAdapter.ViewHolder holder, int position) {
        /*Rooms dto = list.get(position);
        holder.display_name.setText(dto.getDisplayName());
        holder.room_num.setText(String.valueOf(dto.getRoomId()));
        holder.long_description.setText(dto.getLongDescription());*/
        holder.mServiceBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent book = new Intent(context, ActivityBook.class);
                context.startActivity(book);

            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView mServiceName,mServiceDetail;
        Button mServiceBook;

        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            itemView.setClickable(true);
            itemView.setOnClickListener(this);

            mServiceName = itemView.findViewById(R.id.service_name);
            mServiceDetail = itemView.findViewById(R.id.service_details);
            mServiceBook = itemView.findViewById(R.id.service_book);


        }

        @Override
        public void onClick(View v) {


            /*Intent detail = new Intent(context, ActivityDetail.class);
            context.startActivity(detail);*/
        }
    };

}
