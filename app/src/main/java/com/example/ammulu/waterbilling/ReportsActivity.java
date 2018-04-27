package com.example.ammulu.waterbilling;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ammulu.waterbilling.Network.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class ReportsActivity extends AppCompatActivity {
   // Spinner spin;
    LinearLayout newsurvey,updatesurvey;
    String month;
    Button b3;
    ProgressBar progressBar;
    SharedPreferences shre;
    TextView presdate,billedraise, billedcollect, totalconsraise, unbilledraise, demandraise, unpaidcollect, collectioncollect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);
       // spin=findViewById(R.id.reportmeter);
        Date date=new Date();
        presdate = (TextView)findViewById(R.id.presdate);
        Calendar cal = Calendar.getInstance();
        month=new SimpleDateFormat("MMM-yyyy").format(date);
        presdate.setText(month);
        //Toast.makeText(getApplicationContext(),month,Toast.LENGTH_LONG).show();
       /* int opt=0;
        switch (month){
            case "Jan":opt=0;
            break;
            case "Feb":opt=1;
            break;
            case "Mar":opt=2;
            break;
            case "Apr":opt=3;
            break;
            case "May":opt=4;
            break;
            case "Jun":opt=5;
            break;
            case "Jul":opt=6;
            break;
            case "Aug":opt=7;
            break;
            case "Sep":opt=8;
            break;
            case "Oct":opt=9;
            break;
            case "Nov":opt=10;
            break;
            case "Dec":opt=11;
            break;
            }
        spin.setSelection(opt);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        Toast.makeText(getApplicationContext(),"January",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/
        progressBar=findViewById(R.id.progress);
        billedraise = findViewById(R.id.billed);
        billedcollect = findViewById(R.id.billpaid);
        totalconsraise = findViewById(R.id.totalcons);
        unbilledraise = findViewById(R.id.unbilled);
        demandraise = findViewById(R.id.demand);
        unpaidcollect = findViewById(R.id.unpaid);
        collectioncollect = findViewById(R.id.collection);
        newsurvey=findViewById(R.id.newsurveyll);
        updatesurvey=findViewById(R.id.updatesurveyll);
        newsurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),ValidateBillingActivity.class);
                startActivity(intent);
            }
        });
        getreports();

        b3 = (Button) findViewById(R.id.b3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });
        updatesurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),ValidateCollectionActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getreports() {
//        if(ConnectivityReceiver.isConnected()==false){
//            checkConnection();
//            // displayMobileDataSettingsDialog(MainActivity.this);
//        }
        //  checkConnection();
        // progressbar.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String serverURL = API.getreportsUrl;
//        String uname=etname.getText().toString().trim();
        shre = getSharedPreferences("userdetails",MODE_PRIVATE);
        String bid = shre.getString("loginname", null);
       // String m=spin.getSelectedItem().toString();
        String url = serverURL+"?bid="+bid;

        final StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            // Getting JSON Array node
                            JSONArray arr = jsonObj.getJSONArray("result");
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject obj = arr.getJSONObject(i);

                                String billrai = obj.getString("billed");
                                String billcollect = obj.getString("billspaid");
                                String totalconsrai = obj.getString("totalcons");
                                String unbillrai = obj.getString("unbilled");
                                String demandrai = obj.getString("demand");
                                String unpaycollect = obj.getString("unpaid");
                                String collect = obj.getString("collection");

                                billedraise.setText(billcollect);
                                billedcollect.setText(billrai);

                                totalconsraise.setText(totalconsrai);
                                unbilledraise.setText(unbillrai);
                                demandraise.setText(demandrai);
                                unpaidcollect.setText(unpaycollect);
                                collectioncollect.setText(collect);


                            }

                        } catch (final JSONException e) {
                            Log.e("MainActivity", "Json parsing error: " + e.getMessage());
                            e.printStackTrace();
                        }

                    }

                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressBar.setVisibility(View.GONE);
                        // error
                        Toast.makeText(getApplicationContext(), "No Network Connection", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Toast.makeText(ReportsActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                return null;
            }
        };
        progressBar.setVisibility(View.VISIBLE);
        queue.add(getRequest);
    }
}
