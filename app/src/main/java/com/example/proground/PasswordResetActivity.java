package com.example.proground;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PasswordResetActivity extends AppCompatActivity {
    private static final String TAG="RegisterActivity";
    private FirebaseAuth mAuth;
    EditText uemail;
    Button password_reset_btn;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //start point
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        uemail = findViewById(R.id.pass_reset_email);

        password_reset_btn = findViewById(R.id.password_reset_btn);


        password_reset_btn.setOnClickListener(onClickListener);

        toolbar = (Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(""); //No title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // back icon btn


    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.password_reset_btn:
                    send();
                    break;
            }
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.login_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toolbar_logout:
                logout();
                return true;
            case android.R.id.home:
                Toast.makeText(getApplicationContext(), "뒤로가기", Toast.LENGTH_LONG).show();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void send(){

        String email = ((EditText)uemail).getText().toString();


        if(email.length() > 0){              //it user forget email or password for login
            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                startToast("이메일을 보냈습니다.");
                            }
                        }
                    });



        }else{
            startToast("이메일를 입력해주세요.");
        }

    }

    private void logout(){
        FirebaseAuth.getInstance().signOut();
        startToast("로그아웃에 성공하였습니다.");
        startActivity(MainActivity.class);

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
