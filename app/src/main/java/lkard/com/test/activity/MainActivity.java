package lkard.com.test.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import lkard.com.test.Database.UserDBHelper;
import lkard.com.test.MyUser;
import lkard.com.test.R;
import lkard.com.test.util.RequestKey;


public class MainActivity extends FragmentActivity {
    private MyUser myUser;
    private TextView name;
    private TextView lastName;
    private TextView address;
    private TextView email;
    private TextView phoneNumber;
    final String FILENAME = "mUser";
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myUser = new MyUser();
        name = (TextView) findViewById(R.id.usd_tv_name);
        lastName = (TextView) findViewById(R.id.usd_tv_lastname);
        address = (TextView) findViewById(R.id.usd_tv_address);
        email = (TextView) findViewById(R.id.usd_tv_email);
        phoneNumber = (TextView) findViewById(R.id.usd_tv_phone_number);
        showUserData();
    }
    public void addbtnClick(View v){
        UserDBHelper.addUserToDB(this, myUser);
    }
    public void ContactsClick(View v){
        Intent intent = new Intent(this,UsersActivity.class);
        startActivityForResult(intent, RequestKey.USER_KEY);
    }
    public void writerU(View v){
        Context context = getApplicationContext();
        try{
            Log.d("TAG","Write lvl = 1");
            File f = new File(context.getFilesDir(), FILENAME);
            Log.d("TAG","Write lvl = 3");
            FileOutputStream fos = context.openFileOutput(FILENAME, context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(myUser);
            os.close();
            Log.d("TAG", "Write lvl = 4");
        }catch (Exception e){
            Log.d("TAG",e.getMessage());
        }
        showUserData();
    }
    public void readU(View v){
        MyUser user;
        Log.d("TAG","Read lvl = 1");
        Context context = getApplicationContext();
        try{
            FileInputStream fis = context.openFileInput(FILENAME);
            ObjectInputStream is = new ObjectInputStream(fis);
            user = (MyUser) is.readObject();
            if(user!=null){
                myUser = user;
                Log.d("TAG","Read lvl = 1");
                Log.d("TAG",user.toString());
            }
            is.close();
        }catch (Exception e){
            Log.d("TAG",e.getMessage());
        }
        showUserData();
    }

    public void changeBtnClick(View view) {

        Intent intent = new Intent(this, SecondActivity.class);

        startActivityForResult(intent, RequestKey.ADDRESS_KEY | RequestKey.EMAIL_KEY | RequestKey.LASTNAME_KEY | RequestKey.NAME_KEY | RequestKey.PHONE_NUMBER_KEY);
    }
    public void mapActivityStartClick(View view) {
        Intent intent = new Intent(this, LocationActivity.class);
        startActivity(intent);
    }

    public void callBtnClick(View view) {
        String number = myUser.getmPhoneNumber();
        if (number != null && !number.isEmpty()) {
            Toast.makeText(this, number, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + number));
            startActivity(intent);
        } else {
            Toast.makeText(this, R.string.str_incorect_phone_number, Toast.LENGTH_LONG).show();
            return ;
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        myUser =  (MyUser)savedInstanceState.getParcelable("user");
        showUserData();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("user", myUser);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RequestKey.ADDRESS_KEY | RequestKey.NAME_KEY | RequestKey.LASTNAME_KEY | RequestKey.EMAIL_KEY | RequestKey.PHONE_NUMBER_KEY:
                    MyUser user = new MyUser();
                    user.setmName(data.getStringExtra("name"));
                    user.setmPhoneNumber(data.getStringExtra("phone"));
                    user.setmLastname(data.getStringExtra("lastName"));
                    user.setmEmail(data.getStringExtra("email"));
                    user.setmAddress(data.getStringExtra("address"));
                    if(MyUser.isUserInfoCorrect(user)){
                        myUser = user;
                    }else{
                        Toast.makeText(this, R.string.str_incorect_data, Toast.LENGTH_LONG).show();
                    }
                    break;
                case RequestKey.USER_KEY:
                    MyUser mu = data.getParcelableExtra("user");
                    if(MyUser.isUserInfoCorrect(mu)){
                        myUser = mu;
                    }
                    break;
            }
            showUserData();

        } else {
            Toast.makeText(this, R.string.str_incorect_data, Toast.LENGTH_LONG).show();
        }
    }

    public void showUserData() {
        name.setText(getString(R.string.str_name)+ " " + checkData(myUser.getmName()));
        lastName.setText(getString(R.string.str_lastname) + " " + checkData(myUser.getmLastname()));
        address.setText(getString(R.string.str_address) + " " + checkData(myUser.getmAddress()));
        email.setText(getString(R.string.str_email) + " " + checkData(myUser.getmEmail()));
        phoneNumber.setText(getString(R.string.str_phone) + " " + checkData(myUser.getmPhoneNumber()));
    }
    public String checkData(String str){
        if(str!=null&&!str.isEmpty()){
            return str;
        }
        return getString(R.string.str_no_data);
    }
}