package org.falldetectives.falldetector;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class DisplayHistoryActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private List<FallData> fallDataList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    private UserModel selectedUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_data);

        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();

        ListView listView = findViewById(R.id.fall_list);

        //return Arrow
        ImageView returnArrow = findViewById(R.id.iconRightArrow_fallHist);

        returnArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intentPrevious = getIntent();
        if (intentPrevious != null && intentPrevious.hasExtra("SELECTED_USER")) {
            selectedUser = (UserModel) intentPrevious.getSerializableExtra("SELECTED_USER");
        }

        // Load fall data from the database
        loadFallDataFromDatabase(selectedUser);

        // Display data in ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,android.R.id.text1, getDataAsStrings());
        listView.setAdapter(adapter);
    }

    private void loadFallDataFromDatabase(UserModel selectedUser) {
        Cursor cursor;
        if (selectedUser != null){
            String[] selectionArgs = {selectedUser.getName()};
            cursor = database.query(DatabaseHelper.TABLE_FALL_DATA, null, DatabaseHelper.COLUMN_USERNAME + " = ?", selectionArgs, null, null, null);
        }
        else{
            cursor = database.query(DatabaseHelper.TABLE_FALL_DATA, null, null, null, null, null, null);
        }

        int timestampIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_TIMESTAMP);
        int isFalseAlarmIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_IS_FALSE_ALARM);
        int usernameIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_USERNAME);

        while (cursor.moveToNext()) {
            if (timestampIndex != -1 && isFalseAlarmIndex != -1 && usernameIndex != -1) {
                long timestamp = cursor.getLong(timestampIndex);
                boolean isFalseAlarm = cursor.getInt(isFalseAlarmIndex) == 1;
                String username = cursor.getString(usernameIndex);
                fallDataList.add(new FallData(timestamp, isFalseAlarm, username));
            }
        }

        cursor.close();
    }

    private List<String> getDataAsStrings() {
        List<String> dataStrings = new ArrayList<>();

        for (FallData fallData : fallDataList) {
            String dataString = "Timestamp: " + fallData.getFormattedTimestamp() + ", Status: " + fallData.getFalseAlarmStatus();
            dataStrings.add(dataString);
        }

        return dataStrings;
    }
}
