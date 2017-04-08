package vn.edu.greenacademy.gogotravel;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import java.io.File;
import java.util.ArrayList;

import vn.edu.greenacademy.Adapter.SDCardApdapter;

public class SDCardActivity extends AppCompatActivity {
    AsyncTaskLoadFile myAsyncTaskLoadFile;

    public class AsyncTaskLoadFile extends AsyncTask<Void, String, Void> {

        File targetDirector;
        SDCardApdapter sdCardApdapter;

        public AsyncTaskLoadFile(SDCardApdapter adapter){
            sdCardApdapter = adapter;
        }

        @Override
        protected void onPreExecute() {
            String ExternalStorageDirectorPath = Environment
                    .getExternalStorageDirectory().getAbsolutePath();

            String targetPath = ExternalStorageDirectorPath + "/Download/";
            targetDirector = new File(targetPath);
            sdCardApdapter.clear();

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            File[] files = targetDirector.listFiles();
            for (File file : files){
                publishProgress(file.getAbsolutePath());
                if (isCancelled()) break;
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            sdCardApdapter.add(values[0]);
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
            sdCardApdapter.notifyDataSetChanged();
            super.onPostExecute(result);
        }

    }

    SDCardApdapter sdCardApdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sdcardctivity);

        final GridView gridsd = (GridView) findViewById(R.id.gridsd);
        sdCardApdapter = new SDCardApdapter(this);
        gridsd.setAdapter(sdCardApdapter);

        myAsyncTaskLoadFile = new AsyncTaskLoadFile(sdCardApdapter);
        myAsyncTaskLoadFile.execute();

        Button btnChuyen = (Button) findViewById(R.id.btnChuyen);
        btnChuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> checkList = sdCardApdapter.check;
                Intent intent = new Intent();
                intent.putExtra("Image",checkList);
                setResult(1,intent);
                finish();
            }
        });

        AdapterView.OnItemClickListener myOnItemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String promt = "remove" + (String) parent.getItemAtPosition(position);
                //  Toast.makeText()

            }
        };
    }
}
