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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;


import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static com.example.chris.strokere.R.id.pEmail;
import static com.example.chris.strokere.R.id.pPasswordBtn;

public class Account extends BaseActivity {

    private FirebaseUser user;
    public static final String TAG = "Password";
    public static final String dTAG = "Database";
    public static final String eTAG = "Exception";
    private EditText password;
    private EditText confirmPassword;
    private EditText email;
    private EditText hiddenPassword;
    private Button pPasswordBtn;
    private Button pConfirmBtn;
    private DatabaseReference mDatabase;
    private Button pEmailBtn;
    private Button logoutBtn;
    private Button delAccBtn;
    private Button aConfirmBtn;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;
    private ListView mListView;





    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        setupNavbar();

        pEmailBtn = (Button) findViewById(R.id.pEmailBtn);
        delAccBtn = (Button) findViewById(R.id.delAccBtn);
        email = (EditText) findViewById(R.id.pEmail);
        password = (EditText) findViewById(R.id.pPassword);
        confirmPassword = (EditText) findViewById(R.id.pConfirmPassword);
        logoutBtn = (Button) findViewById(R.id.logoutBtn);
        hiddenPassword = (EditText) findViewById(R.id.pHiddenPassword);
        pPasswordBtn = (Button) findViewById(R.id.pPasswordBtn);
        mListView= (ListView) findViewById(R.id.listview);
        aConfirmBtn = (Button) findViewById(R.id.aConfirmBtn);


        //Sets standardised font for each item on activity_account
        pPasswordBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        pEmailBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        delAccBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        email.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        password.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        logoutBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        confirmPassword.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        aConfirmBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        hiddenPassword.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));


        mFirebaseDatabase=FirebaseDatabase.getInstance();
        myRef=mFirebaseDatabase.getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user=FirebaseAuth.getInstance().getCurrentUser();

        //Sets the unique id for the user from Firebase

        if (user!=null) {
            userID = user.getUid();
        }


        mAuthListener = new FirebaseAuth.AuthStateListener()
        {
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
            }
        };


        myRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showData(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Retrieves account details of patient from Firebase database
     * and puts them into a ListView
     * @param dataSnapshot
     */
    private void showData(DataSnapshot dataSnapshot)
    {
        if(!signedIn()) {
            return;
        }
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
                array.add("Email: " + uInfo.getEmail());
                ArrayAdapter adapter = new ArrayAdapter(this, R.layout.list_black_text, R.id.list_content, array);
                mListView.setAdapter(adapter);
            }
        }
    }


    /**
     * Determines whether a user is signed in.
     * @return
     */
    public boolean signedIn()
    {
        if (user != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Reauthorise a user's credentials via Firebase
     */
    public void aReauth(View view)
    {

        String reauthPassword= hiddenPassword.getText().toString();
        String reauthEmail=user.getEmail();
        AuthCredential credential = EmailAuthProvider.getCredential(reauthPassword, reauthEmail);
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        Log.d(TAG, "User re-authenticated.");
                    }
                });

        Toast.makeText(Account.this, "Please enter in your new password again", Toast.LENGTH_SHORT).show();
        aConfirmBtn.setVisibility(View.INVISIBLE);
        hiddenPassword.setVisibility(View.INVISIBLE);
        email.setVisibility(View.VISIBLE);
        pEmailBtn.setVisibility(View.VISIBLE);
        password.setVisibility(View.VISIBLE);
        confirmPassword.setVisibility(View.VISIBLE);
        pPasswordBtn.setVisibility(View.VISIBLE);
        delAccBtn.setVisibility(View.VISIBLE);
    }


    /**
     * Changes a patient's email address
     * @param view
     */
    public void changeEmail(View view)
    {
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

    /**
     * alidates a email address to be of a particular type (e.g like an email would be)
     * @param email the email the a patient enters in
     * @return
     */
    public boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Validates TextViews relating to a user entering in password
     * @return
     */
    public boolean validate() {
        boolean valid = true;
        int passwordLength=6;
        String changePass=password.getText().toString();
        String conPass=confirmPassword.getText().toString();

        if(TextUtils.isEmpty(changePass)) {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        //if the password is smaller than the size that Firebase allows (currently has to be 6 characters or more
        if(TextUtils.getTrimmedLength(changePass)<passwordLength) {
            Toast.makeText(this, "Your password must be 6 characters or longer", Toast.LENGTH_LONG).show();
            valid=false;
        }
        //if password and confirmation password do not match
        if(!changePass.equals(conPass)){
            Toast.makeText(this, "Your passwords do no match, please re-enter", Toast.LENGTH_LONG).show();
            valid=false;
        }
        return valid;
    }

    /**
     * Changes a patient's password
     * An exception is thrown if the user has been logged in for a certain amount of time
     * As Firebase requires them to reauthenticate for account sensitive activity such as changing password.
     * @param view
     * @throws FirebaseAuthRecentLoginRequiredException
     */
    public void changePassword(View view)
    {
        if (!signedIn()) {
            return;
        }
            String changePass=password.getText().toString();
            Log.d(TAG, changePass);
            if (!validate()) {
                return;
            }
                user.updatePassword(changePass)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(Task<Void> task) {
                                if (!task.isSuccessful()) {
                                    try {
                                        throw task.getException();
                                    } catch (FirebaseAuthRecentLoginRequiredException e) {
                                        Toast.makeText(Account.this, "You need to reuthenticate", Toast.LENGTH_SHORT).show();
                                        exceptionHandler();
                                    } catch (Exception e) {
                                        Log.e(eTAG, e.getMessage());
                                        Toast.makeText(Account.this, "You need to reuthenticate", Toast.LENGTH_SHORT).show();
                                        exceptionHandler();
                                    }
                                }
                                else {
                                    Toast.makeText(Account.this, "Your password has been updated", Toast.LENGTH_SHORT).show();
                                }
                            }
                            });

    }

    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(eTAG, "Refreshed token: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        FirebaseDatabase.getInstance()
                .getReference()
                .setValue(token);
        //pPasswordBtn.performClick();
    }



    public void exceptionHandler() {
        // Get auth credentials from the user for re-authentication.
        Log.d(TAG, "Exception thrown");
        //makeVisible();
        aConfirmBtn.setVisibility(View.VISIBLE);
        hiddenPassword.setVisibility(View.VISIBLE);
        email.setVisibility(View.INVISIBLE);
        pEmailBtn.setVisibility(View.INVISIBLE);
        password.setVisibility(View.INVISIBLE);
        confirmPassword.setVisibility(View.INVISIBLE);
        pPasswordBtn.setVisibility(View.INVISIBLE);
        delAccBtn.setVisibility(View.INVISIBLE);
        Toast.makeText(Account.this, "Please enter your original password and username", Toast.LENGTH_SHORT).show();
    }

    /**
     * Delete a patient's account
     */
    public void confirmDelete()
    {
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


    /**
     * Dialogue box to confirm if a patient wants to delete their account
     * @param view
     */
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

    /**
     * Logs out a patient.
     * @param view
     */
    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(Account.this, MainActivity.class));
    }


    /**
     * Is used to setup the navbar at the top of the activity
     */
    @Override
    public int getLayout() {
        return R.layout.activity_account;
    }

}


