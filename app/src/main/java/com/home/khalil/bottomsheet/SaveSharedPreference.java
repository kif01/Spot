package com.home.khalil.bottomsheet;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by khalil on 2/9/17.
 */

public class SaveSharedPreference {

    static final String PREF_USER= "pref";
    static final String PREF_LINK="link";
    static final String PREF_AMERICAN = "american";
    static final String PREF_CAFE = "cafe";
    static final String PREF_CHINESE = "chinese";
    static final String PREF_DESSERTS= "dessert";
    static final String PREF_FASTFOOD = "fastfood";
    static final String PREF_ITALIAN = "italian";
    static final String PREF_LEBANESE = "lebanese";
    static final String PREF_SUSHI = "sushi";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setPref(Context ctx, String pref)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER, pref);
        editor.commit();
    }

    public static void setPrefLink(Context ctx,String pref){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_LINK, pref);
        editor.commit();
    }

    public static String getPrefLink(Context ctx){
        return getSharedPreferences(ctx).getString(PREF_LINK, "");
    }

    public static String getPref(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER, "");
    }

    public static void setAmerican(Context ctx, String pref)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_AMERICAN, pref);
        editor.commit();
    }

    public static void setCafe(Context ctx, String pref)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_CAFE, pref);
        editor.commit();
    }

    public static void setChinese(Context ctx, String pref)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_CHINESE, pref);
        editor.commit();
    }

    public static void setDessert(Context ctx, String pref)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_DESSERTS, pref);
        editor.commit();
    }

    public static void setFastFood(Context ctx, String pref)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_FASTFOOD, pref);
        editor.commit();
    }

    public static void setItalian(Context ctx, String pref)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_ITALIAN, pref);
        editor.commit();
    }

    public static void setLebanese(Context ctx, String pref)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_LEBANESE, pref);
        editor.commit();
    }

    public static void setSushi(Context ctx, String pref)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_SUSHI, pref);
        editor.commit();
    }

    public static String getAmerican(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_AMERICAN, "");
    }

    public static String getCafe(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_CAFE, "");
    }

    public static String getChinese(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_CHINESE, "");
    }

    public static String getDessert(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_DESSERTS, "");
    }

    public static String getFastFood(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_FASTFOOD, "");
    }

    public static String getItalian(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_ITALIAN, "");
    }

    public static String getLebanese(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_LEBANESE, "");
    }

    public static String getSushi(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_SUSHI, "");
    }


}
