package com.example.tarekkma.task001;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    RecyclerView list;
    RequestQueue queue ;
    MainListAdapter adapter;
    final String API_URL="http://mobileapp.ezzmedicalcare.com/providers/departments";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        queue= Volley.newRequestQueue(this);

        list = (RecyclerView)findViewById(R.id.list);
        list.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new MainListAdapter(getApplicationContext());
        list.setAdapter(adapter);
        getDataFromServer();
    }
    void getDataFromServer(){
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, API_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("API", response);
                        adapter.update(Parser.parseSections(response));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext()
                        , "هناك مشكلة في الإتصال بالسرفر" + "\n" + error.getMessage(),
                        Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
