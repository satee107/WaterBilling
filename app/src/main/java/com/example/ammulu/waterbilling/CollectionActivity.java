package com.example.ammulu.waterbilling;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.ammulu.waterbilling.Network.API;
import com.example.ammulu.waterbilling.Network.ConnectivityReceiver;
import com.example.ammulu.waterbilling.Network.MyApplication;
import com.example.ammulu.waterbilling.Network.VolleySingleton;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CollectionActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {
     Button collectionback,collectionsubmit;
     EditText etcflatno,etcamount;
     String cflatno,camount;
     SharedPreferences shre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        collectionback=(Button)findViewById(R.id.collectionback);
        etcflatno=(EditText)findViewById(R.id.editcflatno);
        etcamount=(EditText)findViewById(R.id.editcamount);
        collectionsubmit=(Button)findViewById(R.id.editcsubmit);

        checkConnection();
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
                    billCollect();
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

    private boolean checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
        return isConnected;
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
        }

        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),message , Snackbar.LENGTH_LONG);
        // snackbar.show();
//        Snackbar snackbar = Snackbar
//                .make(findViewById(R.id.fab), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }
    public void billCollect() {
        //initialize the progress dialog and show it
//        pDialog = new ProgressDialog(getApplicationContext());
//        pDialog.setMessage("Inserting....");
//        pDialog.show();
        if(checkConnection()) {
            String serverURL = API.billCollectUrl;
            StringRequest sr = new StringRequest(Request.Method.POST, serverURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jsonObj = new JSONObject(response);
                        String res = jsonObj.getString("result");
                        if (res.equals("success")) {
                            // pDialog.dismiss();
                            //progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Successfully Inserted", Toast.LENGTH_LONG).show();
                            etcflatno.setText(" ");
                            etcamount.setText(" ");


                        } else {
                            //pDialog.dismiss();
                            //progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(), "Error while Inseerting!!", Toast.LENGTH_LONG).show();
                            etcflatno.setText(" ");
                            etcamount.setText(" ");

                        }
                    } catch (Exception e) {
                        Log.e("ERROR", "EXCEPTION");
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("Billing", "Error: " + error.getMessage());
                    Log.d("Billing", "" + error.getMessage() + "," + error.toString());
                    // pDialog.dismiss();
                    //mView.showMessage(error.getMessage());
                }
            }) {
                @Override
                public Map<String, String> getParams() throws AuthFailureError {
                    cflatno = etcflatno.getText().toString();
                    camount = etcamount.getText().toString();
                    shre = getSharedPreferences("userdetails", MODE_PRIVATE);


                    Map<String, String> data = new HashMap<String, String>();
                    String agentname = shre.getString("loginname", null);
                    data.put("flatno", cflatno);
                    data.put("amount", camount);
                    data.put("bid", agentname);
                    return data;
                }
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Content-Type", "application/json; charset=utf-8");
//                return headers;
//            }
//
//            @Override
//            public String getBodyContentType() {
//                return "application/json";
//            }
            };
            VolleySingleton.getInstance(this).addToRequestQueue(sr);
        }else{

        }

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        showSnack(isConnected);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        MyApplication.getInstance().setConnectivityListener(this);
    }
    public void refresh(){          //refresh is onClick name given to the button
        onRestart();
    }

    @Override
    protected void onRestart() {

        // TODO Auto-generated method stub
        super.onRestart();
        Intent i = new Intent(getApplicationContext(), BillActivity.class);  //your class
        startActivity(i);
        finish();

    }
}
