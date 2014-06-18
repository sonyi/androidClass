package com.example.aidldemoclient;

import com.aidldemo.aidl.ICollectUserData;
import com.aidldemo.aidl.ICollectUserData.Stub;
import com.aidldemo.model.User;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private Button btnGetLocation;
	private Button btnAdd;

	public static final String REMOTE_SERVICE_ACTION = "com.aidldemo.ACTION";
	private ICollectUserData mServiceBinder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnGetLocation = (Button) findViewById(R.id.btn_get_location);
		btnAdd = (Button) findViewById(R.id.btn_add);
		btnGetLocation.setOnClickListener(this);
		btnAdd.setOnClickListener(this);

		Intent intent = new Intent(REMOTE_SERVICE_ACTION);
		bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
	}

	private ServiceConnection mServiceConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			mServiceBinder = Stub.asInterface(service);
		}
	};

	@Override
	public void onClick(View v) {
		try {
			switch (v.getId()) {
			case R.id.btn_get_location:
				User user = new User();
				user.setId(1);
				user.setName("Jimmy");
				String info = mServiceBinder.getUserLocation(user);
				Toast.makeText(this, info,
						Toast.LENGTH_SHORT).show();
				break;
			case R.id.btn_add:
				int result = mServiceBinder.add(10, 15);
				Toast.makeText(this, "½á¹û£º" + result, Toast.LENGTH_SHORT).show();
				break;
			}
		} catch (RemoteException e) {
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		unbindService(mServiceConnection);
		super.onDestroy();
	}
}
