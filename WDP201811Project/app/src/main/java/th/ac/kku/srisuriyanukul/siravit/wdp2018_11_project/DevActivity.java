package th.ac.kku.srisuriyanukul.siravit.wdp2018_11_project;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class DevActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ImageButton bigfb, sufb, sifb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);

        bigfb = findViewById(R.id.big_fb);
        sufb = findViewById(R.id.su_fb);
        sifb = findViewById(R.id.si_fb);

        bigfb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebookIntent = openFacebook(DevActivity.this, "100000911685337", "siravit.srisuriyanukul");
                startActivity(facebookIntent);
            }
        });
        sufb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebookIntent = openFacebook(DevActivity.this, "100003097625106", "surawit.wiriyasrisuwatthana.7");
                startActivity(facebookIntent);
            }
        });
        sifb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent facebookIntent = openFacebook(DevActivity.this, "100014103497896", "eaearthhh");
                startActivity(facebookIntent);
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId())
                {
                    case R.id.main_page:
                        Intent intent = new Intent(DevActivity.this, MainActivity.class);
                        menuItem.setChecked(true);
                        startActivity(intent);
                        return true;

                    case R.id.dev:
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        return true;
                }
                return false;
            }
        });

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent openFacebook(Context context, String fb_id, String fb_profile) {
        try{
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent(Intent.ACTION_VIEW,Uri.parse("fb://profile/" + fb_id));
        } catch (Exception e) {
            return new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.facebook.com/" + fb_profile));
        }
    }
}
