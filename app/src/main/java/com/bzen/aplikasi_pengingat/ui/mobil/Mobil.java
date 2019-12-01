package com.bzen.aplikasi_pengingat.ui.mobil;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.Serializable;

public class Mobil implements Serializable {
    private String plat, merk, thnPembuatan, wrn, btsOli, btsPajak, key, statusMobil;
    private int stsMobil;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getStsMobil() {
        return stsMobil;
    }

    public void setStsMobil(int stsMobil) {
        this.stsMobil = stsMobil;
    }

    public String getPlat() {
        return plat;
    }

    public void setPlat(String plat) {
        this.plat = plat;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getThnPembuatan() {
        return thnPembuatan;
    }

    public void setThnPembuatan(String thnPembuatan) {
        this.thnPembuatan = thnPembuatan;
    }

    public String getWrn() {
        return wrn;
    }

    public void setWrn(String wrn) {
        this.wrn = wrn;
    }

    public String getBtsOli() {
        return btsOli;
    }

    public void setBtsOli(String btsOli) {
        this.btsOli = btsOli;
    }

    public String getBtsPajak() {
        return btsPajak;
    }

    public void setBtsPajak(String btsPajak) {
        this.btsPajak = btsPajak;
    }

    @Override
    public String toString(){
        if(stsMobil == 0){
            statusMobil = "Non-Aktif";
        }else{
            statusMobil = "Aktif";
        }
        return " "+plat+"\n"+" "+merk+"\n"+" "+thnPembuatan+"\n"+" " +wrn+"\n"+" "+statusMobil+"\n"+" "+btsOli+"\n"+" "+btsPajak;
    }

    public Mobil(String platKendaraan, String merkKendaraan, String tahunPembuatan, String warna, int status, String batasOli, String batasPajak){
        plat = platKendaraan;
        merk = merkKendaraan;
        thnPembuatan = tahunPembuatan;
        wrn = warna;
        stsMobil = status;
        btsOli = batasOli;
        btsPajak = batasPajak;
    }

    public Mobil(){

    }
}