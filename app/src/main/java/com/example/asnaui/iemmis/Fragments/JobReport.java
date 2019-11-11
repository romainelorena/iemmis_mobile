package com.example.asnaui.iemmis.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asnaui.iemmis.R;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class JobReport extends Fragment implements View.OnClickListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.job_report_form, null, false);
        view.findViewById(R.id.acted_date).setOnClickListener(this);
        view.findViewById(R.id.acted_time).setOnClickListener(this);
        view.findViewById(R.id.completion_date).setOnClickListener(this);
        view.findViewById(R.id.completion_time).setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.acted_date:
            case R.id.completion_date:
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePickerDialog view1, int year, int monthOfYear, int dayOfMonth) {
                                TextView actedDate = (TextView) view;
                                actedDate.setText(year + "-" + String.format("%02d", monthOfYear) + "-" + String.format("%02d", dayOfMonth));
                            }
                        },
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getActivity().getFragmentManager(), "Choose Date");
                break;
            case R.id.acted_time:
            case R.id.completion_time:
                now = Calendar.getInstance();
                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(RadialPickerLayout view1, int hourOfDay, int minute) {
                        TextView actedTime = (TextView) view;
                        actedTime.setText(String.format("%02d", hourOfDay) + ":" + String.format("%02d", minute));
                    }
                }, now.get(Calendar.HOUR), now.get(Calendar.MINUTE), true);
                timePickerDialog.show(getActivity().getFragmentManager(), "Choose Time");
                break;
        }
    }
}
