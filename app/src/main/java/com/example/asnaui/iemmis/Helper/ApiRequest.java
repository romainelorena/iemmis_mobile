package com.example.asnaui.iemmis.Helper;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.asnaui.iemmis.Interfaces.LoginInterface;
import com.example.asnaui.iemmis.Interfaces.MyCallback;
import com.example.asnaui.iemmis.Interfaces.RequestInterface;
import com.example.asnaui.iemmis.LoginActivity;
import com.example.asnaui.iemmis.MainActivity;
import com.example.asnaui.iemmis.Model.Requests;
import com.example.asnaui.iemmis.Model.User;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Asnaui on 1/11/2018.
 */

public class ApiRequest {

    public RequestQueue mRequestQueue;


    public String base_url = "http://210.4.59.5/IEMMIS/Api";

    public ApiRequest(RequestQueue requestQueue) {
        mRequestQueue = requestQueue;
    }


    public void fetchRequest(final String userid, final MyCallback.JSONArrayCallbacks jsonArrayCallbacks) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, base_url + "/GetRequests/" + userid, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("fetchRequest", response.toString());
                jsonArrayCallbacks.onSuccess(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                jsonArrayCallbacks.onFail();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("userid", userid);
                return map;
            }
        };
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(jsonArrayRequest);

    }

    public void insertRequest(final Requests requests, final MyCallback.JSONObjectCallbacks callback) {
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, base_url + "/InsertRequest", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.e("insertRequest", response);
                    callback.onSuccess(new JSONObject(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                    callback.onFail();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callback.onFail();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("requestedBy", requests.getRequestedBy());
                map.put("requestedItems", requests.getRequestItems());
                map.put("others", requests.getOthers());
                return map;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(jsonObjectRequest);

    }


    public void updateRequest(final int index, final Requests requests, final MyCallback.VoidCallbacks voidCallbacks) {
        Log.e("URL", base_url + "/UpdateRequest");
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, base_url + "/UpdateRequest", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("updateRequest", response);
                voidCallbacks.onSuccess();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("insertRequest", error.toString());
                voidCallbacks.onFail();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("formNo", requests.getFormNo());
                map.put("requestedItems", requests.getRequestItems());
                map.put("others", requests.getOthers());
                return map;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(jsonObjectRequest);

    }

    public void deleteRequest(final int index, final String formNo, final MyCallback.IntegerCallbacks voidCallbacks) {
        Log.e("URL", base_url + "/DeleteRequest");
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, base_url + "/DeleteRequest", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("deleteRequest", response);
                voidCallbacks.onSuccess(index);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Login", error.toString());
                voidCallbacks.onFail();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("formNo", formNo);
                return map;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(jsonObjectRequest);
    }

    public void login(final String userid, final String pin, final MyCallback.VoidCallbacks stringCallbacks) {
        Log.e("URL", base_url + "/Login");
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, base_url + "/Login", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

               /* Log.e("Response", response);
                String name = response;
                User user = new User(name, userid);
                */
                Log.e("login", response);
                stringCallbacks.onSuccess();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Login", error.toString());
                stringCallbacks.onFail();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("userid", userid);
                map.put("pin", pin);
                return map;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(jsonObjectRequest);
    }
}

