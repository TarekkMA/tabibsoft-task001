package com.TMAProject.EzzSteel.Activities;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.TMAProject.EzzSteel.API.EzzWS;
import com.TMAProject.EzzSteel.API.Genrator;
import com.TMAProject.EzzSteel.API.POJO.GET.Department;
import com.TMAProject.EzzSteel.Activities.Base.BaseActivity;
import com.TMAProject.EzzSteel.Activities.Base.Parameters.NavInfo;
import com.TMAProject.EzzSteel.Activities.Base.Parameters.TitleInfo;
import com.TMAProject.EzzSteel.Adapters.SideDrawerAdapter;
import com.TMAProject.EzzSteel.Adapters.MainListAdapter;
import com.TMAProject.EzzSteel.Parser;
import com.TMAProject.EzzSteel.R;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends BaseActivity {
    RecyclerView list;
    MainListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toggleLoading();
        list = (RecyclerView)findViewById(R.id.list);
        list.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new MainListAdapter(getApplicationContext());
        list.setAdapter(adapter);
        getDataFromServer();
    }





    void getDataFromServer(){
        Genrator.createService(EzzWS.class).getDepartments().enqueue(new Callback<List<Department>>() {
            @Override
            public void onResponse(Call<List<Department>> call, retrofit2.Response<List<Department>> response) {
                toggleLoading();
                if(!response.isSuccess())
                {
                    return;
                }
                adapter.update(response.body());
                fillNavWithDepartments(response.body());
            }

            @Override
            public void onFailure(Call<List<Department>> call, Throwable t) {
                toggleLoading();
            }
        });
    }


    @Override
    protected Map<Integer, Object> getParameters() {
        Map<Integer,Object> parameters = new Hashtable<>();

        parameters.put(BaseActivity.NAVGATION_DRAWER,new NavInfo(true));
        parameters.put(BaseActivity.TITLE,
                new TitleInfo(true,this.getString(R.string.app_name), ContextCompat.getColor(this,R.color.toolbar_color),18));
        parameters.put(BaseActivity.CONTENT_RESORSES_ID, R.layout.activity_main);

        return parameters;
    }


}
