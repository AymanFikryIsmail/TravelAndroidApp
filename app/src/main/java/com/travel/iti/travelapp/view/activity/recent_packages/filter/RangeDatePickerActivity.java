package com.travel.iti.travelapp.view.activity.recent_packages.filter;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.squareup.timessquare.CalendarPickerView;
import com.travel.iti.travelapp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

        setDatesBtn = findViewById(R.id.setDatesBtn);
        setDatesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Strings to show
//
//                    String first_date = datePicker.getSelectedDates().get(0).toString() ;
//                    String last_date  = datePicker.getSelectedDates().get(datePicker.getSelectedDates().size()-1).toString();

                    DateFormat dateInstance = SimpleDateFormat.getDateInstance();
                    String first_date = dateInstance.format(datePicker.getSelectedDates().get(0).getTime()).toString();
                    String last_date = dateInstance.format(datePicker.getSelectedDates().get(datePicker.getSelectedDates().size()-1)).toString();
                    System.out.println(first_date + " " + last_date);

                    // another date formate to use in filter
                Date firstDate = datePicker.getSelectedDates().get(0);
                Date lastDate = datePicker.getSelectedDates().get(datePicker.getSelectedDates().size()-1);

                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String first_date_check = formatter.format(firstDate);
                String last_date_check = formatter.format(lastDate);

                    Intent intent = new Intent();
                    intent.putExtra("firstDate" , first_date);
                    intent.putExtra("secondDate" , last_date);
                    intent.putExtra("first_date_check",first_date_check);
                    intent.putExtra("last_date_check",last_date_check);

                    setResult(2, intent);
                    finish();

                }
        });



    }
}


