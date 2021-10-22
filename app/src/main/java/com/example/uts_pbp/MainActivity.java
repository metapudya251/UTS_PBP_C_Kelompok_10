package com.example.uts_pbp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.uts_pbp.Preferences.UserPreferences;
import com.example.uts_pbp.databinding.ActivityMainBinding;
import com.example.uts_pbp.user.User;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout inputUsername;
    private TextInputLayout inputPassword;
    private ConstraintLayout mainLayout;
    private UserPreferences userPreferences;
    private User profil;

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);

        userPreferences = new UserPreferences(this);
        profil = userPreferences.getUserLogin();

        setTitle("User Login");

        inputUsername = findViewById(R.id.inputLayoutUsername);
        inputPassword = findViewById(R.id.inputLayoutPassword);

        checkLogin();
    }

    public View.OnClickListener btnLogin = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(validateForm()){
                if(inputUsername.getEditText().getText().toString().trim().equals("Mawar Melati")
                        && inputPassword.getEditText().getText().toString().trim().equals("1234")){
                        userPreferences.setLogin(inputUsername.getEditText().getText().toString().trim(),inputPassword.getEditText().getText().toString().trim());
                        checkLogin();
                }else {
                    Toast.makeText(MainActivity.this,"Username atau Password salah",Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    public View.OnClickListener btnClear = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            inputUsername.getEditText().setText("");
            inputPassword.getEditText().setText("");
        }
    };

    private boolean validateForm(){
        if(inputUsername.getEditText().getText().toString().trim().isEmpty() || inputPassword.getEditText().getText().toString().trim().isEmpty()){
            inputUsername.setError("Username must be filled with text");
            inputPassword.setError("Password must be filled with text");
            return false;
        }
        return true;
    }

    private void checkLogin(){
        if(userPreferences.checkLogin()){
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            finish();
        }
    }
}