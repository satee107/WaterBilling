package com.example.ammulu.waterbilling;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SubBillingActivity extends Activity {
     Button bill,collection,validate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_billing);
        bill=(Button)findViewById(R.id.billing);
        collection=(Button)findViewById(R.id.collection);
        validate=(Button)findViewById(R.id.validate);
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
    }
}
