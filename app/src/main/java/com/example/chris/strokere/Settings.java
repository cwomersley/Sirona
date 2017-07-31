package com.example.chris.strokere;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Settings extends AppCompatActivity {

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public static final String TAG = "Password";
    private EditText password;
    private EditText confirmPassword;
    private String newPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        password = (EditText) findViewById(R.id.pPassword);
        confirmPassword = (EditText) findViewById(R.id.pConfirmPassword);

    }

    public void pConfirm(View view) {

        if (password.getText().toString().equals(confirmPassword.getText().toString())) {
            newPassword= confirmPassword.getText().toString();
        }
        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User password updated.");
                            Toast.makeText(Settings.this, "Password changed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
