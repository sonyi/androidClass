package cn.m15.xys;



import android.content.Context;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;

public class EditTextActivity extends PreferenceActivity {

    Context mContext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	// ����Դ�ļ�����Preferences ��ѡ���ֵ�����Զ����浽SharePreferences
	addPreferencesFromResource(R.xml.edittext);

	mContext = this;

	// EditTextPreference���
	EditTextPreference mEditText = (EditTextPreference) findPreference("edit_0");
	
	//����dialog��ť��Ϣ
	mEditText.setPositiveButtonText("ȷ��");
	mEditText.setNegativeButtonText("ȡ��");
	
	//���ð�ťͼ��
	mEditText.setDialogIcon(R.drawable.jay);
    }

  
}
