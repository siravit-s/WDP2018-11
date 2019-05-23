package example.sirapob.testgoogle;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
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
import com.google.firebase.database.DatabaseRegistrar;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import java.util.ArrayList;

public class DetailAcActivity extends AppCompatActivity {

    TextView title_etDe, desc_etDe, dateSt_etDe, dateEn_etDe, timeSt_etDe, place_etDe, joinText;
    LottieAnimationView addCalendar_btn, ggmap_btn;
    ImageView img_ivDe;
    Toolbar toolbar;

    // Initialize Google Variable
    private GoogleApiClient mGoogleApiClient;
    public static final String TAG = "MenuActivity";
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 1;
    private FirebaseAuth mAuth;
    private GoogleSignInAccount account;
    public final static String ID = "";
    public final static String NAME = "";
    private DrawerLayout drawerLayout;
    String keys;


    DatabaseReference getJoin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_ac);

        // Initialize NavigationBar UI
        drawerLayout = findViewById(R.id.drawer_layout);
        img_ivDe = findViewById(R.id.img_ivDe);

        joinText = findViewById(R.id.joinText);

        String title = getIntent().getExtras().getString("Ac_title");
        final String desc = getIntent().getExtras().getString("Ac_desc");
        String dateSt = getIntent().getExtras().getString("Ac_dateSt");
        final String dateEn =getIntent().getExtras().getString("Ac_dateEn");
        final String timeSt = getIntent().getExtras().getString("Ac_timeSt");
        final String timeEn = getIntent().getExtras().getString("Ac_timeEn");
        String place = getIntent().getExtras().getString("Ac_place");
        String imgUrl = getIntent().getExtras().getString("Ac_imgUrl");
        keys = getIntent().getExtras().getString("keys");

        title_etDe = findViewById(R.id.title_etDe);
        desc_etDe = findViewById(R.id.desc_etDe);
        dateSt_etDe = findViewById(R.id.dateSt_etDe);
        dateEn_etDe = findViewById(R.id.dateEn_etDe);
        timeSt_etDe = findViewById(R.id.timeSt_etDe);
        place_etDe = findViewById(R.id.place_etDe);

        title_etDe.setText(title);
        desc_etDe.setText(desc);
        dateSt_etDe.setText(dateSt);
        dateEn_etDe.setText(dateEn);
        timeSt_etDe.setText(timeSt + " - " + timeEn);
        place_etDe.setText(place);

        getJoin = FirebaseDatabase.getInstance().getReference("activities").child(keys).child("ev_join");

        getJoin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> uidAR = new ArrayList<>();
                for (DataSnapshot uid :dataSnapshot.getChildren()) {
                    String nameJoin = String.valueOf(uid.getValue(String.class));
                    uidAR.add(nameJoin);
                }
                joinText.setText("");
                for (int i = 0; i < uidAR.size(); i++) {
                    if (i == 0 ) {
                        joinText.append(uidAR.get(i));
                    } else {
                        joinText.append(" , " + uidAR.get(i));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Glide.with(DetailAcActivity.this).load(imgUrl).apply(new RequestOptions().override(800, 600)).into(img_ivDe);

        addCalendar_btn = findViewById(R.id.addCalendar_btn);
        ggmap_btn = findViewById(R.id.google_map);

        ggmap_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:?q=" + place_etDe.getText().toString()));
                startActivity(intent);
            }
        });

        mAuth = FirebaseAuth.getInstance();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("605422382642-g2ej4hd86u4kfekogca349qnd570shrn.apps.googleusercontent.com")
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // -- End of Google Check Login -- //

    }

    protected  void onStart() {
        super.onStart();

        // Check if Firebase user assigned in
        final FirebaseUser user = mAuth.getCurrentUser();
        // Check Google Account
        final GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if (user != null) {
            final String userName = user.getDisplayName();
            final String userEmail = user.getEmail();
            final  String userUID = user.getUid();
            final String timeSt = getIntent().getExtras().getString("Ac_timeSt");
            final String timeEn = getIntent().getExtras().getString("Ac_timeEn");

//            Toast.makeText(this,"already loggedIn",Toast.LENGTH_SHORT).show();

            // Initialize NavigationBar UI
            toolbar = (Toolbar) findViewById(R.id.toolbarMain);
            toolbar.setTitle("KKU WE-DO-IT");

            // Create the AccountHeader
            AccountHeader headerResult = new AccountHeaderBuilder()
                    .withActivity(this)
                    .withHeaderBackground(R.drawable.header)
                    .addProfiles(
                            new ProfileDrawerItem().withName(userName).withEmail(userEmail).withIcon(user.getPhotoUrl())
                    )
                    .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                        @Override
                        public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                            return false;
                        }
                    })
                    .build();

            //if you want to update the items at a later time it is recommended to keep it in a variable
            PrimaryDrawerItem mainpageItem = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.mainpageTxt);
            PrimaryDrawerItem newsItem = new PrimaryDrawerItem().withIdentifier(2).withName(R.string.newsTxt);
            PrimaryDrawerItem aboutItem = new PrimaryDrawerItem().withIdentifier(3).withName(R.string.aboutTxt);
            PrimaryDrawerItem logOutItem = new PrimaryDrawerItem().withIdentifier(4).withName(R.string.logoutTxt);
            PrimaryDrawerItem contactItem = new PrimaryDrawerItem().withIdentifier(5).withName(R.string.contactTxt);

            // create the drawer and remember the `Drawer` result object
            Drawer result = new DrawerBuilder()
                    .withActivity(this)
                    .withAccountHeader(headerResult)
                    .withToolbar(toolbar)
                    .addDrawerItems(
                            mainpageItem, newsItem, contactItem, aboutItem, logOutItem
                    )
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            // do something with the clicked item :D
                            switch (position) {
                                case 1:
                                    return false;

                                case 2:
                                    Intent newsIntent = new Intent(DetailAcActivity.this, NewsActivity.class);
                                    startActivity(newsIntent);
                                    return true;

                                case 3:
                                    Intent contactIntent = new Intent(DetailAcActivity.this, ContactActivity.class);
                                    startActivity(contactIntent);
                                    return true;

                                case 4:
                                    Intent devIntent = new Intent(DetailAcActivity.this, DevActivity.class);
                                    startActivity(devIntent);
                                    return true;
                                case 5:
                                    mAuth.signOut();
                                    mGoogleSignInClient.revokeAccess();
                                    account.isExpired();
                                    Intent logout_Intent = new Intent(DetailAcActivity.this, MainPageActivity.class);
                                    startActivity(logout_Intent);
                                    return true;
                            }
                            return false;
                        }
                    })
                    .build();

            addCalendar_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DetailAcActivity.this, AddActivity.class);
                    intent.putExtra("Acti_title",title_etDe.getText());
                    intent.putExtra("Acti_desc",desc_etDe.getText());
                    intent.putExtra("Acti_dateSt",dateSt_etDe.getText());
                    intent.putExtra("Acti_dateEn",dateEn_etDe.getText());
                    intent.putExtra("Acti_timeSt",timeSt);
                    intent.putExtra("Acti_timeEn",timeEn);
                    intent.putExtra("Acti_place",place_etDe.getText());
                    intent.putExtra(NAME,userEmail);
                    intent.putExtra("Acti_key", keys);
                    intent.putExtra("uid",userUID);
                    intent.putExtra("user", userName);
                    startActivity(intent);
                }
            });
        } else {
            // Initialize NavigationBar UI
            toolbar = (Toolbar) findViewById(R.id.toolbarMain);
            toolbar.setTitle("KKU WE-DO-IT");

            //if you want to update the items at a later time it is recommended to keep it in a variable
            PrimaryDrawerItem mainpageItem = new PrimaryDrawerItem().withIdentifier(1).withName(R.string.mainpageTxt);
            PrimaryDrawerItem newsItem = new PrimaryDrawerItem().withIdentifier(2).withName(R.string.newsTxt);
            PrimaryDrawerItem aboutItem = new PrimaryDrawerItem().withIdentifier(3).withName(R.string.aboutTxt);
            PrimaryDrawerItem logInItem = new PrimaryDrawerItem().withIdentifier(4).withName(R.string.loginTxt2);
            PrimaryDrawerItem contactItem = new PrimaryDrawerItem().withIdentifier(5).withName(R.string.contactTxt);

            // create the drawer and remember the `Drawer` result object
            Drawer result = new DrawerBuilder()
                    .withActivity(this)
                    .withToolbar(toolbar)
                    .addDrawerItems(
                            mainpageItem, newsItem, contactItem, aboutItem, logInItem
                    )
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            // do something with the clicked item :D
                            switch (position) {
                                case 0:
                                    return false;

                                case 1:
                                    Intent newsIntent = new Intent(DetailAcActivity.this, NewsActivity.class);
                                    startActivity(newsIntent);
                                    return true;

                                case 2:
                                    Intent contactIntent = new Intent(DetailAcActivity.this, ContactActivity.class);
                                    startActivity(contactIntent);
                                    return true;
                                case 3:
                                    Intent devIntent = new Intent(DetailAcActivity.this, DevActivity.class);
                                    startActivity(devIntent);
                                    return true;
                                case 4:
                                    signIn();
                                    return true;

                            }

                            return false;
                        }
                    })
                    .build();
            addCalendar_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signIn();
                }
            });
        }
    }
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    // -- CHECK SIGN_IN METHOD -- //
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            String userName = user.getDisplayName();
                            String userEmail = user.getEmail();
                            String userImage = user.getPhotoUrl().toString();

                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            Intent i = new Intent(DetailAcActivity.this, MainPageActivity.class);
                            startActivity(i);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Snackbar.make(findViewById(R.id.snackAuth), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            // updateUI(null);
                        }
                    }
                });
    }
}
