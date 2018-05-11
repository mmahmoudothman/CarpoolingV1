package com.example.mahmoud.carpoolingv1.dialogfragment;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import com.example.mahmoud.carpoolingv1.customviews.dialog.CustomTimePickerDialog;
import com.example.mahmoud.carpoolingv1.dialogfragment.base.BaseDialogFragment;
import com.example.mahmoud.carpoolingv1.utils.StringUtils;

import java.util.Calendar;

import static com.example.mahmoud.carpoolingv1.customviews.dialog.CustomTimePickerDialog.TIME_PICKER_INTERVAL;

public class TimePickerFragment extends BaseDialogFragment<Integer>
        implements TimePickerDialog.OnTimeSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = CustomTimePickerDialog.getRoundedMinute(c.get(Calendar.MINUTE), TIME_PICKER_INTERVAL);

        return new CustomTimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        if (minute % 30 == 0) {
            publishSubject.onNext(StringUtils.addZeroToStart(hourOfDay) + "" + StringUtils.addZeroToStart(minute));
        } else {
            publishSubject.onNext(null);
        }
        unsubscribeToDialogFragment();
    }
}
