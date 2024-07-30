package dangnhap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duancuadung.R;

import DAO.NguoiDungDAO;
import Model.NguoiDung;

public class dang_ky extends AppCompatActivity {
    Button bt_signup;
    NguoiDungDAO nguoidungdao;
    TextView bt_back;
    EditText edt_username_signup, edt_pass_signup,edt_confirmpass_signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_ky);

        bt_signup=findViewById(R.id.bt_signup2);
        edt_username_signup=findViewById(R.id.edt_user_signup);
        edt_pass_signup=findViewById(R.id.edt_pass_signup);
        edt_confirmpass_signup=findViewById(R.id.edt_confirmpass_signup);
        bt_back=findViewById(R.id.bt_back);
        nguoidungdao=new NguoiDungDAO(dang_ky.this);

        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_signup=edt_username_signup.getText().toString();
                String pass_signup=edt_pass_signup.getText().toString();
                String confirmpass_signup = edt_confirmpass_signup.getText().toString();

                if (username_signup.isEmpty() || pass_signup.isEmpty()|| confirmpass_signup.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Username và Password không được để trống!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!confirmpass_signup.equals(pass_signup)) {
                    Toast.makeText(getApplicationContext(), "Mật khẩu nhập lại không khớp!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (nguoidungdao.kiemTraNguoiDung(username_signup)) {
                    Toast.makeText(getApplicationContext(), "Tài khoản đã tồn tại!", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                NguoiDung nd=new NguoiDung(username_signup,pass_signup);
                nguoidungdao.themNguoiDung(nd);
                Toast.makeText(getApplicationContext(), "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(dang_ky.this,dang_nhap.class);
                startActivity(i);

            }
        }
        });
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(dang_ky.this,dang_nhap.class);
                startActivity(i);
            }
        });
    }
}