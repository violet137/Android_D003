package vn.edu.greenacademy.gogotravel;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import vn.edu.greenacademy.fragment.QuanAnFragment;

public class QuanAnActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_an);

        getSupportFragmentManager().beginTransaction().replace(R.id.layout,new QuanAnFragment(),"QuanAnFragment").commit();

    }
}
