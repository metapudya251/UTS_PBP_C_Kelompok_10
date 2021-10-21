package com.example.uts_pbp.Preferences;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesSettings extends Application {
    public static final String PREFERENCES = "preferences";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    public static final String CUSTOM_THEME = "customTheme";
    public static final String LIGHT_THEME = "lightTheme";
    public static final String DARK_THEME = "darkTheme";

    private String customTheme;

    public PreferencesSettings(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("PreferencesSettings", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public String getCustomTheme()
    {
        customTheme = sharedPreferences.getString(CUSTOM_THEME,null);
        return customTheme;
    }

    public void setCustomTheme(String customTheme)
    {
        editor.putBoolean("darkTheme", true);
        editor.putString(CUSTOM_THEME, customTheme);
        editor.apply();

        editor.commit();
    }

    public boolean checkDarkMode(){
        return sharedPreferences.getBoolean("darkTheme",false);
    }
}

