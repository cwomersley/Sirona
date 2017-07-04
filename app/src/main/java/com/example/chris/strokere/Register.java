package com.example.chris.strokere;

import android.app.ProgressDialog;
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

import static com.example.chris.strokere.R.id.passConfirmR;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private EditText passR;
    private EditText emailR;
    private EditText passConfirmR;
    private Button registerBtn;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(this);
        passR = (EditText) findViewById(R.id.passR);
        passConfirmR = (EditText) findViewById(R.id.passConfirmR);
        emailR = (EditText) findViewById(R.id.emailR);
        registerBtn = (Button) findViewById(R.id.registerBtnM);
        registerBtn.setOnClickListener(this);

        //initiliase Firebase
        mAuth = FirebaseAuth.getInstance();

        //EditText firstNameR = (EditText) findViewById(R.id.firstNameR);
        //firstNameR.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        //passR.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        //passConfirmR.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        //emailR.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        //EditText surnameR = (EditText) findViewById(R.id.surnameR);
        //surnameR.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        //registerBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));


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

    private void registerUser(){
        String email = emailR.getText().toString().trim();
        String password = passR.getText().toString().trim();
        String passwordConfirm = passConfirmR.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show();
          // stop the functioon
            return;
        }

        if(TextUtils.isEmpty(password)){
           //password is empty
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            // stop the function
            return;
        }

        if(!password.equals(passwordConfirm)){
            //password is empty
            Toast.makeText(this, "Your passwords do no match, please re-enter", Toast.LENGTH_SHORT).show();
            // stop the function
            return;
        }



        //if valid
        //show progress dialog

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

    }

    @Override
    public void onClick(View v) {
        if(v == registerBtn ){
            registerUser();
        }
    }
}
