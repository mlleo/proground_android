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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private static  final String TAG="RegisterActivity";
    private FirebaseAuth mAuth;
    EditText uemail, upassword, upasswordcheck, unickname, ubirth, usex, uheight, uweight;
    Button rgtBtn;
    TextView rgt_to_log_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) { //start point
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        rgtBtn = findViewById(R.id.rgt_btn);
        rgt_to_log_btn = (TextView)findViewById(R.id.rgt_to_lgn_btn); //textview

        rgtBtn.setOnClickListener(onClickListener);
        rgt_to_log_btn.setOnClickListener(onClickListener);


    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.rgt_btn:
                    signUp();
                    break;
                case R.id.rgt_to_lgn_btn:
                    startActivity(LoginActivity.class);

            }
        }
    };

    private void signUp(){

        String uemail = ((EditText)findViewById(R.id.rgt_email)).getText().toString();
        String upassword = ((EditText)findViewById(R.id.rgt_pass)).getText().toString();
        String upasswordCheck = ((EditText)findViewById(R.id.rgt_passcheck)).getText().toString();
        final String unickname = ((EditText)findViewById(R.id.rgt_nickname)).getText().toString();
        final String ubirth = ((EditText)findViewById(R.id.rgt_birth)).getText().toString();
        final String usex = ((EditText)findViewById(R.id.rgt_sex)).getText().toString();
        final String uheight = ((EditText)findViewById(R.id.rgt_height)).getText().toString();
        final String uweight = ((EditText)findViewById(R.id.rgt_weight)).getText().toString();


        if(uemail.length() > 0 && upassword.length() > 0 && upasswordCheck.length() > 0 && unickname.length() > 0 && ubirth.length() > 0
                && usex.length() > 0 && uheight.length() > 0 && uweight.length() > 0){              //it user forget at least 1 info for registration
            if(upassword.equals(upasswordCheck)){
                mAuth.createUserWithEmailAndPassword(uemail, upassword)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    startToast("회원가입에 성공하였습니다.");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    FirebaseFirestore db = FirebaseFirestore.getInstance(); //Initialize cloud firestore instance

                                    MemberInfo memberInfo = new MemberInfo(unickname, ubirth, usex, uheight, uweight);
                                    db.collection("users").document(user.getUid()).set(memberInfo);

                                    String email = user.getEmail();
                                    String uid = user.getUid();
                                    startActivity(RunMainActivity.class);
//

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
                startToast("비밀번호가 일치하지 않습니다.");
            }

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
