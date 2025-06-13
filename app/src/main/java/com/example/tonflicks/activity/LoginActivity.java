package com.example.tonflicks.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.tonflicks.R;
import com.example.tonflicks.client.LoginResponse;
import com.example.tonflicks.client.UserApi;
import com.example.tonflicks.client.UserCredRequest;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        TextInputEditText usernameInput = findViewById(R.id.etEmail);
        TextInputEditText passwordInput = findViewById(R.id.etPassword);
        Button enterButton = findViewById(R.id.btnLogin);
        Button signUpButton = findViewById(R.id.btnSignUp);
        Button guestModeButton = findViewById(R.id.btnGuestLogin);

        guestModeButton.setOnClickListener(v -> {
            getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
                    .edit()
                    .putInt("userId", 331)
                    .apply();

            Toast.makeText(LoginActivity.this, "Добро пожаловать",  Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("userId", 331);
            startActivity(intent);
        });

        signUpButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
        });

        enterButton.setOnClickListener(v -> {
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Введите логин и пароль", Toast.LENGTH_SHORT).show();
                return;
            }

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8000/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            UserApi authApi = retrofit.create(UserApi.class);
            UserCredRequest loginRequest = new UserCredRequest(username, password);

            authApi.login(loginRequest).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        LoginResponse loginResponse = response.body();
                        if (loginResponse.isSuccess()) {
                            getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
                                .edit()
                                .putInt("userId", loginResponse.getUser().getId())
                                .apply();

                            if (loginResponse.getUser().getId() != 332) {
                                Toast.makeText(LoginActivity.this, "Добро пожаловать, " + loginResponse.getUser().getUsername(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("userId", loginResponse.getUser().getId());
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Добро пожаловать, " + loginResponse.getUser().getUsername(), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                                intent.putExtra("userId", loginResponse.getUser().getId());
                                startActivity(intent);
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Ошибка входа", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Ошибка подключения: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
