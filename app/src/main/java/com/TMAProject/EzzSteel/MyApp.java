package com.TMAProject.EzzSteel;

import android.app.Application;
import android.util.Log;

import com.TMAProject.EzzSteel.IO.FontManager;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by TarekkMA on 4/1/16.
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);

        Parse.initialize(this, "02lvTRAMlnFtzLyxZfEha4wEz8UppKjFlcka0c4B", "BhUYNucSuYh0swpMrDPeANHNfvtptL0Fqjo2Pgn7");

        ParseInstallation.getCurrentInstallation().saveInBackground();

        ParsePush.subscribeInBackground("");

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/HelveticaNeueLTArabic-Roman.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }
}
