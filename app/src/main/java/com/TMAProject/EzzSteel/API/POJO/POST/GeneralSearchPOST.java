package com.TMAProject.EzzSteel.API.POJO.POST;

/**
 * I'll <3 EGYPT 4 EVER
 * Created by TarekkMA on 4/1/16.
 * www.FaceBook.com/tarekkma1
 */
public class GeneralSearchPOST {
    private String dept_id;
    private String governat_id;
    private String area_id;

    public GeneralSearchPOST(String dept_id, String governat_id, String area_id) {
        this.dept_id = dept_id;
        this.area_id = area_id;
        this.governat_id = governat_id;
    }
}
