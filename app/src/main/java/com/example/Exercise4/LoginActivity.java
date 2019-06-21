package com.example.Exercise4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    private Button connectButton;
    private String ip;
    private int port;
    private EditText ipEditText;
    private EditText portEditText;


    //initialize login activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // find the ip, port and the connect button
        ipEditText = findViewById(R.id.ip_edit_text);
        portEditText = findViewById(R.id.port_edit_text);
        connectButton = findViewById(R.id.connectButton);
        // set an onClickListener to the connect button that will open the joystick activity
        connectButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ip = ipEditText.getText().toString();
                port = Integer.parseInt(portEditText.getText().toString());
                ClientSide.getInstance().setIpAndPort(ip, port);
                openJoystickActivity();
            }
        });
    }
    
    public void openJoystickActivity() {
        Intent intent = new Intent(this, JoystickActivity.class);
        startActivity(intent);
    }
}