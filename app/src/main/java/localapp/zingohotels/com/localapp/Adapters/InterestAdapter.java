package localapp.zingohotels.com.localapp.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import localapp.zingohotels.com.localapp.Model.Interests;
import localapp.zingohotels.com.localapp.R;

/**
 * Created by ZingoHotels.com on 1/29/2018.
 */

public class InterestAdapter extends BaseAdapter {



        Context context;
        ArrayList<Interests> interests;
        //String floorNo = null;
    /*ArrayList<Boolean> selectedRoomsBoolean;*/
        //boolean roomSelected = true;
    public InterestAdapter(Context context , ArrayList<Interests> interests)//, ArrayList<SelectingRoomModel> rooms)
        {
            this.context = context;
            this.interests = interests;
        }
        @Override
        public int getCount() {
            //System.out.println("class SelectRoomGridViewAdapter = "+rooms.size());
            return interests.size();
            //return 20;//rooms.size();

        }

        @Override
        public Object getItem(int position) {
            return position;//rooms.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null)
            {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.interest_gridview_layout,parent,false);
            }

            TextView interstname = convertView.findViewById(R.id.interest_name);
            interstname.setText(interests.get(position).getInterestName());
            return convertView;
        }
}
