package localapp.zingohotels.com.localapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import localapp.zingohotels.com.localapp.ListOfEventsActivity;
import localapp.zingohotels.com.localapp.Model.Category;
import localapp.zingohotels.com.localapp.Model.PagerModel;
import localapp.zingohotels.com.localapp.R;

/**
 * Created by ZingoHotels.com on 23-01-2018.
 */

public class ViewPagerAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<Category> bgImages;
    private LayoutInflater inflater;


    public ViewPagerAdapter(Context context,ArrayList<Category> bgImages)
    {
        this.context = context;
        this.bgImages = bgImages;
        inflater = LayoutInflater.from(context);

    }
    @Override
    public int getCount() {
        if(bgImages.size() < 7)
        {
            return bgImages.size();
        }
        else
        {
            return 7;
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object) ;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View view = inflater.inflate(R.layout.viewpager_adapter_layout,container,false);

        ImageView imageView = (ImageView) view.findViewById(R.id.category_banner);
        TextView categoryName = (TextView) view.findViewById(R.id.banner_category_name);
        TextView noofexperience = (TextView) view.findViewById(R.id.no_of_experience);

        /*imageView.setImageResource(bgImages.get(position).getImage());
        categoryName.setText(bgImages.get(position).getCategoryName());
        noofexperience.setText(bgImages.get(position).getNoofExperience());*/
        //System.out.println("bgImages = "+bgImages.get(position).getCategoriesName());

       /* if(bgImages.get(position).getCategoriesImage() != null && !bgImages.get(position).getCategoriesImage().isEmpty())
        {
            byte[] imagebytes = Base64.decode(bgImages.get(position).getCategoriesImage(),Base64.DEFAULT);
            Bitmap catimage = BitmapFactory.decodeByteArray(imagebytes,0,imagebytes.length);
            imageView.setImageBitmap(catimage);
        }*/

        String img = bgImages.get(position).getCategoriesImage();
        Picasso.with(context).load(img).placeholder(R.drawable.no_image).error(R.drawable.no_image).into(imageView);
        categoryName.setText(bgImages.get(position).getCategoriesName());

        if(view != null)
        {
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ListOfEventsActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("title",bgImages.get(position).getCategoriesName());
                    bundle.putInt("cat_id",bgImages.get(position).getCategoriesId());
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
