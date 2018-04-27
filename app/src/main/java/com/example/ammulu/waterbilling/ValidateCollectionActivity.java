package com.example.ammulu.waterbilling;

import android.app.Activity;
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
import com.example.ammulu.waterbilling.Fragments.CollectionValidateFragment;
import com.example.ammulu.waterbilling.Fragments.CollectionValidateunpaidFragment;
import com.example.ammulu.waterbilling.Network.API;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ValidateCollectionActivity extends AppCompatActivity {
     TextView cbid,cflatno,camount,cpaid;
    TableLayout t1;
    ProgressBar progressBar;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validate_collection);

        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
//        cbid=findViewById(R.id.collbiddata);
//        cflatno=findViewById(R.id.collflatnodata);
//        camount=findViewById(R.id.collamountdata);
//        cpaid=findViewById(R.id.collpaiddate);


//        t1=findViewById(R.id.table_btechCourses);
//        progressBar=findViewById(R.id.progress);
//
//        getValidateCollections();
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new CollectionValidateFragment(), "Paid Amount");
        adapter.addFrag(new CollectionValidateunpaidFragment(), "UnPaid Amount");
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



//    private void getValidateCollections() {
////        if(ConnectivityReceiver.isConnected()==false){
////            checkConnection();
////            // displayMobileDataSettingsDialog(MainActivity.this);
////        }
//        //  checkConnection();
//        // progressbar.setVisibility(View.VISIBLE);
//        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//        String serverURL = API.getamountstUrl;
////        String uname=etname.getText().toString().trim();
////        String upwd=etpwd.getText().toString().trim();
//        // String url = serverURL+"?uname="+uname+"&upass="+upwd;
//        final StringRequest getRequest = new StringRequest(Request.Method.GET, serverURL,
//                new com.android.volley.Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        progressBar.setVisibility(View.GONE);
//
//                        try {
//                            JSONObject jsonObj = new JSONObject(response);
//                            // Getting JSON Array node
//                            JSONArray arr = jsonObj.getJSONArray("amounts");
//                            for(int i=0;i<arr.length();i++){
//                                JSONObject obj= arr.getJSONObject(i);
//
//                                String sbid = obj.getString("bid");
//                                String sflatno = obj.getString("flatno");
//                                String samount = obj.getString("amount");
//                                String spaiddate = obj.getString("paiddate");
//
//                                TableRow row= new TableRow(getApplicationContext());
//                                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
//                                row.setLayoutParams(lp);
//                                cbid=new TextView(getApplicationContext());
//                                cflatno=new TextView(getApplicationContext());
//                                camount=new TextView(getApplicationContext());
//                                cpaid=new TextView(getApplicationContext());
//
//                                cbid.setText(sbid);
//                                cbid.setTextColor(Color.BLACK);
//                                cbid.setPadding(5, 5, 5, 5);
//                                cbid.setBackgroundResource(R.drawable.cell_shape_data);
//
//                                cflatno.setText(sflatno);
//                                cflatno.setTextColor(Color.BLACK);
//                                cflatno.setPadding(5, 5, 5, 5);
//                                cflatno.setBackgroundResource(R.drawable.cell_shape_data);
//
//                                camount.setText(samount);
//                                camount.setTextColor(Color.BLACK);
//                                camount.setPadding(5, 5, 5, 5);
//                                camount.setBackgroundResource(R.drawable.cell_shape_data);
//
//                                cpaid.setText(spaiddate);
//                                cpaid.setTextColor(Color.BLACK);
//                                cpaid.setPadding(5, 5, 5, 5);
//                                cpaid.setBackgroundResource(R.drawable.cell_shape_data);
//
//                                row.addView(cbid);
//                                row.addView(cflatno);
//                                row.addView(camount);
//                                row.addView(cpaid);
//
//                                t1.addView(row);
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
//
//                        // error
//                        Toast.makeText(getApplicationContext(), "No Network Connection", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        ){
//            @Override
//            protected Map<String, String> getParams() {
//                Toast.makeText(ValidateCollectionActivity.this, "Successful", Toast.LENGTH_SHORT).show();
//                return null;
//            }
//        };
//        progressBar.setVisibility(View.VISIBLE);
//        queue.add(getRequest);
//    }
}
