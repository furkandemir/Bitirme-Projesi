package com.example.booklist.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.booklist.Classes.Kullanicilar;
import com.example.booklist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
// Kayıt Olma
public class ActivityC extends AppCompatActivity {
    private Toolbar toolbar;
    private TextInputEditText textKullaniciAdi, textParola, textMail, textTel, textAd, textSoyad;
    private Button buttonGonder, buttonIptal;
    private ProgressDialog dialog;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);

        textKullaniciAdi = findViewById(R.id.textKullaniciAdi);
        textParola = findViewById(R.id.textParola);
        textMail = findViewById(R.id.textMail);
        textTel = findViewById(R.id.textTel);
        textAd = findViewById(R.id.textAd);
        textSoyad = findViewById(R.id.textSoyad);
        buttonGonder = findViewById(R.id.buttonGonder);
        buttonIptal = findViewById(R.id.buttonIptal);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Kayıt Ekranı");
        setSupportActionBar(toolbar);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("kullanicilar");

        dialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        buttonGonder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String kullaniciAdi = textKullaniciAdi.getText().toString().trim();
                String parola = textParola.getText().toString().trim();
                String isim = textAd.getText().toString().trim();
                String soyisim = textSoyad.getText().toString().trim();
                String tel = textTel.getText().toString().trim();
                String mail = textMail.getText().toString().trim();
                if(!TextUtils.isEmpty(kullaniciAdi) || !TextUtils.isEmpty(parola) || !TextUtils.isEmpty(mail)
                        || !TextUtils.isEmpty(isim) || !TextUtils.isEmpty(soyisim) || !TextUtils.isEmpty(tel)){

                    /*
                    Kullanicilar kullanicilar = new Kullanicilar("", isim, soyisim, parola
                            , mail, tel, "Biyografi", "image");
                    myRef.push().setValue(kullanicilar); */

                    dialog.setTitle("Kaydediliyor");
                    dialog.setMessage("Hesabınızı oluşturuyoruz, lütfen bekleyiniz.");
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();
                    register_user(kullaniciAdi, parola, mail, tel, isim, soyisim);

                }

                //Veritabanına ekleme işlemi
                //startActivity(new Intent(ActivityC.this, MainActivity.class));
            }
        });
        buttonIptal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }*/
    public void register_user(final String kullaniciAdi, final String parola, final String mail
            , final String telefon, final String isim, final String soyisim){
        mAuth.createUserWithEmailAndPassword(mail, parola).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    String userid = mAuth.getCurrentUser().getUid();
                    myRef = FirebaseDatabase.getInstance().getReference().child("kullanicilar").child(userid);
                    HashMap<String, String> userMap = new HashMap<>();
                    userMap.put("nick", kullaniciAdi);
                    userMap.put("pfoto", "default");
                    userMap.put("parola", parola);
                    userMap.put("mail", mail);
                    userMap.put("telefon", telefon);
                    userMap.put("bio", "Nothing!!");
                    userMap.put("kullanici_id", "");
                    userMap.put("isim", isim);
                    userMap.put("soyisim", soyisim);

                    myRef.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                dialog.dismiss();
                                startActivity(new Intent(ActivityC.this, ActivityB.class));
                            }
                        }
                    });

                    dialog.dismiss();
                    startActivity(new Intent(ActivityC.this, ActivityB.class));
                }else{
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Hata: "+task.getException().getMessage()
                            , Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}
