package com.TMAProject.EzzSteel.API.POJO.GET;

import com.TMAProject.EzzSteel.API.Genrator;

public class Contact {
  private String id;
  private String address_english;
  private String address_arabic;
  private String email;
  private String phone;
  private String img;
  private String content_ar;
  private String content_en;
  private String latitude;
  private String longitude;


  public  String getFullImgUrl(){
    return Genrator.API_URL + img;
  }
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getAddress_english() {
    return address_english;
  }

  public void setAddress_english(String address_english) {
    this.address_english = address_english;
  }

  public String getAddress_arabic() {
    return address_arabic;
  }

  public void setAddress_arabic(String address_arabic) {
    this.address_arabic = address_arabic;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
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

  public String getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = latitude;
  }

  public String getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = longitude;
  }
}
