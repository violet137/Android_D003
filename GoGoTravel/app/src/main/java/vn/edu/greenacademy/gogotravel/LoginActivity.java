package vn.edu.greenacademy.gogotravel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
//import com.facebook.FacebookSdk;


public class LoginActivity extends AppCompatActivity {

    TextView tvStatus;
    Button btnLogInFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        tvStatus = (TextView) findViewById(R.id.tvStatus);
        btnLogInFacebook = (Button) findViewById(R.id.btnLogInFacebook);

        btnLogInFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });
    }
}
