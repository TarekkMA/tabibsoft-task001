package com.TMAProject.EzzSteel.API.POJO.GET;

public class Branche {

    private String id;
    private String name_english;
    private String name_arabic;
    private String img;
    private String governat_id;
    private String area_id;
    private String email;
    private String manager_name;

    private String telephone;
    private String mobile;

    private String latitude;
    private String longitude;


    private String fax;
    private String address;
    private String provider_english;
    private String provider_arabic;
    private String area_english;
    private String areas_arabic;
    private String governate_english;
    private String governate_arabic;

    public String getFullImgUrl(){
        return "http://mobileapp.ezzmedicalcare.com/"+img;
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

    public String getGovernat_id() {
        return governat_id;
    }

    public void setGovernat_id(String governat_id) {
        this.governat_id = governat_id;
    }

    public String getArea_id() {
        return area_id;
    }

    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getManager_name() {
        return manager_name;
    }

    public void setManager_name(String manager_name) {
        this.manager_name = manager_name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvider_english() {
        return provider_english;
    }

    public void setProvider_english(String provider_english) {
        this.provider_english = provider_english;
    }

    public String getProvider_arabic() {
        return provider_arabic;
    }

    public void setProvider_arabic(String provider_arabic) {
        this.provider_arabic = provider_arabic;
    }

    public String getArea_english() {
        return area_english;
    }

    public void setArea_english(String area_english) {
        this.area_english = area_english;
    }

    public String getAreas_arabic() {
        return areas_arabic;
    }

    public void setAreas_arabic(String areas_arabic) {
        this.areas_arabic = areas_arabic;
    }

    public String getGovernate_english() {
        return governate_english;
    }

    public void setGovernate_english(String governate_english) {
        this.governate_english = governate_english;
    }

    public String getGovernate_arabic() {
        return governate_arabic;
    }

    public void setGovernate_arabic(String governate_arabic) {
        this.governate_arabic = governate_arabic;
    }
}
