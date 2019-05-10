package localapp.zingohotels.com.localapp.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import localapp.zingohotels.com.localapp.Model.Category;
import localapp.zingohotels.com.localapp.R;

/**
 * Created by ZingoHotels.com on 1/30/2018.
 */

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Category> list;

    public CategoryListAdapter(Context context,ArrayList<Category> list)
    {
        this.context = context;
        this.list = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.all_category_list_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //holder.imageView.setImageBitmap(convertToBitmap(list.get(position).getCategoriesImage()));
        Picasso.with(context).load(list.get(position).getCategoriesImage()).placeholder(R.drawable.no_image).error(R.drawable.no_image).into(holder.imageView);
        holder.textView.setText(list.get(position).getCategoriesName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.iv_background);
            textView = (TextView) itemView.findViewById(R.id.tv_title);
        }
    }

    public Bitmap convertToBitmap(String s)
    {
        byte[] imagebytes = Base64.decode(s,Base64.DEFAULT);
        Bitmap catimage = BitmapFactory.decodeByteArray(imagebytes,0,imagebytes.length);
        return catimage;
    }
}
