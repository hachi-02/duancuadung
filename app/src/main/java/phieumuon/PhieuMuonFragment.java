package phieumuon;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duancuadung.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import DAO.PhieuMuonDAO;
import Model.PhieuMuon;
import Model.SanPham;

public class PhieuMuonFragment extends Fragment {
    RecyclerView rcv;
    PhieuMuon pm;
    PhieuMuonDAO pmd;
    ArrayList<PhieuMuon> dspm;
    FloatingActionButton fabutton;
    Button bt_ngaymuon,bt_ngaytra;
    TextView tv_ngaymuon,tv_ngaytra;
    EditText et_tentp,et_tenngm,et_sdt,et_masp;
    CheckBox cb_trangthai;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        pmd = new PhieuMuonDAO(getContext());
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inlflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inlflater.inflate(R.layout.fragment_phieumuon, container, false);
        rcv = v.findViewById(R.id.rcv);
        fabutton = v.findViewById(R.id.floatactionbutton);
        fabutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogthemPhieuMuon();
            }
        });
        dulieu();

        return v;
    }

    //thêm phiếu mượn
    public void dialogthemPhieuMuon() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inf = getLayoutInflater();
        View v = inf.inflate(R.layout.them_phieumuon, null);
        bt_ngaymuon=v.findViewById(R.id.bt_ngaymuon);
        bt_ngaytra=v.findViewById(R.id.bt_ngaytra);
        tv_ngaymuon=v.findViewById(R.id.tv_ngaymuon);
        tv_ngaytra=v.findViewById(R.id.tv_ngaytra);
        et_masp=v.findViewById(R.id.id_tacpham);
        et_tenngm=v.findViewById(R.id.ten_ngm);
        et_sdt=v.findViewById(R.id.sdt);
        et_tentp=v.findViewById(R.id.ten_tp);
        builder.setView(v);

        //chọn ngày mượn và trả
        bt_ngaymuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(tv_ngaymuon);
            }
        });
        bt_ngaytra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(tv_ngaytra);
            }
        });


        //nhập id tự hiện lên tên sp
        et_masp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    try {
                        int masp = Integer.parseInt(s.toString());
                        String tentp = pmd.getTenSanPham(masp);
                        et_tentp.setText(tentp);
                    } catch (NumberFormatException e) {
                        et_tentp.setText("");
                    }
                } else {
                    et_tentp.setText("");
                }
            }
        });
        // xử lí khi chọn yes
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int masp=Integer.parseInt(et_masp.getText().toString());
                String tentp=et_tentp.getText().toString();
                String tenngm=et_tenngm.getText().toString();
                String sdt=et_sdt.getText().toString();
                String ngaymuonStr = tv_ngaymuon.getText().toString();
                String ngaytraStr = tv_ngaytra.getText().toString();

                // định dạng ngày tháng
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                Date ngaymuon = null;
                Date ngaytra = null;
                try {
                    ngaymuon = dateFormat.parse(ngaymuonStr);
                    ngaytra = dateFormat.parse(ngaytraStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Ngày không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }



                PhieuMuon pm=new PhieuMuon(tenngm,masp,tentp,sdt,ngaymuon,ngaytra);
                pmd.themPhieuMuon(pm);
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


    //Chọn ngày trả và mượn trong thêm phiếu mượn
    private void showDatePickerDialog(final TextView dateTextView) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, month + 1, year);
                        dateTextView.setText(selectedDate);
                        Toast.makeText(getContext(), "Ngày đã chọn: " + selectedDate, Toast.LENGTH_SHORT).show();
                    }
                }, year, month, day);
        // Ngày tối thiểu là hiện tại
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }




    public void xoaSanPham(int mapm){
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
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(DialogInterface dialog, int which) {
                pmd.xoaPhieuMuon(mapm);
                dulieu();
                Toast.makeText(getContext(), "Xoa thanh cong", Toast.LENGTH_SHORT).show();
            }
        });

        AlertDialog dialog=builder.create();
        dialog.show();

    }
    public void dulieu()
    {
        dspm = pmd.xemPM();
        PhieuMuonAdapter adapter =new PhieuMuonAdapter(getContext(),dspm,this);
        LinearLayoutManager linear = new LinearLayoutManager(getContext());
        rcv.setLayoutManager(linear);
        rcv.setAdapter(adapter);
    }


    public void suaPhieuMuon(PhieuMuon pm) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inf = getLayoutInflater();
        View v = inf.inflate(R.layout.sua_phieumuon, null);
        builder.setView(v);
        bt_ngaymuon=v.findViewById(R.id.bt_ngaymuon);
        bt_ngaytra=v.findViewById(R.id.bt_ngaytra);
        tv_ngaymuon=v.findViewById(R.id.tv_ngaymuon);
        tv_ngaytra=v.findViewById(R.id.tv_ngaytra);
        et_masp=v.findViewById(R.id.id_tacpham);
        et_tenngm=v.findViewById(R.id.ten_ngm);
        et_sdt=v.findViewById(R.id.sdt);
        et_tentp=v.findViewById(R.id.ten_tp);
        cb_trangthai=v.findViewById(R.id.checkbox_datra);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        cb_trangthai.setChecked(pm.trangthai != null && pm.trangthai);
        if (pm.ngaymuon != null) {
            tv_ngaymuon.setText(dateFormat.format(pm.ngaymuon));
        }
        if (pm.ngaytra != null) {
            tv_ngaytra.setText(dateFormat.format(pm.ngaytra));
        }

        et_tentp.setText(pm.tentp);
        et_sdt.setText(pm.sdt);
        et_tenngm.setText(pm.tenngm);
        et_masp.setText(pm.masp+"");
        cb_trangthai.setChecked(pm.trangthai != null && pm.trangthai);
        bt_ngaymuon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(tv_ngaymuon);
            }
        });
        bt_ngaytra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(tv_ngaytra);
            }
        });


        builder.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int masp = Integer.parseInt(et_masp.getText().toString());
                String tentp = et_tentp.getText().toString();
                String tenngm = et_tenngm.getText().toString();
                String sdt = et_sdt.getText().toString();
                boolean trangthai = cb_trangthai.isChecked();

                String ngaymuonStr = tv_ngaymuon.getText().toString();
                String ngaytraStr = tv_ngaytra.getText().toString();

                Date ngaymuon = null;
                Date ngaytra = null;
                try {
                    ngaymuon = dateFormat.parse(ngaymuonStr);
                    ngaytra = dateFormat.parse(ngaytraStr);
                } catch (ParseException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Ngày không hợp lệ", Toast.LENGTH_SHORT).show();
                    return;
                }


                PhieuMuon pmnew=new PhieuMuon(pm.mapm,tenngm,masp,tentp,sdt,ngaymuon,ngaytra,trangthai);
                pmd.suaPhieuMuon(pmnew);
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
}
