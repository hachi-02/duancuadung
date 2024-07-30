package Model;

import java.util.Date;

public class PhieuMuon {
    public int mapm, masp;
    public String tentp, tenngm;
    public String sdt;
    public Date ngaymuon, ngaytra;
    public Boolean trangthai;

    public PhieuMuon( int mapm,String tengm,  int masp,String tentp, String sdt, Date ngaymuon, Date ngaytra, Boolean trangthai) {
        this.mapm = mapm;
        this.masp = masp;
        this.tenngm = tengm;
        this.sdt = sdt;
        this.tentp = tentp;
        this.ngaymuon = ngaymuon;
        this.ngaytra = ngaytra;
        this.trangthai = trangthai;
    }

    public PhieuMuon(String tengm,int masp, String tentp, String sdt, Date ngaymuon,Date ngaytra) {
        this.tenngm = tengm;
        this.masp=masp;
        this.sdt = sdt;
        this.tentp = tentp;
        this.ngaymuon = ngaymuon;
        this.ngaytra=ngaytra;
    }
}
