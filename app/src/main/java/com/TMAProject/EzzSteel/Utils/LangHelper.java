package com.TMAProject.EzzSteel.Utils;

import android.content.Context;
import android.preference.PreferenceManager;

import com.TMAProject.EzzSteel.API.POJO.GET.Area;
import com.TMAProject.EzzSteel.API.POJO.GET.Branche;
import com.TMAProject.EzzSteel.API.POJO.GET.Contact;
import com.TMAProject.EzzSteel.API.POJO.GET.Department;
import com.TMAProject.EzzSteel.API.POJO.GET.Governate;
import com.TMAProject.EzzSteel.API.POJO.GET.InfoPdf;
import com.TMAProject.EzzSteel.API.POJO.GET.Notification;
import com.TMAProject.EzzSteel.API.POJO.GET.Provider;
import com.TMAProject.EzzSteel.Activities.Base.BaseActivity;
import com.TMAProject.EzzSteel.Adapters.SideDrawerAdapter;
import com.TMAProject.EzzSteel.R;

import org.json.JSONObject;

/**
 * Created by TarekkMA on 4/6/16.
 */
public class LangHelper {

    static final String LNAG_KEY_PREF = "LAAAANG_KEY_MADA_FKA";

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
    public static String getName(InfoPdf p){
        switch (BaseActivity.lang){
            case "ar":return p.getName_arabic();
            case "en":return p.getName_english();
            default:return "NULL";
        }
    }
    public static String getName(Governate g){
        switch (BaseActivity.lang){
            case "ar":return g.getName_arabic();
            case "en":return g.getName_english();
            default:return "NULL";
        }
    }
    public static String getName(Area a){
        switch (BaseActivity.lang){
            case "ar":return a.getName_arabic();
            case "en":return a.getName_english();
            default:return "NULL";
        }
    }
    public static String getName(Notification n){
        switch (BaseActivity.lang){
            case "ar":return n.getName_arabic();
            case "en":return n.getName_english();
            default:return "NULL";
        }
    }
    public static String getContent(InfoPdf p){
        switch (BaseActivity.lang){
            case "ar":return p.getContent_ar();
            case "en":return p.getContent_en();
            default:return "NULL";
        }
    }

    public static void renameNavOptions(Context c){
        SideDrawerAdapter.SEARCH_OPTION=c.getString(R.string.search_option);
        SideDrawerAdapter.HOME_OPTION=c.getString(R.string.home_option);
        SideDrawerAdapter.NOTIFICATION_OPTION=c.getString(R.string.notifcation_option);
        SideDrawerAdapter.SETTING_OPTION=c.getString(R.string.settings_option);
        SideDrawerAdapter.COMPLAINT_OPTION=c.getString(R.string.complaints_option);
        SideDrawerAdapter.CONTACT_US_OPTION=c.getString(R.string.contactus_option);
    }
    public static void changeLanguage(String code,Context context){
       // BaseActivity.lang = code.toLowerCase();
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit().putString(LNAG_KEY_PREF, code).commit();
    }
    public static String getLangage(Context c){
        return PreferenceManager.getDefaultSharedPreferences(c)
                .getString(LNAG_KEY_PREF,"en");
    }
    public static String getNotificationFromPush(JSONObject j){
        j = j.optJSONObject("alertbody");
        if(j==null)return "NULL";
        switch (BaseActivity.lang){
            case "ar":return j.optString("ar");
            case "en":return j.optString("en");
            default:return "NULL";
        }
    }
    public static String getContentFromContact(Contact c){
        switch (BaseActivity.lang){
            case "ar":return c.getContent_ar();
            case "en":return c.getContent_en();
            default:return "NULL";
        }
    }
    public static String getAddressFromContact(Contact c){
        switch (BaseActivity.lang){
            case "ar":return c.getAddress_arabic();
            case "en":return c.getAddress_english();
            default:return "NULL";
        }
    }
}
