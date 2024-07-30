package Model;

public class SanPham {
     public int masp,soluong,dongia,theloai;
     public String tentp;
    public SanPham(int masp,String tentp, int theloai,int soluong, int dongia){
        this.masp=masp;
        this.tentp=tentp;
        this.theloai=theloai;
        this.soluong=soluong;
        this.dongia=dongia;
    }
    public SanPham(String tentp, int theloai,int soluong, int dongia){
        this.tentp=tentp;
        this.theloai=theloai;
        this.soluong=soluong;
        this.dongia=dongia;
    }


}
