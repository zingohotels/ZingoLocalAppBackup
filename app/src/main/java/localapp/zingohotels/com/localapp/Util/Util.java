package localapp.zingohotels.com.localapp.Util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.ActivityChooserView;
import android.util.Base64;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.io.ByteArrayOutputStream;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ZingoHotels.com on 23-01-2018.
 */

public class Util {

    private static Retrofit retrofit = null;
    private static final int PERMISSION_RESULT = 1;


    public static Retrofit getClient()
    {
        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static void hideSoftKeyboard(View view,Context context)
    {
        //View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static Bitmap convertToBitMap(String base64Str)
    {
        byte[] imagebytes = Base64.decode(base64Str,Base64.DEFAULT);
        Bitmap catimage = BitmapFactory.decodeByteArray(imagebytes,0,imagebytes.length);
        return catimage;
    }

    public static String convertToBase64String(Bitmap bm)
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,90,byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        //String s =
        return Base64.encodeToString(bytes,Base64.DEFAULT);
    }

    public static boolean checkPermissionOfCamera(final Context context, final String permission,String msg)
    {
        if(ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
        {
            if(ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,permission))
            {
                //ActivityCompat.requestPermissions((Activity) context,new String[]{permission},PERMISSION_RESULT);
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                alertBuilder.setCancelable(true);
                alertBuilder.setTitle("Permission necessary");
                alertBuilder.setMessage(msg);
                alertBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, PERMISSION_RESULT);
                        System.out.println("true");

                    }
                });
                alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("false");
                    }
                });
                AlertDialog alert = alertBuilder.create();
                alert.show();
                return false;

            }
            else
            {
                ActivityCompat.requestPermissions((Activity) context, new String[]{permission}, PERMISSION_RESULT);
                return true;
            }
        }
        else
        {
            return true;
        }


    }



}
