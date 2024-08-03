package Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import Model.TheLoai;

public class myhelper extends SQLiteOpenHelper {

    public myhelper(@Nullable Context context){
        super(context,"Quan_Ly_Sach", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Thể loại
        String TheLoai = "CREATE TABLE theloai (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ten_theloai TEXT)";
        db.execSQL(TheLoai);
        String cactheloai = "INSERT INTO theloai (ten_theloai) VALUES ('Kinh dị')";
        String cactheloai1 = "INSERT INTO theloai (ten_theloai) VALUES ('Hài hước')";
        String cactheloai2 = "INSERT INTO theloai (ten_theloai) VALUES ('Lãng mạn')";
        db.execSQL(cactheloai);
        db.execSQL(cactheloai1);
        db.execSQL(cactheloai2);


        // Người dùng
        String sql1= "CREATE TABLE user (" +
                "username TEXT PRIMARY KEY, " +
                "password TEXT)";
        db.execSQL(sql1);

        // Sản phẩm
        String sql = "CREATE TABLE sanpham (" +
                "masp INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tentp TEXT, " +
                "theloai_id INTEGER, " +
                "soluong INTEGER, " +
                "dongia INTEGER, " +
                "FOREIGN KEY(theloai_id) REFERENCES theloai(id))";
        db.execSQL(sql);

        // Phiếu mượn
        String phieuMuon = "CREATE TABLE phieumuon (" +
                "id_phieumuon INTEGER PRIMARY KEY AUTOINCREMENT," +
                "masp INTEGER, " +
                "tennguoimuon TEXT, " +
                "tentp TEXT, " +
                "sdt TEXT, " +
                "ngaymuon DATE, " +
                "ngaytra DATE, " +
                "trangthai INTEGER DEFAULT 0, " +
                "FOREIGN KEY(masp) REFERENCES sanpham(masp))";
        db.execSQL(phieuMuon);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("myhelper", "onUpgrade() called");
        db.execSQL("drop table if exists user");
        db.execSQL("drop table if exists sanpham");
        db.execSQL("DROP TABLE IF EXISTS theloai");
        db.execSQL("drop table if exists phieumuon");
        onCreate(db);
    }
    //lấy các tên thể loại trả về ds các chuỗi
    public List<String> getAllTheLoai() {
        List<String> categories = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT ten_theloai FROM theloai";
        Cursor cursor = db.rawQuery(query, null);

        try {
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex("ten_theloai");
                if (columnIndex != -1) {
                    do {
                        categories.add(cursor.getString(columnIndex));
                    } while (cursor.moveToNext());
                } else {
                    // Cột không tồn tại
                    Log.e("myhelper", "Cột 'ten_theloai' không tồn tại trong kết quả truy vấn.");
                }
            } else {
                // Không có dữ liệu
                Log.e("myhelper", "Không có dữ liệu hoặc con trỏ null.");
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return categories;
    }
    //lấy tất cả tên tác phẩm dựa trên tên thể loại
    public List<String> getSanPhambyTheloai(String categoryName) {
        List<String> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT sanpham.tentp FROM sanpham " +
                "INNER JOIN theloai ON sanpham.theloai_id = theloai.id " +
                "WHERE theloai.ten_theloai = ?";
        Cursor cursor = db.rawQuery(query, new String[]{categoryName});
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex("tentp");
                do {
                    if (columnIndex != -1) {
                        products.add(cursor.getString(columnIndex));
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return products;
    }
    public long themTheLoai(String categoryName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ten_theloai", categoryName);
        long result = db.insert("theloai", null, values);
        return result;
    }


    public List<TheLoai> getTheLoaiID() {
        List<TheLoai> theloai = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, ten_theloai FROM theloai", null);

        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("id");
            int nameIndex = cursor.getColumnIndex("ten_theloai");

            if (idIndex != -1 && nameIndex != -1) {  // Kiểm tra cột có hợp lệ không
                do {
                    int id = cursor.getInt(idIndex);
                    String name = cursor.getString(nameIndex);
                    theloai.add(new TheLoai(id, name));
                } while (cursor.moveToNext());
            } else {
                Log.e("Database", "Một hoặc nhiều cột không tồn tại trong kết quả truy vấn.");
            }
            cursor.close();
        } else {
            Log.e("Database", "Không có dữ liệu hoặc con trỏ null.");
        }
        return theloai;
    }
    public boolean xoaTheLoai(String tenTheLoai) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete("theloai", "ten_theloai = ?", new String[]{tenTheLoai});
        return result > 0;
    }


}
