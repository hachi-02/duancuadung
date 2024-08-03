package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import Database.myhelper;
import Model.PhieuMuon;

public class PhieuMuonDAO {
    public myhelper helper;
    public PhieuMuonDAO(Context c){helper=new myhelper(c);}
    public void themPhieuMuon(PhieuMuon pm){

        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues value=new ContentValues();
        value.put("masp",pm.masp);
        value.put("tentp",pm.tentp);
        value.put("tennguoimuon",pm.tenngm);
        value.put("sdt",pm.sdt);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        value.put("ngaymuon", dateFormat.format(pm.ngaymuon));
        value.put("ngaytra", dateFormat.format(pm.ngaytra));
        value.put("trangthai",pm.trangthai);


        db.insert("phieumuon",null,value);
    }
    public ArrayList<PhieuMuon> xemPM(){
        ArrayList<PhieuMuon> dspm= new ArrayList<PhieuMuon>();
        SQLiteDatabase db=helper.getReadableDatabase();

        Cursor c=db.rawQuery("select* from phieumuon",null);
        if(c.moveToFirst()){
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            do {
                int mapm= c.getInt(0);
                int masp=c.getInt(1);
                String tenngm=c.getString(2);
                String tentp=c.getString(3);
                String sdt=c.getString(4);
                Date ngaymuon = null;
                Date ngaytra = null;
                try {
                    ngaymuon = dateFormat.parse(c.getString(5));
                    ngaytra = dateFormat.parse(c.getString(6));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Boolean trangthai=c.getInt(7)==1;

                PhieuMuon pm = new PhieuMuon(mapm, tenngm, masp, tentp, sdt, ngaymuon, ngaytra, trangthai);
                dspm.add(pm);
            }while(c.moveToNext());

        }
        return dspm;
    }
    public void xoaPhieuMuon(int mapm){
        SQLiteDatabase db=helper.getReadableDatabase();
        db.delete("phieumuon","id_phieumuon=?",new String[]{mapm+""});
    }
    public void suaPhieuMuon(PhieuMuon pm){
        SQLiteDatabase db=helper.getReadableDatabase();
        ContentValues value=new ContentValues();
        value.put("masp", pm.masp);
        value.put("tentp", pm.tentp);
        value.put("tennguoimuon", pm.tenngm);
        value.put("sdt", pm.sdt);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        value.put("ngaymuon", pm.ngaymuon != null ? dateFormat.format(pm.ngaymuon) : null);
        value.put("ngaytra", pm.ngaytra != null ? dateFormat.format(pm.ngaytra) : null);
        value.put("trangthai", pm.trangthai != null && pm.trangthai);

        db.update("phieumuon", value, "id_phieumuon = ?", new String[]{String.valueOf(pm.mapm)});
    }

    public String getTenSanPham(int id) {
        SQLiteDatabase db=helper.getReadableDatabase();
        String tentp = null;
        String query = "SELECT tentp FROM sanpham WHERE masp = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("tentp");
            if (columnIndex != -1) {
                tentp = cursor.getString(columnIndex);
            }
        }
        cursor.close();
        return tentp;
    }
    public int tinhTongDoanhThu(String startDate, String endDate) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String query = "SELECT SUM(s.dongia) FROM phieumuon pm " +
                "JOIN sanpham s ON pm.masp = s.masp " +
                "WHERE pm.trangthai = 1 " +
                "AND pm.ngaytra BETWEEN ? AND ?";
        Cursor cursor = db.rawQuery(query, new String[]{startDate, endDate});
        int kq = 0;
        if (cursor.moveToFirst()) {
            kq = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return kq;
    }
}
