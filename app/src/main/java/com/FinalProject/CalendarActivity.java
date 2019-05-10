package com.FinalProject;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.applandeo.materialcalendarview.*;
import com.applandeo.materialcalendarview.listeners.OnCalendarPageChangeListener;

import java.util.*;
import androidx.appcompat.app.AppCompatActivity;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        CalendarView calendarView = findViewById(R.id.calendarView);
        Calendar minimumDate = Calendar.getInstance();
        Calendar maximumDate = Calendar.getInstance();

        SharedPreferences sp = getSharedPreferences("sp", 0);
        boolean isAdmin = sp.getBoolean("isAdmin", false);

        // Sync Dates with Firebase Dates
        // Date format - YYYYMMDD (String)
        Validator.getMinMaxDatesFromDB(new Callback() {
            @Override
            void minDate(String minDate) {
                super.minDate(minDate);
                String year = minDate.substring(0,4);
                String month = minDate.substring(4,6);
                String day = minDate.substring(6);
                minimumDate.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day));
                calendarView.setMinimumDate(minimumDate);
            }

            @Override
            void maxDate(String maxDate) {
                super.maxDate(maxDate);
                String year = maxDate.substring(0,4);
                String month = maxDate.substring(4,6);
                String day = maxDate.substring(6);
                maximumDate.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day));
                calendarView.setMaximumDate(maximumDate);
            }
        });

        calendarView.setOnDayClickListener(eventDay -> {
            Calendar clickedDate = eventDay.getCalendar();
            Intent intent = new Intent(CalendarActivity.this, DateActivity.class);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("Year", Integer.toString(clickedDate.get(Calendar.YEAR)));
            editor.putString("Month", Integer.toString(clickedDate.get(Calendar.MONTH) + 1));
            editor.putString("Day", Integer.toString(clickedDate.get(Calendar.DAY_OF_MONTH)));
            startActivity(intent);
        });

        // Listener for Previous months
        calendarView.setOnForwardPageChangeListener(new OnCalendarPageChangeListener() {
            @Override
            public void onChange() {

            }
        });

        //Listener for Forward months
        calendarView.setOnPreviousPageChangeListener(new OnCalendarPageChangeListener() {
            @Override
            public void onChange() {

            }
        });

        //Move to Login Activity
        findViewById(R.id.btnLogout).setOnClickListener(v -> {
            Intent intent = new Intent(CalendarActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        //Move to Config Activity
        findViewById(R.id.btnConfig).setOnClickListener(v -> {
            // Only Admin can add Accounts
            if(isAdmin){
                Intent intent = new Intent(CalendarActivity.this, ConfigActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(getApplicationContext(),"Admin privileges required", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
