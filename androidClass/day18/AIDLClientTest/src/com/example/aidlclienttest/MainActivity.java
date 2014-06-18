package com.example.aidlclienttest;

import com.example.aidl.IUserData;
import com.example.aidl.IUserData.Stub;
import com.example.model.User;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	Button btnGetLocation, btnGetAge;
	public static final String SERVER_INTENT_ACTION = "com.example.aidl.ACTION";
	private IUserData mServerBinder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		btnGetLocation = (Button) findViewById(R.id.btn_getlocation);
		btnGetAge = (Button) findViewById(R.id.btn_getage);

		Intent intent = new Intent(SERVER_INTENT_ACTION);
		bindService(intent, mServiceConnection, BIND_AUTO_CREATE);

		btnGetAge.setOnClickListener(this);
		btnGetLocation.setOnClickListener(this);
	}

	private ServiceConnection mServiceConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			mServerBinder = Stub.asInterface(service);
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		unbindService(mServiceConnection);
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		try {
			if (v != null) {
				switch (v.getId()) {
				case R.id.btn_getlocation:
					double[] loc = mServerBinder.getLocation();
					Toast.makeText(this, loc[0] + ":" + loc[1],
							Toast.LENGTH_SHORT).show();
					break;

				case R.id.btn_getage:
					String name = mServerBinder.getUser(new User(1, "hahah"))
							.getName();
					Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
				}
			}
		} catch (RemoteException e) {

		}
	}

}
