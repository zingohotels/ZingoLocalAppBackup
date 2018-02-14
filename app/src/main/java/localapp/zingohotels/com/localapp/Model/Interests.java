package localapp.zingohotels.com.localapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ZingoHotels.com on 1/30/2018.
 */

public class Interests implements Serializable {

    @SerializedName("InterestsId")
    private int InterestsId;
    @SerializedName("InterestName")
    private String InterestName;
    @SerializedName("Description")
    private String Description;
    @SerializedName("activities")
    private ActivityModel activities;
    @SerializedName("ActivitiesId")
    private int ActivitiesId;

    public int getInterestsId() {
        return InterestsId;
    }

    public void setInterestsId(int interestsId) {
        InterestsId = interestsId;
    }

    public String getInterestName() {
        return InterestName;
    }

    public void setInterestName(String interestName) {
        InterestName = interestName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public ActivityModel getActivities() {
        return activities;
    }

    public void setActivities(ActivityModel activities) {
        this.activities = activities;
    }

    public int getActivitiesId() {
        return ActivitiesId;
    }

    public void setActivitiesId(int activitiesId) {
        ActivitiesId = activitiesId;
    }
}
