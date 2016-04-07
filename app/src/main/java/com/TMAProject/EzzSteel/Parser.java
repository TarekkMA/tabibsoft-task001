package com.TMAProject.EzzSteel;

import com.TMAProject.EzzSteel.API.POJO.Section;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by tarekkma on 2/9/16.
 */
public class Parser {
    public static final String NAME_ARABIC="name_arabic";
    public static final String NAME_ENGLISH="name_english";
    public static final String IMG_URL="img";

    public static ArrayList<Section> parseSections(String jsonString)
    {
        ArrayList<Section> list= new ArrayList<>();
        try {
            JSONArray json = new JSONArray(jsonString);
            for (int i=0;i<json.length();i++){
                JSONObject object=json.optJSONObject(i);
                Section section=new Section();
                section.setNameArabic(object.optString(NAME_ARABIC));
                section.setImgURL(object.optString(IMG_URL));
                section.setNameEnglish(object.optString(NAME_ENGLISH));
                list.add(section);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
