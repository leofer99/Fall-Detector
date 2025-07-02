package org.falldetectives.falldetector;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextAge;
    private EditText editTextBloodType;
    private EditText editTextEmergencyContact;
    private Button buttonSubmit;

    private UserModel selectedUser; // Declare selectedUser as a class variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextBloodType = findViewById(R.id.editTextBloodType);
        editTextEmergencyContact = findViewById(R.id.editTextEmergencyContact);
        buttonSubmit = findViewById(R.id.buttonSubmit);

        ImageView returnArrow = findViewById(R.id.iconRightArrow_editProfile);
        returnArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            submitEdit();
        }
        });


        // Retrieve user information from the intent
        selectedUser = (UserModel) getIntent().getSerializableExtra("SELECTED_USER");

        // Pre-fill the EditText fields with user information
        if (selectedUser != null) {
            Log.d("EditProfileActivity", "Selected user not null");

            editTextName.setText(selectedUser.getName());
            editTextAge.setText(String.valueOf(selectedUser.getAge()));
            editTextBloodType.setText(selectedUser.getBlood_type());
            editTextEmergencyContact.setText(String.valueOf(selectedUser.getEmergency_contact()));

        } else {
            Log.d("EditProfileActivity", "Selected user is null");
        }
    }

    private void submitEdit() {

        // Retrieve the edited values from EditText fields
        String editedName = editTextName.getText().toString();
        int editedAge = Integer.parseInt(editTextAge.getText().toString());
        String editedBloodType = editTextBloodType.getText().toString();
        String editedEmergencyContact = editTextEmergencyContact.getText().toString();

        // Update the user information in the database
        UserDatabase userDatabase = new UserDatabase(EditProfileActivity.this);
        boolean updateSuccess = userDatabase.updateUser(selectedUser.getID(), editedName, editedAge, editedBloodType, editedEmergencyContact);

        if (updateSuccess) {
            // If the update is successful, display a toast message
            Toast.makeText(this, "Profile Updated!", Toast.LENGTH_SHORT).show();
            //Intent intent = new Intent(this, MainActivity.class);
            //startActivity(intent);
            finish();

        } else {
            // If the update fails, display an error message
            Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show();
        }
    }

}

