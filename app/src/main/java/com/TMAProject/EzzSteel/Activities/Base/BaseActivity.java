package com.TMAProject.EzzSteel.Activities.Base;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.TMAProject.EzzSteel.API.POJO.GET.Department;
import com.TMAProject.EzzSteel.Activities.Base.Parameters.BackButtonInfo;
import com.TMAProject.EzzSteel.Activities.Base.Parameters.NavInfo;
import com.TMAProject.EzzSteel.Activities.Base.Parameters.TitleInfo;
import com.TMAProject.EzzSteel.Adapters.SideDrawerAdapter;
import com.TMAProject.EzzSteel.R;
import com.TMAProject.EzzSteel.Utils.LangHelper;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by TarekkMA on 4/1/16.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public static final int TEMPLATE = -1;
    public static final int CONTENT_RESORSES_ID = 0;
    public static final int NAVGATION_DRAWER = 1;
    public static final int BACK_BUTTON = 2;
    public static final int TITLE = 3;

    View loadingView = null;

    static boolean firstActivityOppened=true;

    SideDrawerAdapter navAdapter;

    private boolean navExists;

    Map<Integer,Object> parameters;

    public static String lang = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        adjustLanguage();

        parameters = getParameters();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        loadingView = findViewById(R.id.base_loading_layout);


        if(parameters.get(CONTENT_RESORSES_ID)!=null)
            inflateContent();

        if(parameters.get(NAVGATION_DRAWER)!=null && ((NavInfo) parameters.get(NAVGATION_DRAWER)).exists)
            navExists=true;

        if(parameters.get(TITLE)!=null && ((TitleInfo) parameters.get(TITLE)).custom)
            setupTheTitle(((TitleInfo) parameters.get(TITLE)));
        else
            setupTheTitle(new TitleInfo(false));

        if(parameters.get(BACK_BUTTON)!=null && ((BackButtonInfo) parameters.get(BACK_BUTTON)).exists){
            if(getSupportActionBar()==null)return;
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    private void adjustLanguage(){
        if (firstActivityOppened){
            lang = LangHelper.getLangage(this);
            firstActivityOppened=false;
        }
        Resources res = getResources();
        // Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.locale = new Locale(lang);
        res.updateConfiguration(conf, dm);
        conf.setLayoutDirection(conf.locale);
    }

    private void inflateContent(){
        ViewGroup tBaseView = (ViewGroup)findViewById(R.id.base_content);
        LayoutInflater vi = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate((int)parameters.get(CONTENT_RESORSES_ID), null);
        tBaseView.addView(v,0);

    }

    private void checkTemplate(){

    }

    private void setupTheTitle(TitleInfo i){
        Toolbar toolbar=(Toolbar)findViewById(R.id.base_toolbar);
        TextView customTextView = ((TextView) findViewById(R.id.base_toolbar_title));
        if(i.custom){
            customTextView.setText(i.title);
            customTextView.setTextColor(i.colorRes);
            customTextView.setTextSize(i.size);
        }

        setSupportActionBar(toolbar);
        if(i.custom)getSupportActionBar().setDisplayShowTitleEnabled(false);
        else customTextView.setVisibility(View.GONE);
        setupTheNav(toolbar);
    }

    private void setupTheNav(Toolbar toolbar){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.base_drawer);
        if(!navExists) {
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            return;
        }

        Picasso.with(this).load(R.drawable.ezz_header).into((ImageView)findViewById(R.id.base_nav_header));

        RecyclerView navList = (RecyclerView) findViewById(R.id.base_drawer_recyclerView);
        navList.setHasFixedSize(true);
        navList.setLayoutManager(new LinearLayoutManager(this));
        navAdapter = new SideDrawerAdapter(this);
        navList.setAdapter(navAdapter);


        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, drawer,
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
        drawer.setDrawerListener(mDrawerToggle);

        drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();               // Finally we set the drawer toggle sync State

    }


    public void fillNavWithDepartments(List<Department> departments){
        navAdapter.addDepartments(departments);
    }

    public void toggleLoading(){
        if(loadingView.getVisibility() == View.GONE)
            loadingView.setVisibility(View.VISIBLE);
        else
            loadingView.setVisibility(View.GONE);
    }

    protected abstract Map<Integer,Object> getParameters();






}