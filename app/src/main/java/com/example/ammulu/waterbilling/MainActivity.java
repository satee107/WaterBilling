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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ammulu.waterbilling.Network.API;
import com.example.ammulu.waterbilling.Network.Conn;
import com.example.ammulu.waterbilling.Network.ConnectivityReceiver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import static com.example.ammulu.waterbilling.Network.Conn.displayMobileDataSettingsDialog;

public class MainActivity extends AppCompatActivity {
    Button signin;
    EditText etname,etpwd;
    String username,userpwd;
    SharedPreferences shre;

    // JSONObject jsonObj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etname=(EditText)findViewById(R.id.etname);
        etpwd=(EditText)findViewById(R.id.etpwd);
        signin=(Button)findViewById(R.id.signin);

//        shre = getSharedPreferences("userdetails",MODE_PRIVATE);
//        String loginuname = shre.getString("loginname",null);



      //  checkConnection();
//        if(ConnectivityReceiver.isConnected()==false){
//            //checkConnection();
//            displayMobileDataSettingsDialog(MainActivity.this);
//        }
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=etname.getText().toString();
                userpwd=etpwd.getText().toString();
                if(username.equals("")){
                    etname.setError("Enter min 3 chars username");
                    etpwd.setFocusable(true);

                }else if(userpwd.equals("")) {
                    etpwd.setError("Enter password");
                    etpwd.setFocusable(true);

                }else {
                    getCredentials();

                }
            }
        });
    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        showSnack(isConnected);
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
        } Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),message , Snackbar.LENGTH_LONG);
        // snackbar.show();
//        Snackbar snackbar = Snackbar
//                .make(findViewById(R.id.fab), message, Snackbar.LENGTH_LONG);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(color);
        snackbar.show();
    }

    private void getCredentials() {
        //checkConnection();
        // progressbar.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String serverURL = API.logincredentialsurl;
           String uname=etname.getText().toString().trim();
            String upwd=etpwd.getText().toString().trim();
            String url = serverURL+"?uname="+uname+"&upass="+upwd;
            final StringRequest getRequest = new StringRequest(Request.Method.GET, url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String res = jsonObj.getString("result");

                            if (res.equals("success")){
                                //SharedPreferences shared = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
                                //SharedPreferences.Editor editor = shared.edit();
                                //editor.putString(keyChannel, email);
                                //editor.commit();// commit is important here.
                                Toast.makeText(getApplicationContext(), "Successfully Login", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(getApplicationContext(), HomeActivity.class);
                                //intent.putExtra("user",loginusername);
                                shre = getSharedPreferences("userdetails",MODE_PRIVATE);
                                SharedPreferences.Editor editor = shre.edit();
                                String username=etname.getText().toString().trim();
                                // String pass = edtpass.getText().toString();
                                editor.putString("loginname",username);
                                //editor.putString("loginpassword",upwd);
                                editor.commit();

                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
                                etname.setText("");
                                etpwd.setText("");

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
                Toast.makeText(MainActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                return null;
            }
        };
        queue.add(getRequest);
    }


    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        //@Override
        //public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

        // }
    }
}
