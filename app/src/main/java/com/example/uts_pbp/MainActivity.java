package com.example.uts_pbp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
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
    private ConstraintLayout mainLayout;
    private View parentView;
    private UserPreferences userPreferences;
    private User profil;

    private ActivityMainBinding binding;

    private ApiInterface apiService;
    private ArrayList<Pengguna> listUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        apiService = ApiClient.getClient().create(ApiInterface.class);

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

    //get list user from API
    private void login() {
        User user = new User(inputUsername.getEditText().getText().toString().trim(),inputPassword.getEditText().getText().toString().trim());

        Call<AuthResponse> call = apiService.getLogin(user);

        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call,
                                   Response<AuthResponse> response) {
                if (response.isSuccessful()) {
                    userPreferences.setLogin(user.getUsername(),user.getPassword());
                    Toast.makeText(MainActivity.this, "Login Success!", Toast.LENGTH_SHORT).show();
                    checkLogin();
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