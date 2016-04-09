package com.TMAProject.EzzSteel.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.TMAProject.EzzSteel.API.EzzWS;
import com.TMAProject.EzzSteel.API.Genrator;
import com.TMAProject.EzzSteel.API.POJO.Arrays;
import com.TMAProject.EzzSteel.API.POJO.GET.Notification;
import com.TMAProject.EzzSteel.API.POJO.GET.PojoArrayWarper;
import com.TMAProject.EzzSteel.API.POJO.GET.Provider;
import com.TMAProject.EzzSteel.Activities.Base.BaseActivity;
import com.TMAProject.EzzSteel.Activities.Base.Parameters.BackButtonInfo;
import com.TMAProject.EzzSteel.Activities.Base.Parameters.TitleInfo;
import com.TMAProject.EzzSteel.Adapters.NotifcationAdapter;
import com.TMAProject.EzzSteel.Adapters.SideDrawerAdapter;
import com.TMAProject.EzzSteel.R;
import com.TMAProject.EzzSteel.Utils.DialogHelper;
import com.TMAProject.EzzSteel.Utils.Navigation;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class NotificationActivity extends BaseActivity {
    NotifcationAdapter adapter;
    String dataFromPush=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toggleLoading();
        dataFromPush = getIntent().getStringExtra("com.parse.Data");
        final RecyclerView list = (RecyclerView)findViewById(R.id.list);
        list.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NotifcationAdapter(this);
        list.setAdapter(adapter);
        getDataFromServer(this);
        if(dataFromPush!=null)
            Navigation.handleNotifcation(this,dataFromPush);
    }

    void  getDataFromServer(final Context c){
        Genrator.createService(EzzWS.class).getNotifcations().enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, retrofit2.Response<List<Notification>> response) {
                toggleLoading();
                if (!response.isSuccess()) {
                    DialogHelper.errorHappendDialog(c, true, getString(R.string.err_genaric_title), "response : " + response.code());
                    return;
                }
                if (response.body().isEmpty()) {
                    DialogHelper.errorHappendDialog(c, true, getString(R.string.err_empty_title), getString(R.string.err_empty_msg));
                    return;
                }
                adapter.updateList(response.body());
            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {
                toggleLoading();
                DialogHelper.errorHappendDialog(c, true, getString(R.string.err_genaric_title), t.getMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            if(dataFromPush!=null)
                startActivity(new Intent(this,MainActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Map<Integer, Object> getParameters() {
        Map<Integer,Object> parameters = new Hashtable<>();

        parameters.put(BaseActivity.BACK_BUTTON,new BackButtonInfo(true,MainActivity.class));
        parameters.put(BaseActivity.TITLE,
                new TitleInfo(true, SideDrawerAdapter.SEARCH_OPTION, ContextCompat.getColor(this, R.color.toolbar_color),18));
        parameters.put(BaseActivity.CONTENT_RESORSES_ID, R.layout.activity_notifcation);

        return parameters;
    }
}
