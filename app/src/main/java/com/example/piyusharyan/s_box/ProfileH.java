package com.example.piyusharyan.s_box;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;

public class ProfileH extends AppCompatActivity {

    FirebaseListAdapter<Post> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_h);


        ListView l = (ListView)findViewById(R.id.qid);

        final DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        SharedPreferences sharedPref = getSharedPreferences("mypref", 0);
        String name = sharedPref.getString("name", "");

        TextView tt = (TextView) findViewById(R.id.name);
        tt.setText(name);

        String email = sharedPref.getString("id", "");

        URL profile_pic = null;
        try {
            profile_pic = new URL(
                    "https://graph.facebook.com/" + email
                            + "/picture?type=large");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Picasso.with(getBaseContext()).load(profile_pic.toString()).into(((ImageView)findViewById(R.id.pro)));

        mAdapter = new FirebaseListAdapter<Post>(this, Post.class,
                R.layout.lsit_item4, mDatabase.child("postsTrue").child(email)) {
            @Override
            protected void populateView(View view, Post chatMessage, int position) {
                ImageView i = (ImageView)view.findViewById(R.id.p1);

                Picasso.with(getBaseContext()).load(chatMessage.getPic()).into(i);
                ((TextView)view.findViewById(R.id.p2)).setText(chatMessage.getQuery());
            }
        };
        l.setAdapter(mAdapter);


        Button b = (Button) findViewById(R.id.friend);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getBaseContext(),FriendActivity.class));
            }
        });

    }


}

