package com.home.khalil.bottomsheet;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private Button signOut;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Uri photo;
    private CircleImageView profilePic;

    private ImageView status1;
    private ImageView status2;
    private ImageView status3;
    private ImageView status4;
    private ImageView status5;
    private ImageView status6;
    private ImageView status7;
    private ImageView status8;
    private String american ="";
    private String cafe ="";
    private String chinese ="";
    private String dessert ="";
    private String fastfood ="";
    private String italian ="";
    private String lebanese ="";
    private String sushi ="";
    private RelativeLayout layout1;
    private RelativeLayout layout2;
    private RelativeLayout layout3;
    private RelativeLayout layout4;
    private RelativeLayout layout5;
    private RelativeLayout layout6;
    private RelativeLayout layout7;
    private RelativeLayout layout8;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar tb = (Toolbar) findViewById(R.id.prof_toolbar);

        final TextView tvName= (TextView) findViewById(R.id.text_name);
        final TextView tvEmail= (TextView) findViewById(R.id.text_email);



        setSupportActionBar(tb);
        getSupportActionBar();
        tb.setTitle("\t\t"+"Profile");
        tb.setTitleTextColor(Color.parseColor("#8F2AC1"));
        setSupportActionBar(tb);

        ImageButton back= (ImageButton) findViewById(R.id.btn_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this,MainActivity.class);

                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                //finish();
            }
        });



        profilePic= (CircleImageView)findViewById(R.id.profile_pic);
        status1=(ImageView) findViewById(R.id.status1);
        status2=(ImageView) findViewById(R.id.status2);
        status3=(ImageView) findViewById(R.id.status3);
        status4=(ImageView) findViewById(R.id.status4);
        status5=(ImageView) findViewById(R.id.status5);
        status6=(ImageView) findViewById(R.id.status6);
        status7=(ImageView) findViewById(R.id.status7);
        status8=(ImageView) findViewById(R.id.status8);

        layout1=(RelativeLayout) findViewById(R.id.amerc);
        layout2=(RelativeLayout) findViewById(R.id.cafe);
        layout3=(RelativeLayout) findViewById(R.id.chinese);
        layout4=(RelativeLayout) findViewById(R.id.dessert);
        layout5=(RelativeLayout) findViewById(R.id.fastfood);
        layout6=(RelativeLayout) findViewById(R.id.italian);
        layout7=(RelativeLayout) findViewById(R.id.lebanese);
        layout8=(RelativeLayout) findViewById(R.id.sushi);

        fastfood =SaveSharedPreference.getFastFood(ProfileActivity.this);
        dessert =SaveSharedPreference.getDessert(ProfileActivity.this);
        chinese =SaveSharedPreference.getChinese(ProfileActivity.this);
        american =SaveSharedPreference.getAmerican(ProfileActivity.this);
        cafe =SaveSharedPreference.getCafe(ProfileActivity.this);
        italian =SaveSharedPreference.getItalian(ProfileActivity.this);
        lebanese=SaveSharedPreference.getLebanese(ProfileActivity.this);
        sushi=SaveSharedPreference.getSushi(ProfileActivity.this);





        setStatus();
        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status1Click(american);


            }
        });

        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status2Click(cafe);
            }
        });

        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status3Click(chinese);
            }
        });

        layout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status4Click(dessert);
            }
        });

        layout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status5Click(fastfood);
            }
        });

        layout6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status6Click(italian);
            }
        });

        layout7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status7Click(lebanese);
            }
        });


        layout8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status8Click(sushi);
            }
        });



        mAuth= FirebaseAuth.getInstance();
        mAuthListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // User is signed in

                    startActivity(new Intent(ProfileActivity.this, SignInActivity.class));
                    finish();
                }else if (user != null) {
                    // Name, email address, and profile photo Url
                    String name = user.getDisplayName();
                    String email = user.getEmail();
                    String photoUrl = String.valueOf(user.getPhotoUrl());
                     photo=user.getPhotoUrl();
                    //Toast.makeText(ProfileActivity.this,name+"\n"+email+"\n"+photoUrl, Toast.LENGTH_LONG).show();
                   // Log.d("picc",photo+"");
                    Picasso.with(ProfileActivity.this).load(photo).into(profilePic);
                    tvName.setText(name);
                    tvEmail.setText(email);


                    // Check if user's email is verified
                   // boolean emailVerified = user.isEmailVerified();

                    // The user's ID, unique to the Firebase project. Do NOT use this value to
                    // authenticate with your backend server, if you have one. Use
                    // FirebaseUser.getToken() instead.
                    //String uid = user.getUid();
                }

            }
        };
        signOut= (Button) findViewById(R.id.btn_sign_out);

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void setStatus1(){
        String str=SaveSharedPreference.getAmerican(ProfileActivity.this);
        if (str.equals("1")){
            status1.setImageResource(R.mipmap.sleep);

        }else if (str.equals("2")){
            status1.setImageResource(R.mipmap.love);
        }
    }

    private void setStatus2(){
        String str=SaveSharedPreference.getCafe(ProfileActivity.this);
        if (str.equals("1")){
            status2.setImageResource(R.mipmap.sleep);

        }else if (str.equals("2")){
            status2.setImageResource(R.mipmap.love);
        }
    }

    private void setStatus3(){
        String str=SaveSharedPreference.getChinese(ProfileActivity.this);
        if (str.equals("1")){
            status3.setImageResource(R.mipmap.sleep);

        }else if (str.equals("2")){
            status3.setImageResource(R.mipmap.love);
        }
    }

    private void setStatus4(){
        String str=SaveSharedPreference.getDessert(ProfileActivity.this);
        if (str.equals("1")){
            status4.setImageResource(R.mipmap.sleep);

        }else if (str.equals("2")){
            status4.setImageResource(R.mipmap.love);
        }
    }

    private void setStatus5(){
        String str=SaveSharedPreference.getFastFood(ProfileActivity.this);
        if (str.equals("1")){
            status5.setImageResource(R.mipmap.sleep);

        }else if (str.equals("2")){
            status5.setImageResource(R.mipmap.love);
        }
    }

    private void setStatus6(){
        String str=SaveSharedPreference.getItalian(ProfileActivity.this);
        if (str.equals("1")){
            status6.setImageResource(R.mipmap.sleep);

        }else if (str.equals("2")){
            status6.setImageResource(R.mipmap.love);
        }
    }

    private void setStatus7(){
        String str=SaveSharedPreference.getLebanese(ProfileActivity.this);
        if (str.equals("1")){
            status7.setImageResource(R.mipmap.sleep);

        }else if (str.equals("2")){
            status7.setImageResource(R.mipmap.love);
        }
    }

    private void setStatus8(){
        String str=SaveSharedPreference.getSushi(ProfileActivity.this);
        if (str.equals("1")){
            status8.setImageResource(R.mipmap.sleep);

        }else if (str.equals("2")){
            status8.setImageResource(R.mipmap.love);
        }
    }

    private void setStatus(){
        setStatus1();
        setStatus2();
        setStatus3();
        setStatus4();
        setStatus5();
        setStatus6();
        setStatus7();
        setStatus8();
    }

    private void status1Click(String str){
        if (str.equals("1")){
            Toast.makeText(ProfileActivity.this,"You have no interests in American",Toast.LENGTH_SHORT).show();

        }else if (str.equals("2")){
            Toast.makeText(ProfileActivity.this,"You have mega interests in American",Toast.LENGTH_SHORT).show();

        }

    }


    private void status2Click(String str){
        if (str.equals("1")){
            Toast.makeText(ProfileActivity.this,"You have no interests in Cafe",Toast.LENGTH_SHORT).show();

        }else if (str.equals("2")){
            Toast.makeText(ProfileActivity.this,"You have mega interests in Cafe",Toast.LENGTH_SHORT).show();

        }

    }

    private void status3Click(String str){
        if (str.equals("1")){
            Toast.makeText(ProfileActivity.this,"You have no interests in Chinese",Toast.LENGTH_SHORT).show();

        }else if (str.equals("2")){
            Toast.makeText(ProfileActivity.this,"You have mega interests in Chinese",Toast.LENGTH_SHORT).show();

        }

    }

    private void status4Click(String str){
        if (str.equals("1")){
            Toast.makeText(ProfileActivity.this,"You have no interests in Dessert",Toast.LENGTH_SHORT).show();

        }else if (str.equals("2")){
            Toast.makeText(ProfileActivity.this,"You have mega interests in Dessert",Toast.LENGTH_SHORT).show();

        }

    }

    private void status5Click(String str){
        if (str.equals("1")){
            Toast.makeText(ProfileActivity.this,"You have no interests in FastFood",Toast.LENGTH_SHORT).show();

        }else if (str.equals("2")){
            Toast.makeText(ProfileActivity.this,"You have mega interests in FastFood",Toast.LENGTH_SHORT).show();

        }

    }

    private void status6Click(String str){
        if (str.equals("1")){
            Toast.makeText(ProfileActivity.this,"You have no interests in Italian",Toast.LENGTH_SHORT).show();

        }else if (str.equals("2")){
            Toast.makeText(ProfileActivity.this,"You have mega interests in Italian",Toast.LENGTH_SHORT).show();

        }

    }
    private void status7Click(String str){
        if (str.equals("1")){
            Toast.makeText(ProfileActivity.this,"You have no interests in Lebanese",Toast.LENGTH_SHORT).show();

        }else if (str.equals("2")){
            Toast.makeText(ProfileActivity.this,"You have mega interests in Lebanese",Toast.LENGTH_SHORT).show();

        }

    }
    private void status8Click(String str){
        if (str.equals("1")){
            Toast.makeText(ProfileActivity.this,"You have no interests in Sushi",Toast.LENGTH_SHORT).show();

        }else if (str.equals("2")){
            Toast.makeText(ProfileActivity.this,"You have mega interests in Sushi",Toast.LENGTH_SHORT).show();

        }

    }


}
