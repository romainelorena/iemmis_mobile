package com.example.asnaui.iemmis.Helper;

import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asnaui.iemmis.MainActivity;
import com.example.asnaui.iemmis.Model.Requests;
import com.example.asnaui.iemmis.R;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.MyHolder> {

    ArrayList<Requests> list;

    public RequestAdapter(ArrayList<Requests> list) {
        this.list = list;
    }

    public class MyHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {
        TextView formNo, date, time;

        public MyHolder(View itemView) {
            super(itemView);
            formNo = itemView.findViewById(R.id.formNo);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            menu.setHeaderTitle(list.get(getAdapterPosition()).getFormNo());
            MenuItem Edit = menu.add(Menu.NONE, 1, getAdapterPosition(), "Edit");
            MenuItem Delete = menu.add(Menu.NONE, 2, getAdapterPosition(), "Delete");

            Edit.setOnMenuItemClickListener(this);
            Delete.setOnMenuItemClickListener(this);
        }

        @Override
        public boolean onMenuItemClick(final MenuItem item) {
            switch (item.getItemId()) {
                case 1:
                    ((MainActivity) itemView.getContext()).serviceRequest.requestFormDialog(list.get(item.getOrder()), "UPDATE", item.getOrder()).show();
                    break;
                case 2:
                    ((MainActivity) itemView.getContext()).serviceRequest.showProgressDialog("Deleting request, please wait ...");
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Looper.prepare();
                            ((MainActivity) itemView.getContext()).serviceRequest.request_presenter.deleteRequest(item.getOrder(), list.get(item.getOrder()).getFormNo());
                        }
                    });
                    thread.start();
                    break;

            }
            return false;
        }
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_item_layout, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {
        holder.formNo.setText("Form No: " + list.get(position).getFormNo());
        holder.date.setText("Date: " + list.get(position).getDate());
        holder.time.setText("Time: " + list.get(position).getTime());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) v.getContext()).serviceRequest.requestFormDialog(list.get(position), "VIEW", 0).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
