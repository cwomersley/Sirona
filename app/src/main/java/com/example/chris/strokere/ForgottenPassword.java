package com.example.chris.strokere;

import android.support.annotation.NonNull;
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
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgottenPassword extends AppCompatActivity {

    private EditText emailPassword;
    private Button resetPasswordBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotten_password);
        resetPasswordBtn = (Button) findViewById(R.id.resetPasswordBtn);
        emailPassword = (EditText) findViewById(R.id.emailPassword);

        //Sets standardised font for each item on activity_forgotten_password
        resetPasswordBtn.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));
        emailPassword.setTypeface(FontHelper.getLatoRegular(getApplicationContext()));

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

    public void resetPassword(View view) {
        String email = emailPassword.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailPassword.setError("Required");
            return;
        } else if (!emailValidator(email)) {
            emailPassword.setError("Please enter a valid email address");
            return;
        }
        else {
            emailPassword.setError(null);
        }
        FirebaseAuth auth = FirebaseAuth.getInstance();
        String emailAddress = email;

        auth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("EMAIL", "Email sent.");
                            Toast.makeText(ForgottenPassword.this, "Email sent",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
}

