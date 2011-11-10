package org.brandroid.utils;

import java.util.HashSet;
import java.util.Hashtable;

import org.brandroid.openmanager.R;
import org.brandroid.openmanager.R.xml;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

public class Preferences {
	private static Preferences preferences;
	private static Context mContext;
	private static Hashtable<String, SharedPreferences> mStorageHash = new Hashtable<String, SharedPreferences>();
	
	public Preferences(Context context)
	{
		mContext = context;
		PreferenceManager.setDefaultValues(context, R.xml.preferences, false);
		//mStorageHash.put(file, context.getSharedPreferences(file, PreferenceActivity.MODE_PRIVATE));
		//context.getSharedPreferences(PREFS_NAME, 0);
	}
	public static synchronized SharedPreferences getPreferences(Context context, String file)
	{
		if(mStorageHash.containsKey(file))
			return mStorageHash.get(file);
		if(preferences == null)
			preferences = new Preferences(context);
		Logger.LogVerbose("Getting instance of SharedPreferences");
		return mStorageHash.put(file, context.getSharedPreferences(file, PreferenceActivity.MODE_PRIVATE));
	}
	public static SharedPreferences getPreferences(String file)
	{
		return mStorageHash.get(file);
	}
	
	public String getSetting(String file, String key, String defValue)
	{
		try {
			return getPreferences(file).getString(key, defValue);
		} catch(ClassCastException cce) { Logger.LogError("Couldn't get string \"" + key + "\" from Prefs.", cce); return defValue; }
		catch(NullPointerException npe) { return defValue; }
	}
	public int getSetting(String file, String key, Integer defValue)
	{
		//return mStorage.getInt(key, defValue);
		try {
			String s = getPreferences(file).getString(key, defValue.toString());
			return Integer.parseInt(s);
		} catch(Exception e) { return defValue; }
	}
	public float getSetting(String file, String key, Float defValue)
	{
		//return mStorage.getFloat(key, defValue);
		try {
			String s = getPreferences(file).getString(key, defValue.toString());
			return Float.parseFloat(s);
		} catch(Exception e) { return defValue; }
	}
	public Double getSetting(String file, String key, Double defValue)
	{
		//return mStorage.getFloat(key, defValue);
		try {
			String s = getPreferences(file).getString(key, defValue.toString());
			return Double.parseDouble(s);
		} catch(Exception e) { return defValue; }
	}
	public Boolean getSetting(String file, String key, Boolean defValue)
	{
		//return mStorage.getBoolean(key, defValue);
		try {
			String s = getPreferences(file).getString(key, defValue.toString());
			return Boolean.parseBoolean(s);
		} catch(Exception e) { return defValue; }
	}
	public Long getSetting(String file, String key, Long defValue)
	{
		//try {
			//return mStorage.getLong(key, defValue);
		//} catch(Throwable t) { return defValue; }
		try {
			String s = getPreferences(file).getString(key, defValue.toString());
			return Long.parseLong(s);
		} catch(Exception e) { return defValue; }
	}
	public String getString(String file, String key, String defValue) 	{ if(!hasSetting(file, key)) return getSetting("global", key, defValue); else return getSetting(file, key, defValue); }
	public int getInt(String file, String key, int defValue) 			{ if(!hasSetting(file, key)) return getSetting("global", key, defValue); else return getSetting(file, key, defValue); }
	public float getFloat(String file, String key, float defValue) 		{ if(!hasSetting(file, key)) return getSetting("global", key, defValue); else return getSetting(file, key, defValue); }
	public Boolean getBoolean(String file, String key, Boolean defValue) { if(!hasSetting(file, key)) return getSetting("global", key, defValue); else return getSetting(file, key, defValue); }
	public Long getLong(String file, String key, Long defValue) 			{ if(!hasSetting(file, key)) return getSetting("global", key, defValue); else return getSetting(file, key, defValue); }
	
	public void setSetting(String file, String key, String value)
	{
		try {
			Logger.LogDebug("Setting " + key + " to " + value);
			SharedPreferences.Editor editor = getPreferences(mContext, file).edit();
			editor.putString(key, value.toString());
			//editor.putString(key, value);
			editor.commit();
		} catch(Exception e) { Logger.LogError("Couldn't set " + key + " in " + file + " preferences.", e); }
	}
	public void setSetting(String file, String key, Boolean value)
	{
		try {
			SharedPreferences.Editor editor = getPreferences(mContext, file).edit();
			editor.putString(key, value.toString());
			//editor.putBoolean(key, value);
			editor.commit();
		} catch(Exception e) { Logger.LogError("Couldn't set " + key + " in " + file + " preferences.", e); }
	}
	public void setSetting(String file, String key, Integer value)
	{
		try {
			SharedPreferences.Editor editor = getPreferences(mContext, file).edit();
			editor.putString(key, value.toString());
			//editor.putInt(key, value);
			editor.commit();
		} catch(Exception e) { Logger.LogError("Couldn't set " + key + " in " + file + " preferences.", e); }
	}
	public void setSettings(String file, Object... vals)
	{
		try {
			SharedPreferences.Editor editor = getPreferences(mContext, file).edit();
			for(int i = 0; i < vals.length - 1; i += 2)
			{
				String key = vals[i].toString();
				Object val = vals[i+1];
				if(val == null) return;
				if(Integer.class.equals(val.getClass()))
					editor.putInt(key, (Integer)val);
				else if(Float.class.equals(val.getClass()))
					editor.putFloat(key, (Float)val);
				else if(Long.class.equals(val.getClass()))
					editor.putLong(key, (Long)val);
				else if(Boolean.class.equals(val.getClass()))
					editor.putBoolean(key, (Boolean)val);
				else
					editor.putString(key, val.toString());
			}
			editor.commit();
		} catch(Exception e) { Logger.LogError("Couldn't set values in " + file + " preferences.", e); }
		//pairs.
	}
	public Boolean hasSetting(String file, String key)
	{
		try {
			return getPreferences(file).contains(key);
		} catch(Exception e) { return false; }
	}
	
	public SharedPreferences getPreferences() {
        return getPreferences("global");
    }
}