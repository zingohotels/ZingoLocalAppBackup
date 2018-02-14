package localapp.zingohotels.com.localapp.Adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import localapp.zingohotels.com.localapp.R;
import localapp.zingohotels.com.localapp.Util.Util;

/**
 * Created by CSC on 1/29/2018.
 */

public class ActivityAdapter extends PagerAdapter {

    Context context;
    ArrayList<String> activityImages;


    public ActivityAdapter(Context context, ArrayList<String> activityImages)
    {
        this.context = context;
        this.activityImages = activityImages;

        /*options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .resetViewBeforeLoading(true).cacheOnDisk(true)
                .considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565)
                .build();*/
    }

    @Override
    public int getCount() {
        return activityImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View)object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View removableView = (View) object;
        container.removeView(removableView);

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_image_adapter,container,false);
        ImageView actImage = (ImageView) view.findViewById(R.id.activity_image);
        String img = activityImages.get(position);
        actImage.setImageBitmap(Util.convertToBitMap(img));
        //ImageLoader.getInstance().displayImage("file://" + selectedImageList.get(position), selectedImage, options);

        container.addView(view);
        return view;


    }
}