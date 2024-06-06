package com.example.appgym.control;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appgym.R;
import com.example.appgym.model.TaskCompleted;
import com.example.appgym.model.User;
import com.example.appgym.model.UserDTO;
import com.example.appgym.repository.UserRepositoryImpl;

public class SignupActivity extends AppCompatActivity {

    private TextView textUsername, textPassword, textEmail, textHeight, textWeight;
    private Button btnCreate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        textUsername = findViewById(R.id.textUsername);
        textPassword = findViewById(R.id.textPassword);
        textEmail = findViewById(R.id.textEmail);
        textHeight = findViewById(R.id.textHeight);
        textWeight = findViewById(R.id.textWeight);
        btnCreate = findViewById(R.id.btnCreate);

        btnCreate.setOnClickListener(view -> {
            createUser();
        });
    }

    private void createUser() {
        String username = textUsername.getText().toString();
        String password = textPassword.getText().toString();
        String email = textEmail.getText().toString();
        String heightString = textHeight.getText().toString();
        String weightString = textWeight.getText().toString();
        if (!username.trim().isEmpty() && !password.trim().isEmpty() && !email.trim().isEmpty()
                && !heightString.trim().isEmpty() && !weightString.trim().isEmpty()){
            int height = Integer.parseInt(heightString);
            double weight = Double.parseDouble(weightString);

            UserDTO userDTO = new UserDTO(username, password, email, height, weight);
            UserRepositoryImpl userRepository = new UserRepositoryImpl(this);
            userRepository.create(userDTO, new TaskCompleted<Boolean>() {
                @Override
                public void onTaskCompleted(Boolean s) {
                    if (s == true){
                        finish();
                    }
                }
            });
        }
        else {
            Toast.makeText(this, "Campos vacíos", Toast.LENGTH_SHORT).show();
        }
    }

}