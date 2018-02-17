package localapp.zingohotels.com.localapp.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by ZingoHotels.com on 10-11-2017.
 */

public class PreferenceHandler {

    private SharedPreferences sh;

    private PreferenceHandler() {

    }

    private PreferenceHandler(Context mContext) {
        sh = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    private static PreferenceHandler instance = null;

    /**
     *
     * @param mContext
     * @return {@link PreferenceHandler}
     */
    public synchronized static PreferenceHandler getInstance(Context mContext) {
        if (instance == null) {
            instance = new PreferenceHandler(mContext);
        }
        return instance;
    }

    public void setUserId(int id)
    {
        sh.edit().putInt(Constants.USER_ID,id).apply();
    }

    public int getUserId()
    {
        return sh.getInt(Constants.USER_ID,0);
    }

    public void setUserFullName(String approved)
    {
        sh.edit().putString(Constants.USER_FULL_NAME,approved).apply();
    }

    public String getUserFullName()
    {
        return sh.getString(Constants.USER_FULL_NAME,"");
    }

    public void setUserEmail(String approved)
    {
        sh.edit().putString(Constants.USER_EMAIL,approved).apply();
    }

    public String getUserEmail()
    {
        return sh.getString(Constants.USER_EMAIL,"");
    }

    public void setUserPhone(String approved)
    {
        sh.edit().putString(Constants.USER_PHONE,approved).apply();
    }

    public String getUserPhone()
    {
        return sh.getString(Constants.USER_PHONE,"");
    }


    public String getUserPrefix()
    {
        return sh.getString(Constants.USER_PREFIX,"");
    }
    public void setUserPrefix(String prefix)
    {
        sh.edit().putString(Constants.USER_PREFIX,prefix).apply();
    }

    public String getUserSex()
    {
        return sh.getString(Constants.USER_SEX,"");
    }
    public void setUserSex(String approved)
    {
        sh.edit().putString(Constants.USER_SEX,approved).apply();
    }


    public void clear(){
        sh.edit().clear().apply();

    }

}
