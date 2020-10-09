package com.example.proground;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button main_rgt_btn;
    Button main_log_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //start point
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        main_rgt_btn = findViewById(R.id.main_btn_register);
        main_log_btn = findViewById(R.id.main_btn_login);

        main_rgt_btn.setOnClickListener(onClickListener);
        main_log_btn.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.main_btn_register:
                    main2RegisterActivity();
                    break;
                case R.id.main_btn_login:
                    main2LoginActivity();
                    break;

            }
        }
    };

    private void main2LoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void main2RegisterActivity(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item){
//        switch (item.getItemId()){
//            case android.R.id.home:{
//                finish();
//                return true;
//            }
//        }
//        return super.onOptionsItemSelected(item);
//    }
}