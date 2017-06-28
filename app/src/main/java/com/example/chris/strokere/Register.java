package com.example.chris.strokere;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText passR;
    private EditText emailR;
    private Button registerBtn;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressDialog = new ProgressDialog(this);

        EditText firstNameR = (EditText) findViewById(R.id.firstNameR);
        firstNameR.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        passR = (EditText) findViewById(R.id.passR);
        passR.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        EditText passConfirmR = (EditText) findViewById(R.id.passConfirmR);
        passConfirmR.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        emailR = (EditText) findViewById(R.id.emailR);
        emailR.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

        EditText surnameR = (EditText) findViewById(R.id.surnameR);
        surnameR.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));


        registerBtn = (Button) findViewById(R.id.registerBtnM);
        registerBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));


        registerBtn.setOnClickListener(this);

        //initilize fire
        mAuth = FirebaseAuth.getInstance();



    }

    private void registerUser(){
        String email = emailR.getText().toString().trim();
        String password = passR.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this, "Please enter an email", Toast.LENGTH_SHORT).show();
          // stop the function
            return;
        }

        if(TextUtils.isEmpty(password)){
           //password is empty
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(Register.this, "could not register", Toast.LENGTH_SHORT).show();
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
