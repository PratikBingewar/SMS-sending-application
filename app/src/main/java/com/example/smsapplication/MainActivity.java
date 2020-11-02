package com.example.smsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.telephony.SmsManager;
import android.widget.Toast;
import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_RQST_SEND = 0;
    Button button1;
    EditText phoneNo;
    EditText myMessage;
    String strPhoneNO;
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//We’ll create objects
        button1 = (Button) findViewById(R.id.btnSendSMS);
        phoneNo = (EditText) findViewById(R.id.editText);
        myMessage = (EditText) findViewById(R.id.editText2);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendSMSMessage();
            }
        });
    }
    protected void sendSMSMessage() {
        strPhoneNO = phoneNo.getText().toString(); //we’ll get the phone number from the user
        message = myMessage.getText().toString();//we’ll get the Message from the user
//We’ll check the permission is granted or not . If not we’ll change
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.SEND_SMS)) {
            }
            else { ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, PERMISSION_RQST_SEND);
            }
        }
    }
    //Now once the permission is there or not would be checked
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_RQST_SEND: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(strPhoneNO, null, message, null, null);
                    Toast.makeText(getApplicationContext(), "SMS sent.",Toast.LENGTH_LONG).show();
                } else {Toast.makeText(getApplicationContext(), "SMS failed, you may try again later.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }
}