package phieumuon;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duancuadung.R;

public class PhieuMuonViewholder extends RecyclerView.ViewHolder{
    TextView tv_mpm,tv_masp,tv_ten_ngm,tv_tentp,tv_sdt,tv_ngaymuon,tv_ngaytra,tv_trangthai;
    LinearLayout bt_ct,bt_xoa;
    public PhieuMuonViewholder(@NonNull View itemView) {
        super(itemView);
        this.tv_mpm=itemView.findViewById(R.id.tv_mapm);
        this.tv_masp=itemView.findViewById(R.id.tv_masp2);
        this.tv_ten_ngm=itemView.findViewById(R.id.tv_ten_ngm);
        this.tv_tentp=itemView.findViewById(R.id.tv_tp2);
        this.tv_sdt=itemView.findViewById(R.id.tv_sdt);
        this.tv_ngaymuon=itemView.findViewById(R.id.tv_ngaymuon);
        this.tv_ngaytra=itemView.findViewById(R.id.tv_ngaytra);
        this.tv_trangthai=itemView.findViewById(R.id.tv_trangthai);

        this.bt_ct=itemView.findViewById(R.id.bt_ct);
        this.bt_xoa=itemView.findViewById(R.id.bt_xoa2);


    }
}
