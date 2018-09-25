package shysao.com.timepickerwithcenti2.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;


import java.util.Calendar;

import shysao.com.timepickerwithcenti2.R;
import shysao.com.timepickerwithcenti2.view.TimePicker.OnTimeChangedListener;

/**
 * Created by Alison on 04/09/2018.
 */

public class MyTimePickerDialog extends AlertDialog implements DialogInterface.OnClickListener,
        OnTimeChangedListener {

    /**
     * The callback interface used to indicate the user is done filling in
     * the time (they clicked on the 'Set' button).
     */
    public interface OnTimeSetListener {

        /**
         * @param view      The view associated with this listener.
         * @param hourOfDay The hour that was set.
         * @param minute    The minute that was set.
         */
        void onTimeSet(TimePicker view, int hourOfDay, int minute, int seconds);
    }

    private static final String MINUTE = "minute";
    private static final String SECONDS = "seconds";
    private static final String CENTI = "centi";


    private final TimePicker mTimePicker;
    private final OnTimeSetListener mCallback;
    private final Calendar mCalendar;
    private final java.text.DateFormat mDateFormat;

    int mInitialMinute;
    int mInitialSeconds;
    int mInitialCenti;

    /**
     * @param context      Parent.
     * @param callBack     How parent is notified.
     * @param centi        The initial centi.
     * @param minute       The initial minute.
     * @param is24HourView Whether this is a 24 hour view, or AM/PM.
     */
    public MyTimePickerDialog(Context context,
                              OnTimeSetListener callBack,
                              int minute, int seconds, int centi, boolean is24HourView) {

        this(context, 0,
                callBack, minute, seconds, centi, is24HourView);
    }


    /**
     * @param context      Parent.
     * @param theme        the theme to apply to this dialog
     * @param callBack     How parent is notified.
     * @param centi        The initial centi.
     * @param minute       The initial minute.
     * @param is24HourView Whether this is a 24 hour view, or AM/PM.
     */
    public MyTimePickerDialog(Context context,
                              int theme,
                              OnTimeSetListener callBack,
                              int minute, int seconds, int centi, boolean is24HourView) {
        super(context, theme);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mCallback = callBack;
        mInitialMinute = minute;
        mInitialSeconds = seconds;
        mInitialCenti = centi;

        mDateFormat = DateFormat.getTimeFormat(context);
        mCalendar = Calendar.getInstance();
        updateTitle(mInitialMinute, mInitialSeconds, mInitialCenti);

        setButton(context.getText(R.string.time_set), this);
        setButton2(context.getText(R.string.cancel), (OnClickListener) null);
        //setIcon(android.R.drawable.ic_dialog_time);

        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.time_picker_dialog, null);
        setView(view);
        mTimePicker = (TimePicker) view.findViewById(R.id.timePicker);

        // initialize state
        mTimePicker.setCurrentMinute(mInitialMinute);
        mTimePicker.setCurrentSecond(mInitialSeconds);
        mTimePicker.setCurrentCenti(mInitialCenti);
        mTimePicker.setOnTimeChangedListener(this);
    }

    public void onClick(DialogInterface dialog, int which) {
        if (mCallback != null) {
            mTimePicker.clearFocus();
            mCallback.onTimeSet(mTimePicker,
                    mTimePicker.getCurrentMinute(), mTimePicker.getCurrentSeconds(), mTimePicker.getCurrentCenti());
        }
    }

    public void onTimeChanged(TimePicker view, int minute, int seconds, int centi) {
        updateTitle(minute, seconds, centi);
    }

    public void updateTime(int minute, int seconds, int centi) {
        mTimePicker.setCurrentMinute(minute);
        mTimePicker.setCurrentSecond(seconds);
        mTimePicker.setCurrentCenti(centi);
    }

    private void updateTitle(int minute, int seconds, int centi) {
        String sHour = String.format("%02d", minute);
        String sMin = String.format("%02d", seconds);
        String sSec = String.format("%02d", centi);
        setTitle(sHour + ":" + sMin + ":" + sSec);
    }

    @Override
    public Bundle onSaveInstanceState() {
        Bundle state = super.onSaveInstanceState();
        state.putInt(MINUTE, mTimePicker.getCurrentMinute());
        state.putInt(SECONDS, mTimePicker.getCurrentSeconds());
        state.putInt(CENTI, mTimePicker.getCurrentCenti());
        return state;
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int minute = savedInstanceState.getInt(MINUTE);
        int seconds = savedInstanceState.getInt(SECONDS);
        int centi = savedInstanceState.getInt(CENTI);
        mTimePicker.setCurrentMinute(minute);
        mTimePicker.setCurrentSecond(seconds);
        mTimePicker.setCurrentCenti(centi);
        mTimePicker.setOnTimeChangedListener(this);
        updateTitle(minute, seconds, centi);
    }
}
