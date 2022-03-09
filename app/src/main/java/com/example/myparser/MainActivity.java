package com.example.myparser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import org.json.JSONArray;
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
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    List<Livre> LivreList = new ArrayList<Livre>();
    String url = "http://172.17.36.118:45455/api/Livres";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new MySiteAPI().execute(url);
    }

    public class MySiteAPI extends AsyncTask<String, Void, List<Livre>>
    {
        public String ConvertStream2String (InputStream is) throws IOException {
            StringBuilder sb = new StringBuilder();
            BufferedReader br = null;
            String str;

            br = new BufferedReader (new InputStreamReader(is));

            while ((str = br.readLine()) != null)
                sb.append(str);

            return sb.toString();
        }

        @Override
        protected List<Livre> doInBackground(String... strings)
        {
            String urlStr = strings[0];
            String rssStr;

            try {
                URL url = new URL(urlStr);
                HttpURLConnection cnx = (HttpURLConnection) url.openConnection();

                rssStr = ConvertStream2String(cnx.getInputStream());
                //parsing

                JSONArray LivreLArr = new JSONArray(rssStr);

                for(int index = 0; index < LivreLArr.length(); index++)
                {
                    JSONObject LivreObj = LivreLArr.getJSONObject(index);
                    Livre livre = new Livre(
                            LivreObj.getInt("livreID"),
                            LivreObj.getString("nom"),
                            LivreObj.getDouble("price"),
                            LivreObj.getString("urlImg")
                            );
                    LivreList.add(livre);
                }

            } catch (MalformedURLException | JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return LivreList;
        }

        protected void onPostExecute(List<Livre> result)
        {
            RecyclerView list = findViewById(R.id.lister);

            CustomAdapter adapter = new CustomAdapter (LivreList);
            list.setAdapter(adapter);
        }
    }

}