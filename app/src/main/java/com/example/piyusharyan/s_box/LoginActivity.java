package com.example.piyusharyan.s_box;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.internal.Constants;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText password;
    private String email;
    private String name;
    private View facebookLoginButton;
    private ProgressDialog mProgress;
    private  CallbackManager callbackManager;
    private LoginButton mLoginButton;
    private FirebaseDatabase baseref;

    private static final String TAG = LoginActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        FacebookSdk.sdkInitialize(this.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);

        final TextView t = (TextView)findViewById(R.id.sj);

        final LoginButton login_button        = (LoginButton) findViewById(R.id.login_button);

        login_button.setReadPermissions(Arrays.asList("public_profile","email","user_friends"));
        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>()
        {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                login_button.setVisibility(View.GONE);


                GraphRequest graphRequest   =   GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback()
                {
                    @Override
                    public void onCompleted(final JSONObject object, GraphResponse response)
                    {
                        Log.d("JSON", ""+response.getJSONObject().toString());

                        try
                        {



                            final String[] FR = new String[1];
                            new GraphRequest(
                                    AccessToken.getCurrentAccessToken(),
                                    "/" + object.getString("id") + "/friends",
                                    null,
                                    HttpMethod.GET,
                                    new GraphRequest.Callback() {
                                        public void onCompleted(GraphResponse response) {
                                            Log.e("response", "" + response);
                                            FR[0] = response.toString();

                                            Log.e("CHECK",FR[0]);



                                            try {
                                                name =   object.getString("name");
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            try {
                                                email = object.getString("email");
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                                            //mDatabase.child("Operator").child(email.toString().replace(".",",")).setValue(name);



                                            try {

                                                URL profile_pic = new URL(
                                                        "https://graph.facebook.com/" + object.getString("id").toString()
                                                                + "/picture?type=large");

                                                PU u = new PU(name,email,profile_pic.toString());

                                                mDatabase.child("Operator").child(object.getString("id")).setValue(u);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            } catch (MalformedURLException e) {
                                                e.printStackTrace();
                                            }


                                            JSONObject ob = response.getJSONObject();
                                            try {
                                                JSONArray a  = ob.getJSONArray("data");
                                                for(int i=0;i<a.length();i++)
                                                {
                                                    JSONObject B = (JSONObject) a.get(i);

                                                    String id = B.get("id").toString();
                                                   String namee = B.get("name").toString();

                                                    U u = new U(namee,id,"");

                                                   mDatabase.child("friends").child(object.getString("id")).child(id).setValue(u);
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            SharedPreferences sharedPref = getSharedPreferences("mypref", 0);

                                            //now get Editor
                                            SharedPreferences.Editor editor = sharedPref.edit();




                                            Log.e("F1",email+" "+name);
                                            //put your value
                                            editor.putString("name", name);
                                            editor.putString("email",email);
                                            try {
                                                editor.putString("id",object.getString("id"));
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            editor.commit();
                                            Intent  i = new Intent(getBaseContext(),Home.class).putExtra("name",name);
                                            i.putExtra("Response",FR[0]);

                                            startActivity(i);
                                            LoginManager.getInstance().logOut();

                                        }
                                    }
                            ).executeAsync();



                           }


                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,first_name,last_name,email");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();
            }

            @Override
            public void onCancel()
            {

            }

            @Override
            public void onError(FacebookException exception)
            {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    }


