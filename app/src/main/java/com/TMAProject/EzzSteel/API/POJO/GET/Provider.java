package com.TMAProject.EzzSteel.API.POJO.GET;

public class Provider {
  private String id;
  private String name_english;
  private String name_arabic;
  private String img;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
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
}
