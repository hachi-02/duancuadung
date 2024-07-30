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

import com.example.duancuadung.MainActivity;
import com.example.duancuadung.R;

import DAO.NguoiDungDAO;

public class dang_nhap extends AppCompatActivity {
    Button bt_login;
    TextView bt_signup;
    EditText edt_username_signin, edt_pass_signin;
    NguoiDungDAO ndDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_nhap);

        bt_signup=findViewById(R.id.bt_signup);
        bt_login=findViewById(R.id.bt_login);
        edt_pass_signin=findViewById(R.id.edt_pass_signin);
        edt_username_signin=findViewById(R.id.edt_user_signin);

        bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(dang_nhap.this,dang_ky.class);
                startActivity(i);
            }
        });
        ndDAO = new NguoiDungDAO(this);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edt_username_signin.getText().toString();
                String password = edt_pass_signin.getText().toString();

                if (ndDAO.kiemTraDangNhap(username,password)) {
                    Toast.makeText(dang_nhap.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(dang_nhap.this, MainActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    Toast.makeText(dang_nhap.this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}