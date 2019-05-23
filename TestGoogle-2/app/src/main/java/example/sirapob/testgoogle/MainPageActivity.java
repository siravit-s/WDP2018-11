package example.sirapob.testgoogle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.widget.PullRefreshLayout;
import com.bumptech.glide.Glide;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.AbstractDrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.mikepenz.materialdrawer.util.DrawerUIUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainPageActivity extends AppCompatActivity {

    // Initialize Google Variable
    private GoogleApiClient mGoogleApiClient;
    public static final String TAG = "MenuActivity";
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 1;
    private FirebaseAuth mAuth;
    private GoogleSignInAccount account;
    public final static String ID = "";
    public final static String NAME = "";

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private Spinner spinner;
    private TextView large_tv;
    private RecyclerView mRecytcleView;
    private final String Json_URL = "https://www.kku.ac.th/ikku/api/activities/services/topActivity.php";
    private RequestQueue requestQueue;
    private List<KKUActivities> activitieskku;

    String title_ev;
    ArrayAdapter<CharSequence> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

//        title_ev = getIntent().getExtras().getString("Ac_title");
//        Log.d("SUSSSSSS", title_ev);


        mRecytcleView = findViewById(R.id.recyclerV);
        large_tv = findViewById(R.id.name_cag);

        // Initialize NavigationBar UI
        toolbar = (Toolbar) findViewById(R.id.toolbarMain);
        toolbar.setTitle("KKU WE-DO-IT");
        setSupportActionBar(toolbar);


        spinner = findViewById(R.id.sn_event);
        adapter = ArrayAdapter.createFromResource(this,R.array.credit,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //initialize and create the image loader logic
        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder, String tag) {
                Glide.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);

            }

            @Override
            public void cancel(ImageView imageView) {
                Glide.with(imageView.getContext()).clear(imageView);
            }

            @Override
            public Drawable placeholder(Context ctx, String tag) {
                //define different placeholders for different imageView targets
                //default tags are accessible via the DrawerImageLoader.Tags
                //custom ones can be checked via string. see the CustomUrlBasePrimaryDrawerItem LINE 111
                if (DrawerImageLoader.Tags.PROFILE.name().equals(tag)) {
                    return DrawerUIUtils.getPlaceHolder(ctx);
                } else if (DrawerImageLoader.Tags.ACCOUNT_HEADER.name().equals(tag)) {
                    return new IconicsDrawable(ctx).iconText(" ").backgroundColorRes(com.mikepenz.materialdrawer.R.color.primary).sizeDp(56);
                } else if ("customUrlItem".equals(tag)) {
                    return new IconicsDrawable(ctx).iconText(" ").backgroundColorRes(com.mikepenz.materialdrawer.R.color.md_red_500).sizeDp(56);
                }

                //we use the default one for
                //DrawerImageLoader.Tags.PROFILE_DRAWER_ITEM.name()

                return super.placeholder(ctx, tag);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String credit = parent.getItemAtPosition(position).toString();
                large_tv.setText(credit);
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                ((TextView) parent.getChildAt(0)).setTextSize(30);


                if (position == 0) {
                    new FirebaseHelper().readActivities(new FirebaseHelper.DataStatus() {
                        @Override
                        public void DataIsLoaded(List<Activities> activities, List<String> keys) {
                            new RecycleView().setConfig(mRecytcleView,MainPageActivity.this, activities, keys);
                        }

                        @Override
                        public void DataIsinserted() {

                        }

                        @Override
                        public void DataIsUpdated() {

                        }

                        @Override
                        public void DataIsDeleted() {

                        }
                    });
                }

                else if (position == 1) {
                    activitieskku = new ArrayList<>();

                    JsonObjectRequest request = new JsonObjectRequest(Json_URL, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                JSONObject jsonObject;
                                JSONArray jsonArray = response.getJSONArray("activities");

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonObject = jsonArray.getJSONObject(i);
                                    KKUActivities kku_ac = new KKUActivities();
                                    kku_ac.setTitle(jsonObject.getString("title"));
                                    kku_ac.setImage(jsonObject.getString("image"));
                                    kku_ac.setPlace(jsonObject.getString("place"));
                                    kku_ac.setDateSt(jsonObject.getString("dateSt"));
                                    kku_ac.setLink(jsonObject.getString("url"));
                                    activitieskku.add(kku_ac);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            setuprecycleview(activitieskku);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });

                    requestQueue = Volley.newRequestQueue(MainPageActivity.this);
                    requestQueue.add(request);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
//
//        final ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
//        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);





//        addev = findViewById(R.id.add_ev);
//        addev.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainPageActivity.this,AddEventActivity.class);
//                startActivity(intent);
//            }
//        });

        mAuth = FirebaseAuth.getInstance();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("605422382642-g2ej4hd86u4kfekogca349qnd570shrn.apps.googleusercontent.com")
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // -- End of Google Check Login -- //


        drawerLayout = findViewById(R.id.drawer_layout);

        final PullRefreshLayout layout =  findViewById(R.id.swipeRefreshLayout);

        // listen refresh event
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                layout.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        layout.setRefreshing(false);
                    }
                }, 1200);
            }
        });

        // refresh complete

    }




    private void setuprecycleview(List<KKUActivities> kku_ac) {
        KKUAcRecyclerView adapter = new KKUAcRecyclerView(this, kku_ac);
        mRecytcleView.setLayoutManager(new LinearLayoutManager(this));
        mRecytcleView.setAdapter(adapter);
    }

    protected void onStart() {
        super.onStart();

        // Check if Firebase user assigned in
        final FirebaseUser user = mAuth.getCurrentUser();
        // Check Google Account
        final GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if (user != null) {
            String userName = user.getDisplayName();
            final String userEmail = user.getEmail();

//            Toast.makeText(this, "already loggedIn", Toast.LENGTH_SHORT).show();


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
                                    Intent newsIntent = new Intent(MainPageActivity.this, NewsActivity.class);
                                    startActivity(newsIntent);
                                    return true;

                                case 3:
                                    Intent contactIntent = new Intent(MainPageActivity.this, ContactActivity.class);
                                    startActivity(contactIntent);
                                    return true;

                                case 4:
                                    Intent devIntent = new Intent(MainPageActivity.this, DevActivity.class);
                                    startActivity(devIntent);
                                    return true;
                                case 5:
                                    mAuth.signOut();
                                    mGoogleSignInClient.revokeAccess();
                                    account.isExpired();
                                    Intent logout_Intent = new Intent(MainPageActivity.this, MainPageActivity.class);
                                    startActivity(logout_Intent);
                                    return true;
                            }
                            return false;
                        }
                    })
                    .build();


        } else {

//            // Initialize NavigationBar UI
//            toolbar = (Toolbar) findViewById(R.id.toolbarMain);
//            toolbar.setTitle("KKU WE-DO-IT");
//            setSupportActionBar(toolbar);


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
                                    Intent newsIntent = new Intent(MainPageActivity.this, NewsActivity.class);
                                    startActivity(newsIntent);
                                    return true;

                                case 2:
                                    Intent contactIntent = new Intent(MainPageActivity.this, ContactActivity.class);
                                    startActivity(contactIntent);
                                    return true;
                                case 3:
                                    Intent devIntent = new Intent(MainPageActivity.this, DevActivity.class);
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
        }


    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

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
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            Intent j2menuIntent = new Intent(MainPageActivity.this, MainPageActivity.class);
                            startActivity(j2menuIntent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Snackbar.make(findViewById(R.id.snackAuth), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            // updateUI(null);
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
//                title_ev = getIntent().getExtras().getString("Ac_title");
//                Log.d("SUSSSSSS", title_ev);
                return true;
            }
        });
        return true;
    }
}