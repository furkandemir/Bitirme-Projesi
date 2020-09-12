package com.example.booklist.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.booklist.Adapters.RVAdapter;
import com.example.booklist.Classes.Kitap;
import com.example.booklist.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

//İLANLARIM
public class FragmentUc extends Fragment {
    private RecyclerView rv;
    private ArrayList<Kitap> kitapArrayList;
    private RVAdapter adapter;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef2;
    private String mail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_uc, container, false);

        rv = v.findViewById(R.id.rv);
        rv.setHasFixedSize(true);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("ilanlar");
        mAuth = FirebaseAuth.getInstance();
        final String user_id = mAuth.getCurrentUser().getUid();
        myRef2 = FirebaseDatabase.getInstance().getReference().child("kullanicilar").child(user_id);



        rv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        kitapArrayList = new ArrayList<>();
        adapter = new RVAdapter(this, kitapArrayList);
        rv.setAdapter(adapter);


        benimIlanlarim();

        return v;
    }
    public void benimIlanlarim(){

        mAuth = FirebaseAuth.getInstance();
        final String user_id = mAuth.getCurrentUser().getEmail();
        Toast.makeText(getActivity(), user_id, Toast.LENGTH_LONG).show();

        Query mylist = myRef.orderByChild("mail").equalTo(user_id);

        mylist.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                kitapArrayList.clear();
                for(DataSnapshot d : dataSnapshot.getChildren()){
                    Kitap kitap = d.getValue(Kitap.class);
                    kitap.setKitap_id(d.getKey());

                    kitapArrayList.add(kitap);

                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
