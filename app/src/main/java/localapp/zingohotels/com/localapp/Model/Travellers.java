package localapp.zingohotels.com.localapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ZingoHotels.com on 1/30/2018.
 */

public class Travellers implements Serializable {

    @SerializedName("TravellerId")
    private int TravellerId;
    @SerializedName("FirstName")
    private String FirstName;
    @SerializedName("MiddleName")
    private String MiddleName;
    @SerializedName("LastName")
    private String LastName;
    @SerializedName("Gender")
    private String Gender;
    @SerializedName("DOB")
    private String DOB;
    @SerializedName("Email")
    private String Email;
    @SerializedName("PhoneNumber")
    private String PhoneNumber;
    @SerializedName("Address")
    private String Address;
    @SerializedName("PinCode")
    private String PinCode;
    @SerializedName("ProfileId")
    private int ProfileId;//why
    @SerializedName("UserRoleId")
    private int UserRoleId;
    @SerializedName("PlaceName")
    private String PlaceName;
    @SerializedName("bookingList")
    private ArrayList<Bookings> bookingList;


    public int getTravellerId() {
        return TravellerId;
    }

    public void setTravellerId(int travellerId) {
        TravellerId = travellerId;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getMiddleName() {
        return MiddleName;
    }

    public void setMiddleName(String middleName) {
        MiddleName = middleName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPinCode() {
        return PinCode;
    }

    public void setPinCode(String pinCode) {
        PinCode = pinCode;
    }

    public int getProfileId() {
        return ProfileId;
    }

    public void setProfileId(int profileId) {
        ProfileId = profileId;
    }

    public int getUserRoleId() {
        return UserRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        UserRoleId = userRoleId;
    }

    public String getPlaceName() {
        return PlaceName;
    }

    public void setPlaceName(String placeName) {
        PlaceName = placeName;
    }

    public ArrayList<Bookings> getBookingList() {
        return bookingList;
    }

    public void setBookingList(ArrayList<Bookings> bookingList) {
        this.bookingList = bookingList;
    }
}
