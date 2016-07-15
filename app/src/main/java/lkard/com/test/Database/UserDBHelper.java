package lkard.com.test.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.ArrayList;

import lkard.com.test.MyUser;

public class UserDBHelper extends SQLiteOpenHelper implements BaseColumns {
    private static final String DATABASE_NAME = "My_Users_BD";
    private static final String TABLE_USERS = "users";
    private static final int DATABASE_VERSION = 1;
    private static final String USER_NAME_COLUMN = "name";
    private static final String USER_LASTNAME_COLUMN = "lastname";
    private static final String USER_EMAIL_COLUMN = "email";
    private static final String USER_ADDRESS_COLUMN = "address";
    private static final String USER_PHONE_COLUMN = "phone";
    private static final String DATABASE_CREATE_SCRIPT = "create table "
            + TABLE_USERS + " (" + BaseColumns._ID + " integer primary key autoincrement, "
            + USER_NAME_COLUMN + " text not null, "
            + USER_PHONE_COLUMN + " text not null, "
            + USER_LASTNAME_COLUMN + " text, "
            + USER_EMAIL_COLUMN + " text, "
            + USER_ADDRESS_COLUMN + " text);";


    public UserDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    public UserDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public static void addUserToDB(Context context,MyUser myUser){
        UserDBHelper udb = new UserDBHelper(context);
        SQLiteDatabase db = udb.getWritableDatabase();
        writeUser(db, myUser);
        udb.close();
    }
    private static void writeUser(SQLiteDatabase db,MyUser myUser){
        if(MyUser.isUserInfoCorrect(myUser)){
            ContentValues cv = new ContentValues();
            cv.put(USER_NAME_COLUMN,myUser.getmName());
            cv.put(USER_LASTNAME_COLUMN,myUser.getmLastname());
            cv.put(USER_EMAIL_COLUMN,myUser.getmEmail());
            cv.put(USER_ADDRESS_COLUMN, myUser.getmAddress());
            cv.put(USER_PHONE_COLUMN,myUser.getmPhoneNumber());
            db.insert(TABLE_USERS,null,cv);
        }
    }
    public static void writeUsers(Context context,ArrayList<MyUser> users){
        UserDBHelper udb = new UserDBHelper(context);
        SQLiteDatabase db = udb.getWritableDatabase();
        for (MyUser user:users ) {
            writeUser(db,user);
        }
        udb.close();
    }
    public static ArrayList<MyUser> readUsers(Context context){
        UserDBHelper udb = new UserDBHelper(context);
        SQLiteDatabase db = udb.getWritableDatabase();
        Cursor c = db.query(TABLE_USERS, null, null, null, null, null, null);
        ArrayList<MyUser> users = new ArrayList<MyUser>();
        if(c.moveToFirst()){
            int ColIndexName = c.getColumnIndex(USER_NAME_COLUMN);
            int ColIndexlastname = c.getColumnIndex(USER_LASTNAME_COLUMN);
            int ColIndexEmail = c.getColumnIndex(USER_EMAIL_COLUMN);
            int ColIndexAddress = c.getColumnIndex(USER_ADDRESS_COLUMN);
            int ColIndexPhone = c.getColumnIndex(USER_PHONE_COLUMN);

            do {
                MyUser user = new MyUser();
                user.setmName(c.getString(ColIndexName));
                user.setmLastname(c.getString(ColIndexlastname));
                user.setmEmail(c.getString(ColIndexEmail));
                user.setmAddress(c.getString(ColIndexAddress));
                user.setmPhoneNumber(c.getString(ColIndexPhone));
                users.add(user);

            } while (c.moveToNext());

        }
        c.close();
        udb.close();
        return users;
    }
}
