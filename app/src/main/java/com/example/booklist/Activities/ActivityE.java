package com.example.booklist.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.booklist.Classes.Kitap;
import com.example.booklist.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

// İlan Detay
public class ActivityE extends AppCompatActivity {
    private ImageView imageViewFoto;
    private EditText editKitapAdi, editYazar, editYayinci, editMail, editTel, editFiyat;
    private Button buttonGuncel, buttonSil;

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Kitap kitap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e);

        imageViewFoto = findViewById(R.id.imageViewFoto);
        editKitapAdi = findViewById(R.id.editKitapAdi);
        editYazar = findViewById(R.id.editYazar);
        editYayinci = findViewById(R.id.editYayinci);
        editMail = findViewById(R.id.editMail);
        editTel = findViewById(R.id.editTel);
        editFiyat = findViewById(R.id.editFiyat);
        buttonGuncel = findViewById(R.id.buttonGuncel);
        buttonSil = findViewById(R.id.buttonSil);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("ilanlar");

        kitap = (Kitap) getIntent().getSerializableExtra("nesne");

        editKitapAdi.setText(kitap.getKitapAdi());
        editYazar.setText(kitap.getYazar());
        editYayinci.setText(kitap.getYayinci());
        editMail.setText(kitap.getMail());
        editTel.setText(kitap.getTel());
        editFiyat.setText(String.valueOf(kitap.getFiyat()));

        buttonGuncel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Map<String, Object> bilgiler = new HashMap<>();
                bilgiler.put("kitapAdi", editKitapAdi.getText().toString().trim());
                bilgiler.put("yazar", editYazar.getText().toString().trim());
                bilgiler.put("yayinci", editYayinci.getText().toString().trim());
                bilgiler.put("mail", editMail.getText().toString().trim());
                bilgiler.put("tel", editTel.getText().toString().trim());
                bilgiler.put("fiyat", Double.parseDouble(editFiyat.getText().toString().trim()));

                myRef.child(kitap.getKitap_id()).updateChildren(bilgiler);
                Toast.makeText(getApplicationContext(), "İlan Güncellendi.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        buttonSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child(kitap.getKitap_id()).removeValue();
                Toast.makeText(getApplicationContext(), "İlan Silindi.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

}
