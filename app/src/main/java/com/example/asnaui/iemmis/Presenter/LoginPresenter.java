package com.example.asnaui.iemmis.Presenter;

import com.android.volley.RequestQueue;
import com.example.asnaui.iemmis.Helper.ApiRequest;
import com.example.asnaui.iemmis.Interfaces.LoginInterface;
import com.example.asnaui.iemmis.Interfaces.MyCallback;
import com.example.asnaui.iemmis.Model.User;

public class LoginPresenter implements LoginInterface.LoginAction {

    LoginInterface.LoginView view;
    RequestQueue requestQueue;

    public LoginPresenter(LoginInterface.LoginView view, RequestQueue requestQueue) {
        this.view = view;
        this.requestQueue = requestQueue;
    }

    @Override
    public void login(final String userid, final String pin) {
        ApiRequest apiRequest = new ApiRequest(requestQueue);
        apiRequest.login(userid, pin, new MyCallback.VoidCallbacks() {
            @Override
            public void onSuccess() {
                view.navigateToHome(new User(userid, pin));
            }

            @Override
            public void onFail() {
                view.showFailMessage();
            }
        });
    }
}
