package com.example.asnaui.iemmis.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.asnaui.iemmis.Helper.ApiRequest;
import com.example.asnaui.iemmis.Helper.RequestAdapter;
import com.example.asnaui.iemmis.Interfaces.RequestInterface;
import com.example.asnaui.iemmis.Model.Requests;
import com.example.asnaui.iemmis.Presenter.RequestPresenter;
import com.example.asnaui.iemmis.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Asnaui on 1/17/2018.
 */

public class ServiceRequest extends Fragment implements TextWatcher, RequestInterface.RequestView {


    ArrayList<Requests> list = new ArrayList();
    ArrayList<Requests> copy = new ArrayList();
    RequestAdapter adapter;
    EditText search;
    RecyclerView listView;
    ProgressDialog progressDialog;
    Dialog dialog;
    public RequestPresenter request_presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.request_list, null, false);
        listView = view.findViewById(R.id.request_list);
        search = view.findViewById(R.id.search);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        listView.setLayoutManager(gridLayoutManager);
        list.clear();
        adapter = new RequestAdapter(list);
        listView.setAdapter(adapter);
        search.addTextChangedListener(this);
        request_presenter = new RequestPresenter(this, Volley.newRequestQueue(getContext()));
        showProgressDialog("Loading requests, please wait...");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                request_presenter.fetchRequest("0618");
            }
        });
        thread.start();
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.common_menu, menu);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        copy.clear();
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String search_string = s.toString().toLowerCase();
        if (!search_string.trim().isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getFormNo().toLowerCase().contains(search_string)) {
                    copy.add(list.get(i));
                }
            }
            adapter = new RequestAdapter(copy);
        } else {
            adapter = new RequestAdapter(list);
        }
        listView.setAdapter(adapter);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    /*
     *
     * ACTION = {"ADD","VIEW","UPDATE"}
     * index = reference for the request item during update or delete
     *
     */
    public Dialog requestFormDialog(final Requests requests, final String ACTION, final int index) {

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.service_request);

        final EditText other_service = dialog.findViewById(R.id.service_others);
        final Button submit_service = dialog.findViewById(R.id.service_submit);
        LinearLayout checkbox_container = dialog.findViewById(R.id.item_container);

        final ArrayList<String> selected_checkbox = new ArrayList<>();
        final ArrayList<String> checkbox_items = new ArrayList<>();

        checkbox_items.add("Check Internet Connection");
        checkbox_items.add("Install Printer");
        checkbox_items.add("Check Computer");
        checkbox_items.add("Check Monitor");
        checkbox_items.add("Install Software");
        checkbox_items.add("Check Keyboard/Mouse");
        checkbox_items.add("IDTOMIS");
        checkbox_items.add("Biometrics Registration");
        checkbox_items.add("System Technical Assistance");
        checkbox_items.add("");

        LinearLayout.LayoutParams linear_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout vertical = new LinearLayout(getContext());
        vertical.setOrientation(LinearLayout.VERTICAL);
        vertical.setLayoutParams(linear_params);
        vertical.setPadding(5, 5, 5, 5);

        LinearLayout.LayoutParams horizontal_params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LinearLayout horizontal = new LinearLayout(getContext());
        horizontal.setOrientation(LinearLayout.HORIZONTAL);
        horizontal.setLayoutParams(horizontal_params);

        LinearLayout.LayoutParams checkbox_params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1);

        if (ACTION.equalsIgnoreCase("VIEW")) {
            submit_service.setVisibility(View.GONE);
        }
        if (requests != null) {
            String text_others = (requests.getOthers().equals("null")) ? "" : requests.getOthers();
            if (requests.getOthers() != null) {
                other_service.setText(text_others);
            }
        }
        submit_service.setText(ACTION);

        boolean editable = true;
        if (ACTION.equalsIgnoreCase("VIEW"))
            editable = false;
        other_service.setEnabled(editable);

        for (int i = 0; i < checkbox_items.size(); i++) {

            CheckBox checkBox = new CheckBox(getContext());
            checkBox.setLayoutParams(checkbox_params);
            checkBox.setGravity(View.TEXT_ALIGNMENT_CENTER);
            checkBox.setPadding(5, 5, 5, 5);
            checkBox.setId(Integer.parseInt("100" + i));
            checkBox.setTextColor(Color.BLACK);
            checkBox.setText(checkbox_items.get(i));
            checkBox.setChecked(false);
            if (checkbox_items.get(i).equalsIgnoreCase("")) {
                checkBox.setVisibility(View.GONE);
            }
            if (requests != null) {
                if (Arrays.asList(requests.getRequestItems().toLowerCase().split(",")).contains(checkbox_items.get(i).toLowerCase())) {
                    checkBox.setChecked(true);
                }
            }
            checkBox.setEnabled(editable);
            final int finalI = i;
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (b) {
                        selected_checkbox.add(checkbox_items.get(finalI));
                    } else {
                        selected_checkbox.remove(checkbox_items.get(finalI));
                    }
                }
            });
            horizontal.addView(checkBox);
            if (i % 2 == 1) {
                vertical.addView(horizontal);
                horizontal = new LinearLayout(getContext());
                horizontal.setLayoutParams(horizontal_params);
            }
        }
        checkbox_container.addView(vertical);

        submit_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str_selected_item = "";
                for (int i = 0; i < selected_checkbox.size(); i++) {
                    str_selected_item += selected_checkbox.get(i) + ",";
                }
                final String final_str_selected_item = str_selected_item;
                final String str_service_others = other_service.getText().toString();
                if (ACTION.equalsIgnoreCase("UPDATE"))
                    showProgressDialog("Updating requests, please wait...");
                else
                    showProgressDialog("Adding new requests, please wait...");
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        if (ACTION.equalsIgnoreCase("UPDATE")) {

                            requests.setRequestItems(final_str_selected_item);
                            requests.setOthers(str_service_others);

                            request_presenter.updateRequest(index, requests);
                        } else {
                            Requests requests = new Requests("0", "", "", "", "0618", final_str_selected_item, str_service_others, "");
                            request_presenter.insertRequest(requests);
                        }
                    }
                });
                thread.start();
            }
        });
        return dialog;
    }

    @Override
    public void updateListItem(int index, Requests requests) {
        list.set(index, requests);
        adapter.notifyDataSetChanged();
        dialog.dismiss();
        progressDialog.dismiss();
    }

    @Override
    public void updateListOnDelete(int index) {
        list.remove(index);
        adapter.notifyDataSetChanged();
        progressDialog.dismiss();
    }

    @Override
    public void onFailed(int count, String message) {
        switch (count) {
            case 1:
                dismissProgressDialog();
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                break;
            case 2:
                dismissProgressDialog();
                dialog.dismiss();
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                break;
        }
    }


    @Override
    public void showProgressDialog(String message) {
        if (progressDialog == null)
            progressDialog = new ProgressDialog(getContext());

        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        progressDialog.dismiss();
    }

    @Override
    public void updateRequestList(Requests requests) {
        list.add(requests);
        adapter.notifyDataSetChanged();
        dismissProgressDialog();
        dialog.dismiss();
    }

    @Override
    public void fetchRequests(List<Requests> list) {
        this.list.addAll(list);
        adapter.notifyDataSetChanged();
        dismissProgressDialog();
    }
}
