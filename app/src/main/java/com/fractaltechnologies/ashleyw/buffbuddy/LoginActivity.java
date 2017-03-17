package com.fractaltechnologies.ashleyw.buffbuddy;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import net.sqlcipher.database.SQLiteDatabase;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

public class LoginActivity extends AppCompatActivity
//        implements GoogleApiClient.OnConnectionFailedListener,
//        View.OnClickListener
        {

    private static final String TAG = "LoginActivity";
    Button bttnLogin;
    DBAdapter db;
    User user;
    private GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Create instance of SQLite database
        SQLiteDatabase.loadLibs(this);
        db = new DBAdapter(this);
        db = db.openWrite();
    }

    public void Login(View v) {
        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);

        // Retrieve username and password text values
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        try {
            user = User.Login(username, password, db);
        }catch (Exception ex){
            Log.e(TAG, "Login: " + ex.getMessage());
        }
        // Determine if the user successfully logged in
        if (user != null) {
            Toast.makeText(LoginActivity.this, "Successful login!", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(LoginActivity.this, HomeActivity.class);
            i.putExtra("User", user);
            startActivity(i);
        } else {
            Toast.makeText(LoginActivity.this, "Failed to log in. Try again.", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }

}
