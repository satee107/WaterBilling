package com.example.ammulu.waterbilling;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ammulu.waterbilling.Network.API;
import com.example.ammulu.waterbilling.Network.ConnectivityReceiver;
import com.example.ammulu.waterbilling.Network.MyApplication;
import com.example.ammulu.waterbilling.Network.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class CollectionActivity extends AppCompatActivity implements ConnectivityReceiver.ConnectivityReceiverListener {
     Button collectionback,collectionsubmit;
     EditText etccanno,etcflatno,etcamount;
     String ccanno,cflatno,camount;
     SharedPreferences shre;
     TextView collecttoas;
     Button getcandetails;
     String uid;
     ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        collecttoas = findViewById(R.id.collecttoast);
        collectionback = (Button) findViewById(R.id.collectionback);
        etcflatno = (EditText) findViewById(R.id.editcflatno);
        etcamount = (EditText) findViewById(R.id.editcamount);
        collectionsubmit = (Button) findViewById(R.id.editcsubmit);
        etccanno = (EditText) findViewById(R.id.editccanno);
        getcandetails = findViewById(R.id.getcan);
        progressBar = findViewById(R.id.progress);
        shre = getSharedPreferences("userdetails", MODE_PRIVATE);
        uid =  shre.getString("loginname", null);
        etcflatno.setEnabled(false);
        etcamount.setEnabled(false);
        getcandetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getrequest();
            }
        });
        checkConnection();
        collectionsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ccanno=etccanno.getText().toString();
                cflatno=etcflatno.getText().toString();
                camount=etcamount.getText().toString();
                if(ccanno.equals("")){
                    etccanno.setError("Enter valid can no");
                    etccanno.setFocusable(true);

                }else if(cflatno.equals("")){
                    etcflatno.setError("Enter valid flat no");
                    etcflatno.setFocusable(true);

                }else if(camount.equals("")) {
                    etcamount.setError("Enter Amount");
                    etcamount.setFocusable(true);

                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CollectionActivity.this);
                    //Uncomment the below code to Set the message and title from the strings.xml file
                    //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

                    //Setting message manually and performing action on button click
                    builder.setMessage("entered Can number,Flat number and Amount is correct?")
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    billCollect();
                                }
                            })
                            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    dialog.cancel();
                                }
                            });

                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    alert.setTitle("Alert !");
                    alert.show();

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

//    @SuppressLint("ValidFragment")
//    public static class FireMissilesDialogFragment extends DialogFragment {
//        @Override
//        public Dialog onCreateDialog(Bundle savedInstanceState) {
//            // Use the Builder class for convenient dialog construction
//            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//            builder.setMessage(R.string.dialog_fire_missiles)
//                    .setPositiveButton(R.string.fire, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            // FIRE ZE MISSILES!
//                        }
//                    })
//                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            // User cancelled the dialog
//                        }
//                    });
//            // Create the AlertDialog object and return it
//            return builder.create();
//        }
//    }

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
                            //Toast.makeText(getApplicationContext(), "Successfully Inserted", Toast.LENGTH_LONG).show();
                            collecttoas.setText(R.string.toastdisplay);
                            Timer t = new Timer(false);
                            t.schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            collecttoas.setText("");
                                        }
                                    });
                                }
                            }, 5000);
                            etccanno.setText(" ");
                            etcflatno.setText(" ");
                            etcamount.setText(" ");


                        } else {
                            //pDialog.dismiss();
                            //progressBar.setVisibility(View.GONE);
                            //Toast.makeText(getApplicationContext(), "Error while Inseerting!!", Toast.LENGTH_LONG).show();
                            collecttoas.setText(R.string.toastdisplay2);
                            collecttoas.setTextColor(Color.RED);
                            etccanno.setText(" ");
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
                    ccanno = etccanno.getText().toString();
                    cflatno = etcflatno.getText().toString();
                    camount = etcamount.getText().toString();
                    shre = getSharedPreferences("userdetails", MODE_PRIVATE);


                    Map<String, String> data = new HashMap<String, String>();
                    String agentname = shre.getString("loginname", null);
                    data.put("canno", ccanno);
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

    public void getrequest() {
        //initialize the progress dialog and show it
//        pDialog = new ProgressDialog(getApplicationContext());
//        pDialog.setMessage("Inserting....");
//        pDialog.show();
        if(checkConnection()) {
            ccanno = etccanno.getText().toString();
            shre = getSharedPreferences("userdetails", MODE_PRIVATE);
            String agentname = shre.getString("loginname", null);
            String serverURL = API.canrequesturl+"?bid="+agentname+"&canno="+ccanno;
            StringRequest sr = new StringRequest(Request.Method.GET, serverURL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressBar.setVisibility(View.GONE);

                    try {
                        JSONObject jsonObj = new JSONObject(response);
                        JSONArray jsonArray = jsonObj.getJSONArray("hh");

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObject1 = jsonArray.getJSONObject(0);
//                        String eid=jsonObject1.getString("id");
                            String mflatno = jsonObject1.getString("flatno");
                            String mamount = jsonObject1.getString("amount");
                            etcflatno.setText(mflatno);
                            etcamount.setText(mamount);
                       }
                    } catch (Exception e) {
                        Log.e("ERROR", "EXCEPTION");
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressBar.setVisibility(View.GONE);

                    VolleyLog.d("Billing", "Error: " + error.getMessage());
                    Log.d("Billing", "" + error.getMessage() + "," + error.toString());
                    // pDialog.dismiss();
                    //mView.showMessage(error.getMessage());
                }
            }) {
                @Override
                public Map<String, String> getParams() throws AuthFailureError {
                    ccanno = etccanno.getText().toString();
                    shre = getSharedPreferences("userdetails", MODE_PRIVATE);

                    Map<String, String> data = new HashMap<String, String>();
                    String agentname = shre.getString("loginname", null);
                    data.put("canno", ccanno);
                    data.put("bid", agentname);
                    return null;
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
            progressBar.setVisibility(View.VISIBLE);
            VolleySingleton.getInstance(this).addToRequestQueue(sr);
        }else{

        }

    }
}
