package com.FinalProject;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.*;
import java.util.ArrayList;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Validator {

    // Validate Login input against Firebase
    public static void validateUser(final EditText mail, final EditText password, final Callback callback)
    {
        String inputMail = mail.getText().toString();
        String inputPassword = password.getText().toString();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference accountsRef = db.getReference("accounts");
        accountsRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    // Retrieve Account from Database
                    Account account = snapshot.getValue(Account.class);
                     //   account.setImage("");
                    if (!account.getIsActive())
                    {
                        //User is inactive, continue search
                        continue;
                    }

                    // Both Email and Password match in Database
                    if (inputMail.equals(account.getEmail()) && inputPassword.equals(account.getPassword()))
                    {
                        if(account.getIsAdmin())
                        {
                            callback.onLoginCallback(true, true, account.getId() );
                        }
                        else
                        {
                            callback.onLoginCallback(true, false, account.getId());
                        }
                    }

                    // Email or Password don't match in Database
                    if(!inputMail.equals(account.getEmail()) || !inputPassword.equals(account.getPassword()))
                    {
                        callback.onLoginCallback(false, false, account.getId());
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                throw databaseError.toException();
            }
        });
    }

    // Validate New User input against Firebase
    public static void validateNewUser(final EditText mail, final Callback callback)
    {
        String inputMail = mail.getText().toString();
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference accountsRef = db.getReference("accounts");
        accountsRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    // Retrieve Account from Database
                    Account account = snapshot.getValue(Account.class);
                    //   account.setImage("");

                    // Email in database
                    if (inputMail.equals(account.getEmail()))
                    {
                        if(account.getIsActive())
                        {
                            callback.onAddAcountCallback(true, account.getId() );
                        }
                        else
                        {
                            callback.onAddAcountCallback(true, account.getId());
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                throw databaseError.toException();
            }
        });
    }

    // Retrieve shifts per date from Firebase
    // Date passed to this function is of form - YYYYMMDD
    public static void fetchShiftsPerDayFromDB(String date, final Callback callback)
    {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference configRef = db.getReference("config").child(date);
        configRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                ArrayList<Parent> shifts = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    // Retrieve Shift from Database
                    Parent shiftHeader = snapshot.getValue(Parent.class);
                  /*  shift.setName(shiftSnapshot.child("name").getValue().toString());
                    shift.setStart(shiftSnapshot.child("start").getValue().toString());
                    shift.setEnd(shiftSnapshot.child("end").getValue().toString());
                    shift.setNumOfEmps((int)shiftSnapshot.child("numOfEmps").getValue());
                    shift.setWage((int)shiftSnapshot.child("wage").getValue());*/
                //    shift.setEmpsList(null);

                    shifts.add(shiftHeader);
                }
               callback.shiftsCallback(shifts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                throw databaseError.toException();
            }
        });
    }

    // Retrieve Minimum & Maximum Dates from Firebase to sync with Calendar
    public static void getMinMaxDatesFromDB(final Callback callback)
    {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference ref = db.getReference("config");
        // Get First Date from Firebase
        ref.limitToFirst(1).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                callback.minDate(dataSnapshot.getChildren().iterator().next().getKey());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                throw databaseError.toException();
            }
        });
        // Get Last Date from Firebase
        ref.limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                callback.maxDate(dataSnapshot.getChildren().iterator().next().getKey());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                throw databaseError.toException();
            }
        });
    }

    // Insert shift to date in Firebase
    // Date passed to this function is of form - YYYYMMDD
    public static void addNewShiftToDateToDB(Shift shift, String date, final Callback callback){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dateRef = db.getReference("config").child(date);
        dateRef.push().setValue(new Shift(shift.getName(), shift.getStartTime(), shift.getEndTime(), shift.getWage(), shift.getNumOfEmps()), new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if(databaseError != null){
                    throw databaseError.toException();
                }
                else{
                    callback.shiftGeneratedKey(dateRef.push().getKey());
                }
            }
        });
    }

    // Check pre-defined Constraints
    public static void checkConstraints(long userId) {
        MoreThan4DaysInRowConstraint(userId);
        MoreThanHalfShiftsPerDayConstraint(userId);
        MorningNightJuxtapositionConstraint(userId);
    }

    private static void MorningNightJuxtapositionConstraint(long userId) {
    }

    private static void MoreThan4DaysInRowConstraint(long userId) {
    }

    private static void MoreThanHalfShiftsPerDayConstraint(long userId) {
    }
}
