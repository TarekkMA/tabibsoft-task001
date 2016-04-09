package com.TMAProject.EzzSteel.Activities;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.TMAProject.EzzSteel.API.EzzWS;
import com.TMAProject.EzzSteel.API.Genrator;
import com.TMAProject.EzzSteel.API.POJO.Arrays;
import com.TMAProject.EzzSteel.API.POJO.GET.ComplaintResult;
import com.TMAProject.EzzSteel.Activities.Base.BaseActivity;
import com.TMAProject.EzzSteel.Activities.Base.Parameters.BackButtonInfo;
import com.TMAProject.EzzSteel.Activities.Base.Parameters.TitleInfo;
import com.TMAProject.EzzSteel.Adapters.SideDrawerAdapter;
import com.TMAProject.EzzSteel.R;

import java.util.Hashtable;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComplaintActivity extends BaseActivity {

    Button btn;
    EditText name,phone,msg;
    CoordinatorLayout parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btn = (Button)findViewById(R.id.complaint_btn);
        name = (EditText)findViewById(R.id.complaint_name);
        phone = (EditText)findViewById(R.id.complaint_number);
        msg = (EditText)findViewById(R.id.complaint_msg);
        parentLayout = (CoordinatorLayout)findViewById(R.id.base_parent);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty()){
                    showSnackbar(getString(R.string.complaint_nameerr),Snackbar.LENGTH_SHORT);
                }else if(phone.getText().toString().isEmpty()){
                    showSnackbar(getString(R.string.complaint_phoneerr),Snackbar.LENGTH_SHORT);
                }else if(msg.getText().toString().isEmpty()){
                    showSnackbar(getString(R.string.complaint_msgerr),Snackbar.LENGTH_SHORT);
                }else {
                    sendDataToServer(name.getText().toString(),phone.getText().toString(),msg.getText().toString());
                }
            }
        });
    }
    void showSnackbar(String s,int duration){
        Snackbar.make(parentLayout,s,Snackbar.LENGTH_SHORT).show();
    }
    void sendDataToServer(String name,String phone,String msg){
        btn.setEnabled(false);
        Genrator.createService(EzzWS.class).addComplaint(Arrays.getComplainPOST(name,phone,msg)).enqueue(new Callback<ComplaintResult>() {
            @Override
            public void onResponse(Call<ComplaintResult> call, Response<ComplaintResult> response) {
                btn.setEnabled(true);
                if(!response.isSuccess())
                    showSnackbar(getString(R.string.complaint_err),Snackbar.LENGTH_LONG);
                else if(response.body().getStatus().equalsIgnoreCase("Done successfully"))
                    showSnackbar(getString(R.string.complaint_succ),Snackbar.LENGTH_LONG);
            }
            @Override
            public void onFailure(Call<ComplaintResult> call, Throwable t) {
                btn.setEnabled(true);
                showSnackbar(getString(R.string.complaint_err),Snackbar.LENGTH_LONG);
            }
        });
    }
    @Override
    protected Map<Integer, Object> getParameters() {
        Map<Integer,Object> parameters = new Hashtable<>();

        parameters.put(BaseActivity.BACK_BUTTON,new BackButtonInfo(true,MainActivity.class));
        parameters.put(BaseActivity.TITLE,
                new TitleInfo(true, SideDrawerAdapter.COMPLAINT_OPTION, ContextCompat.getColor(this, R.color.toolbar_color),18));
        parameters.put(BaseActivity.CONTENT_RESORSES_ID, R.layout.activity_complaint);

        return parameters;
    }
}
