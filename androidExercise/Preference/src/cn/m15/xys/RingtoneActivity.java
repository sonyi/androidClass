package cn.m15.xys;



import android.os.Bundle;
import android.preference.PreferenceActivity;

public class RingtoneActivity extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	// ����Դ�ļ�����Preferences ��ѡ���ֵ�����Զ����浽SharePreferences
	addPreferencesFromResource(R.xml.ringtone);
    }
}
