package lkard.com.test;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.lang.reflect.Array;

public class MyUser implements Parcelable, Serializable{
    private String mName;
    private String mLastname;
    private String mEmail;
    private String mAddress;
    private String mPhoneNumber;

    public static boolean isUserInfoCorrect(MyUser user){
        if(user.mName!=null&&user.mPhoneNumber!=null) {
            return true;
        }
        return false;
    }
    public MyUser(Parcel in) {
        String[] data = new String[5];
        in.readStringArray(data);
        mName = data[0];
        mLastname = data[1];
        mEmail = data[2];
        mAddress = data[3];
        mPhoneNumber = data[4];
    }
    public MyUser(String name,String lastname,String email,String address,String phone){
        mName = name;
        mLastname = lastname;
        mEmail = email;
        mAddress = address;
        mPhoneNumber = phone;
    }
    public MyUser(){
        mName = null;
        mLastname = null;
        mEmail = null;
        mAddress = null;
        mPhoneNumber = null;
    }
    public String getmPhoneNumber() {
        return mPhoneNumber;
    }
    public String getmName() {
        return mName;
    }
    public String getmLastname() {
        return mLastname;
    }
    public String getmEmail() {
        return mEmail;
    }
    public String getmAddress() {
        return mAddress;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        if(mPhoneNumber==null) return;
        if(!mPhoneNumber.isEmpty()){
            this.mPhoneNumber = mPhoneNumber;
        }
        else{
            this.mPhoneNumber = null;
        }

    }
    public void setmName(String mName) {
        if(mName==null) return;
        if(!mName.isEmpty()){
            this.mName = mName;
        }
        else{
            this.mName = null;
        }
    }
    public void setmLastname(String mLastname) {
        if(mLastname==null) return;
        if(!mLastname.isEmpty()){
            this.mLastname = mLastname;
        }
        else{
            this.mLastname = null;
        }
    }
    public void setmEmail(String mEmail) {
        if(mEmail==null) return;
        if(!mEmail.isEmpty()){
            this.mEmail = mEmail;
        }
        else{
            this.mEmail = null;
        }
    }
    public void setmAddress(String mAddress) {
        if(mAddress==null) return;
        if(!mAddress.isEmpty()){
            this.mAddress = mAddress;
        }
        else{
            this.mAddress = null;
        }
    }

    @Override
    public String toString() {

        return "Name = "+(mName!=null?mName:"No Name")+"\n"+
                "LastName = "+(mLastname !=null? mLastname :"No lastname")+"\n"+
                "Email = "+(mEmail!=null?mEmail:"No email address")+"\n"+
                "Phone = "+(mPhoneNumber!=null?mPhoneNumber:"No phone number")+"\n"+
                "Address = "+(mAddress!=null?mAddress:"No address");
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{mName,mLastname,mEmail,mAddress,mPhoneNumber});
    }

    public static final Parcelable.Creator<MyUser> CREATOR = new Parcelable.Creator<MyUser>() {

        @Override
        public MyUser createFromParcel(Parcel source) {
            return new MyUser(source);
        }

        @Override
        public MyUser[] newArray(int size) {
            return new MyUser[size];
        }
    };
}
