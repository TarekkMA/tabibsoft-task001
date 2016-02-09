package com.example.tarekkma.task001.modles;

/**
 * Created by tarekkma on 2/9/16.
 */
public class Section {
    private String nameArabic;
    private String nameEnglish;
    private String imgURL;

    public String getNameArabic() {
        return nameArabic;
    }

    public void setNameArabic(String nameArabic) {
        this.nameArabic = nameArabic;
    }

    public String getNameEnglish() {
        return nameEnglish;
    }

    public void setNameEnglish(String nameEnglish) {
        this.nameEnglish = nameEnglish;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getFullImgURL(){
        return "http://mobileapp.ezzmedicalcare.com/"+this.imgURL;
    }
}
