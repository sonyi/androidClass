package cn.m15.xys;



import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceClickListener;
import android.widget.Toast;

public class AllActivity extends PreferenceActivity {
   
    /**�Զ��岼��A**/
    Preference preference0 = null;
    
    /**�Զ��岼��B**/
    Preference preference1 = null;
    
    Context mContext = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	// ����Դ�ļ�����Preferences ��ѡ���ֵ�����Զ����浽SharePreferences
	addPreferencesFromResource(R.xml.all);
	mContext = this;
	
	preference0 = findPreference("pref_key_0");
	
	preference0.setOnPreferenceClickListener(new OnPreferenceClickListener() {
	    
	    @Override
	    public boolean onPreferenceClick(Preference preference) {
		  Toast.makeText(mContext, "�Զ��岼��A������", Toast.LENGTH_LONG).show();  
		return false;
	    }
	});
	preference1 = findPreference("pref_key_1");
	
	preference1.setOnPreferenceClickListener(new OnPreferenceClickListener() {
	    
	    @Override
	    public boolean onPreferenceClick(Preference preference) {
		  Toast.makeText(mContext, "�Զ��岼��B������", Toast.LENGTH_LONG).show();  
		return false;
	    }
	});
    }
}
