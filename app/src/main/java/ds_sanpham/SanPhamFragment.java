package ds_sanpham;

import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duancuadung.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import DAO.SanPhamDAO;
import Model.SanPham;

public class SanPhamFragment extends Fragment {
    RecyclerView rcv;
    SanPhamDAO spd;
    ArrayList<SanPham> ds;
    FloatingActionButton fabutton;
    EditText et_ten,et_soluong,et_gia;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inlflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v=inlflater.inflate(R.layout.fragment_sanpham,container,false);
        rcv = v.findViewById(R.id.rcv);
        fabutton = v.findViewById(R.id.floatactionbutton);
        spd = new SanPhamDAO(getContext());


        dulieu();
        fabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogthemSanPham();
            }
        });
        return v;
    }


    public void dulieu()
    {
        ds = spd.xemSP();
        SanPhamAdapterFragment adapter =new SanPhamAdapterFragment(getContext(),ds,this);
        LinearLayoutManager linear = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(linear);
        rcv.setAdapter(adapter);
    }
    // Gọi phương thức này sau khi xóa dữ liệu và trước khi thêm dữ liệu mới







    public void dialogthemSanPham() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inf = getLayoutInflater();
        View v = inf.inflate(R.layout.them_sp, null);
        Spinner spinner = v.findViewById(R.id.theloai);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.theloai_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        builder.setView(v);
        et_ten=v.findViewById(R.id.ten);
        et_soluong=v.findViewById(R.id.soluong);
        et_gia=v.findViewById(R.id.giaban);

        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String ten=et_ten.getText().toString();
                int theloaiPosition = spinner.getSelectedItemPosition();
                int theloai = getTheLoaiIdByPosition(theloaiPosition);
                int soluong=Integer.parseInt(et_soluong.getText().toString());
                int dongia=Integer.parseInt(et_gia.getText().toString());

                SanPham sp=new SanPham(ten,theloai,dongia,soluong);
                spd.themSanPham(sp);
                dulieu();
            }
        });
        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }




    public void xoaSanPham(int masp){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("thong bao");
        builder.setMessage("Ban co muon xoa");
        builder.setCancelable(false);

        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                spd.xoaSanPham(masp);
                dulieu();
                Toast.makeText(getContext(), "Xoa thanh cong", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();

    }






    public void suaSanPham(SanPham sp){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inf= getLayoutInflater();
        View v=inf.inflate(R.layout.sua_sp,null);
        builder.setView(v);
        et_ten=v.findViewById(R.id.ten);
        Spinner spinner = v.findViewById(R.id.theloai);
        et_soluong=v.findViewById(R.id.soluong);
        et_gia=v.findViewById(R.id.giaban);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.theloai_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        et_ten.setText(sp.tentp);
        et_soluong.setText(sp.soluong+"");
        et_gia.setText(sp.dongia+"");


        int spinnerPosition = getPositionByTheLoaiId(sp.theloai);
        spinner.setSelection(spinnerPosition);


        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String ten= et_ten.getText().toString();
                int theloai = getTheLoaiIdByPosition(spinner.getSelectedItemPosition());
                int soluong=Integer.parseInt(et_soluong.getText().toString());
                int giaban=Integer.parseInt(et_gia.getText().toString());

                SanPham spnew=new SanPham(sp.masp,ten,theloai,soluong,giaban);
                spd.suaSanPham(spnew);
                dulieu();
            }
        });


        builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog dialog=builder.create();
        dialog.show();
    }





    private int getPositionByTheLoaiId(int theloaiId) {
        for (int i = 0; i < THE_LOAI_IDS.length; i++) {
            if (THE_LOAI_IDS[i] == theloaiId) {
                return i;
            }
        }
        return 0;
    }
    private static final int[] THE_LOAI_IDS = {1, 2, 3};

    private int getTheLoaiIdByPosition(int position) {
        if (position >= 0 && position < THE_LOAI_IDS.length) {
            return THE_LOAI_IDS[position];
        }
        return -1;
    }

}
