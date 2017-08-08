package com.example.chris.strokere;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseUser;

import static com.example.chris.strokere.R.id.pPasswordBtn;

public class Account extends AppCompatActivity {

    private FirebaseUser user;
    public static final String TAG = "Password";
    private EditText password;
    private EditText confirmPassword;
    private EditText email;
    private EditText hiddenPassword;
    private EditText hiddenEmail;
    private Button pPasswordBtn;
    private Button pConfirmBtn;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        email = (EditText) findViewById(R.id.pEmail);
        password = (EditText) findViewById(R.id.pPassword);
        confirmPassword = (EditText) findViewById(R.id.pConfirmPassword);
        hiddenPassword = (EditText) findViewById(R.id.pHiddenPassword);
        hiddenEmail = (EditText) findViewById(R.id.pHiddenEmail);
        user = FirebaseAuth.getInstance().getCurrentUser();
        pPasswordBtn = (Button) findViewById(R.id.pPasswordBtn);
        pConfirmBtn = (Button) findViewById(R.id.pPasswordBtn);

        //pPasswordBtn.setOnClickListener(this);

    }
    //returns true/false depending on whether a user is signed in
    public boolean signedIn() {
        if (user != null) {
            return true;
        } else {
            return false;
        }
    }

    //reauthorise a user's credentials
    public void aReauth() {

        String reauthPassword= hiddenPassword.getText().toString();
        String reauthEmail=user.getEmail();
        //String reauthEmail= hiddenEmail.getText().toString();
        AuthCredential credential = EmailAuthProvider.getCredential(reauthPassword, reauthEmail);
        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        Log.d(TAG, "User re-authenticated.");
                    }
                });
        pPasswordBtn.performClick();
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

    //validates fields on activity
    public boolean validate() {
        boolean valid = true;
        int passwordLength=6;
        String changePass=password.getText().toString();
        String conPass=confirmPassword.getText().toString();

        if(TextUtils.isEmpty(changePass)) {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        if(TextUtils.getTrimmedLength(changePass)<passwordLength) {
            //password is smaller than the size that Firebase allows
            Toast.makeText(this, "Your password must be 6 characters or longer", Toast.LENGTH_LONG).show();
            valid=false;
        }
        if(!changePass.equals(conPass)){
            //password and confirmation password do not match
            Toast.makeText(this, "Your passwords do no match, please re-enter", Toast.LENGTH_LONG).show();
            valid=false;
        }
        return valid;
    }

    //method for a user to change their password
    public void changePassword(View view) throws FirebaseAuthRecentLoginRequiredException {
        if (signedIn()) {
            String changePass=password.getText().toString();
            Log.d(TAG, changePass);
            if (!validate()) {
                return;
            }
            try {
                user.updatePassword(changePass)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Account.this, "Your password has been updated", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(Account.this, "Please try again later", Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, "task not successful");
                                }
                            }
                        });
            }
            catch (Exception e) {
                // Get auth credentials from the user for re-authentication.
                Log.d(TAG, "Exception thrown");
                //makeVisible();
                pConfirmBtn.setVisibility(View.VISIBLE);
                hiddenPassword.setVisibility(View.VISIBLE);
                Toast.makeText(Account.this, "Please enter your original password and username", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(Account.this, "You are not signed in", Toast.LENGTH_SHORT).show();
        }

    }

    //method to delete a user account
    public void confirmDelete() {
            try {
                user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User account deleted.");
                        }
                    }
                });
            }
            catch (Exception e) {
            // Get auth credentials from the user for re-authentication.
            Log.d(TAG, "Exception thrown");
         }
     }


    //https://stackoverflow.com/questions/25670051/how-to-create-yes-no-alert-dialog-in-fragment-in-android
    //show dialog box assking if user wants to delete account
    public void delAccount(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to delete your account?");
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                confirmDelete();
                dialog.dismiss();
                Toast.makeText(Account.this, "Account deleted", Toast.LENGTH_SHORT).show();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(Account.this, MainActivity.class));
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}


