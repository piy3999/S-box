package com.example.piyusharyan.s_box;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;

public class FriendActivity extends AppCompatActivity {

    FirebaseListAdapter<U> mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend);

        SharedPreferences sharedPref = getSharedPreferences("mypref", 0);

        final String email= sharedPref.getString("id", "null");

        final DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        ListView commentView = (ListView) findViewById(R.id.friendList);

        mAdapter = new FirebaseListAdapter<U>(this, U.class,
                R.layout.list_item3, mDatabase.child("friends").child(email)) {
            @Override
            protected void populateView(View view, U chatMessage, int position) {
                try {


                    URL profile_pic = new URL(
                            "https://graph.facebook.com/" + chatMessage.getmId().toString()
                                    + "/picture?type=large");
                    ImageView i = (ImageView) view.findViewById(R.id.p1);

                    Picasso.with(FriendActivity.this).load(profile_pic.toString()).into(i);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                TextView t = (TextView)view.findViewById(R.id.p2);
                t.setText(chatMessage.getmName());
            }
        };
        commentView.setAdapter(mAdapter);

    }
}
