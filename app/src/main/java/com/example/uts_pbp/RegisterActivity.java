package com.example.uts_pbp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import com.example.uts_pbp.Preferences.UserPreferences;
import com.example.uts_pbp.databinding.ActivityRegisterBinding;
import com.example.uts_pbp.models.AuthResponse;
import com.example.uts_pbp.models.Pengguna;
import com.example.uts_pbp.retrofit.api.ApiClient;
import com.example.uts_pbp.retrofit.api.ApiInterface;
import com.example.uts_pbp.user.User;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout inputUsername, inputEmail, inputPassword;
    private View parentView;
    private UserPreferences userPreferences;
    private User profil;

    private ActivityRegisterBinding binding;

    private ApiInterface apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apiService = ApiClient.getClient().create(ApiInterface.class);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        binding.setActivity(this);

        userPreferences = new UserPreferences(this);
        profil = userPreferences.getUserLogin();

        setTitle("User Register");

        parentView = binding.mainLayout;
        inputUsername = binding.inputLayoutUsername;
        inputEmail = binding.inputLayoutEmail;
        inputPassword = binding.inputLayoutPassword;

        checkLogin();
    }

    public View.OnClickListener btnRegister = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(validateForm()){
                register();
            }
        }
    };

    public View.OnClickListener btnClear = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            inputUsername.getEditText().setText("");
            inputEmail.getEditText().setText("");
            inputPassword.getEditText().setText("");
        }
    };

    private boolean validateForm(){
        if(inputUsername.getEditText().getText().toString().trim().isEmpty() || inputPassword.getEditText().getText().toString().trim().isEmpty()){
            inputUsername.setError("Username must be filled with text");
            inputEmail.setError("Email must be filled with text");
            inputPassword.setError("Password must be filled with text");
            return false;
        }
        return true;
    }

    private void checkLogin(){
        if(userPreferences.checkLogin()){
            startActivity(new Intent(RegisterActivity.this, HomeActivity.class));
            finish();
        }
    }

    //regis
    private void register() {
        Pengguna user = new Pengguna();

        user.setName(inputUsername.getEditText().getText().toString().trim());
        user.setEmail(inputEmail.getEditText().getText().toString().trim());
        user.setPassword(inputPassword.getEditText().getText().toString().trim());

        Call<AuthResponse> call = apiService.getRegister(user);

        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call,
                                   Response<AuthResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Register Success!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    finish();
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(RegisterActivity.this,
                                jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(RegisterActivity.this,
                                e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Network error",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
