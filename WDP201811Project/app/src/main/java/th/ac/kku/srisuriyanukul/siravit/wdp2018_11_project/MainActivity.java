package th.ac.kku.srisuriyanukul.siravit.wdp2018_11_project;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Spinner spinner;
    private TextView large_tv;

    private Button add_event;
    private static final int REQUEST_CODE = 10;

    ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        spinner = findViewById(R.id.sn_event);
        adapter = ArrayAdapter.createFromResource(this,R.array.credit,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        large_tv = findViewById(R.id.name_cag);

        add_event = findViewById(R.id.add_event);

        add_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add = new Intent(MainActivity.this,AddActivity.class);
                startActivityForResult(add,REQUEST_CODE);
            }
        });



        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String credit = parent.getItemAtPosition(position).toString();
                large_tv.setText(credit);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId())
                {
                    case R.id.main_page:
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();
                        return true;

                    case R.id.dev:
                        Intent dev_Intent = new Intent(MainActivity.this, DevActivity.class);
                        startActivity(dev_Intent);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data.hasExtra("returnName")) {
                TextView resultName = findViewById(R.id.nameTV);
                resultName.setText(data.getExtras().getString("returnName"));
            }
            if (data.hasExtra("returnDate")) {
                TextView resultDate = findViewById(R.id.dateTV);
                resultDate.setText(data.getExtras().getString("returnDate"));
            }
            if (data.hasExtra("returnPlace")) {
                TextView resultPlace = findViewById(R.id.placeTV);
                resultPlace.setText(data.getExtras().getString("returnPlace"));
            }
            if (data.hasExtra("returnPhone")) {
                TextView resultPhone = findViewById(R.id.phoneTV);
                resultPhone.setText(data.getExtras().getString("returnPhone"));
            }
        }
    }
}

