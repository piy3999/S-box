package com.example.piyusharyan.s_box;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Explore extends AppCompatActivity {
    private DatabaseReference mDatabase;
    static final String TAG="PIYUSH";
    List<String> s= new ArrayList<>();
    RecyclerView r;
    MyAdapter a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);
         r = (RecyclerView)findViewById(R.id.rview);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        //mDatabase.child("Users").push().setValue(new Users("http://www.yourlogoresources.com/wp-content/uploads/2012/03/Food_Network_logo.png"));
        //mDatabase.child("Users").push().setValue(new Users("http://4.bp.blogspot.com/-8eMC4m4GWWs/Vla7LkcRTnI/AAAAAAAAF9U/n2_Rpa6gz_4/s1600/Baterai%2Bponsel.png"));

        mDatabase.child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {

                    Users u = postSnapshot.getValue(Users.class);
                    s.add(u.getName());
                    Log.e("PBPB",u.getName()+ " PP");
                }

                 a = new MyAdapter(getBaseContext(),s);
                r.setAdapter(a);
                r.setLayoutManager(new GridLayoutManager(getBaseContext(),2));
                a.setClickListener(new MyAdapter.ClickListener() {
                    @Override
                    public void onClick(View v, int pos) {
                        if(pos==0)
                            startActivity(new Intent(getBaseContext(),Cricket.class));
                        else if (pos==1)
                            startActivity(new Intent(getBaseContext(),Food.class));
                        else if (pos==2)
                            startActivity(new Intent(getBaseContext(),Mobile.class));

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

}
