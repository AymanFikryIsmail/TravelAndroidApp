package com.travel.iti.travelapp.view.activity.recent_packages.filter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.squareup.timessquare.CalendarPickerView;
import com.travel.iti.travelapp.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RangeDatePickerActivity extends AppCompatActivity {

    Button setDatesBtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_range_date_picker);

        Date today = new Date();
        Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        CalendarPickerView datePicker = findViewById(R.id.datePicker);
        datePicker.init(today,nextYear.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE);
//                .withSelectedDate(today);

        datePicker.setOnDateSelectedListener(new CalendarPickerView.OnDateSelectedListener() {
            @Override
            public void onDateSelected(Date date) {
                //String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(date);

                Calendar calSelected = Calendar.getInstance();
                calSelected.setTime(date);

                    String selectedDate = "" + calSelected.get(Calendar.DAY_OF_MONTH)
                            + " " + (calSelected.get(Calendar.MONTH) + 1)
                            + " " + calSelected.get(Calendar.YEAR);

                    Toast.makeText(RangeDatePickerActivity.this, selectedDate, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onDateUnselected(Date date) {

            }
        });

        List<Date> dates = datePicker.getSelectedDates();
        setDatesBtn = findViewById(R.id.setDatesBtn);
        setDatesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0 ; i< datePicker.getSelectedDates().size() ; i++){

                    //here you can fetch all dates
//                    Toast.makeText(getApplicationContext(),datePicker.getSelectedDates().get(i).toString(),Toast.LENGTH_LONG).show();

                    String first_date = datePicker.getSelectedDates().get(0).toString();
                    String last_date  = datePicker.getSelectedDates().get(datePicker.getSelectedDates().size()-1).toString();

                    Intent intent = new Intent();
                    intent.putExtra("firstDate" , first_date);
                    intent.putExtra("secondDate" , last_date);
                    setResult(2, intent);
                    finish();

                }
            }
        });



    }
}


