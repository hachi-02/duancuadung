package ds_sanpham;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duancuadung.R;

import java.util.ArrayList;

import Database.myhelper;
import Model.TheLoai;
import Model.SanPham;


public class SanPhamAdapterFragment extends RecyclerView.Adapter<SanPhamViewholder> {
    ArrayList<SanPham> ds ;
    Context context;
    SanPhamFragment spf;
    ArrayList<TheLoai> dstl;

    public SanPhamAdapterFragment(Context context, ArrayList<SanPham> ds,SanPhamFragment spf) {
        this.context = context;
        this.ds = ds;
        this.spf=spf;
        myhelper helper = new myhelper(context);
        this.dstl = new ArrayList<>(helper.getTheLoaiID());
    }
    public SanPhamViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_san_pham_viewholder, parent, false);

        return new SanPhamViewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SanPhamViewholder holder,@SuppressLint("RecyclerView") int position) {
        SanPham sp = ds.get(position);
        //gắn vị  trí index
        holder.tv_id.setText(sp.masp+"");
        holder.tv_tentp.setText(sp.tentp+"");
        String categoryName = tenTLQuaID(sp.theloai);
        holder.tv_theloai.setText(categoryName);
        holder.tv_soluong.setText(sp.soluong+"");
        holder.tv_dongia.setText(sp.dongia+"");




        holder.bt_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int masp = ds.get(position).masp;
                spf.xoaSanPham(masp);
            }
        });
        holder.bt_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SanPham sp=ds.get(position);
                spf.suaSanPham(sp);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ds.size();
    }
    //lấy tên của  thể loại từ id
    private String tenTLQuaID(int theloaiId) {
        for (TheLoai theloai : dstl) {
            if (theloai.getId() == theloaiId) {
                return theloai.getName();
            }
        }
        return "Unknown";
    }
}