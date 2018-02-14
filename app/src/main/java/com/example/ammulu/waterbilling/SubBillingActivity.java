package com.example.ammulu.waterbilling;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class SubBillingActivity extends AppCompatActivity {
     Button bill,collection,validate,billback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_billing);
        bill=(Button)findViewById(R.id.billing);
        collection=(Button)findViewById(R.id.collection);
        validate=(Button)findViewById(R.id.validate);
        billback=(Button)findViewById(R.id.subbillback);
        bill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),BillActivity.class);
                startActivity(i);
            }
        });
        collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),CollectionActivity.class);
                startActivity(i);

            }
        });
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),ValidateActivity.class);
                startActivity(i);
            }
        });
        billback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(i);
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent i=new Intent(getApplicationContext(),HomeActivity.class);
        startActivity(i);
        
    }
}
