package com.aidldemoserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.aidldemo.aidl.ICollectUserData.Stub;
import com.aidldemo.model.User;

public class CollectUserDataService extends Service {
	@Override
	public IBinder onBind(Intent intent) {
		return new ServiceBinder();
	}

	private class ServiceBinder extends Stub {
		@Override
		public String getUserLocation(User user) throws RemoteException {
			return user.getName() + ":" + "(127,90)";
		}

		@Override
		public int add(int x, int y) throws RemoteException {
			return x + y;
		}
	}
}
