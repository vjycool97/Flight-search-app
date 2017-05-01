package com.ssatyacc.ixigo.ixigoflights.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ssatyacc.ixigo.ixigoflights.R;
import com.ssatyacc.ixigo.ixigoflights.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    ActivityMainBinding B;

    /**
     * Shows Flights list when clicked anywhere on the screen!
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        B = DataBindingUtil.setContentView(this, R.layout.activity_main);
        B.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchFlights(v);
            }
        });
    }

    private void searchFlights(View view) {
        startActivity(FlightOptionsActivity.getCallingIntent(this));
    }
}
