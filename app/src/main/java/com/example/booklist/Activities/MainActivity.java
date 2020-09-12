package com.example.booklist.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booklist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

//Giriş Ekranı
public class MainActivity extends AppCompatActivity {
    private Button buttonGiris;
    private Button buttonKayit;
    private TextInputEditText kullaniciAdi, parola;
    private ImageView imageView;

    private FirebaseAuth mAuth;
    private ProgressDialog loginProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        kullaniciAdi = findViewById(R.id.kullaniciAdi);
        parola = findViewById(R.id.tilparola);
        buttonKayit = findViewById(R.id.buttonKayit);
        buttonGiris = findViewById(R.id.buttonGiris);
        imageView = findViewById(R.id.imageView);

        loginProgress = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View tasarim = getLayoutInflater().inflate(R.layout.toast_tasarim, null);
                TextView textViewMesaj = tasarim.findViewById(R.id.textViewMesaj);
                textViewMesaj.setText("Hoşgeldiniz.");
                Toast toastOzel = new Toast(getApplicationContext());
                toastOzel.setView(tasarim);
                toastOzel.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.TOP, 0, 0);
                toastOzel.setDuration(Toast.LENGTH_LONG);
                toastOzel.show();
            }
        });
        buttonGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = kullaniciAdi.getText().toString().trim();
                String pw = parola.getText().toString().trim();
                if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(pw)){
                    loginProgress.setTitle("Oturum Açılıyor");
                    loginProgress.setMessage("Hesabınıza giriş yapılıyor, lütfen bekleyiniz.");
                    loginProgress.setCanceledOnTouchOutside(false);
                    loginProgress.show();
                    loginUser(email, pw);
                }
                //startActivity(new Intent(MainActivity.this, ActivityB.class));
                //finish();
            }
        });
        buttonKayit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ActivityC.class));
            }
        });

    }

    private void loginUser(String email, String pw) {
        mAuth.signInWithEmailAndPassword(email, pw).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    loginProgress.dismiss();
                    startActivity(new Intent(MainActivity.this, ActivityB.class));
                }else{
                    loginProgress.dismiss();
                    Toast.makeText(getApplicationContext(), "Giriş Yapılamadı: "+task.getException().getMessage()
                            , Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
