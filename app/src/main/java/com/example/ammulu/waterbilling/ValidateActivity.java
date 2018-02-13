package com.example.ammulu.waterbilling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ValidateActivity extends AppCompatActivity {
Button vbilling,vcollection;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate);
        vbilling=(Button)findViewById(R.id.vbilling);
        vcollection=(Button)findViewById(R.id.vcollection);
        vbilling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),ValidateBillingActivity.class);
                startActivity(i);
            }
        });
        vcollection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),ValidateCollectionActivity.class);
                startActivity(i);

            }
        });
    }
}
