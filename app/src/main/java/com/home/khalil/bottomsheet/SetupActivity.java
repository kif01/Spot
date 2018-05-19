package com.home.khalil.bottomsheet;

import android.content.Intent;
import android.graphics.Color;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;

import static com.home.khalil.bottomsheet.RecyclerViewCategoryAdapter.CategoryViewHolder.prefs;

public class SetupActivity extends AppCompatActivity {

    private GridLayoutManager sglm;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    //public static boolean flag=false;

    private String american ="1";
    private String cafe ="1";
    private String chinese ="1";
    private String dessert ="1";
    private String fastfood ="1";
    private String italian ="1";
    private String lebanese ="1";
    private String sushi ="1";
    private String prefLink="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        Toolbar tb = (Toolbar) findViewById(R.id.pref_toolbar);
        //tb.setPadding(0, getStatusBarHeight(), 0, 0);

        setSupportActionBar(tb);
        getSupportActionBar();
        tb.setTitle("Preferences");
        tb.setTitleTextColor(Color.parseColor("#8F2AC1"));

        user=FirebaseAuth.getInstance().getCurrentUser();

        mDatabase=FirebaseDatabase.getInstance().getReference().child("Users");




        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_category_setup);
        recyclerView.setHasFixedSize(true);

        sglm=new GridLayoutManager(this,2);//GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(sglm);

        List<Category> categories = getListItemData();
        RecyclerViewCategoryAdapter rvca=new RecyclerViewCategoryAdapter(this,categories);
        recyclerView.setAdapter(rvca);
    }

    private List<Category> getListItemData(){

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_pref, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection

        if (item.getItemId() == R.id.action_submit) {
            setLink();
            goToMain();
        }
                return super.onOptionsItemSelected(item);


        }

    private void goToMain() {
        //SignInActivity.flag=true;
        SaveSharedPreference.setPref(SetupActivity.this,"true");
        startActivity(new Intent(SetupActivity.this, MainActivity.class));
        finish();

    }

    public void setLink(){
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


        writePrefs(user.getUid(),prefs.get("american"),prefs.get("cafe"),prefs.get("chinese"),prefs.get("dessert"),prefs.get("fastfood"),prefs.get("italian"),prefs.get("lebanese"),prefs.get("sushi"));

        SaveSharedPreference.setPrefLink(SetupActivity.this,prefLink);

        //Toast.makeText(MainActivity.this,prefLink,Toast.LENGTH_LONG).show();
    }

    private void writePrefs(String userId, String american, String cafe, String chinese, String dessert, String fastfood, String italian, String lebanese, String sushi){
        UserPref user= new UserPref(american,cafe,chinese,dessert,fastfood,italian,lebanese,sushi);
        Log.d("lalalala","hey"+userId);
        DatabaseReference currentUserDB = mDatabase.child(userId).child("Prefs");
        currentUserDB.setValue(user);
    }


}
