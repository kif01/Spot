package com.home.khalil.bottomsheet;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.sothree.slidinguppanel.SlidingUpPanelLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Random;


import de.hdodenhof.circleimageview.CircleImageView;
import qiu.niorgai.StatusBarCompat;

import static com.home.khalil.bottomsheet.RecyclerViewCategoryAdapter.CategoryViewHolder.items;
import static com.home.khalil.bottomsheet.RecyclerViewCategoryAdapter.CategoryViewHolder.prefs;

public class MainActivity extends AppCompatActivity implements
        GoogleMap.OnMarkerDragListener,
        GoogleMap.OnCameraMoveStartedListener,
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnCameraMoveCanceledListener,
        GoogleMap.OnCameraIdleListener,  OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {


    //public static SmallBang mSmallBang;
    private GridLayoutManager sglm;
    private RelativeLayout view;
    private SlidingUpPanelLayout mLayout;
    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    static double lat;
    static double lon;
    private FloatingActionButton currentLocation;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    private Hashtable<String, Integer> markers;
    private ArrayList<Marker> pointers;
    CircleImageView profilePic;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String american ="1";
    private String cafe ="1";
    private String chinese ="1";
    private String dessert ="1";
    private String fastfood ="1";
    private String italian ="1";
    private String lebanese ="1";
    private String sushi ="1";
    private DatabaseReference mDatabase2;
    private ImageView shape;
    List<Category>listViewItems;
    RecyclerViewCategoryAdapter rvca;
    RecyclerView recyclerView;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    private String jsonResponse;
    private HashMap<String,Shop> map=new HashMap();

    public View mOriginalContentView;

    private RequestQueue requestQueue;
    private VolleySingleton volleySingleton;
    public static ArrayList<Shop> shopList = new ArrayList<>();

    private StaggeredGridLayoutManager grid;

    private String prefLink="";





    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }




       /* LocationManager_check locationManagerCheck = new LocationManager_check(
                this);
        Location location = null;

        if(locationManagerCheck.isLocationServiceAvailable()){

            if (locationManagerCheck.getProviderType() == 1)
                location = locationManagerCheck.locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            else if (locationManagerCheck.getProviderType() == 2)
                location = locationManagerCheck.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }else{
            locationManagerCheck .createLocationServiceError(MainActivity.this);
        }*/

        displayLocationSettingsRequest(this);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        mDatabase.keepSynced(true);



        mDatabase2 = FirebaseDatabase.getInstance().getReference();
        mDatabase2.keepSynced(true);
        //mAuth = FirebaseAuth.getInstance();
        user=FirebaseAuth.getInstance().getCurrentUser();

        pointers=new ArrayList<>();


        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_category);
        recyclerView.setHasFixedSize(true);
        //sglm = new GridLayoutManager(this,);//GridLayoutManager(getActivity(),3);
        grid= new StaggeredGridLayoutManager(2,0);

        recyclerView.setLayoutManager(grid);

        shape=(ImageView) findViewById(R.id.shape);


//        listViewItems= new ArrayList<Category>();



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

       // mSmallBang = SmallBang.attach2Window(this);

        final FloatingActionButton click = (FloatingActionButton) findViewById(R.id.click);
        final FloatingActionButton help= (FloatingActionButton) findViewById(R.id.help);
        currentLocation = (FloatingActionButton) findViewById(R.id.location);
        profilePic=(CircleImageView) findViewById(R.id.profile);

       Uri photo=user.getPhotoUrl();

        Picasso.with(MainActivity.this).load(photo).into(profilePic);


        // dim=findViewById(R.id.dim);
        //collapse= (ImageView) findViewById(R.id.collapse);

        mLayout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        markers = new Hashtable<String, Integer>();

        volleySingleton = VolleySingleton.getInstance(this);
        requestQueue = volleySingleton.getRequestQueue();



        StatusBarCompat.translucentStatusBar(this);

//        Log.d("heyo",g);
        Log.d("heyo", SaveSharedPreference.getAmerican(MainActivity.this));
        fastfood =SaveSharedPreference.getFastFood(MainActivity.this);
        dessert =SaveSharedPreference.getDessert(MainActivity.this);
        chinese =SaveSharedPreference.getChinese(MainActivity.this);
        american =SaveSharedPreference.getAmerican(MainActivity.this);
        cafe =SaveSharedPreference.getCafe(MainActivity.this);
        italian =SaveSharedPreference.getItalian(MainActivity.this);
        lebanese =SaveSharedPreference.getLebanese(MainActivity.this);
        sushi =SaveSharedPreference.getSushi(MainActivity.this);



       List<Category> categories = getListItemData();
        rvca= new RecyclerViewCategoryAdapter(MainActivity.this, categories);
        rvca.setHasStableIds(true);
        recyclerView.setAdapter(rvca);




        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.ANCHORED);

            }
        });
        mLayout.setFadeOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

            }
        });

        currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentLocation.hide();


                if (ActivityCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getBaseContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                LocationManager locationManager = (LocationManager)
                        getSystemService(Context.LOCATION_SERVICE);
                Criteria criteria = new Criteria();

                Location location = locationManager.getLastKnownLocation(locationManager
                        .getBestProvider(criteria, false));

                LatLng loc= new  LatLng(lat,lon);
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(loc)      // Sets the center of the map to Mountain View
                        .zoom(18)                   // Sets the zoom
                        // Sets the orientation of the camera to east
                        .tilt(90)                   // Sets the tilt of the camera to 90 degrees
                        .build();                   // Creates a CameraPosition from the builder
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                Log.d("tiso",mMap.getCameraPosition().toString());

            }
        });

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                i.putExtra("Name1",str2);
                i.putExtra("Rate1",s.getRate());
                i.putExtra("Image1",s.getImage());
                i.putExtra("Name2",str4);
                i.putExtra("Rate2",s2.getRate());
                i.putExtra("Image2",s2.getImage());*/
                Intent i=new Intent(MainActivity.this,ChooseActivity.class);
                startActivity(i);


            }
        });

        View mapView = mapFragment.getView();


        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)
            View mapCompass = ((View) mapView.findViewById(Integer.parseInt("4")).getParent());

            mapCompass.setPadding(20,450,0,0);




            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom

            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
            layoutParams.setMargins(00, 130, 0, 0);
        }

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Toast.makeText(MainActivity.this,SaveSharedPreference.getPrefLink(MainActivity.this),Toast.LENGTH_LONG).show();
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));

            }
        });

        Button c= (Button) findViewById(R.id.pref_done);
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fastfood =SaveSharedPreference.getFastFood(MainActivity.this);
                dessert =SaveSharedPreference.getDessert(MainActivity.this);
                chinese =SaveSharedPreference.getChinese(MainActivity.this);
                american =SaveSharedPreference.getAmerican(MainActivity.this);
                cafe =SaveSharedPreference.getCafe(MainActivity.this);
                italian =SaveSharedPreference.getItalian(MainActivity.this);
                lebanese =SaveSharedPreference.getLebanese(MainActivity.this);
                sushi =SaveSharedPreference.getSushi(MainActivity.this);
               prefLink="";
                if(american.equals("2"))
                    prefLink+="/american";
                if(cafe.equals("2"))
                    prefLink+="/cafe";
                if(chinese.equals("2"))
                    prefLink+="/chinese";
                if(dessert.equals("2"))
                    prefLink+="/dessert";
                if(fastfood.equals("2"))
                    prefLink+="/fastfood";
                if(italian.equals("2"))
                    prefLink+="/italian";
                if(lebanese.equals("2"))
                    prefLink+="/lebanese";
                if(sushi.equals("2"))
                    prefLink+="/sushi";
                Log.d("ahhg",prefs.toString());
               // Log.d("axx",prefs.get("american"));
                //listViewItems= new ArrayList<Category>();
                mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

               writePrefs(user.getUid(),prefs.get("american"),prefs.get("cafe"),prefs.get("chinese"),prefs.get("dessert"),prefs.get("fastfood"),prefs.get("italian"),prefs.get("lebanese"),prefs.get("sushi"));
                Toast.makeText(MainActivity.this,"Your interests have been updated",Toast.LENGTH_SHORT).show();

               /* for (Map.Entry<Integer, String> entry : items.entrySet())
                {
                    prefLink+=entry.getValue();
                }*/
                SaveSharedPreference.setPrefLink(MainActivity.this,prefLink);

                //Toast.makeText(MainActivity.this,prefLink,Toast.LENGTH_LONG).show();
                mMap.clear();
                pointers= makeJsonArrayRequest("https://spot-159922.appspot.com/range/35.4832/33.899181"+SaveSharedPreference.getPrefLink(MainActivity.this));


            }
        });

        //makeJsonArrayRequest("http://20170228t122049-dot-spot-159922.appspot-preview.com/range/35.4812/33.892/");
       //double lo= 35.4832;
       // double la=33.8991;


       // makeJsonArrayRequest("http://20170228t122049-dot-spot-159922.appspot-preview.com/range/"+lo+"/"+la);



    }

    private void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        //Log.i(TAG, "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                       // Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(MainActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            //Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        //Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });
    }






public List<Category> getListItemData(){
    List<Category> listViewItems = new ArrayList<Category>();
        listViewItems.add(new Category("American", american));
        listViewItems.add(new Category("Cafe", cafe));
        listViewItems.add(new Category("Chinese", chinese));
        listViewItems.add(new Category("Dessert", dessert));
        listViewItems.add(new Category("Fastfood", fastfood));
        listViewItems.add(new Category("Italian", italian));
        listViewItems.add(new Category("Lebanese", lebanese));
        listViewItems.add(new Category("Sushi", sushi));

        return listViewItems;
    }




    @Override
    public void onBackPressed() {
        if (mLayout != null &&
                (mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED || mLayout.getPanelState() == SlidingUpPanelLayout.PanelState.ANCHORED)) {
            mLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this,R.raw.style_json));
        this.mMap.getUiSettings().setMyLocationButtonEnabled(false);
       double lo= 35.4832;
        double la=33.8991;


      //pointers= makeJsonArrayRequest("http://20170228t122049-dot-spot-159922.appspot-preview.com/range/"+lon+"/"+lat);
        Log.d("pointer",pointers.size()+"");

        final View v = getLayoutInflater().inflate(R.layout.custom_info_window, null);



        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker arg0) {
                String snip=arg0.getSnippet().toString();
                String[] snipSplit = snip.split("\\s+");
                //Toast.makeText(MainActivity.this,snipSplit[1].toString(),Toast.LENGTH_SHORT).show();
                Shop s=map.get(snipSplit[1]);
                Intent i=new Intent(MainActivity.this,Restaurant.class);
                i.putExtra("name",s.getName());
                i.putExtra("type",s.getType());
                i.putExtra("cuisine",s.getCuisine());
                i.putExtra("phone",s.getPhone());
                i.putExtra("rate",s.getRate());
                i.putExtra("area",s.getArea());
                i.putExtra("image",s.getImage());
                i.putExtra("time",s.getTime());
                i.putExtra("id",s.getId());
                startActivity(i);

                //Toast.makeText(MainActivity.this,s.getCuisine()+" "+s.getArea()+" "+s.getPhone()+" "+s.getRate()+" "+s.getType(),Toast.LENGTH_SHORT).show();

            }
        });
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker arg0) {

                //arg0.showInfoWindow();
                LatLng latLng = arg0.getPosition();

                String snip=arg0.getSnippet().toString();
                String[] snipSplit = snip.split("\\s+");
                CircleImageView img=(CircleImageView) v.findViewById(R.id.image);
                ImageView arrow= (ImageView) v.findViewById(R.id.arrow);
                if(snipSplit[2].equals("American")){
                    img.setBorderColor(Color.parseColor("#FFE082"));
                    arrow.setImageResource(R.drawable.ic_arrow_american);

                }else if (snipSplit[2].equals("Cafe")){
                    img.setBorderColor(Color.parseColor("#FFD54F"));
                    arrow.setImageResource(R.drawable.ic_arrow_cafe);

                }else if (snipSplit[2].equals("Chinese")){
                    img.setBorderColor(Color.parseColor("#FFCA28"));
                    arrow.setImageResource(R.drawable.ic_arrow_chinese);

                }else if (snipSplit[2].equals("Desserts")){
                    img.setBorderColor(Color.parseColor("#FFC107"));
                    arrow.setImageResource(R.drawable.ic_arrow_dessert);

                }else if (snipSplit[2].equals("Fastfood")){
                    img.setBorderColor(Color.parseColor("#FFB300"));
                    arrow.setImageResource(R.drawable.ic_arrow_fastfood);
                }else if (snipSplit[2].equals("Italian")){
                    img.setBorderColor(Color.parseColor("#FFA000"));
                    arrow.setImageResource(R.drawable.ic_arrow_italian);

                }else if (snipSplit[2].equals("Lebanese")){
                    img.setBorderColor(Color.parseColor("#FF8F00"));
                    arrow.setImageResource(R.drawable.ic_arrow_lebanese);
                }else if (snipSplit[2].equals("Sushi")){
                    img.setBorderColor(Color.parseColor("#FF6F00"));
                    arrow.setImageResource(R.drawable.ic_arrow_sushi);
                }
                if(snipSplit[0].equals("")){
                    //Picasso.with(MainActivity.this).load(snipSplit[0]).into(img);
                    img.setImageResource(R.drawable.restaurants3);
                }else {
                    Picasso.with(MainActivity.this).load(snipSplit[0]).placeholder(R.drawable.restaurants3).into(img);
                }
                Log.d("blabla",arg0.getSnippet());
                //img.setImageResource(markers.get(arg0.getId()));

               // TextView tvLat = (TextView) v.findViewById(R.id.tv_lat);

                // Getting reference to the TextView to set longitude
                TextView tvLng = (TextView) v.findViewById(R.id.tv_lng);

                // Setting the latitude
                // tvLat.setText("Latitude:" + latLng.latitude);
                String str=arg0.getTitle().toString();
                String[] splited = str.split("\\s+");
                String str2="";
                for(int i=0;i<splited.length-1;i++){
                    if(i==splited.length-2){
                        str2+=splited[i];
                        break;
                    }
                    str2+=splited[i]+" ";

                }
                Log.d("splited",str2);
                // Setting the longitude
                if(str2.length()>11) {
                    String[] splited2 = str2.split("\\s+");

                    //Toast.makeText(getContext(),splited[0],Toast.LENGTH_SHORT).show();
                    if(splited2.length==3) {
                        tvLng.setText(splited2[0] + " " + splited2[1]+"\n"+splited2[2]);
                    }else{
                        tvLng.setText(splited2[0] + "\n" + splited2[1]);
                    }
                }else {
                    tvLng.setText(str2);
                }
                //tvLng.setText(str2);
                return v;

                // Getting the position from the marker


                // Getting reference to the TextView to set latitude


                // Returning the view containing InfoWindow contents

            }



            @Override
            public View getInfoContents(Marker arg0) {
                return null;
            }
        });



        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {
            @Override
            public void onCameraMove() {


                // Log.d("ajaja",mMap.getCameraPosition().target.toString()+"!");
               currentLocation.show();

                LatLng lt= mMap.getCameraPosition().target;
                double a = lt.latitude;
                a=Math.floor(a * 100000) / 100000;
                double b= lt.longitude;
                b=Math.floor(b * 100000) / 100000;
                for(int i=0;i<pointers.size();i++){
                    double x= Math.pow(a-pointers.get(i).getPosition().latitude,2);
                    double y= Math.pow(b-pointers.get(i).getPosition().longitude,2);
                    double d=Math.sqrt(x+y);

                    if(d>=0.00009 && d<=0.0004){
                        pointers.get(i).showInfoWindow();
                        return;

                    }
                }
                // Log.d("ajaja",a+","+b);




            }

        });




        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest,  this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;


        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());






        //Place current location marker

        lat=location.getLatitude();
        lon=location.getLongitude();
        //pointers= makeJsonArrayRequest("http://20170228t122049-dot-spot-159922.appspot-preview.com/range/"+lon+"/"+lat);
        pointers= makeJsonArrayRequest("https://spot-159922.appspot.com/range/35.4832/33.899181"+SaveSharedPreference.getPrefLink(MainActivity.this));


        Log.d("youpi",lat+""+lon);
        /*MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mMap.addMarker(markerOptions);*/

        //move map camera

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)      // Sets the center of the map to Mountain View
                .zoom(18)                   // Sets the zoom
                               // Sets the orientation of the camera to east
                .tilt(90)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.setBuildingsEnabled(true);
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
       // mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        Log.d("tiso",mMap.getCameraPosition().toString()+"!");

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient,  this);
        }

    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }



    }

    private void writePrefs(String userId, String american, String cafe, String chinese, String dessert, String fastfood, String italian, String lebanese, String sushi){
        UserPref user= new UserPref(american,cafe,chinese,dessert,fastfood,italian,lebanese,sushi);
        DatabaseReference currentUserDB = mDatabase.child(userId).child("Prefs");
        currentUserDB.setValue(user);
    }




    @Override
    public void onCameraIdle() {
        Log.d("tiso2",mMap.getCameraPosition().toString());
    }

    @Override
    public void onCameraMoveCanceled() {

    }

    @Override
    public void onCameraMove() {


    }

    @Override
    public void onCameraMoveStarted(int reason) {
        if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
            Log.d("eyo","ajahaha");
            Toast.makeText(this, "The user gestured on the map2.",
                    Toast.LENGTH_SHORT).show();
        } else if (reason == GoogleMap.OnCameraMoveStartedListener
                .REASON_API_ANIMATION) {
            Toast.makeText(this, "The user tapped something on the map.",
                    Toast.LENGTH_SHORT).show();
        } else if (reason == GoogleMap.OnCameraMoveStartedListener
                .REASON_DEVELOPER_ANIMATION) {
            Toast.makeText(this, "The app moved the camera.",
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onMarkerDragStart(Marker marker) {

    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {

    }



    private ArrayList<Marker> makeJsonArrayRequest(String url) {
        final ArrayList<Marker> markers=new ArrayList<>();

      //  showpDialog();

         shopList=new ArrayList<>();

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

                                shopList.add(shop);
                                map.put(id,shop);

                            }

                            Log.d("suc",jsonResponse);
                            Log.d("sizee",shopList.size()+"");

                           // MarkerOptions markerOptions = new MarkerOptions();
                           // markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)).anchor(0.5f,0.5f);
                            for(int i=0;i<shopList.size();i++){
                                MarkerOptions markerOptions = new MarkerOptions();
                                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)).anchor(0.5f,0.5f);
                                LatLng loc= new LatLng(shopList.get(i).getLat(),shopList.get(i).getLon());
                               /* if(shopList.get(i).getCuisine().equals("American")){
                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)).anchor(0.5f,0.5f);
                                }else if(shopList.get(i).getCuisine().equals("Cafe")){
                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)).anchor(0.5f,0.5f);
                                }else if(shopList.get(i).getCuisine().equals("Chinese")){
                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)).anchor(0.5f,0.5f);
                                }else if(shopList.get(i).getCuisine().equals("Desserts")){
                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)).anchor(0.5f,0.5f);
                                }else if(shopList.get(i).getCuisine().equals("Fastfood")){
                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)).anchor(0.5f,0.5f);
                                }else if(shopList.get(i).getCuisine().equals("Italian")){
                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)).anchor(0.5f,0.5f);
                                }else if(shopList.get(i).getCuisine().equals("Lebanese")){
                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)).anchor(0.5f,0.5f);
                                }else if(shopList.get(i).getCuisine().equals("Sushi")){
                                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)).anchor(0.5f,0.5f);
                                }*/
                                Marker marker=mMap.addMarker(markerOptions.position(loc).title(shopList.get(i).getName()).snippet(shopList.get(i).getImage()+" "+shopList.get(i).getId()+" "+shopList.get(i).getCuisine()));

                                markers.add(marker);


                            }



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

        return markers;



        // Adding request to request queue
        //AppController.getInstance().addToRequestQueue(req);
    }
}
