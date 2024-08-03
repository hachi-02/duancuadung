package doanhthu;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import com.example.duancuadung.R;

import DAO.PhieuMuonDAO;

public class DoanhThuFragment extends Fragment {
    PhieuMuonDAO pmd;
    Button bt_ngaybd, bt_ngaykt,bt_tinhtien;
    TextView tv_tinhtien,tv_ngaybd,tv_ngaykt;
    String startDate, endDate;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        pmd = new PhieuMuonDAO(getContext());
    }
    public View onCreateView(@NonNull LayoutInflater inlflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inlflater.inflate(R.layout.fragment_doanhthu, container, false);
        bt_ngaybd = v.findViewById(R.id.bt_ngaybd);
        bt_ngaykt = v.findViewById(R.id.bt_ngaykt);
        bt_tinhtien = v.findViewById(R.id.bt_tinhtien);
        tv_ngaybd=v.findViewById(R.id.tv_ngaybd);
        tv_ngaykt=v.findViewById(R.id.tv_ngaykt);
        tv_tinhtien = v.findViewById(R.id.tv_tinhtien);

        bt_ngaybd.setOnClickListener(view -> showDatePickerDialog(true));
        bt_ngaykt.setOnClickListener(view -> showDatePickerDialog(false));
        bt_tinhtien.setOnClickListener(view -> tinhTienTrongTG());
        return v;
    }
    private void showDatePickerDialog(boolean isStartDate) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(selectedYear, selectedMonth, selectedDay);
                    String formattedDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(selectedDate.getTime());
                    if (isStartDate) {
                        startDate = formattedDate;
                        tv_ngaybd.setText(formattedDate);
                    } else {
                        endDate = formattedDate;
                        tv_ngaykt.setText(formattedDate);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }
    private void tinhTienTrongTG(){
        //kt ngày tháng
        if (startDate == null || endDate == null) {
            tv_tinhtien.setText("Vui lòng chọn khoảng thời gian.");
            return;
        }

        String start = dinhDangNgay(startDate);
        String end = dinhDangNgay(endDate);

        int tongtien = pmd.tinhTongDoanhThu(start, end);
        tv_tinhtien.setText("Tổng doanh thu: " + tongtien);
    }

    private String dinhDangNgay(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(sdf.parse(date));
            return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
