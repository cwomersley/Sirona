package com.example.chris.strokere;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.chris.strokere.R.id.pPasswordBtn;

public class Account extends AppCompatActivity {

    private FirebaseUser user;
    public static final String TAG = "Password";
    public static final String dTAG = "Database";
    private EditText password;
    private EditText confirmPassword;
    private EditText email;
    private EditText hiddenPassword;
    private EditText hiddenEmail;
    private Button pPasswordBtn;
    private Button pConfirmBtn;
    private DatabaseReference mDatabase;
    private Button pEmailBtn;
    private Button logoutBtn;
    private Button delAccBtn;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    private ListView mListView;





    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        pEmailBtn = (Button) findViewById(R.id.pEmailBtn);
        pEmailBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        delAccBtn = (Button) findViewById(R.id.delAccBtn);
        delAccBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        logoutBtn = (Button) findViewById(R.id.logoutBtn);
        logoutBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        email = (EditText) findViewById(R.id.pEmail);
        email.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        password = (EditText) findViewById(R.id.pPassword);
        password.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        confirmPassword = (EditText) findViewById(R.id.pConfirmPassword);
        confirmPassword.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        hiddenPassword = (EditText) findViewById(R.id.pHiddenPassword);
        hiddenEmail = (EditText) findViewById(R.id.pHiddenEmail);

        user = FirebaseAuth.getInstance().getCurrentUser();

        pPasswordBtn = (Button) findViewById(R.id.pPasswordBtn);
        pPasswordBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        //pConfirmBtn = (Button) findViewById(R.id.pPasswordBtn);
        mDatabase = FirebaseDatabase.getInstance().getReference();


        mAuth=FirebaseAuth.getInstance();
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        myRef=mFirebaseDatabase.getReference();
        FirebaseUser dUser = mAuth.getCurrentUser();
        userID=dUser.getUid();

        mListView= (ListView) findViewById(R.id.listview);
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };


        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void showData(DataSnapshot dataSnapshot) {
        if(!signedIn()) {
            return;
        }
        //method to pull account details of patient from firebase database
        for(DataSnapshot ds: dataSnapshot.getChildren()) {
            if(ds.getKey().equals("Patients")) {
                User uInfo = new User();
                uInfo.setName(ds.child(userID).getValue(User.class).getName());
                uInfo.setSurname(ds.child(userID).getValue(User.class).getSurname());
                uInfo.setEmail(ds.child(userID).getValue(User.class).getEmail());
                Log.d(dTAG, "showData: name: " + uInfo.getName());
                Log.d(dTAG, "showData: surname: " + uInfo.getSurname());
                Log.d(dTAG, "showData: phone_num: " + uInfo.getEmail());

                ArrayList<String> array = new ArrayList<>();
                array.add("Name: " + uInfo.getName() +" " + uInfo.getSurname());
                //array.add("Surname: " + uInfo.getSurname());
                array.add("Email: " + uInfo.getEmail());
                ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_black_text, R.id.list_content, array);
                ;
                mListView.setAdapter(adapter);
            }

        }
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
        if (!signedIn()) {
            return;
        }
        String newEmail = email.getText().toString();
        if (TextUtils.isEmpty(newEmail)) {
            email.setError("required");
            return;
        }
        if (!emailValidator(newEmail)) {
            email.setError("Please enter a valid email address");
            return;
        }
        email.setError(null);
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

    public boolean emailValidator(String email)

    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
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
        if (!signedIn()) {
            return;
        }
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
    //show dialog box asking if user wants to delete account
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

    //method to log user out
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(Account.this, MainActivity.class));
    }

}


