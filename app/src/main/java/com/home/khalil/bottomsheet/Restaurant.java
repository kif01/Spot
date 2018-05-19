package com.home.khalil.bottomsheet;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.hujiaweibujidao.wava.Techniques;
import com.github.hujiaweibujidao.wava.YoYo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class Restaurant extends AppCompatActivity {
    private String phone;
    private boolean flag=false;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    ImageButton like;
   // String k;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);


        final String name = getIntent().getStringExtra("name");
        String type = getIntent().getStringExtra("type");
        String cuisine = getIntent().getStringExtra("cuisine");
        phone = getIntent().getStringExtra("phone");
        String area = getIntent().getStringExtra("area");
        String image = getIntent().getStringExtra("image");
        double rate = getIntent().getDoubleExtra("rate", 0);
        String time = getIntent().getStringExtra("time");
        final String id=getIntent().getStringExtra("id");

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Likes");

        ImageView restImage = (ImageView) findViewById(R.id.restaurant_image);
        RatingBar rating = (RatingBar) findViewById(R.id.restaurant_rate);
        TextView restName = (TextView) findViewById(R.id.restaurant_name);
        TextView restType = (TextView) findViewById(R.id.restaurant_type);
        TextView restCuisine = (TextView) findViewById(R.id.restaurant_cuisine);
        TextView restArea = (TextView) findViewById(R.id.restaurant_location);
        TextView restTime = (TextView) findViewById(R.id.restaurant_time);
        final Button restPhone = (Button) findViewById(R.id.restaurant_phone);

        like = (ImageButton) findViewById(R.id.restaurant_like);

        ImageButton btnVR= (ImageButton) findViewById(R.id.btn_vr);
        btnVR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(Restaurant.this,VrActivity.class);
                startActivity(i);
            }
        });

        if (image.equals("")) {
            restImage.setImageResource(R.drawable.restaurants3);
        } else {
            Picasso.with(Restaurant.this).load(image).into(restImage);
        }
        rating.setRating((float) rate);
        restName.setText(name);
        restType.setText(type);
        restCuisine.setText(cuisine);
        restPhone.setText(phone);
        restArea.setText(area);
        restTime.setText(time);

        setLikebtn(name);

        Button offers = (Button) findViewById(R.id.offers);
        Button menus = (Button) findViewById(R.id.menus);

        offers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Restaurant.this, OfferActivity.class);
                startActivity(i);
            }
        });

        menus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Restaurant.this, MenuActivity.class);
                startActivity(i);
            }
        });


        restPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                startActivity(intent);
            }
        });


        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    flag=true;

                    // k = mDatabase.push().getKey();
                    //mDatabase.child(name).child(mAuth.getCurrentUser().getUid()).setValue(name);
                    mDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (flag) {

                                if (dataSnapshot.child(name).hasChild(mAuth.getCurrentUser().getUid())) {


                                    mDatabase.child(name).child(mAuth.getCurrentUser().getUid()).removeValue();
                                    YoYo.with(Techniques.BounceIn).duration(700).playOn(like);
                                    like.setImageResource(R.drawable.ic_like_default);
                                    flag = false;

                                } else {
                                    mDatabase.child(name).child(mAuth.getCurrentUser().getUid()).setValue(id);
                                    YoYo.with(Techniques.BounceIn).duration(700).playOn(like);
                                    like.setImageResource(R.drawable.ic_like_fill);
                                    flag = false;
                                }
                            }
                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    // YoYo.with(Techniques.BounceIn).duration(700).playOn(like);
                    // like.setImageResource(R.drawable.ic_like_fill);
                    //flag = true;

                /*} else {
                    YoYo.with(Techniques.BounceIn).duration(700).playOn(like);
                    like.setImageResource(R.drawable.ic_like_default);
                    mDatabase.child(name).child(mAuth.getCurrentUser().getUid()).removeValue();
                    flag = false;

                }*/



            }
        });


    }

    private void setLikebtn(final String k){
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(k).hasChild(mAuth.getCurrentUser().getUid())){
                    like.setImageResource(R.drawable.ic_like_fill);
                }else{
                    like.setImageResource(R.drawable.ic_like_default);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
