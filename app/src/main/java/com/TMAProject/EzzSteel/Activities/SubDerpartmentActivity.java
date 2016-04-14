package com.TMAProject.EzzSteel.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.TMAProject.EzzSteel.API.EzzWS;
import com.TMAProject.EzzSteel.API.Genrator;
import com.TMAProject.EzzSteel.API.POJO.Arrays;
import com.TMAProject.EzzSteel.API.POJO.GET.Department;
import com.TMAProject.EzzSteel.Activities.Base.BaseActivity;
import com.TMAProject.EzzSteel.Activities.Base.Parameters.BackButtonInfo;
import com.TMAProject.EzzSteel.Activities.Base.Parameters.TitleInfo;
import com.TMAProject.EzzSteel.Adapters.MainListAdapter;
import com.TMAProject.EzzSteel.R;
import com.TMAProject.EzzSteel.Utils.DialogHelper;
import com.google.common.collect.Lists;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by TarekkMA on 4/2/16.
 */
public class SubDerpartmentActivity extends BaseActivity {
    RecyclerView list;
    MainListAdapter adapter;
    public static final String DEPARTMENT_ID = "DEP_ID";
    String id = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toggleLoading();
        list = (RecyclerView)findViewById(R.id.list);
        list.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new MainListAdapter(getApplicationContext());
        list.setAdapter(adapter);
        id = getIntent().getStringExtra(DEPARTMENT_ID);
        if(id==null){
            DialogHelper.errorHappendDialog(this,true,getString(R.string.err_id_depart_title),getString(R.string.err_id_depart_msg));
            return;
        }
        getDataFromServer(this);
    }


    void getDataFromServer(final Context context){
        Genrator.createService(EzzWS.class).getSubDepartments(Arrays.getSubDepartmentSearchPOST(id)).enqueue(new Callback<List<Department>>() {
            @Override
            public void onResponse(Call<List<Department>> call, retrofit2.Response<List<Department>> response) {
                toggleLoading();
                if (!response.isSuccess()) {
                    showErrDialog();
                    return;
                }
                if(response.body().isEmpty()){
                    showEemtyDialog();
                    return;
                }
                adapter.update(Lists.reverse(response.body()));
            }

            @Override
            public void onFailure(Call<List<Department>> call, Throwable t) {
                toggleLoading();
                showErrDialog();
                t.printStackTrace();            }
        });
    }

    void showErrDialog(){
        final Context c = this;
        new AlertDialog.Builder(c)
                .setMessage(getString(R.string.errLoading))
                .setCancelable(false)
                .setPositiveButton(R.string.try_agin, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getDataFromServer(c);
                    }
                })
                .setNegativeButton(R.string.err_exit, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((Activity)c).finish();
                    }
                })
                .show();
    }
    void showEemtyDialog(){
        final Context c = this;
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.err_empty_msg))
                .setCancelable(false)
                .setPositiveButton(R.string.lang_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((Activity)c).finish();
                    }
                })
                .show();
    }

    @Override
    protected Map<Integer, Object> getParameters() {
        Map<Integer,Object> parameters = new Hashtable<>();

        parameters.put(BaseActivity.BACK_BUTTON,new BackButtonInfo(true,MainActivity.class));
        parameters.put(BaseActivity.TITLE,
                new TitleInfo(true,this.getString(R.string.app_name), ContextCompat.getColor(this, R.color.toolbar_color),18));
        parameters.put(BaseActivity.CONTENT_RESORSES_ID, R.layout.activity_main);

        return parameters;
    }
}
