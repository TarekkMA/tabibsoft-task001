package com.TMAProject.EzzSteel.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.TMAProject.EzzSteel.API.EzzWS;
import com.TMAProject.EzzSteel.API.Genrator;
import com.TMAProject.EzzSteel.API.POJO.Arrays;
import com.TMAProject.EzzSteel.API.POJO.GET.Area;
import com.TMAProject.EzzSteel.API.POJO.GET.Department;
import com.TMAProject.EzzSteel.API.POJO.GET.Governate;
import com.TMAProject.EzzSteel.API.POJO.GET.PojoArrayWarper;
import com.TMAProject.EzzSteel.Activities.Base.BaseActivity;
import com.TMAProject.EzzSteel.Activities.Base.Parameters.BackButtonInfo;
import com.TMAProject.EzzSteel.Activities.Base.Parameters.TitleInfo;
import com.TMAProject.EzzSteel.Adapters.SideDrawerAdapter;
import com.TMAProject.EzzSteel.R;
import com.TMAProject.EzzSteel.Utils.Navigation;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.TMAProject.EzzSteel.Utils.LangHelper.*;
public class GenralSearchActivity extends BaseActivity {

    boolean a=false,b=false;

    Spinner gov,city,department;
    List<Governate> governateList;
    List<Area> areaList;
    List<Department> departmentList;
    View parentLayout;
    Context context;

    Button reset,search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toggleLoading();

        context = this;
        parentLayout = (CoordinatorLayout)findViewById(R.id.base_parent);
        gov = (Spinner)findViewById(R.id.spinner_gov);
        city = (Spinner)findViewById(R.id.spinner_city);
        department = (Spinner)findViewById(R.id.spinner_department);
        search=(Button)findViewById(R.id.btn_search);
        reset=(Button)findViewById(R.id.btn_reset);
        gov.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (governateList == null || governateList.isEmpty()) return;
                if (position == 0) {
                    if (areaList != null && !areaList.isEmpty()) {
                       fillAreaEmpty();
                    }
                }else {
                    toggleLoading();
                    getAreasFromServer(governateList.get(position - 1).getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (governateList != null && !governateList.isEmpty()) {
                    gov.setSelection(0);
                }
                if (departmentList != null && !departmentList.isEmpty()) {
                    department.setSelection(0);
                }
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gov.getSelectedItemPosition()==0 && department.getSelectedItemPosition()==0){
                    Snackbar.make(parentLayout,getString(R.string.genral_serach_less),Snackbar.LENGTH_LONG).show();
                }else{
                    Navigation.handleGenralSearch(context,
                            department.getSelectedItemPosition()+"-"+gov.getSelectedItemPosition()+"-"+city.getSelectedItemPosition());
                }
            }
        });
        fillAreaEmpty();
        getGovFromServer();
        getDepartmentsFromServer();
    }

    void fillAreaEmpty(){
        city.setAdapter(new ArrayAdapter<String>(context,
                R.layout.layout_spinner_item, R.id.spinnerText,
                new String[]{getString(R.string.spinner_area)}));
    }

    void getGovFromServer(){
        Genrator.createService(EzzWS.class).getGovernate().enqueue(new Callback<List<Governate>>() {
            @Override
            public void onResponse(Call<List<Governate>> call, Response<List<Governate>> response) {
                a=true;
                if(a==b)toggleLoading();
                if(!response.isSuccess())return;
                governateList = response.body();
                ArrayList<String> spinnerList = new ArrayList<String>();
                for (Governate g : response.body()) {
                    spinnerList.add(getName(g));
                }
                spinnerList.add(0, getString(R.string.spinner_gov));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        R.layout.layout_spinner_item, R.id.spinnerText,
                        spinnerList);
                gov.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<List<Governate>> call, Throwable t) {
                a=true;
                if(a==b)toggleLoading();
            }
        });
    }

    void getAreasFromServer(String id){
        Genrator.createService(EzzWS.class).getAreas(Arrays.getAreaPOST(id)).enqueue(new Callback<PojoArrayWarper<Area>>() {
            @Override
            public void onResponse(Call<PojoArrayWarper<Area>> call, Response<PojoArrayWarper<Area>> response) {
                toggleLoading();
                if(!response.isSuccess())return;
                areaList = response.body().result;
                ArrayList<String> spinnerList = new ArrayList<String>();
                for (Area a : areaList)
                    spinnerList.add(getName(a));
                spinnerList.add(0, getString(R.string.spinner_area));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        R.layout.layout_spinner_item, R.id.spinnerText,
                        spinnerList);
                city.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<PojoArrayWarper<Area>> call, Throwable t) {
               toggleLoading();
            }
        });
    }

    void getDepartmentsFromServer(){
        Genrator.createService(EzzWS.class).getDepartments().enqueue(new Callback<List<Department>>() {
            @Override
            public void onResponse(Call<List<Department>> call, Response<List<Department>> response) {
                b=true;
                if(a==b)toggleLoading();
                departmentList = response.body();
                ArrayList<String> spinnerList = new ArrayList<String>();
                for (Department d : response.body())
                    spinnerList.add(getName(d));
                spinnerList.add(0, getString(R.string.spinner_dep));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        R.layout.layout_spinner_item, R.id.spinnerText,
                        spinnerList);
                department.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Department>> call, Throwable t) {
                b=true;
                if(a==b)toggleLoading();
            }
        });
    }

    @Override
    protected Map<Integer, Object> getParameters() {
        Map<Integer,Object> parameters = new Hashtable<>();

        parameters.put(BaseActivity.BACK_BUTTON,new BackButtonInfo(true,MainActivity.class));
        parameters.put(BaseActivity.TITLE,
                new TitleInfo(true, SideDrawerAdapter.SEARCH_OPTION, ContextCompat.getColor(this, R.color.toolbar_color),18));
        parameters.put(BaseActivity.CONTENT_RESORSES_ID, R.layout.activity_genral_search);

        return parameters;
    }

}
