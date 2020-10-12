package com.example.proground;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG="RegisterActivity";
    private FirebaseAuth mAuth;
    EditText uemail, upassword;
    Button logBtn, pass_reset;
    TextView log_to_rgt_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //start point
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        uemail = findViewById(R.id.login_email);
        upassword = findViewById(R.id.login_pass);

        logBtn = findViewById(R.id.login_btn);
        pass_reset = findViewById(R.id.login_password_reset);
        log_to_rgt_btn = (TextView)findViewById(R.id.lgn_to_rgt_btn); //textview

        logBtn.setOnClickListener(onClickListener);
        pass_reset.setOnClickListener(onClickListener);
        log_to_rgt_btn.setOnClickListener(onClickListener);

    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.login_btn:
                    login();
                    break;
                case R.id.lgn_to_rgt_btn:
                    startActivity(RegisterActivity.class);
                    break;
                case R.id.login_password_reset:
                    startActivity(PasswordResetActivity.class);
                    break;

            }
        }
    };

    private void login(){

        String email = ((EditText)uemail).getText().toString();
        String password = ((EditText)upassword).getText().toString();


        if(email.length() > 0 && password.length() > 0){              //it user forget email or password for login
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                startToast("로그인에 성공하였습니다.");
                                startActivity(RunMainActivity.class);

                            } else {
                                // If sign in fails, display a message to the user.
                                if(task.getException() != null){
                                    // If sign in fails, display a message to the user.
                                    startToast(task.getException().toString());
                                    // fail
                                }
                            }

                            // ...
                        }
                    });

        }else{
            startToast("정보를 입력해주세요.");
        }

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
