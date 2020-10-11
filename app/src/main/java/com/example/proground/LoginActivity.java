package com.example.proground;

import android.content.Intent;
import android.os.Bundle;
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
    EditText memail, mpassword, mnickname, mbirth, msex, mheight, mweight;
    Button logBtn;
    TextView log_to_rgt_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //start point
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        memail = findViewById(R.id.rgt_email);
        mpassword = findViewById(R.id.rgt_pass);
        mnickname = findViewById(R.id.rgt_nickname);
        mbirth = findViewById(R.id.rgt_nickname);
        msex = findViewById(R.id.rgt_sex);
        mheight = findViewById(R.id.rgt_height);
        mweight = findViewById(R.id.rgt_weight);
        logBtn = findViewById(R.id.login_btn);

        log_to_rgt_btn = (TextView)findViewById(R.id.lgn_to_rgt_btn); //textview

        logBtn.setOnClickListener(onClickListener);
        log_to_rgt_btn.setOnClickListener(onClickListener);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rgt_btn:
                    signUp();
                    break;
                case R.id.lgn_to_rgt_btn:
                    startRegisterActivity();
                    break;
            }
        }
    };

    private void signUp(){

        String email = ((EditText)memail).getText().toString();
        String password = ((EditText)mpassword).getText().toString();
        String nickname = ((EditText)mnickname).getText().toString();
        String birth = ((EditText)mbirth).getText().toString();
        String sex = ((EditText)msex).getText().toString();
        String height = ((EditText)mheight).getText().toString();
        String weight = ((EditText)mweight).getText().toString();


        if(email.length() > 0 && password.length() > 0){              //it user forget email or password for login
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                startToast("회원가입에 성공하였습니다.");
                                FirebaseUser user = mAuth.getCurrentUser();
                                String email = user.getEmail();
                                String uid = user.getUid();
                                String nickname = (mnickname).getText().toString().trim();
                                String birth = (mbirth).getText().toString().trim();
                                String sex = (msex).getText().toString().trim();
                                String height = (mheight).getText().toString().trim();
                                String weight = (mweight).getText().toString().trim();


                                //Save hashmap table to firebase database
                                HashMap<Object,String> hashMap = new HashMap<>();

                                hashMap.put("uid",uid);
                                hashMap.put("email",email);
                                hashMap.put("name",nickname);
                                hashMap.put("birth",birth);
                                hashMap.put("sex",sex);
                                hashMap.put("height",height);
                                hashMap.put("weight",weight);


                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference reference = database.getReference("Users");
                                reference.child(uid).setValue(hashMap);
                                // success
                            } else {
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

    private void startRegisterActivity(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
