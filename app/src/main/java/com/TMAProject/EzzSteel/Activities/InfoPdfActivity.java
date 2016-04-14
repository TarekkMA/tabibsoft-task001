package com.TMAProject.EzzSteel.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

import java.net.URLEncoder;
import java.util.Hashtable;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;import static com.TMAProject.EzzSteel.Utils.LangHelper.*;

public class InfoPdfActivity extends BaseActivity {

    public static String INFO_PDF_ID;
    String id;
    TextView title,content;
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        toggleLoading();
        Genrator.createService(EzzWS.class).getInfoPdf(Arrays.getInfoPDFPOST(id)).enqueue(new Callback<PojoArrayWarper<InfoPdf>>() {
            @Override
            public void onResponse(Call<PojoArrayWarper<InfoPdf>> call, retrofit2.Response<PojoArrayWarper<InfoPdf>> response) {
                toggleLoading();
                if (!response.isSuccess() || response.body().result.isEmpty()) {
                    showErrDialog();
                    return;
                }
                final InfoPdf pdf = response.body().result.get(0);
                if(pdf.getFileurl()!=null){
                    btn.setVisibility(View.VISIBLE);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/viewer?url=" +
                                        URLEncoder.encode(pdf.getFullFileUrl(), "utf-8")));
                                startActivity(browserIntent);
                            }catch (Exception e)
                            {
                                e.printStackTrace();
                                Toast.makeText(InfoPdfActivity.this, "لم نتمكن من فتح الملف", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                content.setText(Html.fromHtml(getContent(pdf)));
                Linkify.addLinks(content,Linkify.PHONE_NUMBERS);
                title.setText(getName(pdf));
            }

            @Override
            public void onFailure(Call<PojoArrayWarper<InfoPdf>> call, Throwable t) {
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
