package org.falldetectives.falldetector;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NewUserActivity extends AppCompatActivity {
    private EditText editTextName, editTextAge, editTextBloodType, editTextEmergencyContact;
    private Button button3;
    private UserDatabase userDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        editTextBloodType = findViewById(R.id.editTextBloodType);
        editTextEmergencyContact = findViewById(R.id.editTextEmergencyContact);
        button3 = findViewById(R.id.button3);

        //return Arrow
        ImageView returnArrow = findViewById(R.id.iconRightArrow_newUser);
        returnArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        userDatabase = new UserDatabase(NewUserActivity.this);

        // Button Register- add to database
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


                UserModel userModel = new UserModel(-1,editTextName.getText().toString(),editTextAge.getText().toString(),editTextBloodType.getText().toString(),Integer.parseInt(editTextEmergencyContact.getText().toString()));
                //Toast.makeText(NewUserActivity.this, userModel.toString(), Toast.LENGTH_SHORT).show();

                UserDatabase userdataBase = new UserDatabase(NewUserActivity.this);
                boolean success = userdataBase.addOne(userModel);

                if (success){
                    Toast.makeText(NewUserActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(NewUserActivity.this, RegisteredUserActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(NewUserActivity.this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show();

                }
            }

        });



    }
}
