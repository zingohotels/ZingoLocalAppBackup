package localapp.zingohotels.com.localapp.Model;

/**
 * Created by ZingoHotels.com on 23-01-2018.
 */

public class PagerModel {
    private int image;
    private String categoryName;
    private String noofExperience;

    public PagerModel(int image,String categoryName,String noofExperience)
    {
        this.image = image;
        this.categoryName = categoryName;
        this.noofExperience = noofExperience;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getNoofExperience() {
        return noofExperience;
    }

    public void setNoofExperience(String noofExperience) {
        this.noofExperience = noofExperience;
    }
}
