package com.example.mvvm_viewmodel_romm_livedata.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {

    public static final String LAYOUT_INFO_KEY = "CURRENT_LAYOUT_INFO";

    private SharedPreferences mSharedPref;

    public SharedPrefManager(Context context) {

        if (mSharedPref==null) {
            mSharedPref = context.getSharedPreferences(context.getPackageName(), Activity.MODE_PRIVATE);
        }
        }

    public void setLayoutSetting(final String key,boolean value){

        SharedPreferences.Editor prefEditor = mSharedPref.edit();
        prefEditor.putBoolean(key,value);
        prefEditor.commit();

    }

    public boolean getLayoutSetting(final String key){

        return mSharedPref.getBoolean(key,false);
    }


    }
