package info.androidhive.firebase;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword,isim,imgurl;
    private Button btnSignIn, btnSignUp, btnResetPassword;

    private final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
        isim=(EditText)findViewById(R.id.name);
        imgurl=(EditText)findViewById(R.id.photourl);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, ResetPasswordActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = inputEmail.getText().toString().trim();
                final String password = inputPassword.getText().toString().trim();
                final String name = isim.getText().toString();
                final String img = imgurl.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                //create user
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);

                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignupActivity.this, "Authentication failed." + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    kullaniciOlustur(name,email,password);
                                    kullaniciGuncelle(name,img);
                                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                                    finish();
                                }
                            }
                        });
            }
        });
    }

    private void kullaniciGuncelle(String name,String img) {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .setPhotoUri(Uri.parse(img))
                .build();

        firebaseUser.updateProfile(userProfileChangeRequest)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            Log.e("Güncelleme Hatası", task.getException().getMessage());
                        }
                        startActivity(new Intent(SignupActivity.this, Profil.class));
                    }
                });
    }

    private void kullaniciOlustur(String name,String email,String sifre) {
        Map<String, String> yeniUser = new HashMap<String, String>();
        yeniUser.put("ad", name);
        yeniUser.put("mail", email);
        yeniUser.put("sifre", sifre);

        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child("users")
                .child(firebaseAuth.getCurrentUser().getUid())
                .setValue(yeniUser);
        databaseReference.child("users")
                .child(firebaseAuth.getCurrentUser().getUid())
                .child("friend").child("1").setValue("JGW8gXHTCxOmAVxeD71bmCOodii1");
        databaseReference.child("users")
                .child(firebaseAuth.getCurrentUser().getUid())
                .child("friend").child("2").setValue("j96ugEVElrVgCFyD8RdRDAgOp9Q2");
        databaseReference.child("count").child(firebaseAuth.getCurrentUser().getUid())
                .child("techno").setValue(0);
        databaseReference.child("count").child(firebaseAuth.getCurrentUser().getUid())
                .child("rock").setValue(0);
        databaseReference.child("count").child(firebaseAuth.getCurrentUser().getUid())
                .child("pop").setValue(0);
        databaseReference.child("count").child(firebaseAuth.getCurrentUser().getUid())
                .child("turkce").setValue(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}