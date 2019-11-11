package com.example.asnaui.iemmis.Interfaces;

import com.example.asnaui.iemmis.Model.User;

public class LoginInterface {
    public interface LoginAction {
        void login(String userid, String pin);
    }

    public interface LoginView {
        void showFailMessage();

        void navigateToHome(User user);
    }
}
