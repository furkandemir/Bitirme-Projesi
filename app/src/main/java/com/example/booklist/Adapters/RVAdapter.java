package com.example.booklist.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.booklist.Activities.ActivityB;
import com.example.booklist.Activities.ActivityD;
import com.example.booklist.Activities.ActivityE;
import com.example.booklist.Classes.Kitap;
import com.example.booklist.R;

import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.CardViewTasarimNesneleriniTutucu>{
    private Fragment mContext;
    private List<Kitap> kitapList;
    private Context context;

    public RVAdapter(Fragment mContext, List<Kitap> kitapList) {
        this.mContext = mContext;
        this.kitapList = kitapList;
    }

    public class CardViewTasarimNesneleriniTutucu extends RecyclerView.ViewHolder{
        public ImageView imageViewKapak;
        public TextView textViewKitapAdi, textViewYazar, textViewYayinci, textViewMail;
        public TextView textViewTel, textViewFiyat, textViewIlanci;
        public CardView card;

        public CardViewTasarimNesneleriniTutucu(@NonNull View iv) {
            super(iv);
            imageViewKapak = iv.findViewById(R.id.imageViewKapak);
            textViewKitapAdi = iv.findViewById(R.id.textViewKitapAdi);
            textViewYazar = iv.findViewById(R.id.textViewYazar);
            textViewYayinci = iv.findViewById(R.id.textViewYayinci);
            textViewMail = iv.findViewById(R.id.textViewMail);
            textViewTel = iv.findViewById(R.id.textViewTel);
            textViewFiyat = iv.findViewById(R.id.textViewFiyat);
            textViewIlanci = iv.findViewById(R.id.textViewIlanci);
            card = iv.findViewById(R.id.card);
        }
    }
    @NonNull
    @Override
    public CardViewTasarimNesneleriniTutucu onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View  v = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.card_tasarim, parent, false);
        return new CardViewTasarimNesneleriniTutucu(v);
    }
    @Override
    public void onBindViewHolder(@NonNull CardViewTasarimNesneleriniTutucu holder, int position) {

        final Kitap kitap = kitapList.get(position);
        //Buton kullanmak gerekirse inner class hata verir. Üst sınıfın başına "final" koy.
        holder.textViewKitapAdi.setText("Kitap: "+kitap.getKitapAdi());
        holder.textViewYazar.setText("Yazar: "+kitap.getYazar());
        holder.textViewYayinci.setText("Yayıncı: "+kitap.getYayinci());
        holder.textViewMail.setText("Mail: "+kitap.getMail());
        holder.textViewTel.setText("Tel: "+kitap.getTel());
        holder.textViewFiyat.setText("Fiyat: "+kitap.getFiyat()+" TL");
        holder.textViewIlanci.setText("İlan Sahibi: "+kitap.getIlanci());
        /*holder.imageViewKapak.setImageResource(mContext.getResources()
                .getIdentifier(kitap.getKitapFoto(), "drawable", mContext.getContext().getPackageName()));

         */
        holder.imageViewKapak.setImageResource(R.drawable.logo);
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(){ }  Fragment kontolü için, eğer FragmentUc ise çalışsın.
                Intent intent = new Intent(mContext.getActivity(), ActivityE.class);
                intent.putExtra("nesne", kitap);
                mContext.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return kitapList.size();
    }

}
