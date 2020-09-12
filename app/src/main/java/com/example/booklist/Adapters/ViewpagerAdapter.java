package com.example.booklist.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewpagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> fragmentListesi = new ArrayList<>();
    private final List<String> fragmentBaslikListesi = new ArrayList<>();

    public ViewpagerAdapter(FragmentManager manager){
        super(manager);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentListesi.get(position);
    }
    @Override
    public int getCount() {
        return fragmentListesi.size();
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentBaslikListesi.get(position);
    }
    public void fragmentEkle(Fragment fragment, String baslik){
        fragmentListesi.add(fragment);
        fragmentBaslikListesi.add(baslik);
    }
    public void fragmentEkle(Fragment fragment){
        fragmentListesi.add(fragment);
    }

}
