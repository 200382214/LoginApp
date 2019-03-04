package com.example.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private EditText userPassword, userEmail;
    private Button login, signUp;
    private CheckBox show_hide_password;//Checkbox for show password
    private FirebaseAuth firebaseAuth;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        find_view_id();
        show_hide_pass();
        firebaseAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(validate()){
                  final  String user_email = userEmail.getText().toString();
                  final String user_password = userPassword.getText().toString();

                  firebaseAuth.createUserWithEmailAndPassword(user_email, user_password).addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {
                          if(task.isSuccessful()){

                           Toast.makeText(RegistrationActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                          }else {
                              Toast.makeText(RegistrationActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();

                          }
                      }
                  });

               }
            }
        });

    }
    public void show_hide_pass() {
        show_hide_password.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (!b) {
                    // hide password
                    userPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());

                } else {
                    // show password
                    userPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
    } // end show_hide_pass

    private Boolean validate() {
        Boolean result = false;


        String password = userPassword.getText().toString();
        String email = userEmail.getText().toString();

        if (password.isEmpty() && email.isEmpty()) {
            Toast.makeText(this, "Please enter all the details", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }
        return result;
    }

    public void find_view_id() {

        userEmail = (EditText) findViewById(R.id.etEmail);
        userPassword = (EditText) findViewById(R.id.etPassword);
        login = (Button) findViewById(R.id.btnLogin);
        signUp = (Button) findViewById(R.id.btnSignup);
        show_hide_password = (CheckBox) findViewById(R.id.showPasswordRegister);

    }
}

