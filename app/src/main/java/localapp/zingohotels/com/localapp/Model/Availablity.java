package localapp.zingohotels.com.localapp.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZingoHotels.com on 2/17/2018.
 */

public class Availablity {

    @SerializedName("ActivityDate")
    private String ActivityDate;

    @SerializedName("ActivitiesId")
    private int ActivitiesId;

    public String getActivityDate() {
        return ActivityDate;
    }

    public void setActivityDate(String activityDate) {
        ActivityDate = activityDate;
    }

    public int getActivitiesId() {
        return ActivitiesId;
    }

    public void setActivitiesId(int activitiesId) {
        ActivitiesId = activitiesId;
    }
}
