package com.example.tarekkma.task001;

import com.example.tarekkma.task001.modles.Section;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tarekkma on 2/9/16.
 */
public class Parser {
    public static final String NAME_ARABIC="name_arabic";
    public static final String IMG_URL="img";

    public static List<Section> parseSections(String jsonString)
    {
        List<Section> list= new ArrayList<>();
        try {
            JSONArray json = new JSONArray(jsonString);
            for (int i=0;i<json.length();i++){
                JSONObject object=json.optJSONObject(i);
                Section section=new Section();
                section.setNameArabic(object.optString(NAME_ARABIC));
                section.setImgURL(object.optString(IMG_URL));
                list.add(section);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
