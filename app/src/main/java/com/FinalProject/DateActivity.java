package com.FinalProject;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Date;

public class DateActivity extends ExpandableListActivity {

    private Long userId;
    private ArrayList<Parent> shiftHeaders;
    private int ParentClickStatus = -1;
    private int ChildClickStatus = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        getExpandableListView().setGroupIndicator(null);
        registerForContextMenu(getExpandableListView());

        shiftHeaders = new ArrayList<>();
        SharedPreferences sp = getSharedPreferences("sp", 0);
        userId = sp.getLong("userId", 0);
        boolean isAdmin = sp.getBoolean("isAdmin", false);
        String day = sp.getString("Day", null);
        String month = sp.getString("Month", null);
        String year = sp.getString("Year",null);

        final String date =  year + month + day;
        final String message = "Shifts for date - " + day + "/" + month + "/" + year;
        ((TextView)findViewById(R.id.txtDate)).setText(message);

        // Lets Admin add new shifts
        findViewById(R.id.btnAddShift).setOnClickListener(v -> {
            // Only Admin can add new shifts
            if(isAdmin){
                Intent intent = new Intent(DateActivity.this, ShiftActivity.class);
                startActivity(intent);
            }
            else{
                Toast.makeText(getApplicationContext(),"Admin privileges required", Toast.LENGTH_SHORT).show();
            }
        });

        // Fetch all shifts for selected date from Firebase
        Validator.fetchShiftsPerDayFromDB(date, new Callback()
        {
            @Override
            void shiftsCallback(ArrayList<Parent> newShifts)
            {
                super.shiftsCallback(newShifts);
                if (newShifts == null) return;

                shiftHeaders = newShifts;

                // Check for ExpandableListAdapter object
                if (getExpandableListAdapter() == null)
                {
                    //Create ExpandableListAdapter Object
                    final CustomExpandableListAdapter adapter = new CustomExpandableListAdapter();

                    // Set Adapter to ExpandableList Adapter
                    setListAdapter(adapter);
                }
                else
                {
                    // Refresh ExpandableListView data
                    ((CustomExpandableListAdapter)getExpandableListAdapter()).notifyDataSetChanged();
                }

            }
        });

        //Move to Calendar Activity
        findViewById(R.id.btnBack).setOnClickListener(v -> {
            Intent intent = new Intent(DateActivity.this, CalendarActivity.class);
            startActivity(intent);
        });
    }


    private class CustomExpandableListAdapter extends BaseExpandableListAdapter
    {
        private LayoutInflater inflater;
        public CustomExpandableListAdapter()
        {
            // Create Layout Inflator
            inflater = LayoutInflater.from(DateActivity.this);
        }
        @Override
        public int getGroupCount()
        {
            return shiftHeaders.size();
        }

        @Override
        public int getChildrenCount(int groupPosition)
        {
            int size = 0;
            if(shiftHeaders.get(groupPosition).getChildren()!= null)
                size = shiftHeaders.get(groupPosition).getChildren().size();
            return size;
        }

        @Override
        public Object getGroup(int groupPosition)
        {
            return shiftHeaders.get(groupPosition);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition)
        {
            return shiftHeaders.get(groupPosition).getChildren().get(childPosition);
        }

        @Override
        public long getGroupId(int groupPosition)
        {
            ParentClickStatus = groupPosition;
            if(ParentClickStatus == 0)
                ParentClickStatus = -1;
            return groupPosition;
        }

        //Called when child row clicked
        @Override
        public long getChildId(int groupPosition, int childPosition)
        {
            if( ChildClickStatus != childPosition)
                ChildClickStatus = childPosition;
            return childPosition;
        }

        @Override
        public boolean hasStableIds()
        {
            return true;
        }

        //Function used to inflate group rows view
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parentView)
        {
                final Parent shiftHeader = shiftHeaders.get(groupPosition);

                // Inflate group_row.xml file for group rows
                convertView = inflater.inflate(R.layout.group_row, parentView, false);

                // Get group_row.xml file elements and set values
                ((TextView) convertView.findViewById(R.id.shiftname)).setText(shiftHeader.getShiftName());
                ((TextView) convertView.findViewById(R.id.starttime)).setText(shiftHeader.getStartTime());
                ((TextView) convertView.findViewById(R.id.dash)).setText(" - ");
                ((TextView) convertView.findViewById(R.id.endtime)).setText(shiftHeader.getEndTime());
                ((TextView) convertView.findViewById(R.id.cap)).setText(" Cap. ");
                ((TextView) convertView.findViewById(R.id.registeredEmployees)).setText(shiftHeader.getEmployeesRegistered());
                ((TextView) convertView.findViewById(R.id.slash)).setText("/");
                ((TextView) convertView.findViewById(R.id.totalEmployees)).setText(shiftHeader.getEmployeesRequired());

                // Change Arrow image on parent at runtime
                if(isExpanded) {
                    ((ImageView)convertView.findViewById(R.id.arrow)).setImageResource(R.drawable.collapse_arrow);
                }
                else {
                    ((ImageView)convertView.findViewById(R.id.arrow)).setImageResource(R.drawable.expand_arrow);
                }

                convertView.findViewById(R.id.add).setOnClickListener(v -> {
                  //Add User to selected shift
                    Validator.checkConstraints(userId);

                });
                // Get grouprow.xml file checkbox elements
             //   CheckBox checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
             //   checkbox.setChecked(parent.isChecked());
                // Set CheckUpdateListener for CheckBox (see below CheckUpdateListener class)
             //   checkbox.setOnCheckedChangeListener(new CheckUpdateListener(parent));

                return convertView;
        }

        // This Function used to inflate child rows view
        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parentView)
        {
            final Parent shiftHeader = shiftHeaders.get(groupPosition);
            final Child child = shiftHeader.getChildren().get(childPosition);

            // Inflate child_row.xml file for child rows
            convertView = inflater.inflate(R.layout.child_row, parentView, false);

            // Get child_row.xml file elements and set values
          //  ((ImageView) convertView.findViewById(R.id.userimage)).setImageResource(child.getImage());
            ((TextView)convertView.findViewById(R.id.name)).setText(child.getUserName());
            ((ImageView) convertView.findViewById(R.id.deleteuser)).setImageResource(R.drawable.delete);
            return convertView;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition)
        {
            return true;
        }

        @Override
        public boolean areAllItemsEnabled()
        {
            return true;
        }

        @Override
        public boolean isEmpty()
        {
            return ((shiftHeaders == null) || shiftHeaders.isEmpty());
        }

        @Override
        public void notifyDataSetChanged()
        {
            // Refresh List rows
            super.notifyDataSetChanged();
        }
    }
}
