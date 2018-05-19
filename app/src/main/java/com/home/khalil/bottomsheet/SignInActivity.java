package com.home.khalil.bottomsheet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static java.security.AccessController.getContext;


public class SignInActivity extends AppCompatActivity {
    private SignInButton mGoogleBtn;
    private static final int RC_SIGN_IN=1;
   /* public static String american="1";
    public static String restaurants="3";
    public static String furniture="3";
    public static String fashion="2";
    public static String electronics="3";
    public static String sports="3";*/
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    Intent i;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private static final String TAG="Sign In Activity";
    ProgressDialog mProgressDialog;
    private DatabaseReference mDatabase2;
    //  public static boolean flag=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mDatabase2 = FirebaseDatabase.getInstance().getReference();
        mDatabase2.keepSynced(true);
        //mAuth = FirebaseAuth.getInstance();
        //user=FirebaseAuth.getInstance().getCurrentUser();

        mAuth=FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    /*if(flag==false) {
                        startActivity(new Intent(SignInActivity.this, SetupActivity.class));
                        Log.d("1",flag+"");
                        finish();
                    }else{
                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                        Log.d("1",flag+"here");
                        finish();
                    }*/

                    if(SaveSharedPreference.getPref(SignInActivity.this).length() == 0)
                    {
                        startActivity(new Intent(SignInActivity.this, SetupActivity.class));
                        finish();
                    }
                    else
                    {
                        i = new Intent(SignInActivity.this, MainActivity.class);
                       /* readDatafromUsers("electronics", user);
                        readDatafromUsers("fashion", user);
                        readDatafromUsers("furniture", user);
                        readDatafromUsers("american", user);
                        readDatafromUsers("restaurants", user);
                        readDatafromUsers("sports", user);*/
                        // Stay at the current activity.
                        startActivity(i);
                        //startActivity(new Intent(SignInActivity.this, MainActivity.class));
                       // Log.d("1",flag+"here");
                        finish();

                    }
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        mProgressDialog=new ProgressDialog(SignInActivity.this);
        mGoogleBtn= (SignInButton) findViewById(R.id.sign_in);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient=new GoogleApiClient.Builder(getApplicationContext()).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(SignInActivity.this,"Connection Error, please try again.", Toast.LENGTH_LONG).show();

            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();


        mGoogleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        showProgressDialog();
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());


                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        hideProgressDialog();
                    }
                });

    }

    public void showProgressDialog() {

        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Signing in.");
            mProgressDialog.setIndeterminate(true);
        }else{
            mProgressDialog.setMessage("Signing in.");
        }

        mProgressDialog.show();



    }

    public void hideProgressDialog() {

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
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

    private void readDatafromUsers(final String element, FirebaseUser user) {
        //List<Category> listViewItems = new ArrayList<Category>();
        //We are using the user to get his unique key and based on that key we get his information
        //listViewItems= new ArrayList<Category>();

        mDatabase2.child("Users").child(user.getUid()).child(element).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                if (element.equals("american")) {
                    i.putExtra("g",snapshot.getValue().toString());
                    // Category c= new Category("Gaming",american);
                    // if(listViewItems.contains(c)){

                    //}
                    // else {

                    // }


                   // Log.d("ajjaj",american);

                } else if (element.equals("electronics")) {
                    //electronics=snapshot.getValue().toString();


                    //Log.d("ajja",electronics);

                } else if (element.equals("fashion")) {
                   // fashion=snapshot.getValue().toString();


                } else if (element.equals("restaurants")) {
                   // restaurants=snapshot.getValue().toString();



                }else if (element.equals("furniture")) {
                    //furniture=snapshot.getValue().toString();



                }else if (element.equals("sports")) {
                   // sports=snapshot.getValue().toString();


                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if(getContext()!=null){
                    //Toast.makeText(getContext(), "Signed Out", Toast.LENGTH_SHORT).show();
                }else {

                }

            }

        });




    }





}
