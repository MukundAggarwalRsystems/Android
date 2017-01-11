package com.example.rsi.httpsimplerequest;

import android.app.Activity;
import android.app.DownloadManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    Button button_geturl;
    Button button_all;
    TextView textView_serverresponse;
    //String ser_url = "http://10.131.60.232:8081/all";
    EditText editText_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_geturl = (Button)findViewById(R.id.button_geturl);
        button_all = (Button)findViewById(R.id.button_all);
        textView_serverresponse = (TextView)findViewById(R.id.textView_serverresponse);
        editText_url = (EditText)findViewById(R.id.editText_url);
       /* button_geturl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!editText_url.getText().toString().equals(null))
                {
                    getRequest(editText_url.getText().toString());
                }else {
                    Toast.makeText(MainActivity.this, "ULR feild is empty. Please enter a URL", Toast.LENGTH_SHORT).show();
                }
        }
        });*/
    }

    public void buttonGET(View view){
        if (!editText_url.getText().toString().equals(null))
        {
            getRequest(editText_url.getText().toString());
        }else {
            Toast.makeText(MainActivity.this, "ULR feild is empty. Please enter a URL", Toast.LENGTH_SHORT).show();
        }
    }
    public void buttonALL(View view){
        String ser_url_all = "http://10.131.60.232:8081/all";
        getRequest(ser_url_all);
    }

    public void getRequest(String server_url){

        final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, server_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                textView_serverresponse.setText(response);
                requestQueue.stop();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView_serverresponse.setText("SOmething went wrong....");
                error.printStackTrace();
                requestQueue.stop();
            }
        });
        requestQueue.add(stringRequest);
    }

}
