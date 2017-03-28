package vn.edu.greenacademy.asynctask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by GIT on 3/28/2017.
 */

public class Image extends AsyncTask<String, Void, Bitmap> {

    String strLinkAnh;
    ImageView ivImage;

    public Image(String strLinkAnh, ImageView ivImage) {
        this.strLinkAnh = strLinkAnh;
        this.ivImage = ivImage;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        try {
            URL url = new URL(strLinkAnh);
            Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream()); //chuyển từ link ành sang thành image
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        ivImage.setImageBitmap(bitmap);
    }
}