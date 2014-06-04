package cn.m15.xys;



import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceClickListener;
import android.widget.Toast;

public class AllActivity extends PreferenceActivity {
   
    /**自定义布局A**/
    Preference preference0 = null;
    
    /**自定义布局B**/
    Preference preference1 = null;
    
    Context mContext = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	// 从资源文件中添Preferences ，选择的值将会自动保存到SharePreferences
	addPreferencesFromResource(R.xml.all);
	mContext = this;
	
	preference0 = findPreference("pref_key_0");
	
	preference0.setOnPreferenceClickListener(new OnPreferenceClickListener() {
	    
	    @Override
	    public boolean onPreferenceClick(Preference preference) {
		  Toast.makeText(mContext, "自定义布局A被按下", Toast.LENGTH_LONG).show();  
		return false;
	    }
	});
	preference1 = findPreference("pref_key_1");
	
	preference1.setOnPreferenceClickListener(new OnPreferenceClickListener() {
	    
	    @Override
	    public boolean onPreferenceClick(Preference preference) {
		  Toast.makeText(mContext, "自定义布局B被按下", Toast.LENGTH_LONG).show();  
		return false;
	    }
	});
    }
}
