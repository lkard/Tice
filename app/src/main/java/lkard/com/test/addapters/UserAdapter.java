package lkard.com.test.addapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import lkard.com.test.MyUser;
import lkard.com.test.R;

/**
 * Created by LKARD on 02.08.2015.
 */
public class UserAdapter extends ArrayAdapter<MyUser> {
    public UserAdapter(Context context, ArrayList<MyUser> myUsers) {
        super(context, R.layout.user_data_item, myUsers);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View serviceView = layoutInflater.inflate(R.layout.user_data_item, parent, false);

        MyUser myUser = getItem(position);
        TextView textView = (TextView)serviceView.findViewById(R.id.usl_name_lastname);
        textView.setText(myUser.getmName()+ " " + myUser.getmLastname());
        textView = (TextView)serviceView.findViewById(R.id.usl_name_phone);
        textView.setText(myUser.getmPhoneNumber());

        return serviceView;
    }
}
