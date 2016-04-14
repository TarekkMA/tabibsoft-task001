package com.TMAProject.EzzSteel.Utils;

import com.TMAProject.EzzSteel.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by TarekkMA on 4/2/16.
 */
public class GenralHelper {

    public static int getIconByParentName(String name,boolean prov){
        if(name==null)name="";
        switch (name){
            case "Hospitals":
                return ((prov) ? R.drawable.def_hos_org : R.drawable.def_hos_gray);
            case "Laboratories":
                return ((prov) ? R.drawable.def_lab_org : R.drawable.def_lab_gray);
            case "Emergency":
                return ((prov) ? R.drawable.def_emergency_org : R.drawable.def_emergency_gray);
            case "Pharmacies":
                return ((prov) ? R.drawable.def_pharmacy_org : R.drawable.def_pharmacy_gray);
            case "Consultants":
                return ((prov) ? R.drawable.def_consultants_org : R.drawable.def_consultants_gary);
            case "Radiology Centers":
                return ((prov) ? R.drawable.def_radiology_org : R.drawable.def_radiology_gray);
            case "Physical Theraby":
                return ((prov) ? R.drawable.def_physical_org : R.drawable.def_physical_gray);
            case "Cash Compensations":
                return ((prov) ? R.drawable.def_cash_org : R.drawable.def_cash_gray);
            case "Health Education":
                return ((prov) ? R.drawable.def_health_org : R.drawable.def_health_gray);
            default:
                return ((prov) ? R.drawable.def_hos_org_old : R.drawable.def_hos_gray_old);
        }
    }


    public static int getTempByParentName(String name){
        if(name==null)name="";
        switch (name){
            case "Hospitals":
                return R.drawable.temp_hos;
            case "Laboratories":
                return R.drawable.temp_lab;
            case "Pharmacies":
                return R.drawable.temp_pharm;
            case "Radiology Centers":
                return R.drawable.temp_rai;
            default:
                return R.drawable.temp_hos;
        }
    }


    public static Object isJSONValid(String test) {
        try {
            return new JSONObject(test);
        } catch (JSONException ex) {
            return false;
        }
    }

}
