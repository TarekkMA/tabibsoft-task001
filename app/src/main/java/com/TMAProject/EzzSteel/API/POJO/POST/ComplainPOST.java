package com.TMAProject.EzzSteel.API.POJO.POST;

/**
 * Created by TarekkMA on 4/1/16.
 */
public class ComplainPOST {
    private String name;
    private String phone;
    private String complains;

    public ComplainPOST(String name, String phone, String complains) {
        this.name = name;
        this.phone = phone;
        this.complains = complains;
    }
}
