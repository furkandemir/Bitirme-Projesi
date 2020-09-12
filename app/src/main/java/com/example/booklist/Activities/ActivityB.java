package com.example.booklist.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
//import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.booklist.Classes.Kitap;
import com.example.booklist.Fragments.FragmentBir;
import com.example.booklist.Fragments.FragmentIki;
import com.example.booklist.Fragments.FragmentUc;
import com.example.booklist.R;
import com.example.booklist.Adapters.ViewpagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

// TabsLayout
public class ActivityB extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabs;
    private ViewPager viewpager;
    private String username, password;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FloatingActionButton fab;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);

        fab = findViewById(R.id.fab);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Booklist");
        setSupportActionBar(toolbar);

        tabs = findViewById(R.id.tabs);
        viewpager = findViewById(R.id.viewpager);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("ilanlar");
        mAuth = FirebaseAuth.getInstance();

        ViewpagerAdapter vpadapter = new ViewpagerAdapter(getSupportFragmentManager());
        vpadapter.fragmentEkle(new FragmentBir(), "ANASAYFA");
        vpadapter.fragmentEkle(new FragmentUc(), "İLANLARIM");
        vpadapter.fragmentEkle(new FragmentIki(), "PROFİL");
        viewpager.setAdapter(vpadapter);
        tabs.setupWithViewPager(viewpager);
        tabs.getTabAt(0).setIcon(R.drawable.anasayfa);
        tabs.getTabAt(1).setIcon(R.drawable.list);
        tabs.getTabAt(2).setIcon(R.drawable.profil);
        /*
        Kitap kitap = new Kitap("", "kitapadı", "yazar", "yayıncı", "mail"
                , "tel", "ilancı", "kitapfoto", 10.00);
        myRef.push().setValue(kitap); */
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityB.this, ActivityD.class));
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case (R.id.action_info):
                Toast.makeText(getApplicationContext(), "Bilgi tıklandı.", Toast.LENGTH_SHORT).show();
                return true;
            case (R.id.action_ekle):
                //Toast.makeText(getApplicationContext(), "Ekle tıklandı.", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ActivityB.this, ActivityD.class));
                return true;
            case (R.id.action_ayarlar):
                Toast.makeText(getApplicationContext(), "Ayarlar tıklandı.", Toast.LENGTH_SHORT).show();
                return true;
            case (R.id.action_cikis):
                mAuth.signOut();
                Toast.makeText(getApplicationContext(), "Çıkış Yapıldı.", Toast.LENGTH_LONG).show();
                startActivity(new Intent(ActivityB.this, MainActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
