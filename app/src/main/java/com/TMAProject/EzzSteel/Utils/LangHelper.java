package com.TMAProject.EzzSteel.Utils;

import com.TMAProject.EzzSteel.API.POJO.GET.Branche;
import com.TMAProject.EzzSteel.API.POJO.GET.Department;
import com.TMAProject.EzzSteel.API.POJO.GET.Provider;
import com.TMAProject.EzzSteel.Activities.Base.BaseActivity;

/**
 * Created by TarekkMA on 4/6/16.
 */
public class LangHelper {
    public static String getName(Department d){
        switch (BaseActivity.lang){
            case "ar":return d.getName_arabic();
            case "en":return d.getName_english();
            default:return "NULL";
        }
    }
    public static String getName(Provider p){
        switch (BaseActivity.lang){
            case "ar":return p.getName_arabic();
            case "en":return p.getName_english();
            default:return "NULL";
        }
    }
    public static String getName(Branche b){
        switch (BaseActivity.lang){
            case "ar":return b.getName_arabic();
            case "en":return b.getName_english();
            default:return "NULL";
        }
    }
}
