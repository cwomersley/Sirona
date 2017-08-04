package com.example.chris.strokere;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.chris.strokere.R.id.passConfirmR;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText passR;
    private EditText emailR;
    private EditText surnameR;
    private EditText firstNameR;
    private EditText passConfirmR;
    private Button registerBtn;
    private ProgressDialog progressDialog;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(this);
        passR = (EditText) findViewById(R.id.passR);
        passConfirmR = (EditText) findViewById(R.id.passConfirmR);
        emailR = (EditText) findViewById(R.id.emailR);
        firstNameR = (EditText) findViewById(R.id.firstNameR);
        surnameR = (EditText) findViewById(R.id.surnameR);
        registerBtn = (Button) findViewById(R.id.registerBtnM);
        registerBtn.setOnClickListener(this);

        //initialise Firebase
        mAuth = FirebaseAuth.getInstance();

        firstNameR.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        passR.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        passConfirmR.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        emailR.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        surnameR.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        registerBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            public static final String TAG = "Register";


            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
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

    }

    /**
     * validate your email address format. Ex-akhi@mani.com
     * from stackoverflow
     * https://stackoverflow.com/questions/12947620/email-address-validation-in-android-on-edittext
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

    private void registerUser(){
        int passwordLength=6;
        final String firstName = firstNameR.getText().toString();
        final String email = emailR.getText().toString();
        final String surname = surnameR.getText().toString();
        String password = passR.getText().toString();
        String passwordConfirm = passConfirmR.getText().toString();


        if(TextUtils.isEmpty(firstName)){
            //email address has not been entered
            Toast.makeText(this, "Please enter your first name", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(surname)){
            //email address has not been entered
            Toast.makeText(this, "Please enter your surname", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(email)){
            //email address has not been entered
            Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show();
            return;
        }

        else if (emailValidator(email) == false) {
            Toast.makeText(this, "Please enter in a valid email address", Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
           //password has not been emptied
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            // stop the function
            return;
        }

        if(TextUtils.getTrimmedLength(password)<passwordLength) {
            //password is smaller than the size that Firebase allows
            Toast.makeText(this, "Your password must be 6 characters or longer", Toast.LENGTH_LONG).show();
            return;
        }

        if(!password.equals(passwordConfirm)){
            //password and confirmation password do not match
            Toast.makeText(this, "Your passwords do no match, please re-enter", Toast.LENGTH_LONG).show();
            return;
        }



        progressDialog.setMessage("Registering user..");
        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                        //user registered succesful
                        Toast.makeText(Register.this, "Registed Successfully", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }else{
                            Toast.makeText(Register.this, "Could not register", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });

        //get the Firebase usery

        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    writeNewUser(user.getUid(), surname, firstName, email);
                }
            }
        });


    }

    private void writeNewUser(String userId, String surname, String name, String email) {
        User user = new User(surname, name, email);
        mDatabase.child("Patients").child(userId).setValue(user);
        startActivity(new Intent(Register.this, Home.class));
        finish();
    }


    @Override
    public void onClick(View v) {
        if(v == registerBtn ){
            registerUser();
        }
    }
}
