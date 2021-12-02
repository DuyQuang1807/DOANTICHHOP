package com.app.cafe.CallBack;

import com.app.cafe.Model.Model;

import java.util.ArrayList;

public interface ItemCallback {
    void onSuccess(ArrayList<Model> lists);
    void onError(String message);
}
