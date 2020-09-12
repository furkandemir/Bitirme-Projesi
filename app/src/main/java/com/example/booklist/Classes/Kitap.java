package com.example.booklist.Classes;

import java.io.Serializable;

public class Kitap implements Serializable {
    private String kitap_id, kitapAdi, yazar, yayinci, mail, tel, ilanci, kitapFoto;
    private double fiyat;

    public Kitap() {
    }
    public Kitap(String kitap_id, String kitapAdi, String yazar, String yayinci, String mail, String tel
            , String ilanci, String kitapFoto, double fiyat) {
        this.kitap_id = kitap_id;
        this.kitapAdi = kitapAdi;
        this.yazar = yazar;
        this.yayinci = yayinci;
        this.mail = mail;
        this.tel = tel;
        this.ilanci = ilanci;
        this.kitapFoto = kitapFoto;
        this.fiyat = fiyat;
    }
    public String getKitap_id() {
        return kitap_id;
    }
    public void setKitap_id(String kitap_id) {
        this.kitap_id = kitap_id;
    }
    public String getKitapAdi() {
        return kitapAdi;
    }
    public void setKitapAdi(String kitapAdi) {
        this.kitapAdi = kitapAdi;
    }
    public String getYazar() {
        return yazar;
    }
    public void setYazar(String yazar) {
        this.yazar = yazar;
    }
    public String getYayinci() {
        return yayinci;
    }
    public void setYayinci(String yayinci) {
        this.yayinci = yayinci;
    }
    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public String getIlanci() {
        return ilanci;
    }
    public void setIlanci(String ilanci) {
        this.ilanci = ilanci;
    }
    public String getKitapFoto() {
        return kitapFoto;
    }
    public void setKitapFoto(String kitapFoto) {
        this.kitapFoto = kitapFoto;
    }
    public double getFiyat() {
        return fiyat;
    }
    public void setFiyat(double fiyat) {
        this.fiyat = fiyat;
    }
}
