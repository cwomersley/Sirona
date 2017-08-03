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
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseUser;

import static com.example.chris.strokere.R.id.hiddenPassword;

public class Account extends AppCompatActivity {

    private FirebaseUser user;
    public static final String TAG = "Password";
    private EditText password;
    private EditText confirmPassword;
    private EditText email;
    private EditText hiddenPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        email = (EditText) findViewById(R.id.pEmail);
        password = (EditText) findViewById(R.id.pPassword);
        confirmPassword = (EditText) findViewById(R.id.pConfirmPassword);
        hiddenPassword = (EditText) findViewById(R.id.hiddenPassword);
        user = FirebaseAuth.getInstance().getCurrentUser();

    }
    //returns true/false depending on whether a user is signed in
    public boolean signedIn() {
        if (user != null) {
            return true;
        } else {
            return false;
        }
    }

    //method for a user to change their email
    public void changeEmail(View view) {
        if (signedIn()) {
            String newEmail = email.getText().toString();
            user.updateEmail(newEmail)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Account.this, "Your email address has been updated", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        else {
            Toast.makeText(Account.this, "You are not signed in", Toast.LENGTH_SHORT).show();
        }

    }

    //method for a user to change their password
    public void changePassword(View view) throws FirebaseAuthRecentLoginRequiredException {
        if (signedIn()) {
            if (password.getText().toString().equals(confirmPassword.getText().toString())) {
                String newPassword = confirmPassword.getText().toString();
                try {
                    user.updatePassword(newPassword)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(Account.this, "Your password has been updated", Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Log.d(TAG, "task not successful");
                                    }
                                }
                            });
                }
                catch (Exception e) {
                    // Get auth credentials from the user for re-authentication.
                    Log.d(TAG, "Exception thrown");
                    hiddenPassword.setVisibility(View.VISIBLE);
                    AuthCredential credential = EmailAuthProvider.getCredential("user@example.com", "password1234");

                    // Prompt the user to re-provide their sign-in credentials
                    user.reauthenticate(credential)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(Task<Void> task) {
                                    Log.d(TAG, "User re-authenticated.");
                                }
                            });
                }
            } else {
                Toast.makeText(Account.this, "Your passswords do not match", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(Account.this, "You are not signed in", Toast.LENGTH_SHORT).show();
        }

    }


}


