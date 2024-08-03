package doanhthu;


import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.duancuadung.R;

import DAO.TheLoaiDAO;
import Database.myhelper;

import java.util.ArrayList;
import java.util.List;

public class TheLoaiFragment extends Fragment {
   EditText et_tentl;
   Button bt_themtl;
   ListView ds_theloai;
   ListView ds_tacpham;
   myhelper helper;
   ArrayAdapter<String> theloaiAdapter;
   ArrayAdapter<String> tacphamdapter;
   List<String> dstl;
   List<String> dstp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_theloai, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        et_tentl = view.findViewById(R.id.et_tentl);
        bt_themtl = view.findViewById(R.id.bt_themtl);
        ds_theloai = view.findViewById(R.id.dstheloai);
        ds_tacpham = view.findViewById(R.id.dstacpham);
        helper = new myhelper(getContext());

        dstl = new ArrayList<>();
        dstp = new ArrayList<>();
        //khởi tạo các ArrayAdapter để hiển thị danh sách thể loại (dstl) và danh sách tác phẩm (dstp) trong ListView.
        theloaiAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, dstl);
        tacphamdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, dstp);

        //hiển thị dữ liệu trong các danh sách.
        ds_theloai.setAdapter(theloaiAdapter);
        ds_tacpham.setAdapter(tacphamdapter);

        loadTL();

        bt_themtl.setOnClickListener(v -> themTL());

        ds_theloai.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedCategory = dstl.get(position);
            loadTPbyTL(selectedCategory);
        });
        ds_theloai.setOnItemLongClickListener((parent, view1, position, id) -> {
            String chonTL = dstl.get(position);
            confirmXoaTL(chonTL);
            return true;
        });
    }
    //làm mới và cập nhật ds lên gd người dùng
    private void loadTL() {
        //xóa cũ
        dstl.clear();
        //gọi
        dstl.addAll(helper.getAllTheLoai());
        //thêm mới
        theloaiAdapter.notifyDataSetChanged();
    }

    //thêm một thể loại mới vào cơ sở dữ liệu và cập nhật danh sách thể loại hiển thị trong giao diện người dùng
    private void themTL() {
        String categoryName = et_tentl.getText().toString().trim();
        if (categoryName.isEmpty()) {
            Toast.makeText(getContext(), "Tên thể loại không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }

        long result = helper.themTheLoai(categoryName);
        if (result > 0) {
            Toast.makeText(getContext(), "Thêm thể loại thành công", Toast.LENGTH_SHORT).show();
            et_tentl.setText("");
            loadTL();  // Cập nhật danh sách thể loại
        } else {
            Toast.makeText(getContext(), "Lỗi khi thêm thể loại", Toast.LENGTH_SHORT).show();
        }
    }

    // tải danh sách sản phẩm thuộc một thể loại cụ thể và cập nhật giao diện người dùng
    private void loadTPbyTL(String categoryName) {
        dstp.clear();
        dstp.addAll(helper.getSanPhambyTheloai(categoryName));
        tacphamdapter.notifyDataSetChanged();
    }
    private void confirmXoaTL(String categoryName) {
        new AlertDialog.Builder(getContext())
                .setTitle("Xóa thể loại")
                .setMessage("Bạn có chắc chắn muốn xóa thể loại này không?")
                .setPositiveButton("Có", (dialog, which) -> xoaTheLoai(categoryName))
                .setNegativeButton("Không", null)
                .show();
    }

    private void xoaTheLoai(String tenTheLoai) {
        boolean result = helper.xoaTheLoai(tenTheLoai);
        if (result) {
            Toast.makeText(getContext(), "Xóa thể loại thành công", Toast.LENGTH_SHORT).show();
            loadTL();  // Cập nhật danh sách thể loại
            dstp.clear();  // Xóa danh sách tác phẩm khi xóa thể loại
            tacphamdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getContext(), "Lỗi khi xóa thể loại", Toast.LENGTH_SHORT).show();
        }
    }
}