package com.example.tarekkma.task001;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView list;
    RecyclerView drawer;
    RequestQueue queue;
    MainListAdapter adapter;
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;

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

        setupAppBar();
        setupDrawer();

    }

    void setupAppBar(){
        //Tool Bar
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Tabs
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabTextColors(getResources().getColor(R.color.primary_text), getResources().getColor(R.color.secondary_text));
        tabLayout.setSelectedTabIndicatorHeight(6);
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.primary_light));
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SectionsFragment(queue), "Home");
        adapter.addFragment(new SectionsFragment(queue), "Sittings");
        adapter.addFragment(new SectionsFragment(queue), "Contact us");
        viewPager.setAdapter(adapter);
    }

    void setupDrawer(){
        drawer = (RecyclerView) findViewById(R.id.drawer_recyclerView); // Assigning the RecyclerView Object to the xml View
        drawer.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size
        drawer.setLayoutManager(new LinearLayoutManager(this));
        drawerAdapter = new DrawerAdapter(TITLES,ICONS,"Tarek Mohamed Abdalla","tarekkma@gmail.com",R.drawable.download);       // Creating the Adapter of MyAdapter class(which we are going to see in a bit)
        drawer.setAdapter(drawerAdapter);
        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);

        mDrawerToggle = new ActionBarDrawerToggle(this, Drawer,
                toolbar, //nav menu toggle icon
                R.string.app_name, // nav drawer open - description for accessibility
                R.string.app_name // nav drawer close - description for accessibility
        ){
            public void onDrawerClosed(View view) {
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        Drawer.setDrawerListener(mDrawerToggle);

        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State

    }





    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
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

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
