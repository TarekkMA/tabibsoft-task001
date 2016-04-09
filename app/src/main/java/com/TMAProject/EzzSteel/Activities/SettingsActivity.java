package com.TMAProject.EzzSteel.Activities;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.TMAProject.EzzSteel.Activities.Base.BaseActivity;
import com.TMAProject.EzzSteel.Activities.Base.Parameters.BackButtonInfo;
import com.TMAProject.EzzSteel.Activities.Base.Parameters.TitleInfo;
import com.TMAProject.EzzSteel.Adapters.SideDrawerAdapter;
import com.TMAProject.EzzSteel.R;
import com.TMAProject.EzzSteel.Utils.DialogHelper;
import com.TMAProject.EzzSteel.Utils.LangHelper;

import java.util.Hashtable;
import java.util.Map;

public class SettingsActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Spinner s = (Spinner)findViewById(R.id.spinner_lang);
        final Button b = (Button)findViewById(R.id.btn_save);
        s.setAdapter(new ArrayAdapter<String>(this,
                R.layout.layout_spinner_item, R.id.spinnerText,
                new String[]{getString(R.string.arabic_option),getString(R.string.english_option)}));
        final int index = (BaseActivity.lang.equalsIgnoreCase("ar")) ? 0 : 1;
        final Context c = this;
        s.setSelection(index);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogHelper.infoHappendDialog(c,false,getString(R.string.lang_change_title),getString(R.string.lang_change_msg));
                LangHelper.changeLanguage((s.getSelectedItemPosition()==0)?"ar":"en",c);
            }
        });
    }

    @Override
    protected Map<Integer, Object> getParameters() {
        Map<Integer,Object> parameters = new Hashtable<>();

        parameters.put(BaseActivity.BACK_BUTTON,new BackButtonInfo(true,MainActivity.class));
        parameters.put(BaseActivity.TITLE,
                new TitleInfo(true, SideDrawerAdapter.COMPLAINT_OPTION, ContextCompat.getColor(this, R.color.toolbar_color),18));
        parameters.put(BaseActivity.CONTENT_RESORSES_ID, R.layout.activity_settings);

        return parameters;
    }
}
