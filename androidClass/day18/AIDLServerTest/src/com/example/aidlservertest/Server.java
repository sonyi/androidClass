package com.example.aidlservertest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import com.example.aidl.IUserData.Stub;
import com.example.model.User;

public class Server extends Service{

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return new ServerBinder();
	}

	private class ServerBinder extends Stub{

		@Override
		public double[] getLocation() throws RemoteException {
			// TODO Auto-generated method stub
			return new double[]{100,90};
		}

		@Override
		public User getUser(User user) throws RemoteException {
			// TODO Auto-generated method stub
			user.setName(user.getName() + "---enene");
			return user;
		}
		
	}
}
