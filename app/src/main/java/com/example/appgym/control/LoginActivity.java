package com.example.appgym.control;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.appgym.R;
import com.example.appgym.model.TaskCompleted;
import com.example.appgym.model.User;
import com.example.appgym.repository.UserRepositoryImpl;
import com.example.appgym.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {

    private TextView textUsername, textPassword;
    private Button btnConfirmar, btnSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textUsername = findViewById(R.id.textUsername);
        textPassword = findViewById(R.id.textPassword);
        btnConfirmar = findViewById(R.id.btnConfirmar);
        btnSignup = findViewById(R.id.btnSignup);

        btnConfirmar.setOnClickListener(view -> {
            login();
        });

        btnSignup.setOnClickListener(view -> {
            goToSignUp();
        });
    }

    private void goToSignUp() {
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);
    }

    private void login() {
        String username = textUsername.getText().toString();
        String password = textPassword.getText().toString();
        if (username != null && password != null){
            UserRepositoryImpl userRepository= new UserRepositoryImpl(this);
            userRepository.get(username, password, new TaskCompleted<User>() {
                @Override
                public void onTaskCompleted(User s) {
                    if (s != null){
                        SessionManager sessionManager = new SessionManager(LoginActivity.this);
                        if (sessionManager.createLoginSession(s)){
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            });
        }
    }
}