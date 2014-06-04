package cn.m15.xys;



import android.os.Bundle;
import android.preference.PreferenceActivity;

public class RingtoneActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	// 从资源文件中添Preferences ，选择的值将会自动保存到SharePreferences
	addPreferencesFromResource(R.xml.ringtone);
    }
}
