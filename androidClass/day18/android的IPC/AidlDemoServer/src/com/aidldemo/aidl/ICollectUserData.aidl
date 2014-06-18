package com.aidldemo.aidl;

import com.aidldemo.model.User;

interface ICollectUserData {
    String getUserLocation(in User user);
    
    int add(int x, int y);
}