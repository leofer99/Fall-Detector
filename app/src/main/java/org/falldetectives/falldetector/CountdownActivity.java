
package org.falldetectives.falldetector;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import android.widget.TextView;

public class CountdownActivity extends AppCompatActivity {

    private List<FallData> fallDataList = new ArrayList<>();
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    private UserModel selectedUser;

    static final int RESULT_SEND_FALL_ALERT = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        selectedUser = (UserModel) getIntent().getSerializableExtra("SELECTED_USER");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);

        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getWritableDatabase();

        TextView textCountdown = findViewById(R.id.textCountdown);
        Button buttonImOk = findViewById(R.id.buttonImOk);
        Button buttonSendFallAlert = findViewById(R.id.buttonSendFallAlert);

        buttonImOk.setOnClickListener(getClickListener(true, selectedUser));
        buttonSendFallAlert.setOnClickListener(getClickListener(false, selectedUser));

        loadFallDataFromDatabase();

        CountDownTimer countDownTimer = new CountDownTimer(30000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                textCountdown.setText(String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                sendFallAlert(selectedUser);
            }
        };

        countDownTimer.start();
    }

    private View.OnClickListener getClickListener(final boolean isFalseAlarm, final UserModel user) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFalseAlarm) {
                    onImOkClicked(user);
                } else {
                    onSendFallAlertClicked(view, user);
                }
            }
        };
    }

    private void onImOkClicked(UserModel selectedUser) {
        addFallData(true, selectedUser);
        setResult(RESULT_OK);
        finish();
    }

    private void onSendFallAlertClicked(View view, UserModel selectedUser) {
        sendFallAlert(selectedUser);

    }
    private void sendFallAlert(UserModel selectedUser) {
        addFallData(false, selectedUser);
        setResult(RESULT_SEND_FALL_ALERT);
        finish();
    }

    private void addFallData(boolean isFalseAlarm, UserModel selectedUser) {
        long timestamp = System.currentTimeMillis();
        String currentUser;
        if (selectedUser != null){
            currentUser = selectedUser.getName();
        }
        else{
            currentUser = "Unknown User";
        }
        FallData fallData = new FallData(timestamp, isFalseAlarm, currentUser);
        fallDataList.add(fallData);
        saveFallDataToDatabase(isFalseAlarm, timestamp, currentUser);
    }

    private void loadFallDataFromDatabase() {
        Cursor cursor = database.query(DatabaseHelper.TABLE_FALL_DATA, null, null, null, null, null, null);

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

    private void saveFallDataToDatabase(boolean isFalseAlarm, long timestamp, String currentUser) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_TIMESTAMP, timestamp);
        values.put(DatabaseHelper.COLUMN_IS_FALSE_ALARM, isFalseAlarm ? 1 : 0);
        values.put(DatabaseHelper.COLUMN_USERNAME, currentUser);

        database.insert(DatabaseHelper.TABLE_FALL_DATA, null, values);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }

}
