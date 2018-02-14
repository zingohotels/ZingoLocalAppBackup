package localapp.zingohotels.com.localapp.Adapters;

/**
 * Created by CSC on 1/29/2018.
 */
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

/**
 * Created by new on 1/25/2018.
 */

public class SplashSlider extends PagerAdapter {

    //Activity activity;

    Context context;
    int[] slideImagesList;
    private LayoutInflater inflater;


    public SplashSlider(Context context, int[] slideImagesList)
    {
        this.context = context;
        this.slideImagesList = slideImagesList;


    }

    @Override
    public int getCount() {
        return slideImagesList.length;
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
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate( slideImagesList[position],container,false);

        container.addView(view);
        return view;


    }
}