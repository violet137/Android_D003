package vn.edu.greenacademy.gogotravel;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import vn.edu.greenacademy.adapter.ReviewImageAdapter;

public class CameraActivity extends AppCompatActivity {
    private ImageButton ibCapture;
    private ImageView ivImage;
    private Uri uri;
    private List<String> list;
    private GridView grid;
    ReviewImageAdapter reviewImageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        grid = (GridView) findViewById(R.id.grid);
        ivImage = (ImageView) findViewById(R.id.ivImage);
        ibCapture = (ImageButton) findViewById(R.id.ibCapture);
        ibCapture.setImageResource(R.drawable.camera);

        reviewImageAdapter = new ReviewImageAdapter(this);
        list = null;
        grid.setAdapter(reviewImageAdapter);


        ibCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                capturePicture();
            }
        });
    }

    private void capturePicture() {
        if (getApplicationContext().getPackageManager().
                hasSystemFeature(PackageManager.FEATURE_CAMERA)) {

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,100);
        }else{
            Toast.makeText(getApplication(),"Camera không được hỗ trợ",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data!= null){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ivImage.setImageBitmap(bitmap);
            reviewImageAdapter.AddBitmap(bitmap);
        }


    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (this.checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.CAMERA},100);
            }
        }
        return super.onCreateView(parent, name, context, attrs);

    }


}
