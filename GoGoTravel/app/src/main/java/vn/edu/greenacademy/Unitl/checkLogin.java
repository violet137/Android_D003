package vn.edu.greenacademy.Unitl;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import vn.edu.greenacademy.Model.userLogin;

/**
 * Created by MyPC on 25/03/2017.
 */

public class checkLogin extends AsyncTask<String, String, String> {

    userLogin user;
    transferServer server;
    public checkLogin(userLogin user){
        this.user = user;
        server = new transferServer();
    }

    @Override
    protected String doInBackground(String... params) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.accumulate("Username", user.getUserName());
            jsonObject.accumulate("MatKhau", user.getMatKhau());
            jsonObject.accumulate("KieuTk", user.getKieuTk());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return server.postServer(params[0], jsonObject.toString());
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
