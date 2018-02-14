package localapp.zingohotels.com.localapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by CSC on 10/31/2017.
 */

public class Profile {
    @SerializedName("ProfileId")
    private int profileId;
    @SerializedName("FirstName")
    private String firstName;
    @SerializedName("MiddleName")
    private String middleName;
    @SerializedName("LastName")
    private String lastName;

    @SerializedName("UserName")
    private String userName;
    @SerializedName("Password")
    private String Password;
    @SerializedName("Cities")
    private City _city;

    @SerializedName("UniqueId")
    private String UniqueId;

    @SerializedName("CityId")
    private int CityId;

    @SerializedName("PlaceName")
    private String PlaceName;
    @SerializedName("Email")
    private String email;
    @SerializedName("PhoneNumber")
    private String phoneNumber;
    @SerializedName("Address")
    private String address;
    @SerializedName("PinCode")
    private String pinCode;
    @SerializedName("UserRoles")
    private ArrayList<UserRole> _userRole;
    @SerializedName("documentsList")
    private ArrayList<Documents> profileList;

    @SerializedName("UserRoleId")
    private int UserRoleId;

    public int getCityId() {
        return CityId;
    }

    public void setCityId(int cityId) {
        CityId = cityId;
    }



    public String getPlaceName() {
        return PlaceName;
    }

    public void setPlaceName(String placeName) {
        PlaceName = placeName;
    }

    public String getUniqueId() {
        return UniqueId;
    }

    public void setUniqueId(String uniqueId) {
        UniqueId = uniqueId;
    }



    public int getProfileId() {
        return profileId;
    }

    public void setProfileId(int profileId) {
        this.profileId = profileId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public City get_city() {
        return _city;
    }

    public void set_city(City _city) {
        this._city = _city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public ArrayList<UserRole> get_userRole() {
        return _userRole;
    }

    public void set_userRole(ArrayList<UserRole> _userRole) {
        this._userRole = _userRole;
    }

    public ArrayList<Documents> getProfileList() {
        return profileList;
    }

    public void setProfileList(ArrayList<Documents> profileList) {
        this.profileList = profileList;
    }



    public int getUserRoleId() {
        return UserRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        UserRoleId = userRoleId;
    }


}
