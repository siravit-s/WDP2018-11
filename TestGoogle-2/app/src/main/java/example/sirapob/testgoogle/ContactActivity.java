package example.sirapob.testgoogle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
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

public class ContactActivity extends AppCompatActivity {

    private EditText sub_et,msg_et;
    private Button gmail_btn;

    // Initialize Google Variable
    public static final String TAG = "MenuActivity";
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 1;
    private FirebaseAuth mAuth;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        sub_et = findViewById(R.id.sendmail_sub_et);
        msg_et = findViewById(R.id.sendmail_mes_et);
        gmail_btn = findViewById(R.id.sendmail_btn);

        gmail_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipient = "avita@kkumail.com,surawitwiriya@kkumail.com,j_sirapob@kkumail.com";
                String subject = sub_et.getText().toString().trim();
                String message = msg_et.getText().toString().trim();

                SendEmail(recipient,subject,message);
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
                    return new IconicsDrawable(ctx).iconText(" ").backgroundColorRes(R.color.md_red_500).sizeDp(56);
                }

                //we use the default one for
                //DrawerImageLoader.Tags.PROFILE_DRAWER_ITEM.name()

                return super.placeholder(ctx, tag);
            }
        });
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

            Toast.makeText(this,"already loggedIn",Toast.LENGTH_SHORT).show();

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
                    ).withSelectedItem(5)
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            // do something with the clicked item :D
                            switch (position) {
                                case 1:
                                    Intent mainIntent = new Intent(ContactActivity.this, MainPageActivity.class);
                                    startActivity(mainIntent);
                                    return true;

                                case 2:
                                    Intent newsIntent = new Intent(ContactActivity.this, NewsActivity.class);
                                    startActivity(newsIntent);
                                    return true;

                                case 3:
                                    return false;

                                case 4:
                                    Intent devIntent = new Intent(ContactActivity.this, DevActivity.class);
                                    startActivity(devIntent);
                                    return true;
                                case 5:
                                    mAuth.signOut();
                                    mGoogleSignInClient.revokeAccess();
                                    account.isExpired();
                                    Intent logout_Intent = new Intent(ContactActivity.this, MainPageActivity.class);
                                    startActivity(logout_Intent);
                                    return true;
                            }
                            return false;
                        }
                    })
                    .build();
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
                    ).withSelectedItem(5)
                    .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                        @Override
                        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                            // do something with the clicked item :D
                            switch (position) {
                                case 0:
                                    Intent mainIntent = new Intent(ContactActivity.this, MainPageActivity.class);
                                    startActivity(mainIntent);
                                    return true;

                                case 1:
                                    Intent newsIntent = new Intent(ContactActivity.this, NewsActivity.class);
                                    startActivity(newsIntent);
                                    return true;

                                case 2:
                                    return false;

                                case 3:
                                    Intent devIntent = new Intent(ContactActivity.this, DevActivity.class);
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

    // -- Google Sign-in Method -- //
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
                            Intent i = new Intent(ContactActivity.this, MainPageActivity.class);
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

    private void SendEmail(String recipient, String subject, String message) {
        Intent sendmail = new Intent(Intent.ACTION_SEND);
        sendmail.setData(Uri.parse("mailto:"));
        sendmail.setType("text/plain");
        sendmail.putExtra(Intent.EXTRA_EMAIL, new String[] {recipient});
        sendmail.putExtra(Intent.EXTRA_SUBJECT, subject);
        sendmail.putExtra(Intent.EXTRA_TEXT,message);
        startActivity(sendmail);
    }

}
