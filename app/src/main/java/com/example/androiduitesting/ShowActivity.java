package com.example.androiduitesting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ShowActivity extends AppCompatActivity {

    // key used to pass the city name via intent
    public static final String EXTRA_CITY_NAME = "city_name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        // retrieve the city name passed from MainActivity
        String cityName = getIntent().getStringExtra(EXTRA_CITY_NAME);

        // display the city name in the text view
        TextView cityNameText = findViewById(R.id.text_city_name);
        cityNameText.setText(cityName);

        // navigate back to MainActivity when back button is pressed
        Button backButton = findViewById(R.id.button_back);
        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(ShowActivity.this, MainActivity.class);
            // clear the back stack so pressing back on MainActivity doesn't return here
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }
}

