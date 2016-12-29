package com.doday.rxjavaeventbussample.ui.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;

import java.util.Calendar;

/**
 * Created by sessi on 21/10/16.
 */

public class DatePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    public static final String ARG_CALENDAR_DATE = "calendar_date";

    private Callbacks callbacks;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof Callbacks)) {
            throw new ClassCastException("Activity doit implémenter les callbacks du fragment.");
        }
        callbacks = (Callbacks) activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        int year, month, day;
        if (getArguments() != null && getArguments().getSerializable(ARG_CALENDAR_DATE) != null) {
            Calendar arg = (Calendar) getArguments().getSerializable(ARG_CALENDAR_DATE);
            year = arg.get(Calendar.YEAR);
            month = arg.get(Calendar.MONTH);
            day = arg.get(Calendar.DAY_OF_MONTH);
        } else {
            // Date par défaut
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
        }

        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        callbacks.onDateSet(this, year, monthOfYear, dayOfMonth);
    }

    public interface Callbacks {
        void onDateSet(DatePickerDialogFragment fragment, int year, int monthOfYear, int dayOfMonth);
    }
}