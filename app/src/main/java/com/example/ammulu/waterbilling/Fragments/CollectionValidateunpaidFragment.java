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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


public class CollectionValidateunpaidFragment extends Fragment {
    TextView cbid,cflatno,camount,cpaid,cmeter;
    TableLayout t1;
    ProgressBar progressBar;
SharedPreferences shre;
    public CollectionValidateunpaidFragment() {
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
        View view=inflater.inflate(R.layout.fragment_collection_validateunpaid, container, false);
        t1=view.findViewById(R.id.table_btechCourses);
        progressBar=view.findViewById(R.id.progress);
        getValidateCollections();
        return view;
    }

    private void getValidateCollections() {
//        if(ConnectivityReceiver.isConnected()==false){
//            checkConnection();
//            // displayMobileDataSettingsDialog(MainActivity.this);
//        }
        //  checkConnection();
        // progressbar.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url = API.getunpaidamountstUrl;
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
                            JSONArray arr = jsonObj.getJSONArray("amounts");
                            for(int i=0;i<arr.length();i++){
                                JSONObject obj= arr.getJSONObject(i);

                                String sbid = obj.getString("buildingno");
                                String sflatno = obj.getString("name");
                                String samount = obj.getString("flatno");
                                String spaiddate = obj.getString("phone");
                                String smeterno=obj.getString("meterno");

                                TableRow row= new TableRow(getActivity());
                                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                                row.setLayoutParams(lp);
                                cbid=new TextView(getActivity());
                                cflatno=new TextView(getActivity());
                                camount=new TextView(getActivity());
                                cpaid=new TextView(getActivity());
                                cmeter=new TextView(getActivity());

                                cbid.setText(sbid);
                                cbid.setTextColor(Color.BLACK);
                                cbid.setPadding(5, 5, 5, 5);
                                cbid.setBackgroundResource(R.drawable.cell_shape_data);

                                cflatno.setText(sflatno);
                                cflatno.setTextColor(Color.BLACK);
                                cflatno.setPadding(5, 5, 5, 5);
                                cflatno.setBackgroundResource(R.drawable.cell_shape_data);

                                camount.setText(samount);
                                camount.setTextColor(Color.BLACK);
                                camount.setPadding(5, 5, 5, 5);
                                camount.setBackgroundResource(R.drawable.cell_shape_data);

                                cpaid.setText(spaiddate);
                                cpaid.setTextColor(Color.BLACK);
                                cpaid.setPadding(5, 5, 5, 5);
                                cpaid.setBackgroundResource(R.drawable.cell_shape_data);

                                cmeter.setText(smeterno);
                                cmeter.setTextColor(Color.BLACK);
                                cmeter.setPadding(5, 5, 5, 5);
                                cmeter.setBackgroundResource(R.drawable.cell_shape_data);

                                row.addView(cbid);
                                row.addView(cflatno);
                                row.addView(camount);
                                row.addView(cpaid);
                                row.addView(cmeter);

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
