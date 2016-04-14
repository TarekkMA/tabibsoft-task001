package com.TMAProject.EzzSteel.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.TMAProject.EzzSteel.API.EzzWS;
import com.TMAProject.EzzSteel.API.Genrator;
import com.TMAProject.EzzSteel.API.POJO.Arrays;
import com.TMAProject.EzzSteel.API.POJO.GET.Branche;
import com.TMAProject.EzzSteel.API.POJO.GET.PojoArrayWarper;
import com.TMAProject.EzzSteel.API.POJO.GET.Provider;
import com.TMAProject.EzzSteel.Activities.Base.BaseActivity;
import com.TMAProject.EzzSteel.Activities.Base.Parameters.BackButtonInfo;
import com.TMAProject.EzzSteel.Activities.Base.Parameters.TitleInfo;
import com.TMAProject.EzzSteel.Adapters.BranchAdapter;
import com.TMAProject.EzzSteel.Adapters.ProviderAdapter;
import com.TMAProject.EzzSteel.R;
import com.TMAProject.EzzSteel.Utils.DialogHelper;
import com.TMAProject.EzzSteel.Utils.GenralHelper;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProviderActivity extends BaseActivity {

    public static String PROVIDER_PARENT_ID = "PROV_PARENT_ID";
    public static String PROVIDER_PARENT_TYPE = "PROV_PARENT_ICON";

    String id = null;
    int iconRes;
    RecyclerView list;
    ProviderAdapter adapter;
    BranchAdapter searchAdapter;
    String type;


    Snackbar searchSnackbar;

    Call<List<Branche>> searchCall = null;

    Map<String,List<Branche>> searchList = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        id = getIntent().getStringExtra(PROVIDER_PARENT_ID);
        type = getIntent().getStringExtra(PROVIDER_PARENT_TYPE);
        iconRes = GenralHelper.getIconByParentName(type,true);
        if(id==null) {
            DialogHelper.errorHappendDialog(this,true,getString(R.string.err_id_depart_title),getString(R.string.err_id_depart_msg));
            return;
        }
        list = (RecyclerView)findViewById(R.id.prov_list);
        list.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ProviderAdapter(this,GenralHelper.getIconByParentName(type,true),type);
        searchAdapter = new BranchAdapter(this,type,BranchAdapter.BRANCH_SEARCH);
        list.setAdapter(adapter);
        Log.d("Provider", "Parent ID > " + id + " ,Icon ID > " + iconRes);
        getDataFromServer(this);
        final EditText editText = (EditText)findViewById(R.id.search_box_tv);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                applySearch(s.toString());
            }
        });
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setCursorVisible(true);
            }
        });
    }

    void getDataFromServer(final Context context){
        toggleLoading();
        Genrator.createService(EzzWS.class).getProviders(Arrays.getProvidersPOST(id)).enqueue(new Callback<PojoArrayWarper<Provider>>() {
            @Override
            public void onResponse(Call<PojoArrayWarper<Provider>> call, retrofit2.Response<PojoArrayWarper<Provider>> response) {
                toggleLoading();
                if (!response.isSuccess()) {
                    showErrDialog();
                    return;
                }
                if (response.body().result.isEmpty()) {
                    showEemtyDialog();
                    return;
                }
                adapter.updateProviders(response.body().result);
            }

            @Override
            public void onFailure(Call<PojoArrayWarper<Provider>> call, Throwable t) {
                toggleLoading();
                showErrDialog();
                t.printStackTrace();
            }
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

    void getSearchDataFromServer(final String s){
        toggleLoading();
        if(searchCall!=null)
            searchCall.cancel();
        if(searchSnackbar!=null && searchSnackbar.isShown())
            searchSnackbar.dismiss();
        searchSnackbar =  Snackbar.make(findViewById(R.id.base_parent),getString(R.string.searching)+"\""+s+"\"",Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.cancel_search), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        searchSnackbar.dismiss();
                        Log.d("Snackbar", "Dismissed By Action");
                        searchCall.cancel();
                    }
                });
        searchSnackbar.getView().setTag(s);
        searchSnackbar.show();
        searchCall=Genrator.createService(EzzWS.class).getSearch(Arrays.getSearchPOST(s));
        searchCall.enqueue(new Callback<List<Branche>>() {
            @Override
            public void onResponse(Call<List<Branche>> call, Response<List<Branche>> response) {
                if(searchSnackbar.getView().getTag().toString().equalsIgnoreCase(s))
                    searchSnackbar.dismiss();
                if(!response.isSuccess()){
                    Toast.makeText(getApplicationContext(),getString(R.string.search_cannot)+"\"" + s + "\"", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(response.body().isEmpty()){
                    Toast.makeText(getApplicationContext(),getString(R.string.search_404)+"\""+s+"\"",Toast.LENGTH_SHORT).show();
                }
                searchList.put(s, response.body());
                if(!list.getAdapter().equals(searchAdapter))
                    list.setAdapter(searchAdapter);
                searchAdapter.updateBranches(response.body());
            }

            @Override
            public void onFailure(Call<List<Branche>> call, Throwable t) {
                if(searchSnackbar.getView().getTag().toString().equalsIgnoreCase(s))
                    searchSnackbar.dismiss();
                if(t.getMessage().equalsIgnoreCase("Canceled") || t.getMessage().equalsIgnoreCase("Socket closed"))
                    return;//This is probably canceled by the application
                Toast.makeText(getApplicationContext(),"ERR",Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    void applySearch(String s){
        if(s.equalsIgnoreCase("")){
            if(!list.getAdapter().equals(adapter))
                list.setAdapter(adapter);
            return;
        }
        if(searchList.get(s) == null){
            getSearchDataFromServer(s);
        }else{
            if(!list.getAdapter().equals(searchAdapter))
                list.setAdapter(searchAdapter);
            searchAdapter.updateBranches(searchList.get(s));
        }
    }

    @Override
    protected Map<Integer, Object> getParameters() {
        Map<Integer,Object> parameters = new Hashtable<>();

        parameters.put(BaseActivity.BACK_BUTTON,new BackButtonInfo(true,MainActivity.class));
        parameters.put(BaseActivity.TITLE,
                new TitleInfo(true,this.getString(R.string.app_name), ContextCompat.getColor(this, R.color.toolbar_color),18));
        parameters.put(BaseActivity.CONTENT_RESORSES_ID, R.layout.activity_provider);

        return parameters;
    }
}
