package com.example.ammulu.waterbilling.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ammulu.waterbilling.Network.API;
import com.example.ammulu.waterbilling.R;
import com.example.ammulu.waterbilling.ValidateBillingActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class BilledFragment extends Fragment {
    TextView biddata,candata,flatnodata,readingdata,oldreadingdata,unitsdata,amountdata,readingdate;
    TableLayout t1;
    ProgressBar progressBar;
    SharedPreferences shre;
    public BilledFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_billed, container, false);
        progressBar=view.findViewById(R.id.progress);
        t1=view.findViewById(R.id.table_btechCourses);
        getValidateBilling();
        return view;
    }
    private void getValidateBilling() {
//        if(ConnectivityReceiver.isConnected()==false){
//            checkConnection();
//           displayMobileDataSettingsDialog(ValidateBillingActivity.this);
//        }
//        checkConnection();
//        progressbar.setVisibility(View.VISIBLE);

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = API.getreagingsUrl;
        shre = getActivity().getSharedPreferences("userdetails",MODE_PRIVATE);
        String bid = shre.getString("loginname", null);
        String serverURL = url+"?bid="+bid;
//        String uname=etname.getText().toString().trim();
//        String upwd=etpwd.getText().toString().trim();
        // String url = serverURL+"?uname="+uname+"&upass="+upwd;
        final StringRequest getRequest = new StringRequest(Request.Method.GET, serverURL,
                new com.android.volley.Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            // Getting JSON Array node
                            JSONArray arr = jsonObj.getJSONArray("readings");
                            for(int i=0;i<arr.length();i++){
                                JSONObject obj= arr.getJSONObject(i);

                                String sbid = obj.getString("buildingid");
                                String scanno = obj.getString("canno");
                                String sflatno = obj.getString("flatno");
                                String sreading = obj.getString("reading");
                                String soldreading = obj.getString("oldreading");
                                String sunits = obj.getString("units");
                                String samount = obj.getString("amount");
                                String sreadingdate = obj.getString("readingdate");

                                TableRow row= new TableRow(getActivity());
                                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                                row.setLayoutParams(lp);

                                biddata = new TextView(getActivity());
                                candata = new TextView(getActivity());
                                flatnodata = new TextView(getActivity());
                                readingdata = new TextView(getActivity());
                                oldreadingdata=new TextView(getActivity());
                                unitsdata=new TextView(getActivity());
                                amountdata=new TextView(getActivity());
                                readingdate = new TextView(getActivity());

                                biddata.setText(sbid);
                                biddata.setTextColor(Color.BLACK);
                                biddata.setPadding(5, 5, 5, 5);
                                biddata.setBackgroundResource(R.drawable.cell_shape_data);

                                candata.setText(scanno);
                                candata.setTextColor(Color.BLACK);
                                candata.setPadding(5, 5, 5, 5);
                                candata.setBackgroundResource(R.drawable.cell_shape_data);

                                flatnodata.setText(sflatno);
                                flatnodata.setTextColor(Color.BLACK);
                                flatnodata.setPadding(5, 5, 5, 5);
                                flatnodata.setBackgroundResource(R.drawable.cell_shape_data);

                                readingdata.setText(sreading);
                                readingdata.setTextColor(Color.BLACK);
                                readingdata.setPadding(5, 5, 5, 5);
                                readingdata.setBackgroundResource(R.drawable.cell_shape_data);

                                oldreadingdata.setText(soldreading);
                                oldreadingdata.setTextColor(Color.BLACK);
                                oldreadingdata.setPadding(5, 5, 5, 5);
                                oldreadingdata.setBackgroundResource(R.drawable.cell_shape_data);

                                unitsdata.setText(sunits);
                                unitsdata.setTextColor(Color.BLACK);
                                unitsdata.setPadding(5, 5, 5, 5);
                                unitsdata.setBackgroundResource(R.drawable.cell_shape_data);

                                amountdata.setText(samount);
                                amountdata.setTextColor(Color.BLACK);
                                amountdata.setPadding(5, 5, 5, 5);
                                amountdata.setBackgroundResource(R.drawable.cell_shape_data);

                                readingdate.setText(sreadingdate);
                                readingdate.setTextColor(Color.BLACK);
                                readingdate.setPadding(5, 5, 5, 5);
                                readingdate.setBackgroundResource(R.drawable.cell_shape_data);

                                row.addView(biddata);
                                row.addView(candata);
                                row.addView(flatnodata);
                                row.addView(readingdata);
                                row.addView(oldreadingdata);
                                row.addView(unitsdata);
                                row.addView(amountdata);
                                row.addView(readingdate);

                                t1.addView(row);



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
                        Toast.makeText(getActivity(), "No Network Connection", Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() {
                Toast.makeText(getActivity(), "Successful", Toast.LENGTH_SHORT).show();
                return null;
            }
        };
        progressBar.setVisibility(View.VISIBLE);
        queue.add(getRequest);
    }

}
