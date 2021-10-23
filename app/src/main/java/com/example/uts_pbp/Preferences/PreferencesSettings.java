package com.example.uts_pbp.Preferences;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesSettings extends Application {
    public static final String PREFERENCES = "preferences";

    public static final String CUSTOM_THEME = "customTheme";
    public static final String LIGHT_THEME = "lightTheme";
    public static final String DARK_THEME = "darkTheme";

    public static final String CUSTOM_MODE = "customMode";
    public static final String PORTRAIT_MODE = "customMode";
    public static final String LANDSCAPE_MODE = "customMode";

    private String customTheme;
    private String customMode;

    public String getCustomTheme()
    {
        return customTheme;
    }

    public void setCustomTheme(String customTheme)
    {
        this.customTheme = customTheme;
    }

    public String getCustomMode() {
        return customMode;
    }

    public void setCustomMode(String customMode) {
        this.customMode = customMode;
    }
}

