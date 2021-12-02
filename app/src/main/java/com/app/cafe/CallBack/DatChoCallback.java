package com.app.cafe.CallBack;

import com.app.cafe.Model.DatCho;
import com.app.cafe.Model.User;

import java.util.ArrayList;

public interface DatChoCallback {
    void onSuccess(ArrayList<DatCho> lists);
    void onError(String message);
}
