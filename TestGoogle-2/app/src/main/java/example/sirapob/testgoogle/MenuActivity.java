package example.sirapob.testgoogle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class MenuActivity extends AppCompatActivity {

    private GoogleApiClient mGoogleApiClient;
    public static final String TAG = "MenuActivity";
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 1;
    private FirebaseAuth mAuth;
    private GoogleSignInAccount account;
    public final static String ID = "";
    public final static String NAME = "";

    Button signOutButton,addBtn,showBtn;
    TextView userNameTxt;
    TextView userEmailTxt;
    ImageView userImg;
    TextView testTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mAuth = FirebaseAuth.getInstance();

        signOutButton = (Button) findViewById(R.id.logout_btn2);
        addBtn = (Button) findViewById(R.id.addBtn);
        showBtn = (Button) findViewById(R.id.showBtn);

        userNameTxt = (TextView) findViewById(R.id.login_tv2);
        userEmailTxt = (TextView) findViewById(R.id.email_tv2);
        userImg = (ImageView) findViewById(R.id.userImg2);
        testTxt = (TextView) findViewById(R.id.debug_tv2);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("605422382642-g2ej4hd86u4kfekogca349qnd570shrn.apps.googleusercontent.com")
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    @Override
    protected  void onStart() {
        super.onStart();

        // Check if Firebase user assigned in
        final FirebaseUser user = mAuth.getCurrentUser();
        // Check Google Account
        final GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if (user != null) {
            String userName = user.getDisplayName();
            final String userEmail = user.getEmail();
            userImg = (ImageView) findViewById(R.id.userImg2);
            Picasso.with(this).load(user.getPhotoUrl()).into(userImg);

            Toast.makeText(this,"already loggedIn",Toast.LENGTH_SHORT).show();

            testTxt.setText("Logged in");
            userEmailTxt.setText(userEmail);
            userNameTxt.setText(userName);
            signOutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAuth.signOut();
                    mGoogleSignInClient.revokeAccess();
                    account.isExpired();
                    Intent gotoMainIntent = new Intent(MenuActivity.this,MainActivity.class);
                    startActivity(gotoMainIntent);
                }
            });
            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MenuActivity.this,AddActivity.class);
                    intent.putExtra(NAME,userEmail);
                    startActivity(intent);
                }
            });
            showBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MenuActivity.this,EventListActivity.class);
                    intent.putExtra(NAME,userEmail);
                    startActivity(intent);
                }
            });


        }
    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//        // Check if user assigned in (non-null) and Update UI accordingly.
//        final FirebaseUser user = mAuth.getCurrentUser();
//        // Check for existing Google Sign In account, if the user is already signed in
//        // the GoogleSignInAccount will be non-null.
//        final GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
////        updateUI(account);
//
//        final TextView debug_tv = (TextView) findViewById(R.id.debug_tv);
//        debug_tv.setText(getString(R.string.default_web_client_id));
//
//        if (user != null) {
//
//            String userName = user.getDisplayName();
//            String userEmail = user.getEmail();
//
//            userImg = (ImageView) findViewById(R.id.userImg2);
//
//            Picasso.with(this).load(user.getPhotoUrl()).into(userImg);
//
//            Toast.makeText(this,"already loggedIn",Toast.LENGTH_SHORT).show();
//            debug_tv.setText("Login");
//            userEmailTxt.setText(userEmail);
//            userNameTxt.setText(userName);
//            signOutButton = (Button) findViewById(R.id.logout_btn2);
//            signOutButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    mAuth.signOut();
//                    mGoogleSignInClient.revokeAccess();
//                    account.isExpired();
//                    Intent gotoMainIntent = new Intent(MenuActivity.this,MainActivity.class);
//                    startActivity(gotoMainIntent);
//                }
//            });
//
//
//        } else if (user == null) {
//        }
//    }




}
