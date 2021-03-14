package com.example.nastoyshiishashlik.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.nastoyshiishashlik.App;
import com.example.nastoyshiishashlik.R;

import java.util.Calendar;

import butterknife.BindView;

public class CalendarFragment extends BaseFragment {

    @BindView(R.id.calendar__date)
    TextView date;
    @BindView(R.id.calendar__time)
    TextView time;
    @BindView(R.id.calendar__button_date)
    ImageView buttonDate;
    @BindView(R.id.calendar__button_time)
    ImageView buttonTime;

    private Calendar dateAndTime;

    @Override
    public int getViewId() {
        return R.layout.fragment_calendar;
    }

    @Override
    public void onViewCreated(View view) {
        dateAndTime = Calendar.getInstance();

        setInitialDate();
        setInitialTime();

        buttonDate.setOnClickListener(v -> setDate());
        buttonTime.setOnClickListener(v -> setTime());
    }

    public String getDate(){
        return date.getText().toString();
    }

    public String getTime(){
        return time.getText().toString();
    }

    // отображаем диалоговое окно для выбора даты
    private void setDate() {
        new DatePickerDialog(this.context, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // отображаем диалоговое окно для выбора времени
    private void setTime() {
        new TimePickerDialog(this.context, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();
    }

    // установка начальной даты
    private void setInitialTime() {
        dateAndTime.add(Calendar.HOUR_OF_DAY,2);

        time.setText(DateUtils.formatDateTime(App.getContext(),
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_TIME));
    }


    // установка обработчика выбора времени
    TimePickerDialog.OnTimeSetListener t=new TimePickerDialog.OnTimeSetListener() {
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            dateAndTime.set(Calendar.MINUTE, minute);
            setInitialTime();
        }
    };

    // установка начальной даты
    private void setInitialDate() {
        date.setText(DateUtils.formatDateTime(App.getContext(),
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR));
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d=new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDate();
        }
    };
}
