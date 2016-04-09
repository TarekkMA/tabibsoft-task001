package com.TMAProject.EzzSteel.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
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
                    DialogHelper.errorHappendDialog(context, true, getString(R.string.err_genaric_title), "response : " + response.code());
                    return;
                }
                if(response.body().isEmpty()){
                    DialogHelper.errorHappendDialog(context, true, getString(R.string.err_empty_title), getString(R.string.err_empty_msg));
                    return;
                }
                adapter.update(response.body());
            }

            @Override
            public void onFailure(Call<List<Department>> call, Throwable t) {
                toggleLoading();
                DialogHelper.errorHappendDialog(context, true, getString(R.string.err_genaric_title), t.getMessage());
                t.printStackTrace();            }
        });
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
