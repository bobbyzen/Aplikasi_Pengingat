package com.bzen.aplikasi_pengingat.ui.user;

import java.io.Serializable;

public class User implements Serializable {
    private String nama, email, password, foto, platmobil, tglLahir, alamat, key;
    private int stsUser, jenisUser, jenisKelamin;

    public User(String nama, String email, String password, String foto, String platmobil, int jenisUser, int jenisKelamin, int stsUser, String tglLahir, String alamat) {
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.foto = foto;
        this.platmobil = platmobil;
        this.stsUser = stsUser;
        this.jenisUser = jenisUser;
        this.tglLahir = tglLahir;
        this.alamat = alamat;
        this.jenisKelamin = jenisKelamin;
    }

    public User(){

    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTglLahir() {
        return tglLahir;
    }

    public void setTglLahir(String tglLahir) {
        this.tglLahir = tglLahir;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public int getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(int jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getPlatmobil() {
        return platmobil;
    }

    public void setPlatmobil(String platmobil) {
        this.platmobil = platmobil;
    }

    public int getStsUser() {
        return stsUser;
    }

    public void setStsUser(int stsUser) {
        this.stsUser = stsUser;
    }

    public int getJenisUser() {
        return jenisUser;
    }

    public void setJenisUser(int jenisUser) {
        this.jenisUser = jenisUser;
    }
}
