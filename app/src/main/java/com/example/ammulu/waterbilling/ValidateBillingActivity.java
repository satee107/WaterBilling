package com.example.ammulu.waterbilling;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
import com.example.ammulu.waterbilling.Fragments.BilledFragment;
import com.example.ammulu.waterbilling.Fragments.CollectionValidateFragment;
import com.example.ammulu.waterbilling.Fragments.CollectionValidateunpaidFragment;
import com.example.ammulu.waterbilling.Fragments.UnBilledFragment;
import com.example.ammulu.waterbilling.Network.API;
import com.example.ammulu.waterbilling.Network.ConnectivityReceiver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.ammulu.waterbilling.Network.Conn.displayMobileDataSettingsDialog;

public class ValidateBillingActivity extends AppCompatActivity {
      TextView biddata,candata,flatnodata,readingdata,readingdate;
      TableLayout t1;
      ProgressBar progressBar;
    ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_billing);

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

//        progressBar=findViewById(R.id.progress);
//        t1=findViewById(R.id.table_btechCourses);

        //getValidateBilling();

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new BilledFragment(), "Raised Bills");
        adapter.addFrag(new UnBilledFragment(), "UnRaised Bills");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

//    private void getValidateBilling() {
////        if(ConnectivityReceiver.isConnected()==false){
////            checkConnection();
////           displayMobileDataSettingsDialog(ValidateBillingActivity.this);
////        }
////        checkConnection();
////        progressbar.setVisibility(View.VISIBLE);
//
//        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//        String serverURL = API.getreagingsUrl;
////        String uname=etname.getText().toString().trim();
////        String upwd=etpwd.getText().toString().trim();
//       // String url = serverURL+"?uname="+uname+"&upass="+upwd;
//        final StringRequest getRequest = new StringRequest(Request.Method.GET, serverURL,
//                new com.android.volley.Response.Listener<String>() {
//
//                    @Override
//                    public void onResponse(String response) {
//                        progressBar.setVisibility(View.GONE);
//
//                        try {
//                            JSONObject jsonObj = new JSONObject(response);
//                            // Getting JSON Array node
//                            JSONArray arr = jsonObj.getJSONArray("readings");
//                            for(int i=0;i<arr.length();i++){
//                                JSONObject obj= arr.getJSONObject(i);
//
//                                String sbid = obj.getString("buildingid");
//                                String scanno = obj.getString("canno");
//                                String sflatno = obj.getString("flatno");
//                                String sreading = obj.getString("reading");
//                                String sreadingdate = obj.getString("readingdate");
//
//                                TableRow row= new TableRow(getApplicationContext());
//                                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
//                                row.setLayoutParams(lp);
//
//                                biddata = new TextView(getApplicationContext());
//                                candata = new TextView(getApplicationContext());
//                                flatnodata = new TextView(getApplicationContext());
//                                readingdata = new TextView(getApplicationContext());
//                                readingdate = new TextView(getApplicationContext());
//
//                                biddata.setText(sbid);
//                                biddata.setTextColor(Color.BLACK);
//                                biddata.setPadding(5, 5, 5, 5);
//                                biddata.setBackgroundResource(R.drawable.cell_shape_data);
//
//                                candata.setText(scanno);
//                                candata.setTextColor(Color.BLACK);
//                                candata.setPadding(5, 5, 5, 5);
//                                candata.setBackgroundResource(R.drawable.cell_shape_data);
//
//                                flatnodata.setText(sflatno);
//                                flatnodata.setTextColor(Color.BLACK);
//                                flatnodata.setPadding(5, 5, 5, 5);
//                                flatnodata.setBackgroundResource(R.drawable.cell_shape_data);
//
//                                readingdata.setText(sreading);
//                                readingdata.setTextColor(Color.BLACK);
//                                readingdata.setPadding(5, 5, 5, 5);
//                                readingdata.setBackgroundResource(R.drawable.cell_shape_data);
//
//                                readingdate.setText(sreadingdate);
//                                readingdate.setTextColor(Color.BLACK);
//                                readingdate.setPadding(5, 5, 5, 5);
//                                readingdate.setBackgroundResource(R.drawable.cell_shape_data);
//
//                                row.addView(biddata);
//                                row.addView(candata);
//                                row.addView(flatnodata);
//                                row.addView(readingdata);
//                                row.addView(readingdate);
//
//                                t1.addView(row);
//
//
//
//                            }
//
//                        } catch (final JSONException e) {
//                            Log.e("MainActivity", "Json parsing error: " + e.getMessage());
//                            e.printStackTrace();
//                        }
//
//                    }
//
//                },
//                new com.android.volley.Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        progressBar.setVisibility(View.GONE);
//                        // error
//                        Toast.makeText(getApplicationContext(), "No Network Connection", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        ){
//            @Override
//            protected Map<String, String> getParams() {
//                Toast.makeText(ValidateBillingActivity.this, "Successful", Toast.LENGTH_SHORT).show();
//                return null;
//            }
//        };
//        progressBar.setVisibility(View.VISIBLE);
//        queue.add(getRequest);
//    }
}
