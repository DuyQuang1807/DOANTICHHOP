package com.app.cafe.CallBack;

import com.app.cafe.Model.User;

import java.util.ArrayList;

public interface UserCallBack {
    void onSuccess(ArrayList<User> lists);
    void onError(String message);
}
