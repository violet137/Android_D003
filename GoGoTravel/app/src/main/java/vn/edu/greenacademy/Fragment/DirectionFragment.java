package vn.edu.greenacademy.Fragment;


import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import vn.edu.greenacademy.Model.DataParser;
import vn.edu.greenacademy.gogotravel.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DirectionFragment extends Fragment {
    private GoogleMap map;
    private static DirectionFragment instance = null;

    public DirectionFragment() {
        // Required empty public constructor
    }

    public static DirectionFragment getInstance()
    {
        if(instance == null)
            instance = new DirectionFragment();
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_direction, container, false);
        return view;
    }

    private String downloadUrl(String strUrl)
    {
        String result = "";
        InputStream inputStream = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(strUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            inputStream = connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();
            String chunks;
            while((chunks=br.readLine()) != null)
            {
                result += chunks;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private class FetchUrl extends AsyncTask<String, Void, String>
    {

        @Override
        protected String doInBackground(String... params) {
            String data = "";
            data = downloadUrl(params[0]);
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }
    }

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>>
    {
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... params) {
            JSONObject jsonObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jsonObject = new JSONObject(params[0]);
                DataParser parser = new DataParser();

                //start parsing data
                routes = parser.parse(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> lists) {
            super.onPostExecute(lists);
            ArrayList<LatLng> points = null;
            PolylineOptions polylineOptions = null;
            MarkerOptions markerOptions = new MarkerOptions();

            //Traversing through all routes
            for(int i=0; i<lists.size(); i++)
            {
                points = new ArrayList<LatLng>();
                polylineOptions = new PolylineOptions();

                //Fetch i-th route
                List<HashMap<String, String>> path = lists.get(i);

                //Fetch all points in i-th route
                for(int j=0; j<path.size(); j++)
                {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));
                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                polylineOptions.addAll(points);
                polylineOptions.width(2);
                polylineOptions.color(Color.RED);
            }
            if(polylineOptions != null) {
                map.addPolyline(polylineOptions);
            }
        }
    }

    public void direction(List<LatLng> MarkerPoints)
    {
        map.clear();
    }

}
