package lkard.com.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import lkard.com.test.Database.UserDBHelper;
import lkard.com.test.MyUser;
import lkard.com.test.R;
import lkard.com.test.Runner.MySynchronizedObj;
import lkard.com.test.addapters.UserAdapter;

/**
 * Created by LKARD on 02.08.2015.
 */
public class UsersActivity extends Activity {
    private ArrayList<MyUser> myUserList;
    private ListView listView;
    ListAdapter listAdapter;
    private Object sunchObj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.users_activity);
        UserDataLoaderAsync DBtask = new UserDataLoaderAsync();
        //myUserList = UserDBHelper.readUsers(this);
        myUserList = new ArrayList<MyUser>();
        synchronized (MySynchronizedObj.USER_ACT_FIRST) {
            listView = (ListView) findViewById(R.id.listView);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    MyUser mu = myUserList.get(position);
                    Intent intent = new Intent();
                    intent.putExtra("user", (Parcelable) mu);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
            DBtask.execute();
            listAdapter = new UserAdapter(this, myUserList);
            listView.setAdapter(listAdapter);
        }
    }
    class UserDataLoaderAsync extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            synchronized (MySynchronizedObj.USER_ACT_FIRST) {
                listView.setAdapter(listAdapter);
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            ArrayList<MyUser> buferUsers = UserDBHelper.readUsers(getApplicationContext());
            synchronized (MySynchronizedObj.USER_ACT_FIRST) {
                myUserList = buferUsers;
                listAdapter = new UserAdapter(getApplicationContext(), myUserList);
            }
            return null;
        }

    }
}
