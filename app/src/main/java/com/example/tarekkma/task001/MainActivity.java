package com.example.tarekkma.task001;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    RecyclerView list;
    RecyclerView drawer;
    RequestQueue queue ;
    MainListAdapter adapter;
    Toolbar toolbar;
    final String API_URL="http://mobileapp.ezzmedicalcare.com/providers/departments";

    String TITLES[] = {"Home","Events","Mail","Shop","Travel"};
    int ICONS[] = {R.drawable.home_48,R.drawable.planner_48,
            R.drawable.reply_48,R.drawable.shopping_cart_48,
            R.drawable.map_48};

    DrawerAdapter drawerAdapter;
    DrawerLayout Drawer;
    ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_side_drawer);
        queue= Volley.newRequestQueue(this);

        setupDrawer();

        list = (RecyclerView)findViewById(R.id.list);
        list.setLayoutManager(new GridLayoutManager(this,2));
        adapter = new MainListAdapter(getApplicationContext());
        list.setAdapter(adapter);
        getDataFromServer();
    }


    void setupDrawer(){
        drawer = (RecyclerView) findViewById(R.id.drawer_recyclerView); // Assigning the RecyclerView Object to the xml View
        drawer.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size
        drawer.setLayoutManager(new LinearLayoutManager(this));
        drawerAdapter = new DrawerAdapter(TITLES,ICONS,"Tarek Mohamed Abdalla","tarekkma@gmail.com",R.drawable.download);       // Creating the Adapter of MyAdapter class(which we are going to see in a bit)
        drawer.setAdapter(drawerAdapter);
        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDrawerToggle = new ActionBarDrawerToggle(this, Drawer,
                toolbar, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ){
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle("My Title");
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle("Drawer title");
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        Drawer.setDrawerListener(mDrawerToggle);

        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State

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
