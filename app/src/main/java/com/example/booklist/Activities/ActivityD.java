package com.example.booklist.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.booklist.Classes.Kitap;
import com.example.booklist.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

public class ActivityD extends AppCompatActivity {
    private ImageView imageİlanFoto;
    private EditText textKitapAdi, textYazar, textYayinci, textMail, textTel, textFiyat;
    private Button buttonVer, buttonVerme;
    private String ilanıverenkisininnicki;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private DatabaseReference myRef2;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d);

        imageİlanFoto = findViewById(R.id.imageİlanFoto);
        textKitapAdi = findViewById(R.id.textKitapAdi);
        textYazar = findViewById(R.id.textYazar);
        textYayinci = findViewById(R.id.textYayinci);
        textMail = findViewById(R.id.textMail);
        textTel = findViewById(R.id.textTel);
        textFiyat = findViewById(R.id.textFiyat);
        buttonVer = findViewById(R.id.buttonVer);
        buttonVerme = findViewById(R.id.buttonVerme);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("ilanlar");


        mAuth = FirebaseAuth.getInstance();
        final String user_id = mAuth.getCurrentUser().getUid();

        myRef2 = FirebaseDatabase.getInstance().getReference().child("kullanicilar").child(user_id);
        myRef2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ilanıverenkisininnicki = dataSnapshot.child("nick").getValue().toString().trim();
                //Toast.makeText(getApplicationContext(), ilanıverenkisininnicki, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        buttonVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String kitapAdi = textKitapAdi.getText().toString().trim();
                String yazar = textYazar.getText().toString().trim();
                String yayinci = textYayinci.getText().toString().trim();
                String mail = textMail.getText().toString().trim();
                String tel = textTel.getText().toString().trim();
                double fiyat = Double.parseDouble(textFiyat.getText().toString().trim());
                String ilancı = ilanıverenkisininnicki;

                Kitap kitap = new Kitap("", kitapAdi, yazar, yayinci, mail, tel, ilancı+""
                        , "kitapFoto", fiyat);

                myRef.push().setValue(kitap);
                finish();
            }
        });
        buttonVerme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}


