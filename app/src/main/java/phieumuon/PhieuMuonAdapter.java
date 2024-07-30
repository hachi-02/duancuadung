package phieumuon;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duancuadung.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import Model.PhieuMuon;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonViewholder> {
    Context c;
    ArrayList<PhieuMuon> dspm;
    PhieuMuonFragment pmf;
    public PhieuMuonAdapter(Context c,ArrayList<PhieuMuon> dspm,PhieuMuonFragment pmf){
        this.c=c;
        this.dspm=dspm;
        this.pmf=pmf;
    }
    @NonNull
    @Override
    public PhieuMuonViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(c).inflate(R.layout.item_phieu_muon_viewholder,parent,false);
        return new PhieuMuonViewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PhieuMuonViewholder holder,@SuppressLint("RecyclerView") int position) {
        PhieuMuon pm=dspm.get(position);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String ngaymuon = (pm.ngaymuon != null) ? dateFormat.format(pm.ngaymuon) : "N/A";
        String ngaytra = (pm.ngaytra != null) ? dateFormat.format(pm.ngaytra) : "N/A";
        String trangThaiText = pm.trangthai ? "Đã trả" : "Chưa trả";//0 1


        holder.tv_mpm.setText(pm.mapm+"");
        holder.tv_masp.setText(pm.masp+"");
        holder.tv_ten_ngm.setText(pm.tenngm);
        holder.tv_tentp.setText(pm.tentp);
        holder.tv_sdt.setText(pm.sdt+"");
        holder.tv_ngaymuon.setText(ngaymuon);
        holder.tv_ngaytra.setText(ngaytra);
        holder.tv_trangthai.setText(trangThaiText);

        holder.bt_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mpm = dspm.get(position).mapm;
                pmf.xoaSanPham(mpm);
            }
        });
        holder.bt_ct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhieuMuon pm=dspm.get(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return dspm.size();
    }
}
