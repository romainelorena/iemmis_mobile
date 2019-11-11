package com.example.asnaui.iemmis.Presenter;

import com.android.volley.RequestQueue;
import com.example.asnaui.iemmis.Helper.ApiRequest;
import com.example.asnaui.iemmis.Interfaces.MyCallback;
import com.example.asnaui.iemmis.Interfaces.RequestInterface;
import com.example.asnaui.iemmis.Model.Requests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RequestPresenter implements RequestInterface.RequestAction {

    RequestQueue requestQueue;
    RequestInterface.RequestView view;

    public RequestPresenter(RequestInterface.RequestView view, RequestQueue requestQueue) {
        this.view = view;
        this.requestQueue = requestQueue;

    }

    @Override
    public void insertRequest(final Requests requests) {

        ApiRequest apiRequest = new ApiRequest(requestQueue);
        apiRequest.insertRequest(requests, new MyCallback.JSONObjectCallbacks() {
            @Override
            public void onSuccess(JSONObject request1) {
                try {
                    String id = request1.getString("id");
                    String formNo = request1.getString("formNo");
                    String date = request1.getString("date");
                    String time = request1.getString("time");
                    String requestedBy = request1.getString("requestedBy");
                    String requestItems = request1.getString("requestItems");
                    String others = request1.getString("others");
                    String receivedBy = request1.getString("receivedBy");
                    Requests model = new Requests(id, formNo, date, time, requestedBy, requestItems, others, receivedBy);
                    view.updateRequestList(model);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail() {
                view.onFailed(2,"Failed to add new request,please try again...");
            }
        });
    }

    @Override
    public void fetchRequest(String userid) {
        view.showProgressDialog("Loading requests, please wait....");
        ApiRequest apiRequest = new ApiRequest(requestQueue);
        apiRequest.fetchRequest(userid, new MyCallback.JSONArrayCallbacks() {
            @Override
            public void onSuccess(JSONArray request) {
                ArrayList<Requests> list = new ArrayList<>();
                for (int i = 0; i < request.length(); i++) {
                    try {
                        JSONObject jsonObject = request.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String formNo = jsonObject.getString("formNo");
                        String date = jsonObject.getString("date");
                        String time = jsonObject.getString("time");
                        String requestedBy = jsonObject.getString("requestedBy");
                        String requestItems = jsonObject.getString("requestItems");
                        String others = jsonObject.getString("others");
                        String receivedBy = jsonObject.getString("receivedBy");
                        Requests requests = new Requests(id, formNo, date, time, requestedBy, requestItems, others, receivedBy);
                        list.add(requests);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                view.fetchRequests(list);
            }

            @Override
            public void onFail() {
                view.onFailed(1,"Failed to fetch request,please try again...");
            }
        });

    }

    @Override
    public void deleteRequest(int order, String formNo) {
        view.showProgressDialog("Deleting requests, please wait ....");
        ApiRequest apiRequest = new ApiRequest(requestQueue);
        apiRequest.deleteRequest(order, formNo, new MyCallback.IntegerCallbacks() {
            @Override
            public void onSuccess(int index) {
                view.updateListOnDelete(index);
            }

            @Override
            public void onFail() {
                view.onFailed(1,"Failed to delete request,please try again...");
            }
        });
    }

    @Override
    public void updateRequest(final int index, final Requests requests) {
        view.showProgressDialog("Updating requests, please wait ....");
        ApiRequest apiRequest = new ApiRequest(requestQueue);
        apiRequest.updateRequest(index, requests, new MyCallback.VoidCallbacks() {
            @Override
            public void onSuccess() {
                view.updateListItem(index, requests);
            }

            @Override
            public void onFail() {
                view.onFailed(2,"Failed to update request,please try again...");
            }
        });
    }
}
