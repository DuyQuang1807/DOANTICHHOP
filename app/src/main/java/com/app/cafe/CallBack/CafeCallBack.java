package com.app.cafe.CallBack;

import com.app.cafe.Model.Cafe;

import java.util.ArrayList;

public interface CafeCallBack {
    void onSuccess(ArrayList<Cafe> lists);
    void onError(String message);
}
