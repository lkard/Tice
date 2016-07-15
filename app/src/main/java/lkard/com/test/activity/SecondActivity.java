package lkard.com.test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import lkard.com.test.R;

public class SecondActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
    }
    public void OnGoBackBtnClick(View view){
        Intent intent = new Intent();
        intent.putExtra("name",((EditText)findViewById(R.id.as_et_name)).getText().toString());
        intent.putExtra("lastName",((EditText)findViewById(R.id.as_et_lastName)).getText().toString());
        intent.putExtra("email",((EditText)findViewById(R.id.as_et_email)).getText().toString());
        intent.putExtra("address",((EditText)findViewById(R.id.as_et_address)).getText().toString());
        intent.putExtra("phone",((EditText)findViewById(R.id.as_et_phone_number)).getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
    public void OnCancelBtnClick(View view){
        Intent intent = new Intent();
        setResult(RESULT_CANCELED,intent);
        finish();
    }
}
