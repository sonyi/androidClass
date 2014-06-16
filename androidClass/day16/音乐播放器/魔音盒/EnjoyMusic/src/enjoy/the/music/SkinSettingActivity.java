package enjoy.the.music;



import enjoy.the.music.main.adapter.ImageAdapter;
import enjoy.the.music.tools.Setting;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class SkinSettingActivity extends SettingActivity {
	private GridView gv_skin;
	private ImageAdapter adapter;
	private Setting mSetting;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ����ȫ����ʾ
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.skinsetting);
     	resultCode=2;
		setBackButton();
		setTopTitle(getResources().getString(R.string.skin_settings));
		
		mSetting=new Setting(this, true);
		
		adapter=new ImageAdapter(this, mSetting.getCurrentSkinId());
		gv_skin=(GridView)findViewById(R.id.gv_skin);
		gv_skin.setAdapter(adapter);
		gv_skin.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				//����GridView
				adapter.setCurrentId(position);
				//���±���ͼƬ
				SkinSettingActivity.this.getWindow().setBackgroundDrawableResource(Setting.SKIN_RESOURCES[position]);
				if(position==0){ Toast.makeText(SkinSettingActivity.this,"������������", Toast.LENGTH_SHORT).show();  }
				if(position==1){ Toast.makeText(SkinSettingActivity.this,"ֿ��Ƥֽ����", Toast.LENGTH_SHORT).show();  }
				if(position==2){ Toast.makeText(SkinSettingActivity.this,"�ܿ��㳵����", Toast.LENGTH_SHORT).show();  }
				if(position==3){ Toast.makeText(SkinSettingActivity.this,"������ɴ����", Toast.LENGTH_SHORT).show();  }
				if(position==4){ Toast.makeText(SkinSettingActivity.this,"�� �� ������", Toast.LENGTH_SHORT).show();  }
				if(position==5){ Toast.makeText(SkinSettingActivity.this,"�ѹ�Ӣ����", Toast.LENGTH_SHORT).show();  }
				if(position==6){ Toast.makeText(SkinSettingActivity.this,"���º�������", Toast.LENGTH_SHORT).show();  }
				if(position==7){ Toast.makeText(SkinSettingActivity.this,"����Ħ��������", Toast.LENGTH_SHORT).show();  }
				if(position==8){ Toast.makeText(SkinSettingActivity.this,"�����黳����", Toast.LENGTH_SHORT).show();  }
				if(position==9){ Toast.makeText(SkinSettingActivity.this,"ɽˮʫ������", Toast.LENGTH_SHORT).show();  }
				//��������
				mSetting.setCurrentSkinResId(position);
				
			}
		});
		

	}
	
}
