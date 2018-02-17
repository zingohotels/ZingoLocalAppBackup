package localapp.zingohotels.com.localapp.Model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ZingoHotels.com on 2/14/2018.
 */

public class InterestId {

    @SerializedName("ZingoInterestId")
    private int ZingoInterestId;

    public int getZingoInterestId() {
        return ZingoInterestId;
    }

    public void setZingoInterestId(int zingoInterestId) {
        ZingoInterestId = zingoInterestId;
    }
}
