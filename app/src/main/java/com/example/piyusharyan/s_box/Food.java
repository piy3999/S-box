package com.example.piyusharyan.s_box;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Food extends AppCompatActivity {

    boolean topflag=false;
    int val;
    int c=0;String email="";
    Set<String> sets = new HashSet<>();


    EditText e ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
        String url = "https://www.zomato.com/ncr/best-restaurants";

e =  (EditText) findViewById(R.id.edit);


        TextView b= (TextView) findViewById(R.id.go);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String t = e.getText().toString();

            }
        });


    }


    public void funda(View b){
        Intent i;
        String s = e.getText().toString();

        final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        ;


        String [] k = s.split("\\s+");
        for(String ss: k)
        {
            Log.e("PB",ss);
            sets.add(ss);
        }

        Post post = null;
        final String key = mDatabase.child("posts").push().getKey().toString();

        SharedPreferences sharedPref = getSharedPreferences("mypref", 0);
        final String email= sharedPref.getString("id", "");

        if(sets.contains("odi") && sets.contains("batsman"))
        {
            i = new Intent(getBaseContext(),Results.class);
            i.putExtra("Type","odi-batsman");
            post = new Post(s,"http://www.bluebirdnews.co.uk/wp-content/uploads/2016/03/cricket-ball-bat.jpg","odi-batsman",c,key);



            mDatabase.child("posts").child(email).child(key).setValue(post);

            startActivity(i);

        }else if(sets.contains("test") && sets.contains("bowlers"))
        {
            post = new Post(s,"http://www.bluebirdnews.co.uk/wp-content/uploads/2016/03/cricket-ball-bat.jpg","test-bowlers",c,key);



            mDatabase.child("posts").child(email).child(key).setValue(post);

            i = new Intent(getBaseContext(),Results.class);
            i.putExtra("Type","test-bowlers");
            startActivity(i);

        }
        else if (sets.contains("test") && sets.contains("batsman"))
        {
            post = new Post(s,"http://www.bluebirdnews.co.uk/wp-content/uploads/2016/03/cricket-ball-bat.jpg","test-batsman",c,key);



            mDatabase.child("posts").child(email).child(key).setValue(post);

            i = new Intent(getBaseContext(),Results.class);
            i.putExtra("Type","test-batsman");
            startActivity(i);
        }
        else if (sets.contains("odi") && sets.contains("bowlers"))
        {


            post = new Post(s,"http://www.bluebirdnews.co.uk/wp-content/uploads/2016/03/cricket-ball-bat.jpg","odi-bowlers",c,key);


            mDatabase.child("posts").child(email).child(key).setValue(post);

            i = new Intent(getBaseContext(),Results.class);
            i.putExtra("Type","odi-bowlers");
            startActivity(i);
        }

        mDatabase.child("postsTrue").child(email).child(key).setValue(post);



        final Post finalPost = post;
        mDatabase.child("friends").child(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    U pt =  postSnapshot.getValue(U.class);
                    mDatabase.child("posts").child(pt.getmId()).child(key).setValue(finalPost);

                }


            }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //  mDatabase.child("posts").child()
    }




}


