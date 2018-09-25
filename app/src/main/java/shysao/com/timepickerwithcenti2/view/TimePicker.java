package shysao.com.timepickerwithcenti2.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.NumberPicker;

import java.util.Calendar;

import shysao.com.azertyliste.R;

/**
 * Created by Alison on 04/09/2018.
 */

public class TimePicker extends FrameLayout {
    /**
     * A no-op callback used in the constructor to avoid null checks
     * later in the code.
     */
    private static final OnTimeChangedListener NO_OP_CHANGE_LISTENER = new OnTimeChangedListener() {
        public void onTimeChanged(TimePicker view, int hourOfDay, int minute, int seconds) {
        }
    };

    public static final NumberPicker.Formatter TWO_DIGIT_FORMATTER =
            new NumberPicker.Formatter() {

                @Override
                public String format(int value) {
                    // TODO Auto-generated method stub
                    return String.format("%02d", value);
                }
            };

    // state
    private int mCurrentMinute = 0; // 0-59
    private int mCurrentSeconds = 0; // 0-59
    private int mCurrentCenti = 0; // 0-99


    // ui components
    private final NumberPicker mMinutePicker;
    private final NumberPicker mSecondPicker;
    private final NumberPicker mCentiPicker;


    // callbacks
    private OnTimeChangedListener mOnTimeChangedListener;

    /**
     * The callback interface used to indicate the time has been adjusted.
     */
    public interface OnTimeChangedListener {

        /**
         * @param view The view associated with this listener.
         * @param hourOfDay The current hour.
         * @param minute The current minute.
         * @param seconds The current second.
         */
        void onTimeChanged(TimePicker view, int hourOfDay, int minute, int seconds);
    }

    public TimePicker(Context context) {
        this(context, null);
    }

    public TimePicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TimePicker(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        LayoutInflater inflater =
                (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.time_picker_widget,
                this, // we are the parent
                true);

        // digits of minute
        mMinutePicker = (NumberPicker) findViewById(R.id.minute);
        mMinutePicker.setMinValue(0);
        mMinutePicker.setMaxValue(59);
        mMinutePicker.setFormatter(TWO_DIGIT_FORMATTER);
        mMinutePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker spinner, int oldVal, int newVal) {
                mCurrentMinute = newVal;
                onTimeChanged();
            }
        });

        // digits of secods
        mSecondPicker = (NumberPicker) findViewById(R.id.seconds);
        mSecondPicker.setMinValue(0);
        mSecondPicker.setMaxValue(59);
        mSecondPicker.setFormatter(TWO_DIGIT_FORMATTER);
        mSecondPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker spinner, int oldVal, int newVal) {
                mCurrentSeconds = newVal;
                onTimeChanged();
            }
        });

        // digits of seconds
        mCentiPicker = (NumberPicker) findViewById(R.id.centi);
        mCentiPicker.setMinValue(0);
        mCentiPicker.setMaxValue(99);
        mCentiPicker.setFormatter( TWO_DIGIT_FORMATTER);
        mCentiPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {

            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mCurrentCenti = newVal;
                onTimeChanged();

            }
        });

        // initialize to current time
        Calendar cal = Calendar.getInstance();
        setOnTimeChangedListener(NO_OP_CHANGE_LISTENER);

        // by default we're not in 24 hour mode
        setCurrentMinute(cal.get(Calendar.HOUR_OF_DAY));
        setCurrentSecond(cal.get(Calendar.MINUTE));
        setCurrentCenti(cal.get(Calendar.SECOND));


        if (!isEnabled()) {
            setEnabled(false);
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        mMinutePicker.setEnabled(enabled);
        mSecondPicker.setEnabled(enabled);
    }

/*
    private static class SavedState extends BaseSavedState {

        private final int mHour;
        private final int mMinute;

        private SavedState(Parcelable superState, int hour, int minute) {
            super(superState);
            mHour = hour;
            mMinute = minute;
        }

        private SavedState(Parcel in) {
            super(in);
            mHour = in.readInt();
            mMinute = in.readInt();
        }

        public int getHour() {
            return mHour;
        }

        public int getMinute() {
            return mMinute;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            super.writeToParcel(dest, flags);
            dest.writeInt(mHour);
            dest.writeInt(mMinute);
        }

        public static final Parcelable.Creator<SavedState> CREATOR
                = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        return new SavedState(superState, mCurrentHour, mCurrentMinute);
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setCurrentHour(ss.getHour());
        setCurrentMinute(ss.getMinute());
    }
*/
    /**
     * Set the callback that indicates the time has been adjusted by the user.
     * @param onTimeChangedListener the callback, should not be null.
     */
    public void setOnTimeChangedListener(OnTimeChangedListener onTimeChangedListener) {
        mOnTimeChangedListener = onTimeChangedListener;
    }

    /**
     * @return The current hour (0-59).
     */
    public Integer getCurrentMinute() {
        return mCurrentMinute;
    }

    /**
     * Set the current hour.
     */
    public void setCurrentMinute(Integer currentMinute) {
        this.mCurrentMinute = currentMinute;
        updateMinuteDisplay();
    }

    /**
     * @return The current second.
     */
    public Integer getCurrentSeconds() {
        return mCurrentSeconds;
    }

    /**
     * Set the current second (0-59).
     */
    public void setCurrentSecond(Integer currentSecond) {
        this.mCurrentSeconds = currentSecond;
        updateSecondsDisplay();
    }

    /**
     * @return The current centi.
     */
    public Integer getCurrentCenti() {
        return mCurrentCenti;
    }

    /**
     * Set the current second (0-59).
     */
    public void setCurrentCenti(Integer currentCenti) {
        this.mCurrentCenti = currentCenti;
        updateCentiDisplay();
    }


    /**
     * Set the state of the spinners appropriate to the current hour.
     */


    private void onTimeChanged() {
        mOnTimeChangedListener.onTimeChanged(this, getCurrentMinute(), getCurrentSeconds(),getCurrentCenti());
    }

    /**
     * Set the state of the spinners appropriate to the current minute.
     */
    private void updateMinuteDisplay() {
        mMinutePicker.setValue(mCurrentMinute);
        mOnTimeChangedListener.onTimeChanged(this, getCurrentMinute(), getCurrentSeconds(),getCurrentCenti());
    }

    /**
     * Set the state of the spinners appropriate to the current second.
     */
    private void updateSecondsDisplay() {
        mSecondPicker.setValue(mCurrentSeconds);
        mOnTimeChangedListener.onTimeChanged(this, getCurrentMinute(), getCurrentSeconds(),getCurrentCenti());
    }

    /**
     * Set the state of the spinners appropriate to the current second.
     */
    private void updateCentiDisplay() {
        mCentiPicker.setValue(mCurrentCenti);
        mOnTimeChangedListener.onTimeChanged(this, getCurrentMinute(), getCurrentSeconds(),getCurrentCenti());
    }
}

