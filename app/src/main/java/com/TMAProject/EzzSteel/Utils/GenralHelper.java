package com.TMAProject.EzzSteel.Utils;

import com.TMAProject.EzzSteel.R;

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

}
