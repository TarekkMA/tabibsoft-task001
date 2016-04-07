package com.TMAProject.EzzSteel.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.TMAProject.EzzSteel.API.EzzWS;
import com.TMAProject.EzzSteel.API.Genrator;
import com.TMAProject.EzzSteel.API.POJO.Arrays;
import com.TMAProject.EzzSteel.API.POJO.GET.Branche;
import com.TMAProject.EzzSteel.Activities.Base.BaseActivity;
import com.TMAProject.EzzSteel.Activities.Base.Parameters.BackButtonInfo;
import com.TMAProject.EzzSteel.Activities.Base.Parameters.TitleInfo;
import com.TMAProject.EzzSteel.R;
import com.TMAProject.EzzSteel.Utils.DialogHelper;
import com.TMAProject.EzzSteel.Utils.GenralHelper;
import com.squareup.picasso.Picasso;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.TMAProject.EzzSteel.Utils.LangHelper.*;
public class BranchDetailsActivity extends BaseActivity {

    public static final String BRANCH_DETAILS_PARENT_ID = "IDDD";
    public static final String BRANCH_DETAILS_TYPE = "TYPEE";

    String id,type;

    ImageView icon,iamge;
    TextView name,address,email,numbers;
    Button mapBtn;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toggleLoading();
        mapBtn = (Button)findViewById(R.id.branch_map_btn);
        icon = (ImageView)findViewById(R.id.branch_icon);
        iamge = (ImageView)findViewById(R.id.branch_img);
        name = (TextView)findViewById(R.id.branch_name);
        address = (TextView)findViewById(R.id.branch_address);
        email = (TextView)findViewById(R.id.branch_email);
        numbers = (TextView)findViewById(R.id.branch_numbers);

        id = getIntent().getStringExtra(BRANCH_DETAILS_PARENT_ID);
        type = getIntent().getStringExtra(BRANCH_DETAILS_TYPE);

        context =this;
        getDataFromServer();
    }

    void getDataFromServer(){
        Genrator.createService(EzzWS.class).getBranchDetailes(Arrays.getBranchDetailsPOST(id)).enqueue(new Callback<List<Branche>>() {
            @Override
            public void onResponse(Call<List<Branche>> call, Response<List<Branche>> response) {
                toggleLoading();
                if (!response.isSuccess()) {
                    DialogHelper.errorHappendDialog(context, true, getString(R.string.err_genaric_title), "response : " + response.code());
                    return;
                }
                if (response.body().isEmpty()) {
                    DialogHelper.errorHappendDialog(context, true, getString(R.string.err_empty_title), getString(R.string.err_empty_msg));
                    return;
                }
                final Branche b = response.body().get(0);
                Picasso.with(context).load(GenralHelper.getIconByParentName(type, false)).fit().into(icon);
                Picasso.with(context).load(b.getFullImgUrl()).into(iamge);
                name.setText(getName(b));
                address.setText(b.getAddress());
                email.setText(b.getEmail());
                Linkify.addLinks(email, Linkify.EMAIL_ADDRESSES);
                numbers.setText(b.getTelephone() + " - " + b.getMobile());
                Linkify.addLinks(numbers, Linkify.PHONE_NUMBERS);
                mapBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double latitude = Double.parseDouble(b.getLatitude());
                        double longitude = Double.parseDouble(b.getLongitude());
                        String label = b.getName_english();
                        String uriBegin = "geo:" + latitude + "," + longitude;
                        String query = latitude + "," + longitude + "(" + label + ")";
                        String encodedQuery = Uri.encode(query);
                        String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
                        Uri uri = Uri.parse(uriString);
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Branche>> call, Throwable t) {
                toggleLoading();
                DialogHelper.errorHappendDialog(context, true, getString(R.string.err_genaric_title), t.getMessage());
                t.printStackTrace();
            }
        });
    }

    @Override
    protected Map<Integer, Object> getParameters() {
        Map<Integer,Object> parameters = new Hashtable<>();

        parameters.put(BaseActivity.BACK_BUTTON,new BackButtonInfo(true,MainActivity.class));
        parameters.put(BaseActivity.TITLE,
                new TitleInfo(true,this.getString(R.string.app_name), ContextCompat.getColor(this, R.color.toolbar_color),18));
        parameters.put(BaseActivity.CONTENT_RESORSES_ID, R.layout.activity_branch_details);

        return parameters;
    }

}
