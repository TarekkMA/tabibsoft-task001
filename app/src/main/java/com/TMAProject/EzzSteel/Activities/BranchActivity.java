package com.TMAProject.EzzSteel.Activities;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
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
import com.TMAProject.EzzSteel.Activities.Base.BaseActivity;
import com.TMAProject.EzzSteel.Activities.Base.Parameters.BackButtonInfo;
import com.TMAProject.EzzSteel.Activities.Base.Parameters.TitleInfo;
import com.TMAProject.EzzSteel.Adapters.BranchAdapter;
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

public class BranchActivity extends BaseActivity {

    public static String BRANCH_PARENT_ID = "BRANCH_PARENT_ID";
    public static String SEARCH_TERMS = "SEARCH_TERMS";
    public static String BRANCH_PARENT_TYPE = "BRANCH_PARENT_TYPE";


    String id = null;
    String searchTerms = null;
    String type = null;
    int iconRes;
    RecyclerView list;
    BranchAdapter adapter;

    Snackbar searchSnackbar;

    Call<List<Branche>> searchCall = null;

    Map<String,List<Branche>> searchList = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toggleLoading();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        id = getIntent().getStringExtra(BranchActivity.BRANCH_PARENT_ID);
        searchTerms = getIntent().getStringExtra(SEARCH_TERMS);
        type = getIntent().getStringExtra(BRANCH_PARENT_TYPE);
        iconRes = GenralHelper.getIconByParentName(type,false);
        if(id==null && searchTerms==null) { //if both null
            DialogHelper.errorHappendDialog(this, true, getString(R.string.err_id_depart_title), getString(R.string.err_id_depart_msg));
            return;
        }
        list = (RecyclerView)findViewById(R.id.prov_list);
        list.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BranchAdapter(this,type,BranchAdapter.BRANCH_NORMAL);
        list.setAdapter(adapter);
        Log.d("Branch", "Parent ID > " + id + " ,Icon ID > " + iconRes);
        if(id!=null)getDataFromServer(this);
        else if (searchTerms!=null)getGenralSearchFromServer(this);
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
        Genrator.createService(EzzWS.class).getBranches(Arrays.getBranchesPOST(id)).enqueue(new Callback<List<Branche>>() {
            @Override
            public void onResponse(Call<List<Branche>> call, retrofit2.Response<List<Branche>> response) {
                toggleLoading();
                if (!response.isSuccess()) {
                    DialogHelper.errorHappendDialog(context, true, getString(R.string.err_genaric_title), "response : " + response.code());
                    return;
                }
                if (response.body().isEmpty()) {
                    DialogHelper.errorHappendDialog(context, true, getString(R.string.err_empty_title), getString(R.string.err_empty_msg));
                    return;
                }
                searchList.put("",response.body());
                adapter.updateBranches(response.body());
            }

            @Override
            public void onFailure(Call<List<Branche>> call, Throwable t) {
                toggleLoading();
                DialogHelper.errorHappendDialog(context, true, getString(R.string.err_genaric_title), t.getMessage());
                t.printStackTrace();
            }
        });
    }

    void getGenralSearchFromServer(final Context context){
        Log.d("Genral Search",searchTerms);
        String[] s = searchTerms.split("-");
        Genrator.createService(EzzWS.class).getGenralSearch(Arrays.getGeneralSearchPOST(s[0],s[1],s[2])).enqueue(new Callback<List<Branche>>() {
            @Override
            public void onResponse(Call<List<Branche>> call, retrofit2.Response<List<Branche>> response) {
                toggleLoading();
                if (!response.isSuccess()) {
                    DialogHelper.errorHappendDialog(context, true, getString(R.string.err_genaric_title), "response : " + response.code());
                    return;
                }
                if (response.body().isEmpty()) {
                    DialogHelper.errorHappendDialog(context, true, getString(R.string.err_empty_title), getString(R.string.err_empty_msg));
                    return;
                }
                searchList.put("", response.body());
                adapter.updateBranches(response.body());
            }

            @Override
            public void onFailure(Call<List<Branche>> call, Throwable t) {
                toggleLoading();
                DialogHelper.errorHappendDialog(context, true, getString(R.string.err_genaric_title), t.getMessage());
                t.printStackTrace();
            }
        });
    }

    void getSearchDataFromServer(final String s){
        if(searchCall!=null)
            searchCall.cancel();
        if(searchSnackbar!=null && searchSnackbar.isShown())
            searchSnackbar.dismiss();
        searchSnackbar =  Snackbar.make(findViewById(R.id.base_parent),"Searching for \""+s+"\"",Snackbar.LENGTH_INDEFINITE)
                .setAction("CANCEL", new View.OnClickListener() {
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
                    Toast.makeText(getApplicationContext(), "Can't search \"" + s + "\"", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(response.body().isEmpty()){
                    Toast.makeText(getApplicationContext(),"Nothing matches \""+s+"\"",Toast.LENGTH_SHORT).show();
                }
                searchList.put(s, response.body());
                if(!list.getAdapter().equals(adapter))
                    list.setAdapter(adapter);
                adapter.updateBranches(response.body());
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
        if(searchList.get(s) == null){
            getSearchDataFromServer(s);
        }else{
            adapter.updateBranches(searchList.get(s));
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
