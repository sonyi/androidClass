package cn.ingenic.gabriel.applist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

/*
 * Just for practice.
 * mail to: gabriel_luoyer@sina.com
 * welcome to my blog http://blog.sina.com.cn/0luoyer0
 */
public class AppList extends Activity {
    private ListView mSysLv, mLocalLv;
    private View mLoadView, mSysView, mLocalView;
    private Handler mHandler;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        initView();
        loadData();
    }
    
    private void initView() {
    	mLoadView = findViewById(R.id.loading);
    	
    	mSysView = findViewById(R.id.sys_ll);
        mSysLv = (ListView) mSysView.findViewById(R.id.sys_lv);
        
        
        mLocalView = findViewById(R.id.local_ll);
        mLocalLv = (ListView) mLocalView.findViewById(R.id.local_lv);

    }
    private void loadData() {
        mHandler = new Handler() {
        	public void handleMessage(Message msg) {
        		switch(msg.what) {
	        		case 0:
	        			mLoadView.setVisibility(View.GONE);
	        			break;
	        		case 1:
	        			mSysView.setVisibility(View.VISIBLE);
	        			break;
	        		case 2:
	        			mLocalView.setVisibility(View.VISIBLE);
	        			break;
        		}
        	}
        };
        new Thread() {
        	public void run() {
        		setApplicationData();
        	}
        }.start();    	
    }
    
    private void setApplicationData() {
    	final String[] from = new String[] {"appname", "pname"};
    	final int[] to = new int[] {android.R.id.text1, android.R.id.text2};
    	List<Map<String, Object>> mSysData = new ArrayList<Map<String, Object>>();
    	List<Map<String, Object>> mLocalData = new ArrayList<Map<String, Object>>();
        PackageManager pm = getPackageManager();
        List<PackageInfo> pal = pm.getInstalledPackages(0);
        for(int i=0,size=pal.size(); i<size; i++) {
        	PackageInfo p = pal.get(i);
            ApplicationInfo ai = p.applicationInfo;
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put(from[0], ai.loadLabel(pm).toString());
            map.put(from[1], p.packageName);
            if((ai.flags & ApplicationInfo.FLAG_SYSTEM) > 0) {
            	mSysData.add(map);
            } else {
            	mLocalData.add(map);
            }
        }
         
        if(!mSysData.isEmpty()) {
        	mHandler.sendEmptyMessage(1);
        	mSysLv.setAdapter(new SimpleAdapter(
        			this, 
        			mSysData,
                    android.R.layout.simple_list_item_2, 
                    from, 
                    to
        			));
        }
        if(!mLocalData.isEmpty()) {
        	mHandler.sendEmptyMessage(2);
        	mLocalLv.setAdapter(new SimpleAdapter(
        			this, 
        			mLocalData,
                    android.R.layout.simple_list_item_2, 
                    from, 
                    to
        			));
        }
        mHandler.sendEmptyMessage(0);
    }
}