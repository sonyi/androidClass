package com.example.aidl;
import com.example.model.User;
interface IUserData{
	double[] getLocation();
	User getUser(in User user);
}