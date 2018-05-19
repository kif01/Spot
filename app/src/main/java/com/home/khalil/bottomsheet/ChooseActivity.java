package com.home.khalil.bottomsheet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.github.hujiaweibujidao.wava.Techniques;
import com.github.hujiaweibujidao.wava.YoYo;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static com.home.khalil.bottomsheet.MainActivity.shopList;

public class ChooseActivity extends AppCompatActivity {

    TextView name1;
    TextView name2;
    ImageView image1;
    ImageView image2;
    TextView rate1;
    TextView rate2;
    private boolean flag=false;
    String n1;
    String n2;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private RequestQueue requestQueue;
    private VolleySingleton volleySingleton;

    private ArrayList<Shop> list;
    private String jsonResponse;
    Random rand;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        volleySingleton = VolleySingleton.getInstance(this);
        requestQueue = volleySingleton.getRequestQueue();

        String url= "https://spot-159922.appspot.com/range50/35.4832/33.899181";
        makeJsonArrayRequest(url);
        rand=new Random();


        name1= (TextView) findViewById(R.id.choose_name);
        name2= (TextView) findViewById(R.id.choose_name2);
        image1=(ImageView) findViewById(R.id.choose_image);
        image2=(ImageView) findViewById(R.id.choose_image2);
        rate1= (TextView) findViewById(R.id.choose_rate);
        rate2= (TextView) findViewById(R.id.choose_rate2);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid()).child("Choices");

        final CardView card1=(CardView) findViewById(R.id.card1);
        final CardView card2=(CardView) findViewById(R.id.card2);

        /* n1 = getIntent().getStringExtra("Name1");
        if(n1.contains("Dottore ")){
            n1="Dottore L'antica";
        }
        String img1 = getIntent().getStringExtra("Image1");
        double rating1 = getIntent().getDoubleExtra("Rate1", 0);

        n2 = getIntent().getStringExtra("Name2");
        if(n2.contains("Dottore ")){
            n2="Dottore L'antica";
        }
        String img2 = getIntent().getStringExtra("Image2");
        double rating2 = getIntent().getDoubleExtra("Rate2", 0);*/



       /* name1.setText(n1);
        if(img1.equals("")){
            image1.setImageResource(R.drawable.restaurants3);
        }else {
            Picasso.with(ChooseActivity.this).load(img1).into(image1);
        }
        rate1.setText(Double.toString(rating1));

        name2.setText(n2);
        if(img2.equals("")){
            image2.setImageResource(R.drawable.restaurants3);
        }else {
            Picasso.with(ChooseActivity.this).load(img2).into(image2);
        }
        rate2.setText(Double.toString(rating2));*/
      // firstTime();

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("triplex","2: "+name1.getText().toString());

                updateData(name1.getText().toString());
                YoYo.with(Techniques.BounceIn).duration(1000).playOn(card1);
                random();

                /*Random rand = new Random();
                int v1 = rand.nextInt(shopList.size());
                Shop s= shopList.get(v1);


                int v2 = rand.nextInt(shopList.size());
                if(v2==v1){
                    v2=rand.nextInt(shopList.size());;
                }
                Shop s2=shopList.get(v2);

                String str=s.getName();
                String[] splited = str.split("\\s+");
                String str2="";
                for(int j=0;j<splited.length-1;j++){
                    if(j==splited.length-2){
                        str2+=splited[j];
                        break;
                    }
                    str2+=splited[j]+" ";

                }

                String str3=s2.getName();
                String[] splited2 = str3.split("\\s+");
                String str4="";
                for(int j=0;j<splited2.length-1;j++){
                    if(j==splited2.length-2){
                        str4+=splited2[j];
                        break;
                    }
                    str4+=splited2[j]+" ";

                }


                updateUIcard1(str2,s.getRate(),s.getImage(),str4,s2.getRate(),s2.getImage());*/
            }
        });

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Log.d("triplex","2: "+name2.getText().toString());
                updateData(name2.getText().toString());

                YoYo.with(Techniques.BounceIn).duration(1000).playOn(card2);
                random();


               /* Random rand = new Random();
                int v1 = rand.nextInt(shopList.size());
                Shop s= shopList.get(v1);


                int v2 = rand.nextInt(shopList.size());
                if(v2==v1){
                    v2=rand.nextInt(shopList.size());;
                }
                Shop s2=shopList.get(v2);

                String str=s.getName();
                String[] splited = str.split("\\s+");
                String str2="";
                for(int j=0;j<splited.length-1;j++){
                    if(j==splited.length-2){
                        str2+=splited[j];
                        break;
                    }
                    str2+=splited[j]+" ";

                }

                String str3=s2.getName();
                String[] splited2 = str3.split("\\s+");
                String str4="";
                for(int j=0;j<splited2.length-1;j++){
                    if(j==splited2.length-2){
                        str4+=splited2[j];
                        break;
                    }
                    str4+=splited2[j]+" ";

                }



                updateUIcard1(str2,s.getRate(),s.getImage(),str4,s2.getRate(),s2.getImage());*/

            }
        });






    }

    public void updateUIcard1(String n1,double rating1,String img1,String n2, double rating2, String img2){
        if(n1.contains("Dottore ")){
            n1="Dottore L'antica";
        }

        if(n2.contains("Dottore ")){
            n2="Dottore L'antica";
        }
        name1.setText(n1);
        if(img1.equals("")){
            image1.setImageResource(R.drawable.restaurants3);
        }else {
            Picasso.with(ChooseActivity.this).load(img1).into(image1);
        }
        rate1.setText(Double.toString(rating1));

        name2.setText(n2);
        if(img2.equals("")){
            image2.setImageResource(R.drawable.restaurants3);
        }else {
            Picasso.with(ChooseActivity.this).load(img2).into(image2);
        }
        rate2.setText(Double.toString(rating2));

    }

    private void updateData(final String str){
        flag=true;
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(flag) {
                    if (dataSnapshot.hasChild(str)) {
                        HashMap<String, Long> map = new HashMap<>();
                        map = (HashMap<String, Long>) dataSnapshot.getValue();
                        long c=map.get(str);
                        c+=1;
                        mDatabase.child(str).setValue(c);


                        Log.d("countx", c +"");
                        flag=false;

                    } else {
                        mDatabase.child(str).setValue(1);
                        flag=false;
                    }
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private ArrayList<Shop> makeJsonArrayRequest(String url) {


        //  showpDialog();

       list=new ArrayList<>();

        final JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Log.d(TAG, response.toString());

                        try {
                            // Parsing json array response
                            // loop through each json object
                            jsonResponse = "";
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject element = (JSONObject) response
                                        .get(i);

                                String name = element.getString("r_name");
                                String url=element.getString("r_image");
                                String id=element.getString("r_id");
                                String phone=element.getString("r_tel");
                                String type= element.getString("r_type");
                                String time= element.getString("r_timing");
                                String r=  element.getString("rating");
                                String[] splited = r.split("\\s+");
                                double rate= Double.parseDouble(splited[1]);

                                String area=element.getString("area");
                                // JSONArray cuis=element.getJSONArray("cuisines");
                                JSONArray cuis= new JSONArray(element.getString("cuisines"));
                                // Log.d("nas",cuis.get(0).toString());
                                String category=cuis.get(0).toString();
                                double lat = element.getDouble("r_latitude");
                                lat=Math.floor(lat * 100000) / 100000;


                                double lon = element.getDouble("r_longitude");
                                lon=Math.floor(lon * 100000) / 100000;

                                Shop shop= new Shop(name,lon,lat,url,category,id,phone,type,rate,area,time);
                               /* shop.setName(name);
                                shop.setLat(lat);
                                shop.setLon(lon);
                                shop.setImage(url);
                                shop.setCuisine(category);
                                shop.setId(id);*/

                                jsonResponse += "Name: " + name + "\n\n";
                                jsonResponse += "Lat: " + lat + "\n\n";
                                jsonResponse += "Lon: " + lon + "\n\n";

                                list.add(shop);


                            }

                           // Log.d("suc",jsonResponse);
                            Log.d("sizee",list.size()+"");








                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(),
                                    "Error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(req);

        return list;



        // Adding request to request queue
        //AppController.getInstance().addToRequestQueue(req);
    }

    public void random(){
       // Random rand = new Random();
        try {
            int v1 = rand.nextInt(list.size());
            Shop s = list.get(v1);


            int v2 = rand.nextInt(list.size());
            if (v2 == v1) {
                v2 = rand.nextInt(list.size());
                ;
            }
            Shop s2 = list.get(v2);

            String str = s.getName();
        /*String[] splited = str.split("\\s+");
        String str2="";
        for(int j=0;j<splited.length-1;j++){
            if(j==splited.length-2){
                str2+=splited[j];
                break;
            }
            str2+=splited[j]+" ";

        }*/

            String str3 = s2.getName();
        /*String[] splited2 = str3.split("\\s+");
        String str4="";
        for(int j=0;j<splited2.length-1;j++){
            if(j==splited2.length-2){
                str4+=splited2[j];
                break;
            }
            str4+=splited2[j]+" ";*/

            updateUIcard1(str, s.getRate(), s.getImage(), str3, s2.getRate(), s2.getImage());
        }catch (IllegalArgumentException e){
            
        }

        }


    private void firstTime(){
        random();
    }
}
