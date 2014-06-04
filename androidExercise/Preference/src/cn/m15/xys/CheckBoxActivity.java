package cn.m15.xys;



import android.content.Context;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.widget.Toast;

public class CheckBoxActivity extends PreferenceActivity {

    Context mContext = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	// ����Դ�ļ�����Preferences ��ѡ���ֵ�����Զ����浽SharePreferences
	addPreferencesFromResource(R.xml.checkbox);
    
	mContext = this;
	
	//CheckBoxPreference���
	CheckBoxPreference mCheckbox0 = (CheckBoxPreference) findPreference("checkbox_0");
	mCheckbox0.setOnPreferenceClickListener(new OnPreferenceClickListener() {
	    
	    @Override
	    public boolean onPreferenceClick(Preference preference) {
		//������Լ��������CheckBox �ĵ���¼�
		return true;
	    }
	});
	
	mCheckbox0.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
	    
	    @Override
	    public boolean onPreferenceChange(Preference arg0, Object newValue) {
		//������Լ�����checkBox��ֵ�Ƿ�ı���
		//���ҿ����õ��¸ı��ֵ
		  Toast.makeText(mContext, "checkBox_0�ı��ֵΪ" +  (Boolean)newValue, Toast.LENGTH_LONG).show();  
		return true;
	    }
	});

	CheckBoxPreference mCheckbox1 = (CheckBoxPreference) findPreference("checkbox_1");
	mCheckbox1.setOnPreferenceClickListener(new OnPreferenceClickListener() {
	    
	    @Override
	    public boolean onPreferenceClick(Preference preference) {
		//������Լ��������CheckBox �ĵ���¼�
		return true;
	    }
	});
	
	mCheckbox1.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
	    
	    @Override
	    public boolean onPreferenceChange(Preference arg0, Object newValue) {
		//������Լ�����checkBox��ֵ�Ƿ�ı���
		//���ҿ����õ��¸ı��ֵ
		  Toast.makeText(mContext, "checkBox_1�ı��ֵΪ" +  (Boolean)newValue, Toast.LENGTH_LONG).show();  
		return true;
	    }
	});
    
    }

}
