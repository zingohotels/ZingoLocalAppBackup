package localapp.zingohotels.com.localapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by ZingoHotels.com on 1/26/2018.
 */

public class UserProfile implements Serializable{

    @SerializedName("ProfileId")
    private int ProfileId;

    @SerializedName("Prefix")
    private String Prefix;
    @SerializedName("FullName")
    private String FullName;
    @SerializedName("Password")
    private String Password;
    @SerializedName("Gender")
    private String Gender;
    @SerializedName("Email")
    private String Email;
    @SerializedName("PhoneNumber")
    private String PhoneNumber;
    @SerializedName("UserRoleId")
    private int UserRoleId;
    @SerializedName("UserRoles")
    private UserRole UserRoles;
    @SerializedName("ProfilePhoto")
    private String ProfilePhoto;


    public int getProfileId() {
        return ProfileId;
    }

    public void setProfileId(int profileId) {
        ProfileId = profileId;
    }

    public String getPrefix() {
        return Prefix;
    }

    public void setPrefix(String prefix) {
        Prefix = prefix;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
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

    public int getUserRoleId() {
        return UserRoleId;
    }

    public void setUserRoleId(int userRoleId) {
        UserRoleId = userRoleId;
    }

    public UserRole getUserRoles() {
        return UserRoles;
    }

    public void setUserRoles(UserRole userRoles) {
        UserRoles = userRoles;
    }

    public String getProfilePhoto() {
        return ProfilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        ProfilePhoto = profilePhoto;
    }
}
