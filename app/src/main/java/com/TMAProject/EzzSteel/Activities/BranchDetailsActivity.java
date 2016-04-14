package com.TMAProject.EzzSteel.Activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.TMAProject.EzzSteel.API.EzzWS;
import com.TMAProject.EzzSteel.API.Genrator;
import com.TMAProject.EzzSteel.API.POJO.Arrays;
import com.TMAProject.EzzSteel.API.POJO.GET.Branche;
import com.TMAProject.EzzSteel.API.POJO.GET.Contact;
import com.TMAProject.EzzSteel.Activities.Base.BaseActivity;
import com.TMAProject.EzzSteel.Activities.Base.Parameters.BackButtonInfo;
import com.TMAProject.EzzSteel.Activities.Base.Parameters.TitleInfo;
import com.TMAProject.EzzSteel.R;
import com.TMAProject.EzzSteel.Utils.DialogHelper;
import com.TMAProject.EzzSteel.Utils.GenralHelper;
import com.TMAProject.EzzSteel.Utils.LangHelper;
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
    public static final String IS_ABOUT = "ABOUTT";

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

        if(getIntent().getBooleanExtra(IS_ABOUT,false))
            getContactFromServer();
        else
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
                Picasso.with(context).load(b.getFullImgUrl()).into(iamge, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        ContentLoadingProgressBar progressBar = (ContentLoadingProgressBar) findViewById(R.id.image_loading);
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        ContentLoadingProgressBar progressBar = (ContentLoadingProgressBar) findViewById(R.id.image_loading);
                        progressBar.setVisibility(View.GONE);
                        Picasso.with(context).load(GenralHelper.getTempByParentName(type)).into(iamge);
                    }
                });
                name.setText(getName(b));
                address.setText(b.getAddress());
                email.setText(b.getEmail());
                Linkify.addLinks(email, Linkify.EMAIL_ADDRESSES);
                if (b.getTelephone().isEmpty() || b.getMobile().isEmpty()){
                    numbers.setText(b.getTelephone() + b.getMobile());
                }else {
                    numbers.setText(b.getTelephone() + " - " + b.getMobile());
                }
                numbers.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String[] numbersArray = numbers.getText().toString().split("-");

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle(getString(R.string.call_dialog));
                        builder.setItems(numbersArray, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {

                                try {
                                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                    callIntent.setData(Uri.parse("tel:"+numbersArray[item]));
                                    context.startActivity(callIntent);
                                } catch (ActivityNotFoundException activityException) {
                                    Toast.makeText(context, "FAIL", Toast.LENGTH_LONG).show();
                                }
                            }

                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
                mapBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(b.getLatitude().isEmpty() || b.getLongitude().isEmpty()) {
                            DialogHelper.infoHappendDialog(context,false,getString(R.string.err_empty_title),getString(R.string.map_empty_cords));
                        }else {
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
                        }}
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


    void getContactFromServer(){
        Genrator.createService(EzzWS.class).getContact().enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                toggleLoading();
                if (!response.isSuccess()) {
                    DialogHelper.errorHappendDialog(context, true, getString(R.string.err_genaric_title), "response : " + response.code());
                    return;
                }
                if (response.body().isEmpty()) {
                    DialogHelper.errorHappendDialog(context, true, getString(R.string.err_empty_title), getString(R.string.err_empty_msg));
                    return;
                }
                final Contact c = response.body().get(0);
                Picasso.with(context).load(c.getFullImgUrl()).into(iamge, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        ContentLoadingProgressBar progressBar = (ContentLoadingProgressBar) findViewById(R.id.image_loading);
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        ContentLoadingProgressBar progressBar = (ContentLoadingProgressBar) findViewById(R.id.image_loading);
                        progressBar.setVisibility(View.GONE);
                        Picasso.with(context).load(GenralHelper.getTempByParentName(type)).into(iamge);
                    }
                });
                address.setText(LangHelper.getAddressFromContact(c));
                email.setText(c.getEmail());
                Linkify.addLinks(email, Linkify.EMAIL_ADDRESSES);
                numbers.setText(c.getPhone());
                numbers.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String[] numbersArray = numbers.getText().toString().split("-");

                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setTitle(getString(R.string.call_dialog));
                        builder.setItems(numbersArray, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {

                                try {
                                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                                    callIntent.setData(Uri.parse("tel:"+numbersArray[item]));
                                    context.startActivity(callIntent);
                                } catch (ActivityNotFoundException activityException) {
                                    Toast.makeText(context, "FAIL", Toast.LENGTH_LONG).show();
                                }
                            }

                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
                mapBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(c.getLatitude().isEmpty() || c.getLongitude().isEmpty()) {
                            DialogHelper.infoHappendDialog(context,false,getString(R.string.err_empty_title),getString(R.string.map_empty_cords));
                        }else {
                            double latitude = Double.parseDouble(c.getLatitude());
                            double longitude = Double.parseDouble(c.getLongitude());
                            String label = "EZZ STEEL";
                            String uriBegin = "geo:" + latitude + "," + longitude;
                            String query = latitude + "," + longitude + "(" + label + ")";
                            String encodedQuery = Uri.encode(query);
                            String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
                            Uri uri = Uri.parse(uriString);
                            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }}
                });
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
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
        int layout = (getIntent().getBooleanExtra(IS_ABOUT,false)) ? R.layout.activity_contcat_us : R.layout.activity_branch_details;
        parameters.put(BaseActivity.CONTENT_RESORSES_ID, layout);

        return parameters;
    }

}
