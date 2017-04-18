package id.latiev.onlineregistration.model;

/**
 * Created by latiev on 4/17/17.
 */

public class Dokter {

    private String id_dokter;
    private String nama;
    private String kelamin;
    private String ava_url;
    private int id_klinik;
    private String klinik;
    private String waktu;

    public Dokter(){}

    public Dokter(String nama, String ava_url, String klinik, String waktu) {
        this.nama = nama;
        this.ava_url = ava_url;
        this.klinik = klinik;
        this.waktu = waktu;
    }

    public Dokter(String id_dokter, String nama, String kelamin, String ava_url, int id_klinik, String klinik, String waktu) {
        this.id_dokter = id_dokter;
        this.nama = nama;
        this.kelamin = kelamin;
        this.ava_url = ava_url;
        this.id_klinik = id_klinik;
        this.klinik = klinik;
        this.waktu = waktu;
    }

    public String getId_dokter() {
        return id_dokter;
    }

    public void setId_dokter(String id_dokter) {
        this.id_dokter = id_dokter;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKelamin() {
        return kelamin;
    }

    public void setKelamin(String kelamin) {
        this.kelamin = kelamin;
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

    public String getAva_url() {
        return ava_url;
    }

    public void setAva_url(String ava_url) {
        this.ava_url = ava_url;
    }
}
