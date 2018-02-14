package com.example.ammulu.waterbilling;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CollectionActivity extends AppCompatActivity {
     Button collectionback,collectionsubmit;
     EditText etcflatno,etcamount;
     String cflatno,camount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        collectionback=(Button)findViewById(R.id.collectionback);
        etcflatno=(EditText)findViewById(R.id.editcflatno);
        etcamount=(EditText)findViewById(R.id.editcamount);
        collectionsubmit=(Button)findViewById(R.id.editcsubmit);
        collectionsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cflatno=etcflatno.getText().toString();
                camount=etcamount.getText().toString();
                if(cflatno.equals("")){
                    etcflatno.setError("Enter valid flat no");
                    etcflatno.setFocusable(true);

                }else if(camount.equals("")) {
                    etcamount.setError("Enter Amount");
                    etcamount.setFocusable(true);

                }else {
                    Toast.makeText(getApplicationContext(),"Submitted Successfully",Toast.LENGTH_LONG).show();
                }
            }
        });
        collectionback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),SubBillingActivity.class);
                startActivity(i);
            }
        });
    }
}
