package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import Database.myhelper;
import Model.NguoiDung;

public class NguoiDungDAO {
    public myhelper helper;
    public NguoiDungDAO(Context c){helper=new myhelper(c);}
    public void themNguoiDung(NguoiDung nd){
        SQLiteDatabase db=helper.getWritableDatabase();
        ContentValues value=new ContentValues();
        value.put("username",nd.username);
        value.put("password",nd.password);
        db.insert("user",null,value);
    }

    public boolean kiemTraDangNhap(String username, String password) {
        SQLiteDatabase db = helper.getReadableDatabase();
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{username, password});

        if (cursor != null && cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        cursor.close();
        return false;
    }
    public boolean kiemTraNguoiDung(String username) {
        SQLiteDatabase db=helper.getWritableDatabase();
        Cursor cursor = db.query("user", null, "username=?", new String[]{username}, null, null, null);
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }
}
