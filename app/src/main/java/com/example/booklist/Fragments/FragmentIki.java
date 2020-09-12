package com.example.booklist.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.booklist.Activities.ActivityB;
import com.example.booklist.Activities.MainActivity;
import com.example.booklist.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

//PROFİL
public class FragmentIki extends Fragment {
    private CircleImageView imageViewFoto;
    private TextView textViewIsim, textViewSoyisim, textViewMail, textViewTel, textViewBio, textViewNick;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private Uri uri = null;
    private ProgressDialog dialog;
    private StorageReference storageReference;
    private Button buttonKaydet;
    private boolean isCheck = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_iki, container, false);
        textViewIsim = v.findViewById(R.id.textViewIsim);
        textViewSoyisim = v.findViewById(R.id.textViewSoyisim);
        textViewMail = v.findViewById(R.id.textViewMail);
        textViewTel = v.findViewById(R.id.textViewTel);
        textViewBio = v.findViewById(R.id.textViewBio);
        textViewNick = v.findViewById(R.id.textViewNick);
        imageViewFoto = v.findViewById(R.id.imageViewFoto);
        buttonKaydet = v.findViewById(R.id.buttonKaydet);

        dialog = new ProgressDialog(getActivity());

        mAuth = FirebaseAuth.getInstance();

        storageReference = FirebaseStorage.getInstance().getReference();

        final String userid = mAuth.getCurrentUser().getUid();
        myRef = FirebaseDatabase.getInstance().getReference().child("kullanicilar").child(userid);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nnick = dataSnapshot.child("nick").getValue().toString();
                String nisim = dataSnapshot.child("isim").getValue().toString();
                String nsoyisim = dataSnapshot.child("soyisim").getValue().toString();
                String nmail = dataSnapshot.child("mail").getValue().toString();
                String ntel = dataSnapshot.child("telefon").getValue().toString();
                String nbio = dataSnapshot.child("bio").getValue().toString();
                String nimage = dataSnapshot.child("pfoto").getValue().toString();

                textViewNick.setText(nnick);
                textViewIsim.setText(nisim);
                textViewSoyisim.setText(nsoyisim);
                textViewMail.setText(nmail);
                textViewTel.setText(ntel);
                textViewBio.setText(nbio);

                uri = Uri.parse(nimage);

                RequestOptions requestOptions = new RequestOptions();
                requestOptions.placeholder(R.drawable.profil);

                Glide.with(getActivity()).setDefaultRequestOptions(requestOptions)
                        .load(uri).into(imageViewFoto);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        imageViewFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{
                    android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                CropImage.activity()
                        .start(getContext(),FragmentIki.this);

            }
        });

        buttonKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setTitle("Kaydediliyor");
                dialog.setMessage("Bilgilerinizi kaydediyoruz.");
                dialog.show();

                if(uri != null){
                    if(isCheck){
                        StorageReference userimage = storageReference.child("pp").child(userid+".jpg");
                        userimage.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if(task.isSuccessful()){
                                    final Uri download_uri;
                                    if(task != null){
                                        //download_uri = task.getResult().getUploadSessionUri();
                                        download_uri = uri;
                                    }
                                    else{
                                        download_uri = task.getResult().getUploadSessionUri();
                                        //download_uri = uri;
                                    }
                                    Map userUpdateMap = new HashMap();
                                    userUpdateMap.put("pfoto", download_uri.toString());
                                    myRef.updateChildren(userUpdateMap).addOnCompleteListener(new OnCompleteListener() {
                                        @Override
                                        public void onComplete(@NonNull Task task) {
                                            if(task.isSuccessful()){
                                                dialog.dismiss();
                                                Toast.makeText(getActivity(), "Başarılı", Toast.LENGTH_SHORT).show();
                                            }else{
                                                dialog.dismiss();
                                                Toast.makeText(getActivity(), "Hata: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }
        });


        return v;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                uri = result.getUri();
                imageViewFoto.setImageURI(uri);
                isCheck = true;

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }
}
