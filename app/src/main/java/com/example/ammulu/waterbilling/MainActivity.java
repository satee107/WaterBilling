package com.example.ammulu.waterbilling;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.ammulu.waterbilling.Network.Conn;
import com.example.ammulu.waterbilling.Network.ConnectivityReceiver;

import static com.example.ammulu.waterbilling.Network.Conn.displayMobileDataSettingsDialog;

public class MainActivity extends AppCompatActivity {
    Button signin;
    EditText etname,etpwd;
    String uname,upwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etname=(EditText)findViewById(R.id.editname);
        etpwd=(EditText)findViewById(R.id.editpwd);
        signin=(Button)findViewById(R.id.signin);
//        checkConnection();
//        if(ConnectivityReceiver.isConnected()==false){
//            //checkConnection();
//            displayMobileDataSettingsDialog(MainActivity.this);
//        }
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uname=etname.getText().toString();
                upwd=etpwd.getText().toString();
                if(uname.equals("")||uname.length()<3){
                    etname.setError("Enter min 3 chars username");
                    etpwd.setFocusable(true);

                }else if(upwd.equals("")) {
                    etpwd.setError("Enter valid Mobile no");
                    etpwd.setFocusable(true);

                }else {
                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(i);
                }
            }
        });
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
    }
    // Showing the status in Snackbar
    private void showSnack(boolean isConnected) {
        String message;
        int color;
        if (isConnected) {
            message = "Good! Connected to Internet";
            color = Color.WHITE;
        } else {
            message = "Sorry! Not connected to internet";
            color = Color.RED;
        } Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),message , Snackbar.LENGTH_LONG);
        // snackbar.show();
//        Snackbar snackbar = Snackbar
//                .make(findViewById(R.id.fab), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }
    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        //@Override
        //public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        // }
    }
}
