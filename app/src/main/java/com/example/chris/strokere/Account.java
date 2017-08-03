package com.example.chris.strokere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Account extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public static final String TAG = "Password";
    private EditText password;
    private EditText confirmPassword;
    private String newPassword;
    private boolean signed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        password = (EditText) findViewById(R.id.pPassword);
        confirmPassword = (EditText) findViewById(R.id.pConfirmPassword);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();



    }
    //returns true/false depending on whether a user is signed in
    public boolean signedIn() {
        if (user != null) {
            return true;
        } else {
            return false;
        }
    }

    //method for a user to change their password
    public void pConfirm(View view) {
        if (signedIn()) {
            if (password.getText().toString().equals(confirmPassword.getText().toString())) {
                newPassword = confirmPassword.getText().toString();
                user.updatePassword(newPassword)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Account.this, "Your password has been changed", Toast.LENGTH_SHORT).show();                               }
                            }
                        });
            } else {
                Toast.makeText(Account.this, "Your passswords do not match", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(Account.this, "You are not signed in", Toast.LENGTH_SHORT).show();
        }

    }


}


