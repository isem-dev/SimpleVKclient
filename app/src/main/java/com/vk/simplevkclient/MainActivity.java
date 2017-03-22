package com.vk.simplevkclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fetchUserData();

        TextView textView = (TextView) findViewById(R.id.hello_text);
        SharedPreferences preferences = getSharedPreferences("VKPref", MODE_PRIVATE);

        textView.setText("Hello "
                + preferences.getString("first_name", "") + " "
                + preferences.getString("last_name", "") + "!"
        );

    }

    private void fetchUserData() {
        Intent intent = new Intent(this, FetchUserDataService.class);
        startService(intent);
        Log.d(LOG_TAG, "fetchUserData()");
    }

}
