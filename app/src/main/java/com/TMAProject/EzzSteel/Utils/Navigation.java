package com.TMAProject.EzzSteel.Utils;

import android.content.Context;
import android.content.Intent;

import com.TMAProject.EzzSteel.API.POJO.GET.Branche;
import com.TMAProject.EzzSteel.API.POJO.GET.Department;
import com.TMAProject.EzzSteel.API.POJO.GET.Provider;
import com.TMAProject.EzzSteel.Activities.BranchActivity;
import com.TMAProject.EzzSteel.Activities.BranchDetailsActivity;
import com.TMAProject.EzzSteel.Activities.ComplaintActivity;
import com.TMAProject.EzzSteel.Activities.GenralSearchActivity;
import com.TMAProject.EzzSteel.Activities.InfoPdfActivity;
import com.TMAProject.EzzSteel.Activities.ProviderActivity;
import com.TMAProject.EzzSteel.Activities.SubDerpartmentActivity;
import com.TMAProject.EzzSteel.Adapters.SideDrawerAdapter;

/**
 * Created by TarekkMA on 4/2/16.
 */
public class Navigation {
    public static void handleDepartment(Context context,Department d){
        if(Integer.parseInt(d.getChild())==0 && d.getType().equalsIgnoreCase("provider")){
            //provider
            Intent i = new Intent(context, ProviderActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra(ProviderActivity.PROVIDER_PARENT_ID,d.getId());
            i.putExtra(ProviderActivity.PROVIDER_PARENT_TYPE,d.getName_english());
            context.startActivity(i);
        }else if(Integer.parseInt(d.getChild())==0 && d.getType().matches("pdf|info")){
            //info | pdf
            Intent i = new Intent(context, InfoPdfActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra(InfoPdfActivity.INFO_PDF_ID,d.getId());
            context.startActivity(i);
        }else{
            //sub departments
            Intent i = new Intent(context, SubDerpartmentActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.putExtra(SubDerpartmentActivity.DEPARTMENT_ID,d.getId());
            context.startActivity(i);
        }
    }
    public static void handleNavItem(Context context,String s){
        Intent i = null;
        switch (s){
            case SideDrawerAdapter.COMPLAINT_OPTION:
                i = new Intent(context, ComplaintActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                break;
            case SideDrawerAdapter.SEARCH_OPTION:
                i = new Intent(context, GenralSearchActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                break;
        }
        if (i!=null){
            context.startActivity(i);
        }
    }
    public static void handleProvider(Context context,Provider p,String type){
        Intent i = new Intent(context, BranchActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra(BranchActivity.BRANCH_PARENT_TYPE,type);
        i.putExtra(BranchActivity.BRANCH_PARENT_ID,p.getId());
        context.startActivity(i);
    }
    public static void handleGenralSearch(Context context,String terms){
        Intent i = new Intent(context, BranchActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra(BranchActivity.SEARCH_TERMS,terms);
        context.startActivity(i);
    }

    public static void handleBranch(Context c,Branche b,String s){
        Intent i = new Intent(c, BranchDetailsActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra(BranchDetailsActivity.BRANCH_DETAILS_TYPE,s);
        i.putExtra(BranchDetailsActivity.BRANCH_DETAILS_PARENT_ID,b.getId());
        c.startActivity(i);
    }
}
