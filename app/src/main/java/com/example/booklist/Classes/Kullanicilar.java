package com.example.booklist.Classes;

public class Kullanicilar {
    private String isim, soyisim, parola, mail, telefon, bio, kullanici_id, pfoto, nick;

    public Kullanicilar() { }

    public Kullanicilar(String kullanici_id, String isim, String soyisim, String parola, String mail
            , String telefon, String bio, String pfoto, String nick) {
        this.isim = isim;
        this.soyisim = soyisim;
        this.parola = parola;
        this.mail = mail;
        this.telefon = telefon;
        this.bio = bio;
        this.kullanici_id = kullanici_id;
        this.pfoto = pfoto;
        this.nick = nick;
    }
    public String getIsim() {
        return isim;
    }
    public void setIsim(String isim) {
        this.isim = isim;
    }
    public String getSoyisim() {
        return soyisim;
    }
    public void setSoyisim(String soyisim) {
        this.soyisim = soyisim;
    }
    public String getParola() {
        return parola;
    }
    public void setParola(String parola) {
        this.parola = parola;
    }
    public String getMail() {
        return mail;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
    public String getTelefon() {
        return telefon;
    }
    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    public String getKullanici_id() {
        return kullanici_id;
    }
    public void setKullanici_id(String kullanici_id) {
        this.kullanici_id = kullanici_id;
    }
    public String getPfoto() {
        return pfoto;
    }
    public void setPfoto(String pfoto) {
        this.pfoto = pfoto;
    }
    public String getNick() {
        return nick;
    }
    public void setNick(String nick) {
        this.nick = nick;
    }
}
