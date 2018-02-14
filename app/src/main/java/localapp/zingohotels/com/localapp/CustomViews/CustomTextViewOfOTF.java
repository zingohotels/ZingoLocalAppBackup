package localapp.zingohotels.com.localapp.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import localapp.zingohotels.com.localapp.R;

/**
 * Created by ZingoHotels.com on 1/26/2018.
 */

public class CustomTextViewOfOTF extends AppCompatTextView {
    String customFont;

    public CustomTextViewOfOTF(Context context, AttributeSet attrs) {
        super(context, attrs);
        style(context, attrs);
    }

    public CustomTextViewOfOTF(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        style(context, attrs);

    }

    private void style(Context context, AttributeSet attrs) {

        TypedArray a = context.obtainStyledAttributes(attrs,
                R.styleable.CustomFontTextView);
        int cf = a.getInteger(R.styleable.CustomFontTextView_fontName, 1);
        System.out.println("cf = " + cf);
        int fontName = 0;
        switch (cf) {

                /*case 1:
                    fontName = R.string.ColonnaMT;
                    break;
                case 2:
                    fontName = R.string.Leprosy;
                    break;
                case 3:
                    fontName = R.string.BomBardment;
                    break;
                case 4:
                    fontName = R.string.mina;
                    break;*/
            case 1:
                fontName = R.string.Yeysk;
                break;
            /*default:
                fontName = R.string.Yeysk;
                break;*/
        }

        customFont = getResources().getString(fontName);

        Typeface tf = Typeface.createFromAsset(context.getAssets(),
                "font/" + customFont + ".otf");
        setTypeface(tf);
        a.recycle();
    }
}
