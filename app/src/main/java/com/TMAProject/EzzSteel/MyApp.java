package com.TMAProject.EzzSteel;

import android.app.Application;

import com.TMAProject.EzzSteel.IO.FontManager;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by TarekkMA on 4/1/16.
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/HelveticaNeueLTArabic-Roman.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }
}
