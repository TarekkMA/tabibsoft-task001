package com.TMAProject.EzzSteel.API;

import com.TMAProject.EzzSteel.API.POJO.GET.Area;
import com.TMAProject.EzzSteel.API.POJO.GET.Branche;
import com.TMAProject.EzzSteel.API.POJO.GET.ComplaintResult;
import com.TMAProject.EzzSteel.API.POJO.GET.Department;
import com.TMAProject.EzzSteel.API.POJO.GET.Governate;
import com.TMAProject.EzzSteel.API.POJO.GET.InfoPdf;
import com.TMAProject.EzzSteel.API.POJO.GET.PojoArrayWarper;
import com.TMAProject.EzzSteel.API.POJO.GET.Provider;
import com.TMAProject.EzzSteel.API.POJO.POST.AreaPOST;
import com.TMAProject.EzzSteel.API.POJO.POST.BranchDetailesPOST;
import com.TMAProject.EzzSteel.API.POJO.POST.BranchePOST;
import com.TMAProject.EzzSteel.API.POJO.POST.ComplainPOST;
import com.TMAProject.EzzSteel.API.POJO.POST.GeneralSearchPOST;
import com.TMAProject.EzzSteel.API.POJO.POST.InfoPDFPOST;
import com.TMAProject.EzzSteel.API.POJO.POST.ProviderPOST;
import com.TMAProject.EzzSteel.API.POJO.POST.SearchPOST;
import com.TMAProject.EzzSteel.API.POJO.POST.subDepartmentPOST;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by tarekkma on 2/21/16.
 */
public interface EzzWS {
    @GET("providers/departments")
    Call<List<Department>> getDepartments();
    @POST("providers/subdepartments")
    Call<List<Department>> getSubDepartments(@Body List<subDepartmentPOST> sentData);
    @POST("providers/providers")
    Call<PojoArrayWarper<Provider>> getProviders(@Body List<ProviderPOST> sentData);
    @POST("providers/branchessearch")
    Call<List<Branche>> getSearch(@Body List<SearchPOST> sentData);
    @POST("providers/infopdf")
    Call<PojoArrayWarper<InfoPdf>> getInfoPdf(@Body List<InfoPDFPOST> sentData);
    @POST("providers/complains")
    Call<ComplaintResult> addComplaint(@Body List<ComplainPOST> sentData);
    @GET("providers/governates")
    Call<List<Governate>> getGovernate();
    @POST("providers/areas")
    Call<PojoArrayWarper<Area>> getAreas(@Body List<AreaPOST> sentData);
    @POST("providers/branches")
    Call<List<Branche>> getBranches(@Body List<BranchePOST> sentData);
    @POST("providers/search_branches")
    Call<List<Branche>> getGenralSearch(@Body List<GeneralSearchPOST> sentData);
    @POST("providers/branchedetail")
    Call<List<Branche>> getBranchDetailes(@Body List<BranchDetailesPOST> sentData);


}
