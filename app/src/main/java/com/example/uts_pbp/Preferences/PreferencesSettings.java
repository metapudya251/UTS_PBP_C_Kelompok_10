package com.example.uts_pbp.Preferences;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesSettings extends Application {
    public static final String PREFERENCES = "preferences";

    public static final String CUSTOM_THEME = "customTheme";
    public static final String LIGHT_THEME = "lightTheme";
    public static final String DARK_THEME = "darkTheme";

    private String customTheme;

    public String getCustomTheme()
    {
        return customTheme;
    }

    public void setCustomTheme(String customTheme)
    {
        this.customTheme = customTheme;
    }
}

