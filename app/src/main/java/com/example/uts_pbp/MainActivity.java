package com.example.uts_pbp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uts_pbp.Preferences.UserPreferences;
import com.example.uts_pbp.databinding.ActivityMainBinding;
import com.example.uts_pbp.models.AuthResponse;
import com.example.uts_pbp.models.Pengguna;
import com.example.uts_pbp.models.PenggunaResponse;
import com.example.uts_pbp.models.ProdukResponse;
import com.example.uts_pbp.recyclerViews.RVProdukAdapter;
import com.example.uts_pbp.retrofit.api.ApiClient;
import com.example.uts_pbp.retrofit.api.ApiInterface;
import com.example.uts_pbp.user.User;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static final String CHANNEL_1_ID = "channel1";

    private TextInputLayout inputUsername;
    private TextInputLayout inputPassword;
    private View parentView;
    private UserPreferences userPreferences;
    private User profil;

    private ActivityMainBinding binding;

    private ApiInterface apiServiceLogin, apiServiceVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apiServiceLogin = ApiClient.getClient().create(ApiInterface.class);
        apiServiceVerify = ApiClient.getClient().create(ApiInterface.class);

        FirebaseMessaging.getInstance().subscribeToTopic("sample_notification");
        createNotificationChannel();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setActivity(this);

        userPreferences = new UserPreferences(this);
        profil = userPreferences.getUserLogin();

        setTitle("User Login");

        parentView = binding.mainLayout;
        inputUsername = binding.inputLayoutUsername;
        inputPassword = binding.inputLayoutPassword;

        checkLogin();
    }

    public View.OnClickListener btnLogin = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(validateForm()){
                login();
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

    public View.OnClickListener register = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            finish();
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

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is Channel 1");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }

    //get login
    private void login() {
        Pengguna user = new Pengguna();

        user.setName(inputUsername.getEditText().getText().toString().trim());
        user.setPassword(inputPassword.getEditText().getText().toString().trim());

        Call<AuthResponse> call = apiServiceLogin.getLogin(user);

        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call,
                                   Response<AuthResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body().getUser().getToken().equals("1")){
                        userPreferences.setLogin(user.getName(),user.getPassword());
                        Toast.makeText(MainActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
                        checkLogin();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Verify Token");
                        builder.setMessage("Your account hasn't been verified. Please enter verify token from your email.");

                        final EditText input = new EditText(MainActivity.this);
                        builder.setView(input);

                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String token = input.getText().toString();
                                long id = response.body().getUser().getId();
                                verify(token, id);
                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });

                        builder.show();
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(MainActivity.this,
                                jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this,
                                e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Network error",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    //verify
    private void verify(String token, long id) {
        Pengguna user = new Pengguna();

        user.setToken(token);

        Call<AuthResponse> call = apiServiceVerify.getVerify(id, user);

        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call,
                                   Response<AuthResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        Toast.makeText(MainActivity.this,
                                jObjError.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(MainActivity.this,
                                e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Network error",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}