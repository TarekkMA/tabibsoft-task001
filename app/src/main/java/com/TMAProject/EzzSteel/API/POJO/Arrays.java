package com.TMAProject.EzzSteel.API.POJO;

import com.TMAProject.EzzSteel.API.POJO.POST.AreaPOST;
import com.TMAProject.EzzSteel.API.POJO.POST.BranchDetailesPOST;
import com.TMAProject.EzzSteel.API.POJO.POST.BranchePOST;
import com.TMAProject.EzzSteel.API.POJO.POST.ComplainPOST;
import com.TMAProject.EzzSteel.API.POJO.POST.GeneralSearchPOST;
import com.TMAProject.EzzSteel.API.POJO.POST.InfoPDFPOST;
import com.TMAProject.EzzSteel.API.POJO.POST.ProviderPOST;
import com.TMAProject.EzzSteel.API.POJO.POST.SearchPOST;
import com.TMAProject.EzzSteel.API.POJO.POST.subDepartmentPOST;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TarekkMA on 4/1/16.
 */
public class Arrays {
    public static List<ProviderPOST> getProvidersPOST(String id){
        ArrayList<ProviderPOST> l = new ArrayList<>();
        l.add(new ProviderPOST(id));
        return l;
    }
    public static ArrayList<BranchePOST> getBranchesPOST(String id){
        ArrayList<BranchePOST> l = new ArrayList<>();
        l.add(new BranchePOST(id));
        return l;
    }
    public static ArrayList<GeneralSearchPOST> getGeneralSearchPOST(String id_d,String id_g,String id_a){
        ArrayList<GeneralSearchPOST> l = new ArrayList<>();
        l.add(new GeneralSearchPOST(id_d,id_g,id_a));
        return l;
    }
    public static List<subDepartmentPOST> getSubDepartmentSearchPOST(String id){
        ArrayList<subDepartmentPOST> l = new ArrayList<>();
        l.add(new subDepartmentPOST(id));
        return l;
    }
    public static ArrayList<InfoPDFPOST> getInfoPDFPOST(String id){
        ArrayList<InfoPDFPOST> l = new ArrayList<>();
        l.add(new InfoPDFPOST(id));
        return l;
    }
    public static List<ComplainPOST> getComplainPOST(String name, String phone, String complains){
        ArrayList<ComplainPOST> l = new ArrayList<>();
        l.add(new ComplainPOST(name,phone,complains));
        return l;
    }
    public static List<SearchPOST> getSearchPOST(String word){
        ArrayList<SearchPOST> l = new ArrayList<>();
        l.add(new SearchPOST(word));
        return l;
    }
    public static List<AreaPOST> getAreaPOST(String id){
        ArrayList<AreaPOST> l = new ArrayList<>();
        l.add(new AreaPOST(id));
        return l;
    }
    public static List<BranchDetailesPOST> getBranchDetailsPOST(String id){
        ArrayList<BranchDetailesPOST> l = new ArrayList<>();
        l.add(new BranchDetailesPOST(id));
        return l;
    }
}
