package com.example.piyusharyan.s_box;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CommentAct extends AppCompatActivity {

    FirebaseListAdapter<CommentModel> mAdapter;

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPref = getSharedPreferences("mypref", 0);
        String name = sharedPref.getString("name", "");
        Log.e("PBPB",name.toString());
    }


    String email,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);


        final String Key =  getIntent().getExtras().get("Key").toString();

        final DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();

        ListView commentView = (ListView) findViewById(R.id.comment_list);

        mAdapter = new FirebaseListAdapter<CommentModel>(this, CommentModel.class,
                android.R.layout.two_line_list_item, mDatabase.child("comments").child(Key)) {
            @Override
            protected void populateView(View view, CommentModel chatMessage, int position) {
                ((TextView)view.findViewById(android.R.id.text1)).setText(chatMessage.getName());

                ((TextView)view.findViewById(android.R.id.text2)).setText(chatMessage.getMessage());
            }
        };
        commentView.setAdapter(mAdapter);



        Button b = (Button )findViewById(R.id.send);
        final LinearLayout r= (LinearLayout) findViewById(R.id.root);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                EditText e = (EditText)findViewById(R.id.e);

                SharedPreferences sharedPref = getSharedPreferences("mypref", 0);
                String name = sharedPref.getString("name", "");

                mDatabase.child("comments").child(Key).push().setValue(new CommentModel(name,e.getText().toString()));
                e.setText("");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.cleanup();
    }
}
