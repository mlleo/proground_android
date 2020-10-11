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

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private static  final String TAG="RegisterActivity";
    private FirebaseAuth mAuth;
    EditText memail, mpassword, mpasswordcheck, mnickname, mbirth, msex, mheight, mweight;
    Button rgtBtn;
    TextView rgt_to_log_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //start point
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        memail = findViewById(R.id.rgt_email);
        mpassword = findViewById(R.id.rgt_pass);
        mpasswordcheck = findViewById(R.id.rgt_passcheck);
        mnickname = findViewById(R.id.rgt_nickname);
        mbirth = findViewById(R.id.rgt_nickname);
        msex = findViewById(R.id.rgt_sex);
        mheight = findViewById(R.id.rgt_height);
        mweight = findViewById(R.id.rgt_weight);
        rgtBtn = findViewById(R.id.rgt_btn);
        rgt_to_log_btn = (TextView)findViewById(R.id.rgt_to_lgn_btn); //textview

        rgtBtn.setOnClickListener(onClickListener);
        rgt_to_log_btn.setOnClickListener(onClickListener);


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
                    if(signUp()>0){
                        startLoginActivity(); // after register successfully finished
                    }
                    break;
                case R.id.rgt_to_lgn_btn:
                    startLoginActivity();

            }
        }
    };

    private int signUp(){

        String email = ((EditText)memail).getText().toString();
        String password = ((EditText)mpassword).getText().toString();
        String passwordCheck = ((EditText)mpasswordcheck).getText().toString();
        String nickname = ((EditText)mnickname).getText().toString();
        String birth = ((EditText)mbirth).getText().toString();
        String sex = ((EditText)msex).getText().toString();
        String height = ((EditText)mheight).getText().toString();
        String weight = ((EditText)mweight).getText().toString();


        if(email.length() > 0 && password.length() > 0 && passwordCheck.length() > 0 && nickname.length() > 0 && birth.length() > 0
                && sex.length() > 0 && height.length() > 0 && weight.length() > 0){              //it user forget at least 1 info for registration
            if(password.equals(passwordCheck)){
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
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
                return 1;

            }else{
                startToast("비밀번호가 일치하지 않습니다.");
            }

        }else{
            startToast("정보를 입력해주세요.");
        }


        return 0;
    }

    private void startToast(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void startLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
