package com.example.proground;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class RunMainActivity extends AppCompatActivity {

    Button logout_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) { //start point
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running);


//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        logout_btn = findViewById(R.id.logout_btn);


        logout_btn.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.logout_btn:
                    logout();
                    break;

            }
        }
    };

    private void logout(){
        FirebaseAuth.getInstance().signOut();
        startToast("로그아웃에 성공하였습니다.");
        startActivity(LoginActivity.class);

    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void startActivity(Class c){
        Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

}
