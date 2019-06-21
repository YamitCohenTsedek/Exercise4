package com.example.Exercise4;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class JoystickActivity extends AppCompatActivity {

    //initialize joystick activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ClientSide.getInstance().Connect();
        setContentView(R.layout.activity_joystick);
    }

    // when the activity is destroyed, close the connection to the server
    protected void onDestroy()
    {
        super.onDestroy();
        ClientSide.getInstance().closeSocket();
    }
}