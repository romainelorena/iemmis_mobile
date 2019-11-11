package com.example.asnaui.iemmis.Interfaces;

import com.example.asnaui.iemmis.Model.Requests;

import java.util.List;

public class RequestInterface {

    public interface RequestAction {
        void insertRequest(Requests requests);

        void fetchRequest(String userid);

        void deleteRequest(int order, String formNo);

        void updateRequest(int index, Requests requests);
    }

    public interface RequestView {

        void showProgressDialog(String message);

        void dismissProgressDialog();

        void updateRequestList(Requests requests);

        void fetchRequests(List<Requests> list);

        void updateListItem(int index, Requests requests);

        void updateListOnDelete(int index);

        void onFailed(int count, String message);
    }

}
