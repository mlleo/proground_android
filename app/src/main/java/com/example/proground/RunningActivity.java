package com.example.proground;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;

public class RunningActivity extends AppCompatActivity {


    ToggleButton toggleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) { //start point
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);

        toggleButton = findViewById(R.id.run_start_btn);

        toggleButton.setOnClickListener(onClickListener);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.run_start_btn:
                    if (toggleButton.isChecked()) {
                        toggleButton.setBackgroundDrawable(getResources().getDrawable(R.drawable.run_toggle_selector_go)
                        );
                    } else {
                        toggleButton.setBackgroundDrawable(
                                getResources().
                                        getDrawable(R.drawable.run_toggle_selector_pause)
                        );
                        break;

                    }
            }
        }
    };


//    public void onToggleClicked(View v){
//        boolean on = ((ToggleButton) v).isChecked();
//
//        if(on){
//            Toast.makeText(getApplicationContext(),"Running stop", Toast.LENGTH_LONG).show();
//        }else {
//            Toast.makeText(getApplicationContext(),"Resume", Toast.LENGTH_LONG).show();
//        }
//    }


    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void startActivity(Class c){
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
