package DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import Database.myhelper;
import Model.TheLoai;

public class TheLoaiDAO {
    private myhelper helper;
    private SQLiteDatabase db;

    public TheLoaiDAO(Context context) {
        helper = new myhelper(context);
        db = helper.getWritableDatabase();
    }

}

