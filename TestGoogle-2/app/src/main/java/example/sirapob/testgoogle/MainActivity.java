package example.sirapob.testgoogle;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = "main Activity";
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 1;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    SignInButton signInButton;
    Button guessButton;
    Button signOutButton;
    ImageView userImg;

    private Context mCtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        guessButton = findViewById(R.id.btn_guess);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("605422382642-g2ej4hd86u4kfekogca349qnd570shrn.apps.googleusercontent.com")
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        final Intent signIn = new Intent(MainActivity.this, MainActivity.class);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();

            }
        });

        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j2menuIntent = new Intent(MainActivity.this, MainPageActivity.class);
                startActivity(j2menuIntent);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user assigned in (non-null) and Update UI accordingly.
        final FirebaseUser user = mAuth.getCurrentUser();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        final GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        updateUI(account);


        if (user != null) {

//            String userName = user.getDisplayName();
//            final String userEmail = user.getEmail();
//            userImg = (ImageView) findViewById(R.id.userImg2);
//            Picasso.with(this).load(user.getPhotoUrl()).into(userImg);

           // Toast.makeText(this,"already loggedIn",Toast.LENGTH_SHORT).show();

            Intent j2menuIntent = new Intent(MainActivity.this, MainPageActivity.class);
            startActivity(j2menuIntent);
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
                            FirebaseUser user = mAuth.getCurrentUser();
                            String userId = databaseReference.push().getKey();
                            String userName = user.getDisplayName();
                            String userEmail = user.getEmail();
                            String userImage = user.getPhotoUrl().toString();

                            Users users = new Users(userId,userName,userEmail,userImage);
                            databaseReference.child(userId).setValue(users);

                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            Intent j2menuIntent = new Intent(MainActivity.this, MainPageActivity.class);
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
}
