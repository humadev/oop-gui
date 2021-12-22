

public class Mahasiswa {

    private int id;
    private String nama = null;
    private String telpon = null;

    public Mahasiswa(int id, String nama, String telpon) {
        this.id = id;
        this.nama = nama;
        this.telpon = telpon;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String namaBaru) {
        this.nama = namaBaru;
    }

    public String getTelpon() {
        return telpon;
    }

    public void setLastName(String telponBaru) {
        this.telpon = telponBaru;
    }
}
