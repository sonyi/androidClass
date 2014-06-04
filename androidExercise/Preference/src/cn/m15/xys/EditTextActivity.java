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
	// 从资源文件中添Preferences ，选择的值将会自动保存到SharePreferences
	addPreferencesFromResource(R.xml.edittext);

	mContext = this;

	// EditTextPreference组件
	EditTextPreference mEditText = (EditTextPreference) findPreference("edit_0");
	
	//设置dialog按钮信息
	mEditText.setPositiveButtonText("确定");
	mEditText.setNegativeButtonText("取消");
	
	//设置按钮图标
	mEditText.setDialogIcon(R.drawable.jay);
    }

  
}
