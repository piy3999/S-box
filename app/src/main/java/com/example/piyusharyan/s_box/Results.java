package com.example.piyusharyan.s_box;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Results extends AppCompatActivity {

    List<String> Res =new ArrayList<>();
    List<String> Res2 =new ArrayList<>();

RecyclerView rview;

    String type="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        rview = (RecyclerView) findViewById(R.id.rview1);
        Log.e("PB","RESULT");

        new Fun().execute();


    }

    class Fun extends AsyncTask<Void,Void,Void>
    {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                type = getIntent().getExtras().getString("Type");
                Document doc=null;

                doc = Jsoup.connect("http://www.cricbuzz.com/cricket-stats/icc-rankings").get();

                Elements k=null;
                if(type.equals("odi-batsman"))
                    k = doc.select("div#batsmen-odis");
                else if(type.equals("test-bowlers"))
                    k = doc.select("div#bowlers-tests");
                else if (type.equals("test-batsman"))
                    k = doc.select("div#batsmen-tests");
                else if (type.equals("odi-bowlers"))
                    k = doc.select("div#bowlers-odis");
                else if (type.equals("t20-batsman"))
                    k = doc.select("div#batsmen-t20s");
                else if (type.equals("t20-bowlers"))
                    k = doc.select("div#bowlers-t20s");


                Elements newsHeadlines = k.select("a.text-hvr-underline.text-bold.cb-font-16");
                Res.add(newsHeadlines.html());


                System.out.println("testing"+newsHeadlines.html());

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("thread start");


            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            rview.setAdapter(new CricketAdapter(getBaseContext(),Res,Res));
            rview.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        }
    }


}
