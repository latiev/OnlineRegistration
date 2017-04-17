package id.latiev.onlineregistration.model;

/**
 * Created by latiev on 4/17/17.
 */

public class Klinik {

    private int id_klinik;
    private String klinik;
    private String waktu;

    private Klinik(){}

    public Klinik(int id_klinik, String klinik, String waktu) {
        this.id_klinik = id_klinik;
        this.klinik = klinik;
        this.waktu = waktu;
    }

    public int getId_klinik() {
        return id_klinik;
    }

    public void setId_klinik(int id_klinik) {
        this.id_klinik = id_klinik;
    }

    public String getKlinik() {
        return klinik;
    }

    public void setKlinik(String klinik) {
        this.klinik = klinik;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }
}
