package localapp.zingohotels.com.localapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ZingoHotels.com on 1/30/2018.
 */

public class Category implements Serializable {

    @SerializedName("CategoriesId")
    private int CategoriesId;
    @SerializedName("CategoriesName")
    private String CategoriesName;
    @SerializedName("city")
    private ActivityCity city;
    @SerializedName("CityId")
    private int CityId;
    @SerializedName("Description")
    private String Description;
    @SerializedName("CategoriesImage")
    private String CategoriesImage;
    @SerializedName("Reviews")
    private String Reviews;
    @SerializedName("StarRating")
    private double StarRating;
    @SerializedName("OrderNo")
    private int OrderNo;
    @SerializedName("subCategoriesList")
    private ArrayList<SubCategories> subCategoriesList;

    public int getCategoriesId() {
        return CategoriesId;
    }

    public void setCategoriesId(int categoriesId) {
        CategoriesId = categoriesId;
    }

    public String getCategoriesName() {
        return CategoriesName;
    }

    public void setCategoriesName(String categoriesName) {
        CategoriesName = categoriesName;
    }

    public ActivityCity getCity() {
        return city;
    }

    public void setCity(ActivityCity city) {
        this.city = city;
    }

    public int getCityId() {
        return CityId;
    }

    public void setCityId(int cityId) {
        CityId = cityId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getCategoriesImage() {
        return CategoriesImage;
    }

    public void setCategoriesImage(String categoriesImage) {
        CategoriesImage = categoriesImage;
    }

    public String getReviews() {
        return Reviews;
    }

    public void setReviews(String reviews) {
        Reviews = reviews;
    }

    public double getStarRating() {
        return StarRating;
    }

    public void setStarRating(double starRating) {
        StarRating = starRating;
    }

    public int getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(int orderNo) {
        OrderNo = orderNo;
    }

    public ArrayList<SubCategories> getSubCategoriesList() {
        return subCategoriesList;
    }

    public void setSubCategoriesList(ArrayList<SubCategories> subCategoriesList) {
        this.subCategoriesList = subCategoriesList;
    }
}
