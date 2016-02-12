package com.example.tarekkma.task001;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by tarekkma on 2/12/16.
 */
public class SectionsFragment extends Fragment {
    RecyclerView list;
    MainListAdapter adapter;
    final String API_URL="http://mobileapp.ezzmedicalcare.com/providers/departments";
    RequestQueue queue;
    public SectionsFragment(RequestQueue mainQueue) {
        queue=mainQueue;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sections_fragment, container, false);


        list = (RecyclerView)v.findViewById(R.id.list);
        list.setLayoutManager(new GridLayoutManager(getContext(),2));
        adapter = new MainListAdapter(getContext());
        list.setAdapter(adapter);
             getDataFromServer();

        return v;
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
                Toast.makeText(getContext()
                        , "هناك مشكلة في الإتصال بالسرفر" + "\n" + error.getMessage(),
                        Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
