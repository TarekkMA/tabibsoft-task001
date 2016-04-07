package com.TMAProject.EzzSteel.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.TMAProject.EzzSteel.API.EzzWS;
import com.TMAProject.EzzSteel.API.Genrator;
import com.TMAProject.EzzSteel.API.POJO.Arrays;
import com.TMAProject.EzzSteel.API.POJO.GET.InfoPdf;
import com.TMAProject.EzzSteel.API.POJO.GET.PojoArrayWarper;
import com.TMAProject.EzzSteel.API.POJO.GET.Provider;
import com.TMAProject.EzzSteel.Activities.Base.BaseActivity;
import com.TMAProject.EzzSteel.Activities.Base.Parameters.BackButtonInfo;
import com.TMAProject.EzzSteel.Activities.Base.Parameters.TitleInfo;
import com.TMAProject.EzzSteel.R;
import com.TMAProject.EzzSteel.Utils.DialogHelper;
import com.android.volley.toolbox.StringRequest;

import java.util.Hashtable;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoPdfActivity extends BaseActivity {

    public static String INFO_PDF_ID;
    String id;
    TextView title,content;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toggleLoading();
        id = getIntent().getStringExtra(INFO_PDF_ID);
        if(id==null) {
            DialogHelper.errorHappendDialog(this, true, getString(R.string.err_id_depart_title), getString(R.string.err_id_depart_msg));
            return;
        }
        title = (TextView)findViewById(R.id.pdf_title);
        content = (TextView)findViewById(R.id.pdf_content);
        btn = (Button)findViewById(R.id.pdf_button);
        btn.setVisibility(View.GONE);
        getDataFromServer(this);
    }

    void getDataFromServer(final Context context){
        Genrator.createService(EzzWS.class).getInfoPdf(Arrays.getInfoPDFPOST(id)).enqueue(new Callback<PojoArrayWarper<InfoPdf>>() {
            @Override
            public void onResponse(Call<PojoArrayWarper<InfoPdf>> call, retrofit2.Response<PojoArrayWarper<InfoPdf>> response) {
                toggleLoading();
                if (!response.isSuccess()) {
                    DialogHelper.errorHappendDialog(context, true, getString(R.string.err_genaric_title), "response : " + response.code());
                    return;
                }
                if(response.body().result.isEmpty()){
                    DialogHelper.errorHappendDialog(context, true, getString(R.string.err_empty_title), getString(R.string.err_empty_msg));
                    return;
                }
                final InfoPdf pdf = response.body().result.get(0);
                if(pdf.getFileurl()!=null){
                    btn.setVisibility(View.VISIBLE);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(pdf.getFullFileUrl()));
                            startActivity(browserIntent);
                        }
                    });
                }
                content.setText(Html.fromHtml(pdf.getContent_en()));
                title.setText(pdf.getName_english());
            }

            @Override
            public void onFailure(Call<PojoArrayWarper<InfoPdf>> call, Throwable t) {
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
        parameters.put(BaseActivity.CONTENT_RESORSES_ID, R.layout.activity_info_pdf);

        return parameters;
    }
}
