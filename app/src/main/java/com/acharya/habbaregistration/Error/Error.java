package com.acharya.habbaregistration.Error;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.acharya.habbaregistration.R;

public class Error extends AppCompatActivity {

    private TextView textView_title, textView_message;
    private String error_title = "N/A",error_message = "N/A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        textView_title = (TextView)findViewById(R.id.message_tittle);
        textView_message = (TextView)findViewById(R.id.message_error);

        Intent mIntent = getIntent();
        Bundle bundle = mIntent.getExtras();
        if (bundle != null) {
            error_title = bundle.getString("error_title");
            error_message = bundle.getString("error_message");
        }

        textView_title.setText(error_title);
        textView_message.setText(error_message);

    }
}
