package com.example.piyusharyan.s_box;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    List<String> s1= new ArrayList<>();
    List<String> s2= new ArrayList<>();
    private LinearLayoutManager mLayoutManager;


    List<String> s4
            = new ArrayList<>();
    List<Integer> s3 = new ArrayList<>();
    RecyclerView recycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

/* make the API call */

//        String json = getIntent().getExtras().getString("Response");
  //      Log.e("Piyush",json);


        recycler = (RecyclerView) findViewById(R.id.rview11);

        Log.e("Start","Starrted");
        new Fun().execute();

        Button Explore = (Button)findViewById(R.id.pb1);
        Explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getBaseContext(), Explore.class);
                startActivity(i);
            }
        });


        Button profile = (Button)findViewById(R.id.pro1);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(), ProfileH.class));
            }
        });



    }

    String query = null;

    class Fun extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] params) {


            DatabaseReference mDatabase;
            mDatabase = FirebaseDatabase.getInstance().getReference();


            Document doc = null;
            try {
                doc = Jsoup.connect("http://en.wikipedia.org/").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements newsHeadlines = doc.select("#mp-itn b a");


            Log.e("Testign",newsHeadlines.toString());

            SharedPreferences sharedPref = getSharedPreferences("mypref", 0);

            Log.e("CHECKIGN SJ",sharedPref.getString("name","SS"));
            String email= sharedPref.getString("email", "anjaana,3999@gmail,com");


            email = email.replace(".",",");

            mDatabase.child("posts").child(sharedPref.getString("id","null")).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {



                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        Log.e("PBPB","Piyush");
                            Post p = postSnapshot.getValue(Post.class);
                            s1.add(p.getPic());
                            s2.add(p.getHead());
                            s4.add(p.getKey());
                            s3.add(p.getLikeCount());
                            query = p.getQuery();
                    }

                    HomeAdapter h = new HomeAdapter(getBaseContext(),s1,s2,s3,s4);
                    mLayoutManager = new LinearLayoutManager(Home.this);
                    mLayoutManager.setReverseLayout(true);
                    mLayoutManager.setStackFromEnd(true);

// And now set it to the RecyclerView
                    recycler.setLayoutManager(mLayoutManager);
                    recycler.setHasFixedSize(true);

                    recycler.setAdapter(h);
                    h.setClickListener(new HomeAdapter.ClickListener(){
                        @Override
                        public void onClick(View v, int pos) {
                            Intent i = new Intent(getBaseContext(),Results.class);
                            i.putExtra("Type",query);
                            startActivity(i);
                        }
                    });

                    h.setC1Listener(new HomeAdapter.C1Listener(){

                        @Override
                        public void onClick(View v, int pos) {
                            Intent i = new Intent(getBaseContext(),CommentAct.class);
                            i.putExtra("Key",s4.get(pos).toString());
                            startActivity(i);
                        }
                    });


//                    h.setLikClickListener(new HomeAdapter.LikClickListener(){
//
//                        @Override
//                        public void onClick(View v, int pos) {
//                            DatabaseReference d = FirebaseDatabase.getInstance().getReference();
//                            d.child("posts").child(s4.get(pos).toString()).setValue(null);
//
//
//                        }
//                    });



// Now set the properties of the LinearLayoutManager
                    Log.e("Ended","True");

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }


            });
            return null;
        }


        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);


        }
    }




}



