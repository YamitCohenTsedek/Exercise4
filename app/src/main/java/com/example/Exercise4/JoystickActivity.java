package com.example.Exercise4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class JoystickActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ClientSide.getInstance().Connect();
        // JoystickView joystickView = new JoystickView(this);
        setContentView(R.layout.activity_joystick);
    }
    protected void onDestroy()
    {
        super.onDestroy();
        ClientSide.getInstance().closeSocket();
    }
}