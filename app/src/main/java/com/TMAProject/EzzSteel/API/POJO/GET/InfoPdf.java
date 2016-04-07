package com.TMAProject.EzzSteel.API.POJO.GET;

import com.TMAProject.EzzSteel.API.POJO.POST.SearchPOST;

public class InfoPdf {
  private String id;
  private String name_english;
  private String name_arabic;
  private String img;
  private String fileurl;
  private String content_ar;
  private String content_en;


  public String getFullFileUrl(){
    return "http://mobileapp.ezzmedicalcare.com/"+fileurl;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName_english() {
    return name_english;
  }

  public void setName_english(String name_english) {
    this.name_english = name_english;
  }

  public String getName_arabic() {
    return name_arabic;
  }

  public void setName_arabic(String name_arabic) {
    this.name_arabic = name_arabic;
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  public String getFileurl() {
    return fileurl;
  }

  public void setFileurl(String fileurl) {
    this.fileurl = fileurl;
  }

  public String getContent_ar() {
    return content_ar;
  }

  public void setContent_ar(String content_ar) {
    this.content_ar = content_ar;
  }

  public String getContent_en() {
    return content_en;
  }

  public void setContent_en(String content_en) {
    this.content_en = content_en;
  }
}
