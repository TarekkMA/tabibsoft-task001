package com.TMAProject.EzzSteel.Activities.Base.Parameters;

/**
 * Created by TarekkMA on 4/1/16.
 */
public class TitleInfo{
    public boolean custom = false;
    public String title = "NULL";
    public int colorRes = -1;
    public boolean subTitle = false;
    public String subTitleText = "NULL";
    public int subColorRes = -1;
    public int size = 18;
    public int subSize = 10;

    public TitleInfo(boolean custom) {
        this.custom = custom;
    }

    public TitleInfo(boolean custom, String title, int colorRes, int size) {
        this.custom = custom;
        this.title = title;
        this.colorRes = colorRes;
        this.size = size;
    }

    public TitleInfo(boolean custom, String title, int colorRes, boolean subTitle, String subTitleText, int subColorRes, int size, int subSize) {
        this.custom = custom;
        this.title = title;
        this.colorRes = colorRes;
        this.subTitle = subTitle;
        this.subTitleText = subTitleText;
        this.subColorRes = subColorRes;
        this.size = size;
        this.subSize = subSize;
    }
}