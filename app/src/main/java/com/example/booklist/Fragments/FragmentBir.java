package com.example.booklist.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.booklist.Activities.ActivityE;
import com.example.booklist.Classes.Kitap;
import com.example.booklist.R;
import com.example.booklist.Adapters.RVAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

//ANASAYFA
public class FragmentBir extends Fragment {
    private RecyclerView rv;
    private ArrayList<Kitap> kitapArrayList;
    private RVAdapter adapter;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bir, container, false);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("ilanlar");

        rv = v.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        kitapArrayList = new ArrayList<>();
        adapter = new RVAdapter(this, kitapArrayList);
        rv.setAdapter(adapter);

        tumIlanlar();

        return v;
    }
    public void tumIlanlar(){
            myRef.addValueEventListener(new ValueEventListener() {
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
