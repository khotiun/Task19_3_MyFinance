package com.example.mypc.task19_3_myfinance;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    TextView textLink;
    TextView tvJson;
    Button btnStart;
    String contentText = null;
    String s;

    TextView tvUsdAsk, tvUsdBid, tvEurAsk, tvEurBid, tvRurAsk, tvRurBid;
    String mUsdAsk, mUsdBid, mEurAsk, mEurBid, mRurAsk, mRurBid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvJson = (TextView) findViewById(R.id.text_view_json);
        btnStart = (Button) findViewById(R.id.btn_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (contentText == null) {
//                    tvJson.setText("Загрузка...");
                    //В AsyncTask передается ссы
                    new ProgressTask().execute("http://api.minfin.com.ua/mb/8b7051383f41d1feaedf93e1c0b315a86b10c510/");
                }
            }
        });

        textLink = (TextView) findViewById(R.id.link);
        textLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri address = Uri.parse("http://minfin.com.ua/");
                Intent openlinkIntent = new Intent(Intent.ACTION_VIEW, address);
                startActivity(openlinkIntent);
            }
        });
        tvUsdAsk = (TextView) findViewById(R.id.tv_usd_ask);
        tvUsdBid = (TextView) findViewById(R.id.tv_usd_bid);
        tvEurAsk = (TextView) findViewById(R.id.tv_eur_ask);
        tvEurBid = (TextView) findViewById(R.id.tv_eur_bid);
        tvRurAsk = (TextView) findViewById(R.id.tv_rur_ask);
        tvRurBid = (TextView) findViewById(R.id.tv_rur_bid);
    }

    private class ProgressTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... path) {

            String content;
            try {
                content = getContent(path[0]);
            } catch (IOException ex) {
                content = ex.getMessage();
            }

            return content;
        }

        @Override
        protected void onPostExecute(String content) {

            contentText = content;
            Log.d("TAG", content);
//            tvJson.setText(content);
//            webView.loadData(content, "text/html; charset=utf-8", "utf-8");
//            Toast.makeText(getActivity(), "Данные загружены", Toast.LENGTH_SHORT)
//                    .show();

//            tvJson.setText(s);
            tvUsdAsk.setText(mUsdAsk);
            tvUsdBid.setText(mUsdBid);
            tvEurAsk.setText(mEurAsk);
            tvEurBid.setText(mEurBid);
            tvRurAsk.setText(mRurAsk);
            tvRurBid.setText(mRurBid);
        }

        private String getContent(String path) throws IOException {
            BufferedReader reader = null;
            try {
                URL url = new URL(path);
                Log.d("TAG", "getContent");
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.setReadTimeout(300005);
                c.connect();
                reader = new BufferedReader(new InputStreamReader(c.getInputStream()));
                StringBuilder buf = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    buf.append(line + "\n");
                }
                Log.d("TAG", buf.toString());

                try {
                    JSONArray jArray = new JSONArray(buf.toString());//получается массив из участков {...}
                    for (int i = 0; i < 3; i++) {
                        JSONObject jObject = jArray.getJSONObject(i);
                        //s = (String) jObject.get("id");
                        switch (i) {
                            case 0:
                                mRurAsk = (String) jObject.opt("ask");
                                mRurBid = (String) jObject.opt("bid");
                                break;
                            case 1:
                                mEurAsk = (String) jObject.opt("ask");
                                mEurBid = (String) jObject.opt("bid");
                                break;
                            case 2:
                                mUsdAsk = (String) jObject.opt("ask");
                                mUsdBid = (String) jObject.opt("bid");
                                break;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return (buf.toString());//убрать к чертям собочачим, возвращать ничего не нужно
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
        }
    }
}
