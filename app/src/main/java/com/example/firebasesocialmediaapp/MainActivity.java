package com.example.firebasesocialmediaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    private EditText edtEmail,edtUsername,edtpassword;
    private Button btnSignUp,btnSignIn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtEmail=findViewById(R.id.edtEmail);
        edtUsername=findViewById(R.id.edtUsername);
        edtpassword=findViewById(R.id.edtPassword);
        mAuth = FirebaseAuth.getInstance();
        btnSignUp=findViewById(R.id.btnSignUp);
        btnSignIn=findViewById(R.id.btnSignIn);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }
    public void onStart()
    {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null)
        {
            transitionToSoacialMediaActivity();
            //Transition to next Activity
        }
    }

    private void SignUp()
    {
        mAuth.createUserWithEmailAndPassword(edtEmail.getText().toString(),edtpassword.getText().toString()).addOnCompleteListener(this
                , new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {

                        if(task.isSuccessful())
                        {

                            Toast.makeText(MainActivity.this,"Sign Up is Sucessful",Toast.LENGTH_LONG).show();
                            FirebaseDatabase.getInstance().getReference().child("my_users").child(task.getResult().getUser().getUid()).child("usernmae").setValue(edtUsername.getText().toString());
                            transitionToSoacialMediaActivity();
                        }else
                        {
                            Toast.makeText(MainActivity.this,"Sign Up is Failed",Toast.LENGTH_LONG).show();
                        }


                    }
                });

    }

    private void signIn()
    {
        mAuth.signInWithEmailAndPassword(edtEmail.getText().toString(),edtpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful())
                {
                    transitionToSoacialMediaActivity();
                }else
                {

                }

            }
        });



    }

    private void transitionToSoacialMediaActivity()
    {
        Intent intent=new  Intent(this,SocialMediaActivity.class);
        startActivity(intent);
    }
}