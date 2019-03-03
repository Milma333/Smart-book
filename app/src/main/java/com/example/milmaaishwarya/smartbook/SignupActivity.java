package com.example.milmaaishwarya.smartbook;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.support.annotation.NonNull;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;
//import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
//import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignupActivity extends AppCompatActivity implements View.OnClickListener {
    TextView log;
    EditText email, password,name,cpassword;
   CheckBox showpass;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_signup);
        cpassword = findViewById(R.id.editText_conpass);
        log = findViewById(R.id.textView_login);
        name = findViewById(R.id.editText_name);
        email = findViewById(R.id.editText_email);
        password = findViewById(R.id.editText_pass);
        showpass = findViewById(R.id.showpass);
        findViewById(R.id.button_reg).setOnClickListener(this);
        log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity();
            }
        });
        showpass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

    }


    private void registeruser() {
        final String sname=name.getText().toString().trim();
        final String semail = email.getText().toString().trim();
        String spassword = password.getText().toString().trim();
        String scpassword=cpassword.getText().toString().trim();
        if (semail.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if(sname.isEmpty()){
            name.setError("Name cannot be empty");
            name.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(semail).matches()) {
            email.setError("Please enter a valid mail address");
            email.requestFocus();
            return;
        }

        if (spassword.isEmpty()) {
            password.setError("password is required");
            password.requestFocus();
            return;
        }
        if (scpassword.isEmpty()) {
            cpassword.setError("password is required");
            cpassword.requestFocus();
            return;
        }
        if (spassword.length() < 6) {

            password.setError("Minimum length of password should be 6");
            password.requestFocus();
            return;
        }
        if(!spassword.equals(scpassword))
        {
            cpassword.setError("confirmation password should be same as password");
            cpassword.requestFocus();
            return;
        }

            mAuth.createUserWithEmailAndPassword(semail, spassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show();
                        User user=new User(sname,semail);
                        FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),"User data saved to database",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getApplicationContext(),"failed to save data to database"+task.getResult(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                          Intent intent=new Intent(SignupActivity.this,HomeActivity.class);

                          startActivity(intent);
                            }
                           else{
                        String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();

                        switch (errorCode) {

                            case "ERROR_INVALID_CUSTOM_TOKEN":
                                Toast.makeText(SignupActivity.this, "The custom token format is incorrect. Please check the documentation.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                                Toast.makeText(SignupActivity.this, "The custom token corresponds to a different audience.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_INVALID_CREDENTIAL":
                                Toast.makeText(SignupActivity.this, "The supplied auth credential is malformed or has expired.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_INVALID_EMAIL":
                                Toast.makeText(SignupActivity.this, "The email address is badly formatted.", Toast.LENGTH_LONG).show();
                                email.setError("The email address is badly formatted.");
                                email.requestFocus();
                                break;

                            case "ERROR_WRONG_PASSWORD":
                                Toast.makeText(SignupActivity.this, "The password is invalid or the user does not have a password.", Toast.LENGTH_LONG).show();
                                password.setError("password is incorrect ");
                                password.requestFocus();
                                password.setText("");
                                break;

                            case "ERROR_USER_MISMATCH":
                                Toast.makeText(SignupActivity.this, "The supplied credentials do not correspond to the previously signed in user.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_REQUIRES_RECENT_LOGIN":
                                Toast.makeText(SignupActivity.this, "This operation is sensitive and requires recent authentication. Log in again before retrying this request.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                                Toast.makeText(SignupActivity.this, "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_EMAIL_ALREADY_IN_USE":
                                Toast.makeText(SignupActivity.this, "The email address is already in use by another account.   ", Toast.LENGTH_LONG).show();
                                email.setError("The email address is already in use by another account.");
                                email.requestFocus();
                                break;

                            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                                Toast.makeText(SignupActivity.this, "This credential is already associated with a different user account.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_USER_DISABLED":
                                Toast.makeText(SignupActivity.this, "The user account has been disabled by an administrator.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_USER_TOKEN_EXPIRED":
                                Toast.makeText(SignupActivity.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_USER_NOT_FOUND":
                                Toast.makeText(SignupActivity.this, "There is no user record corresponding to this identifier. The user may have been deleted.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_INVALID_USER_TOKEN":
                                Toast.makeText(SignupActivity.this, "The user\\'s credential is no longer valid. The user must sign in again.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_OPERATION_NOT_ALLOWED":
                                Toast.makeText(SignupActivity.this, "This operation is not allowed. You must enable this service in the console.", Toast.LENGTH_LONG).show();
                                break;

                            case "ERROR_WEAK_PASSWORD":
                                Toast.makeText(SignupActivity.this, "The given password is invalid.", Toast.LENGTH_LONG).show();
                                password.setError("The password is invalid it must 6 characters at least");
                                password.requestFocus();
                                break;

                        }

                    }
                }
            });
        }


    private void openActivity() {
        Intent intent=new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
       finishAffinity();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_reg:
                registeruser();
                break;
        }
    }
}
