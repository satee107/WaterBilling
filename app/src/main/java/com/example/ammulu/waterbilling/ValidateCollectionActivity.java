package com.example.ammulu.waterbilling;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import java.util.Map;

public class ValidateCollectionActivity extends AppCompatActivity {
     TextView cbid,cflatno,camount,cpaid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_collection);
        cbid=findViewById(R.id.collbiddata);
        cflatno=findViewById(R.id.collflatnodata);
        camount=findViewById(R.id.collamountdata);
        cpaid=findViewById(R.id.collpaiddate);

        getValidateCollections();
    }
    private void getValidateCollections() {
//        if(ConnectivityReceiver.isConnected()==false){
//            checkConnection();
//            // displayMobileDataSettingsDialog(MainActivity.this);
//        }
        //  checkConnection();
        // progressbar.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String serverURL = API.getamountstUrl;
//        String uname=etname.getText().toString().trim();
//        String upwd=etpwd.getText().toString().trim();
        // String url = serverURL+"?uname="+uname+"&upass="+upwd;
        final StringRequest getRequest = new StringRequest(Request.Method.GET, serverURL,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            // Getting JSON Array node
                            JSONArray arr = jsonObj.getJSONArray("amounts");
                            for(int i=0;i<arr.length();i++){
                                JSONObject obj= arr.getJSONObject(i);

                                String sbid = obj.getString("bid");
                                String sflatno = obj.getString("flatno");
                                String samount = obj.getString("amount");
                                String spaiddate = obj.getString("paiddate");

                                cbid.setText(sbid);
                                cflatno.setText(sflatno);
                                camount.setText(samount);
                                cpaid.setText(spaiddate);

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
                        // error
                        Toast.makeText(getApplicationContext(), "No Network Connection", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() {
                Toast.makeText(ValidateCollectionActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                return null;
            }
        };
        queue.add(getRequest);
    }
}
