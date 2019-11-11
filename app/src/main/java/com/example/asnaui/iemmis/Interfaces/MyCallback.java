package com.example.asnaui.iemmis.Interfaces;

import org.json.JSONArray;
import org.json.JSONObject;

public class MyCallback {

    public interface JSONArrayCallbacks {
        public void onSuccess(JSONArray response);

        public void onFail();
    }

    public interface JSONObjectCallbacks {
        public void onSuccess(JSONObject response);

        public void onFail();
    }

    public interface StringCallbacks {
        public void onSuccess(String response);

        public void onFail();
    }
    public interface IntegerCallbacks {
        public void onSuccess(int response);

        public void onFail();
    }

    public interface VoidCallbacks {
        public void onSuccess();

        public void onFail();
    }

}
