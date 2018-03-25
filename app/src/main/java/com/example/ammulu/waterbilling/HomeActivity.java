package com.example.ammulu.waterbilling;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {
    Button billing,reports,logout;
    SharedPreferences shre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        billing = (Button)findViewById(R.id.billingbtn);
        reports = (Button)findViewById(R.id.reports);
        logout = (Button)findViewById(R.id.logout);
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //String imgSett = prefs.getString(keyChannel, "");

        if(shre==null) {
            shre = getSharedPreferences("userdetails", MODE_PRIVATE);
            String loginuname = shre.getString("loginname", null);
            if (loginuname == "" || loginuname == null) {
                Intent i = new Intent(this, MainActivity.class);
                startActivity(i);
            }
        }

        billing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),SubBillingActivity.class);
                startActivity(i);
            }
        });

        reports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),ReportsActivity.class);
                startActivity(i);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HomeActivity.this,MainActivity.class);
                getApplicationContext().getSharedPreferences("userdetails", 0).edit().clear().commit();
                startActivity(i);
            }
        });
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
