package com.FinalProject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ShiftActivity extends AppCompatActivity {
    private String shiftName, shiftStartTime, shiftEndTime, shiftWage, shiftNumOfEmps, timeRegex;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift);

        SharedPreferences sp = getSharedPreferences("sp", 0);
        String day = sp.getString("Day",null);
        String month = sp.getString("Month",null);
        String year = sp.getString("Year",null);
        ((TextView) findViewById(R.id.txtShift)).setText("Add New Shift For Date " + day + "/" + month + "/" + year);
        final String date = year+month+day;

        shiftName = ((EditText) (findViewById(R.id.newShiftName))).getText().toString();
        shiftStartTime = ((EditText) (findViewById(R.id.newShiftStartTime))).getText().toString();
        shiftEndTime = ((EditText) (findViewById(R.id.newShiftEndTime))).getText().toString();
        shiftWage = ((EditText) (findViewById(R.id.newShiftWage))).getText().toString();
        shiftNumOfEmps = ((EditText) (findViewById(R.id.newShiftNumOfEmps))).getText().toString();
        // Time format of HH:MM
        timeRegex = "/^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$/";

        findViewById(R.id.btnAddNewShift).setOnClickListener(v -> {
            if(validateData()){
                Shift shift = new Shift(shiftName, shiftStartTime, shiftEndTime, Integer.parseInt(shiftWage), Integer.parseInt(shiftNumOfEmps));
                // Client-side Validation is successful, insert data into Firebase
                Validator.addNewShiftToDateToDB(shift, date, new Callback() {
                    @Override
                    void shiftGeneratedKey(String key) {
                        super.shiftGeneratedKey(key);
                        Intent intent = new Intent(ShiftActivity.this, DateActivity.class);
                        SharedPreferences sp = getSharedPreferences("sp", 0);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("generatedKey", key);
                        editor.apply();
                    }
                });
            }
            else{
                Toast.makeText(getApplicationContext(),"Invalid input", Toast.LENGTH_SHORT).show();
            }
        });

        //Move to Date Activity
        findViewById(R.id.btnBack).setOnClickListener(v -> {
            Intent intent = new Intent(ShiftActivity.this, DateActivity.class);
            startActivity(intent);
        });
    }

    private boolean validateData(){
        if (shiftName.isEmpty() || shiftName == null) {
            ((EditText) (findViewById(R.id.newShiftName))).setError("Shift Name is required");
            return false;
        }
        else {
            ((EditText) (findViewById(R.id.newShiftName))).setError(null);
            if (shiftStartTime.isEmpty() || shiftStartTime == null || !shiftStartTime.matches(timeRegex)) {
                ((EditText) (findViewById(R.id.newShiftStartTime))).setError("Time format - HH:MM");
                return false;
            }
            else {
                ((EditText) (findViewById(R.id.newShiftStartTime))).setError(null);
                if (shiftEndTime.isEmpty() || shiftEndTime == null || !shiftEndTime.matches(timeRegex)) {
                    ((EditText) (findViewById(R.id.newShiftEndTime))).setError("Time format - HH:MM");
                    return false;
                }
                else {
                    ((EditText) (findViewById(R.id.newShiftEndTime))).setError(null);
                    if (shiftWage.isEmpty() || shiftWage == null || Integer.parseInt(shiftWage) <= 0) {
                        ((EditText) (findViewById(R.id.newShiftWage))).setError("Wage must be a positive number");
                        return false;
                    }
                    else {
                        ((EditText) (findViewById(R.id.newShiftWage))).setError(null);
                        if (shiftNumOfEmps.isEmpty() || shiftNumOfEmps == null || Integer.parseInt(shiftNumOfEmps) <= 0) {
                            ((EditText) (findViewById(R.id.newShiftWage))).setError("Number of Employees must be a positive number");
                            return false;
                        }
                        else {
                            ((EditText) (findViewById(R.id.newShiftWage))).setError(null);
                            return true;
                        }
                    }
                }
            }
        }
    }
}


