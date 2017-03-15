package com.fractaltechnologies.ashleyw.buffbuddy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    User user = new User();
    DBAdapter dbAdapter;

    // A password is required to have an uppercase, lowercase, number
    private static final Pattern [] passwordRegexes = new Pattern[3];
    {
        passwordRegexes[0] = Pattern.compile(".*[A-Z].*");
        passwordRegexes[1] = Pattern.compile(".*[a-z].*");
        passwordRegexes[2] = Pattern.compile(".*\\d.*");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dbAdapter = new DBAdapter(this);
    }


    public void Register(View v) {
        boolean valid = true;
        // Gather all variables from form
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etEmail = (EditText) findViewById((R.id.etEmail));
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
        final EditText etFirstName = (EditText) findViewById(R.id.etFirstName);
        final EditText etLastName = (EditText) findViewById(R.id.etLastName);

        // Retrieve text values
        String username = etUsername.getText().toString();
        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();
        String fname = etFirstName.getText().toString();
        String lname = etLastName.getText().toString();

        // Ensure all fields are filled
        if (username.equals("") || email.equals("") || password.equals("") ||
                confirmPassword.equals("") || lname.equals("") || fname.equals("")) {
            Toast.makeText(this, "All fields must have values.", Toast.LENGTH_LONG);
            valid = false;
        }
        // Ensure password values are the same
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords must match", Toast.LENGTH_LONG);
            valid = false;
        }
        // Ensures the password is valid in the context of this application
        if (!isLegalPassword(password)) {
            Toast.makeText(this, "Password must contain at least one uppercase, one lowercase, one " +
                    "number, and be at least 8 characters long.", Toast.LENGTH_LONG);
            valid = false;
        }
        if (valid) {
            // Determine if the user successfully logged in
            dbAdapter = new DBAdapter(this);
            if (User.Register(username, email, password, confirmPassword, fname, lname, dbAdapter)) {
                Toast.makeText(RegisterActivity.this, "Successful registration!", Toast.LENGTH_SHORT).show();
                int id = User.GetIDFromDatabase(email, dbAdapter);
                InsertDefaultInformation(id);
            } else {
                Toast.makeText(RegisterActivity.this, "Failed to register. Try again.", Toast.LENGTH_LONG).show();
            }
        }
    }

    // Ensures that the password entered has an uppercase, lowercase, and a number
    public boolean isLegalPassword(String password) {
        // Iterate over requirement regexes and ensure all are valid
        for(int i = 0; i < passwordRegexes.length; i++){
            if(!passwordRegexes[i].matcher(password).matches())
                return false;
        }
        if(password.length() < 8){
            return false;
        }
        return true;
    }

    public void InsertDefaultInformation(int userId){
        WorkoutDAO workoutDAO = new WorkoutDAO();
        workoutDAO.InitDefaultWorkoutExerciseInformation(userId, this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbAdapter.close();
    }
}
